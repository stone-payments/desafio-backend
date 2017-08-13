package history

import (
	"time"
	"gopkg.in/mgo.v2/bson"
	"gopkg.in/mgo.v2"
	"errors"
	"challengego/purchase"
	"github.com/pborman/uuid"
	"challengego/memcached"
	"challengego/infrastructure"
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

type HistoryRepository struct {
	*infrastructure.MongoRepository
}


func (r *HistoryRepository) Store(history *History) (*History,error) {

	sess := r.Session.Copy()
	defer sess.Close()

	c := sess.DB(r.DB).C("History")

	if _, err := c.Upsert(bson.M{"historyid": history.HistoryId}, bson.M{"$set": history}); err != nil {
		return nil, err
	}

	memcached.Store("history_by_client_id_"+history.ClientId, history)
	memcached.Store("history_by_id_"+history.HistoryId, history)

	return history, nil
}


func (r *HistoryRepository) FindByClientId(cId string) ([]*History, error) {
	var result []*History
	mkey := "history_by_client_id_"+cId
	memcached.Get(mkey, &result)
	if result != nil {
		return result, nil
	}

	sess := r.Session.Copy()
	defer sess.Close()

	c := sess.DB(r.DB).C("History")


	if err := c.Find(bson.M{"client_id": cId}).All(&result); err != nil {
		return nil, err
	}

	memcached.Store(mkey, result)

	return result, nil
}

var ErrUnknown = errors.New("unknown history")


func (r *HistoryRepository) FindAll() ([]*History, error) {
	var result []*History
	mkey := "history"
	memcached.Get(mkey, &result)
	if result != nil {
		return result, nil
	}

	sess := r.Session.Copy()
	defer sess.Close()

	c := sess.DB(r.DB).C("History")

	if err := c.Find(bson.M{}).All(&result); err != nil {
		return nil, err
	}
	memcached.Store(mkey, result)

	return result, nil
}

func NewHistoryRepository(repository *infrastructure.MongoRepository) (*HistoryRepository, error) {
	r := &HistoryRepository{repository}

	index := mgo.Index{
		Key:        []string{"historyid"},
		Unique:     true,
		DropDups:   true,
		Background: true,
		Sparse:     true,
	}

	sess := r.Session.Copy()
	defer sess.Close()

	c := sess.DB(r.DB).C("History")

	if err := c.EnsureIndex(index); err != nil {
		return nil, err
	}

	return r, nil
}
