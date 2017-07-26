package purchase

import (
	"net/http"
	"encoding/json"
	"io/ioutil"
	"github.com/gorilla/context"
)

func (repository *MongoRepository) StorePurchase(w http.ResponseWriter, r *http.Request) (interface{}, error) {
	p := context.Get(r, "purchase").(Purchase)
	return repository.Store(&p)
}

func (repository *MongoRepository) CreatePurchaseId(w http.ResponseWriter, r *http.Request, next http.HandlerFunc) {

	//creates a purchase id and input him at request.
	r.Header.Add("purchase_id", NextPurchaseID())
	context.Set(r, "purchase", Extract(r))

	next(w, r)
}

func Extract(r *http.Request) Purchase {
	prod := Purchase{}
	b, _ := ioutil.ReadAll(r.Body)
	prod.PurchaseId = r.Header.Get("purchase_id")
	json.Unmarshal(b, &prod)
	return prod;
}
