package product

import (
	"net/http"
	"encoding/json"
	"io/ioutil"
	"time"
	"infrastructure"
)

func (repository *MongoRepository) GetAllProdutcs(r *http.Request) (interface{}, *infrastructure.AppError){
	prod, err := repository.FindAll()
	if err != nil {
		return nil, &infrastructure.AppError{err, "Error trying to get", 500}
	}
	return prod, nil
}

func (repository *MongoRepository) StoreProduct(r *http.Request) (interface{}, *infrastructure.AppError) {
	prod := Product{}
	b, _ := ioutil.ReadAll(r.Body)
	json.Unmarshal(b, &prod)
	prod.Date = time.Now()
	p, err := repository.Store(&prod)

	if err != nil {
		return nil, &infrastructure.AppError{err, "Error trying to save", 500}
	}
	return p, nil
}
