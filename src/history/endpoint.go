package history

import (
	"net/http"
	"purchase"
)


func (repository *MongoRepository) CreateHistory(w http.ResponseWriter, r *http.Request, next http.HandlerFunc){
	h := CreateLog(purchase.Extract(r))
	repository.Store(&h);
	next(w,r)
}
