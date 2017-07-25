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
)

func main() {

	mux := mux.NewRouter()

	session, err := mgo.Dial("localhost")
	if err != nil {
		panic(err)
	}
	defer session.Close()

	productRepository, _ := product.NewProductRepository("GoChallenge", session)
	buyRepository, _ := purchase.NewPurchaseRepository("GoChallenge", session)
	historyRepository, _ := history.NewHistoryRepository("GoChallenge", session)

	mux.Handle("/auth/login", toJson(jwtauthorization.LoginHandler))
	//	auth.Path("/logout").HandlerFunc(LogoutHandler)
	//	auth.Path("/signup").HandlerFunc(SignupHandler)

	mux.Handle("/starstore/product", negroni.New(
		negroni.HandlerFunc(jwtauthorization.ValidateTokenMiddleware),
		negroni.Wrap(toJson(productRepository.StoreProduct)),
	)).Methods("POST")

	mux.Handle("/starstore/products", negroni.New(
		negroni.HandlerFunc(jwtauthorization.ValidateTokenMiddleware),
		negroni.Wrap(toJson(productRepository.GetAllProdutcs)),
	)).Methods("GET")

	mux.Handle("/starstore/purchase", negroni.New(
		negroni.HandlerFunc(jwtauthorization.ValidateTokenMiddleware),
		negroni.HandlerFunc(buyRepository.CreatePurchaseId),
		negroni.HandlerFunc(historyRepository.CreateHistory),
		negroni.Wrap(toJson(buyRepository.StorePurchase)),
	)).Methods("POST")

	mux.Handle("/starstore/history", negroni.New(
		negroni.HandlerFunc(jwtauthorization.ValidateTokenMiddleware),
		negroni.Wrap(toJson(productRepository.GetAllProdutcs)),
	)).Methods("GET")

	mux.Handle("/starstore/history/{clientId}", negroni.New(
		negroni.HandlerFunc(jwtauthorization.ValidateTokenMiddleware),
		negroni.Wrap(toJson(productRepository.GetAllProdutcs)),
	)).Methods("GET")

	http.Handle("/", accessControl(mux))

	http.ListenAndServe(":8000", mux)

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

func accessControl(h http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		w.Header().Set("Access-Control-Allow-Origin", "*")
		w.Header().Set("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
		w.Header().Set("Access-Control-Allow-Headers", "Origin, Content-Type")

		if r.Method == "OPTIONS" {
			return
		}

		h.ServeHTTP(w, r)
	})
}

