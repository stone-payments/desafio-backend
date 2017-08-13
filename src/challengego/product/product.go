package product

import (
	"time"
	"github.com/pborman/uuid"
	"gopkg.in/mgo.v2/bson"
	"gopkg.in/mgo.v2"
	"errors"
	"challengego/memcached"
	"challengego/infrastructure"
)

type ProductId string

//product model structure
type Product struct {
	ProductId	ProductId
	Title       string `json:"title"`
	Price       float32 `json:"price"`
	Zipcode     uint16 `json:"zipcode"`
	Seller      string `json:"seller"`
	Thumbnailhd string `json:"thumbnailHd"`
	Date        time.Time `json:"date"`
}

func NextTrackingID() ProductId {
	return ProductId(uuid.New())
}

type ProductRepository struct {
	*infrastructure.MongoRepository
}


func (r *ProductRepository) Store(product *Product) (*Product,error) {
	sess := r.Session.Copy()
	defer sess.Close()

	c := sess.DB(r.DB).C("Product")

	_, err := c.Upsert(bson.M{"productid": product.ProductId}, bson.M{"$set": product})

	mkey := "product_by_id_"+string(product.ProductId)
	memcached.Store(mkey, product)


	return product, err
}

var ErrUnknown = errors.New("unknown product")

func (r *ProductRepository) Find(id ProductId) (*Product, error) {
	var result Product
	mkey := "product_by_id_"+string(id)
	memcached.Get(mkey, &result)
	if &result != nil {
		return &result, nil
	}
	sess := r.Session.Copy()
	defer sess.Close()

	c := sess.DB(r.DB).C("Product")

	if err := c.Find(bson.M{"productid": id}).One(&result); err != nil {
		if err == mgo.ErrNotFound {
			return nil, ErrUnknown
		}
		return nil, err
	}
	memcached.Store(mkey, &result)

	return &result, nil
}

func (r *ProductRepository) FindAll() ([]*Product, error) {
	var result []*Product
	mkey := "product"
	memcached.Get(mkey, &result)
	if result != nil {
		return result, nil
	}
	sess := r.Session.Copy()
	defer sess.Close()

	c := sess.DB(r.DB).C("Product")

	if err := c.Find(bson.M{}).All(&result); err != nil {
		return nil, err
	}

	memcached.Store(mkey, result)

	return result, nil
}

// NewCargoRepository returns a new instance of a MongoDB cargo repository.
func NewProductRepository(repository *infrastructure.MongoRepository) (*ProductRepository, error) {
	r := &ProductRepository{repository}

	index := mgo.Index{
		Key:        []string{"productid"},
		Unique:     true,
		DropDups:   true,
		Background: true,
		Sparse:     true,
	}

	sess := r.Session.Copy()
	defer sess.Close()

	c := sess.DB(r.DB).C("Product")

	if err := c.EnsureIndex(index); err != nil {
		return nil, err
	}

	return r, nil
}

