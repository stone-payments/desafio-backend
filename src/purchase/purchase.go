package purchase

import (
	"github.com/pborman/uuid"
	"gopkg.in/mgo.v2/bson"
	"gopkg.in/mgo.v2"
	"errors"
)

//Purchase model structure
type Purchase struct {
	PurchaseId string `json:"purchase_id"`
	ClientId string `json:"client_id"`
	ClientName string `json:"client_name"`
	TotalToPay	uint32 `json:"total_to_pay"`
	CreditCard	CreditCard `json:"credit_card"`
}
//Purchase model structure
type CreditCard struct {
	CardNumber string `json:"card_number"`
	Value uint32 `json:"value"`
	Cvv uint32 `json:"cvv"`
	CardHolderName	string `json:"card_holder_name"`
	ExpDate	string `json:"exp_date"`
}


// Repository provides access a Purchase store.
type PurchaseRepository interface {
	Store(Purchase *Purchase)  (*Purchase, error)
}


func NextPurchaseID() string {
	return uuid.New()
}

type MongoRepository struct {
	db      string
	session *mgo.Session
}


func (r *MongoRepository) Store(purchase *Purchase) (*Purchase,error) {
	sess := r.session.Copy()
	defer sess.Close()

	c := sess.DB(r.db).C("Purchase")

	_, err := c.Upsert(bson.M{"purchaseId": purchase.PurchaseId}, bson.M{"$set": purchase})

	return purchase, err
}

var ErrUnknown = errors.New("unknown purchase")


// NewCargoRepository returns a new instance of a MongoDB cargo repository.
func NewPurchaseRepository(db string, session *mgo.Session) (*MongoRepository, error) {
	r := &MongoRepository{
		db:      db,
		session: session,
	}

	index := mgo.Index{
		Key:        []string{"purchaseId"},
		Unique:     true,
		DropDups:   true,
		Background: true,
		Sparse:     true,
	}

	sess := r.session.Copy()
	defer sess.Close()

	c := sess.DB(r.db).C("Purchase")

	if err := c.EnsureIndex(index); err != nil {
		return nil, err
	}

	return r, nil
}
