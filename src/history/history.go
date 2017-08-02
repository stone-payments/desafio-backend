package history

import (
	"time"
	"gopkg.in/mgo.v2/bson"
	"gopkg.in/mgo.v2"
	"errors"
	"purchase"
	"github.com/pborman/uuid"
	"memcached"
)

//History model structure
type History struct {
	HistoryId string
	ClientId string `json:"client_id"`
	PurchaseId string `json:"purchase_id"`
	Value	uint32 `json:"value"`
	Date	time.Time `json:"date"`
	FinalNumber	string `json:"card_number"`
}

func CreateLog(purchase purchase.Purchase) History {
	s :=purchase.CreditCard.CardNumber
	finalNumber := s[len(s)-4:]
	return History{
		uuid.New(),
		purchase.ClientId,
		purchase.PurchaseId,
		purchase.TotalToPay,
		time.Now(),
		finalNumber,
	}
}

type MongoRepository struct {
	db      string
	session *mgo.Session
}


func (r *MongoRepository) Store(history *History) (*History,error) {

	sess := r.session.Copy()
	defer sess.Close()

	c := sess.DB(r.db).C("History")

	if _, err := c.Upsert(bson.M{"historyid": history.HistoryId}, bson.M{"$set": history}); err != nil {
		return nil, err
	}

	memcached.Store("history_by_client_id_"+history.ClientId, history)
	memcached.Store("history_by_id_"+history.HistoryId, history)

	return history, nil
}


func (r *MongoRepository) FindByClientId(cId string) ([]*History, error) {
	var result []*History
	mkey := "history_by_client_id_"+cId
	memcached.Get(mkey, &result)
	if result != nil {
		return result, nil
	}

	sess := r.session.Copy()
	defer sess.Close()

	c := sess.DB(r.db).C("History")


	if err := c.Find(bson.M{"client_id": cId}).All(&result); err != nil {
		return nil, err
	}

	memcached.Store(mkey, result)

	return result, nil
}

var ErrUnknown = errors.New("unknown history")


func (r *MongoRepository) FindAll() ([]*History, error) {
	var result []*History
	mkey := "history"
	memcached.Get(mkey, &result)
	if result != nil {
		return result, nil
	}

	sess := r.session.Copy()
	defer sess.Close()

	c := sess.DB(r.db).C("History")

	if err := c.Find(bson.M{}).All(&result); err != nil {
		return nil, err
	}
	memcached.Store(mkey, result)

	return result, nil
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
