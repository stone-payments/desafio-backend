package history

import (
	"net/http"
	"purchase"
	"github.com/gorilla/mux"
	"github.com/gorilla/context"
)


func (repository *MongoRepository) CreateHistory(w http.ResponseWriter, r *http.Request, next http.HandlerFunc){
	p := context.Get(r, "purchase").(purchase.Purchase)
	h := CreateLog(p)
	repository.Store(&h);
	next(w,r)
}


func (repository *MongoRepository) GetHistoryByCl(w http.ResponseWriter, r *http.Request) (interface{}, error){
	id := mux.Vars(r)["clientId"]
	return repository.FindByClientId(id), nil
}
func (repository *MongoRepository) GetAll(w http.ResponseWriter, r *http.Request) (interface{}, error){
	return repository.FindAll(), nil
}