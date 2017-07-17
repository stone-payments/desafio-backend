package product

import (
	"net/http"
	"github.com/gorilla/mux"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"gopkg.in/mgo.v2"
)

func MakeHandler(session *mgo.Session) http.Handler {

	repository, _ := NewProductRepository("GoChallenge", session)
	r := mux.NewRouter()

	r.HandleFunc("/starstore/products", toJson(repository.GetAllProdutcs)).Methods("GET")
	r.HandleFunc("/starstore/product", toJson(repository.StoreProduct)).Methods("POST")

	return r
}


func toJson(f func(w http.ResponseWriter, r *http.Request) (interface{}, error)) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		response, _ := f(w, r)
		res1B, _ := json.Marshal(response)
		fmt.Fprint(w, string(res1B))
	}
}

func (repository *MongoRepository) GetAllProdutcs(w http.ResponseWriter, r *http.Request) (interface{}, error){
	return repository.FindAll(), nil
}

func (repository *MongoRepository) StoreProduct(w http.ResponseWriter, r *http.Request) (interface{}, error) {
	prod := Product{}
	b, _ := ioutil.ReadAll(r.Body)
	json.Unmarshal(b, &prod)
	return repository.Store(&prod)
}
