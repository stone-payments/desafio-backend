package history

import (
	"net/http"
	"purchase"
	"github.com/gorilla/mux"
	"github.com/gorilla/context"
	"infrastructure"
)


func (repository *HistoryRepository) CreateHistory(r *http.Request) (interface{}, *infrastructure.AppError){
	p := context.Get(r, "purchase").(purchase.Purchase)
	h := CreateLog(p)
	rp, err := repository.Store(&h)
	if err != nil {
		return nil, &infrastructure.AppError{err, "Error trying to get", 500}
	}
	return rp, nil
}


func (repository *HistoryRepository) GetHistoryByCl(r *http.Request) (interface{}, *infrastructure.AppError){
	id := mux.Vars(r)["clientId"]
	rp, err := repository.FindByClientId(id)
	if err != nil {
		return nil, &infrastructure.AppError{err, "Error trying to get", 500}
	}
	return rp, nil
}
func (repository *HistoryRepository) GetAll(r *http.Request) (interface{}, *infrastructure.AppError){
	rp, err := repository.FindAll()
	if err != nil {
		return nil, &infrastructure.AppError{err, "Error trying to get", 500}
	}
	return rp, nil
}