package client

import (
	"github.com/pborman/uuid"
	"gopkg.in/mgo.v2/bson"
	"gopkg.in/mgo.v2"
	"errors"
	"challengego/memcached"
	"challengego/infrastructure"
)

type ClientId string

//client model structure
type Client struct {
	ClientId	ClientId
	Name		string  `json:"name"`
	Username	string  `json:"username"`
	Password	string	`json:"password"`
	Salt		string
}

func NextClientID() ClientId {
	return ClientId(uuid.New())
}

type ClientRepository struct {
	*infrastructure.MongoRepository
}


func (r *ClientRepository) Store(client *Client) (*Client,error) {
	sess := r.Session.Copy()
	defer sess.Close()

	c := sess.DB(r.DB).C("Client")

	_, err := c.Upsert(bson.M{"clientid": client.ClientId}, bson.M{"$set": client})

	mkey := "client_by_id_"+string(client.ClientId)
	memcached.Store(mkey, client)


	return client, err
}

var ErrUnknown = errors.New("unknown client")

func (r *ClientRepository) Find(id ClientId) (*Client, error) {
	var result Client
	mkey := "client_by_id_"+string(id)
	memcached.Get(mkey, &result)
	if &result != nil {
		return &result, nil
	}
	sess := r.Session.Copy()
	defer sess.Close()

	c := sess.DB(r.DB).C("Client")

	if err := c.Find(bson.M{"clientid": id}).One(&result); err != nil {
		if err == mgo.ErrNotFound {
			return nil, ErrUnknown
		}
		return nil, err
	}
	memcached.Store(mkey, &result)
	memcached.Store("client_by_username_"+result.Username, &result)

	return &result, nil
}

func (r *ClientRepository) FindByUsername(username string) (*Client, error) {
	var result Client
	mkey := "client_by_username_"+username
	v := memcached.Get(mkey, &result)
	if v {
		return &result, nil
	}
	sess := r.Session.Copy()
	defer sess.Close()

	c := sess.DB(r.DB).C("Client")

	if err := c.Find(bson.M{"username": username}).One(&result); err != nil {
		if err == mgo.ErrNotFound {
			return nil, ErrUnknown
		}
		return nil, err
	}
	memcached.Store(mkey, &result)

	return &result, nil
}


// NewCargoRepository returns a new instance of a MongoDB cargo repository.
func NewClientRepository(repository *infrastructure.MongoRepository) (*ClientRepository, error) {
	r := &ClientRepository{repository}

	indexA := []mgo.Index{{
		Key:        []string{"clientid"},
		Unique:     true,
		DropDups:   true,
		Background: true,
		Sparse:     true,
	}, {
		Key:        []string{"username"},
		Unique:     true,
		DropDups:   true,
		Background: true,
		Sparse:     true,
	}}

	sess := r.Session.Copy()
	defer sess.Close()

	c := sess.DB(r.DB).C("Client")

	for _, index := range indexA {
		if err := c.EnsureIndex(index); err != nil {
			return nil, err
		}
	}

	return r, nil
}

