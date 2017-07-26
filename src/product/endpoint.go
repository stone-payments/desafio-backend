package product

import (
	"net/http"
	"encoding/json"
	"io/ioutil"
	"time"
)

func (repository *MongoRepository) GetAllProdutcs(w http.ResponseWriter, r *http.Request) (interface{}, error){
	return repository.FindAll(), nil
}

func (repository *MongoRepository) StoreProduct(w http.ResponseWriter, r *http.Request) (interface{}, error) {
	prod := Product{}
	b, _ := ioutil.ReadAll(r.Body)
	json.Unmarshal(b, &prod)
	prod.Date = time.Now()
	return repository.Store(&prod)
}
