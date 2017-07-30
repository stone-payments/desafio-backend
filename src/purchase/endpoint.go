package purchase

import (
	"net/http"
	"encoding/json"
	"io/ioutil"
	"github.com/gorilla/context"
	"infrastructure"
)

func (repository *MongoRepository) StorePurchase(r *http.Request) (interface{}, *infrastructure.AppError) {
	p := context.Get(r, "purchase").(Purchase)
	pur, err := repository.Store(&p)
	if err != nil {
		return nil, &infrastructure.AppError{err, "Error trying to save", 500}
	}
	return pur, nil
}

func (repository *MongoRepository) CreatePurchaseId(r *http.Request) (interface{}, *infrastructure.AppError){
	//creates a purchase id and input him at request.
	r.Header.Add("purchase_id", NextPurchaseID())
	context.Set(r, "purchase", Extract(r))
	return nil, nil
}

func Extract(r *http.Request) Purchase {
	prod := Purchase{}
	b, _ := ioutil.ReadAll(r.Body)
	prod.PurchaseId = r.Header.Get("purchase_id")
	json.Unmarshal(b, &prod)
	return prod;
}
