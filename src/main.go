package main

import (
	"github.com/gorilla/mux"
	"gopkg.in/mgo.v2"
	"product"
	"jwtauthorization"
	"github.com/codegangsta/negroni"
	"net/http"
	"fmt"
	"encoding/json"
	"purchase"
	"history"
	"infrastructure"
)



func main() {

	m := mux.NewRouter()

	session, err := mgo.Dial("localhost")
	if err != nil {
		panic(err)
	}
	defer session.Close()

	productRepository, _ := product.NewProductRepository("GoChallenge", session)
	buyRepository, _ := purchase.NewPurchaseRepository("GoChallenge", session)
	historyRepository, _ := history.NewHistoryRepository("GoChallenge", session)



	middlewareAuth := infrastructure.AppHandler(jwtauthorization.ValidateTokenMiddleware).Build()

	m.Handle("/starstore/auth/login", toJson(jwtauthorization.LoginHandler))
	m.Handle("/starstore/product", negroni.New(
		infrastructure.AppHandler(productRepository.StoreProduct).BuildResponse(),
	)).Methods("POST")

	m.Handle("/starstore/products", negroni.New(
		infrastructure.AppHandler(productRepository.GetAllProdutcs).BuildResponse(),
	)).Methods("GET")

	m.Handle("/starstore/buy", negroni.New(
		infrastructure.AppHandler(buyRepository.CreatePurchaseId).Build(),
		infrastructure.AppHandler(historyRepository.CreateHistory).Build(),
		infrastructure.AppHandler(buyRepository.StorePurchase).BuildResponse(),
	)).Methods("POST")

	m.Handle("/starstore/history", negroni.New(
		infrastructure.AppHandler(historyRepository.GetAll).BuildResponse(),
	)).Methods("GET")

	m.Handle("/starstore/history/{clientId}", negroni.New(
		infrastructure.AppHandler(historyRepository.GetHistoryByCl).BuildResponse(),
	)).Methods("GET")

	n := negroni.New()
	n.Use(negroni.NewLogger())
	n.Use(accessControl(m))
	n.UseFunc(middlewareAuth)
	n.UseHandler(m)

	http.ListenAndServe(":8000", n)

}

func HomeHandler(rw http.ResponseWriter, r *http.Request) {
	fmt.Fprintln(rw, "Home")
}

func toJson(f func(w http.ResponseWriter, r *http.Request) (interface{}, error)) http.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request) {
		response, _ := f(w, r)
		res1B, _ := json.Marshal(response)
		fmt.Fprint(w, string(res1B))
	}
}

func accessControl(h http.Handler) negroni.Handler {
	return negroni.HandlerFunc(func(w http.ResponseWriter, r *http.Request, next http.HandlerFunc) {
		w.Header().Set("Access-Control-Allow-Origin", "*")
		w.Header().Set("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
		w.Header().Set("Access-Control-Allow-Headers", "Origin, Content-Type")

		if r.Method == "OPTIONS" {
			return
		}
		next(w,r)
	})
}

