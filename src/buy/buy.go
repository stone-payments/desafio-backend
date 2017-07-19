package buy

import (
	"time"
	"github.com/pborman/uuid"
	"gopkg.in/mgo.v2/bson"
	"gopkg.in/mgo.v2"
	"errors"
)

type BuyId string

//Buy model structure
type Buy struct {
	BuyId BuyId
	ClientId string `json:"client_id"`
	ClientName string `json:"client_name"`
	TotalToPay	uint32 `json:"total_to_pay"`
	CreditCard	CreditCard `json:"credit_card"`
}
//Buy model structure
type CreditCard struct {
	CardNumber string `json:"card_number"`
	Value uint32 `json:"value"`
	Cvv uint8 `json:"cvv"`
	CardHolderName	string `json:"card_holder_name"`
	ExpDate	string `json:"exp_date"`
}


// Repository provides access a Buy store.
type BuyRepository interface {
	Store(Buy *Buy)  (*Buy, error)
}


func NextBuyID() BuyId {
	return BuyId(uuid.New())
}

type MongoRepository struct {
	db      string
	session *mgo.Session
}


func (r *MongoRepository) Store(buy *Buy) (*Buy,error) {
	sess := r.session.Copy()
	defer sess.Close()

	c := sess.DB(r.db).C("Buy")

	_, err := c.Upsert(bson.M{"buyId": buy.BuyId}, bson.M{"$set": buy})

	return buy, err
}

var ErrUnknown = errors.New("unknown buy")


// NewCargoRepository returns a new instance of a MongoDB cargo repository.
func NewBuyRepository(db string, session *mgo.Session) (*MongoRepository, error) {
	r := &MongoRepository{
		db:      db,
		session: session,
	}

	index := mgo.Index{
		Key:        []string{"buyId"},
		Unique:     true,
		DropDups:   true,
		Background: true,
		Sparse:     true,
	}

	sess := r.session.Copy()
	defer sess.Close()

	c := sess.DB(r.db).C("Buy")

	if err := c.EnsureIndex(index); err != nil {
		return nil, err
	}

	return r, nil
}
