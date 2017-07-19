package buy

import (
	"net/http"
	"encoding/json"
	"io/ioutil"
)

func (repository *MongoRepository) StoreProduct(w http.ResponseWriter, r *http.Request) (interface{}, error) {
	prod := Buy{}
	b, _ := ioutil.ReadAll(r.Body)
	prod.BuyId = NextBuyID()
	json.Unmarshal(b, &prod)
	return repository.Store(&prod)
}
