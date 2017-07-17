package main

import (
	"net/http"
	product2 "product"
	"gopkg.in/mgo.v2"
)


func main() {

	mux := http.NewServeMux()

	session, err := mgo.Dial("localhost")
	if err != nil {
		panic(err)
	}
	defer session.Close()

	handlerProduct := product2.MakeHandler(session)

	mux.Handle("/starstore/product", handlerProduct)
	mux.Handle("/starstore/products", handlerProduct)

	http.Handle("/", accessControl(mux))

	http.ListenAndServe(":8080", mux)

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

