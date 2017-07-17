package history

import (
	"time"
	"github.com/pborman/uuid"
	"gopkg.in/mgo.v2/bson"
	"gopkg.in/mgo.v2"
	"errors"
)

type HistoryId string

//History model structure
type History struct {
	HistoryId HistoryId
	ClientId string `json:"client_id"`
	PurchaseId string `json:"purchase_id"`
	Value	uint32 `json:"value"`
	Date	time.Time `json:"date"`
	FinalNumber	uint8 `json:"card_number"`
}

// Repository provides access a History store.
type HistoryRepository interface {
	Store(History *History)  (*History, error)
	Find(id HistoryId) (*History, error)
	FindAll() []*History
}


func NextHistoryID() HistoryId {
	return HistoryId(uuid.New())
}

type MongoRepository struct {
	db      string
	session *mgo.Session
}


func (r *MongoRepository) Store(history *History) (*History,error) {
	sess := r.session.Copy()
	defer sess.Close()

	c := sess.DB(r.db).C("History")

	_, err := c.Upsert(bson.M{"historyid": history.HistoryId}, bson.M{"$set": history})

	return history, err
}

var ErrUnknown = errors.New("unknown history")

func (r *MongoRepository) Find(id HistoryId) (*History, error) {
	sess := r.session.Copy()
	defer sess.Close()

	c := sess.DB(r.db).C("History")

	var result History
	if err := c.Find(bson.M{"historyid": id}).One(&result); err != nil {
		if err == mgo.ErrNotFound {
			return nil, ErrUnknown
		}
		return nil, err
	}

	return &result, nil
}

func (r *MongoRepository) FindAll() []*History {
	sess := r.session.Copy()
	defer sess.Close()

	c := sess.DB(r.db).C("History")

	var result []*History
	if err := c.Find(bson.M{}).All(&result); err != nil {
		return []*History{}
	}

	return result
}

// NewCargoRepository returns a new instance of a MongoDB cargo repository.
func NewHistoryRepository(db string, session *mgo.Session) (*MongoRepository, error) {
	r := &MongoRepository{
		db:      db,
		session: session,
	}

	index := mgo.Index{
		Key:        []string{"historyid"},
		Unique:     true,
		DropDups:   true,
		Background: true,
		Sparse:     true,
	}

	sess := r.session.Copy()
	defer sess.Close()

	c := sess.DB(r.db).C("History")

	if err := c.EnsureIndex(index); err != nil {
		return nil, err
	}

	return r, nil
}
