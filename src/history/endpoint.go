package history

import (
	"net/http"
	"github.com/gorilla/mux"
	"encoding/json"
	"fmt"
	"io/ioutil"
	"gopkg.in/mgo.v2"
)

func MakeHandler(session *mgo.Session) http.Handler {

	repository, _ := NewHistoryRepository("GoChallenge", session)
	r := mux.NewRouter()
	r.HandleFunc("/starstore/history", toJson(repository.GetAllProdutcs)).Methods("GET")
	r.HandleFunc("/starstore/history/:clientId", toJson(repository.GetAllProdutcs)).Methods("GET")

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
