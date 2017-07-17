package main

import (
	"fmt"
	"github.com/gorilla/mux"
	"net/http"
	"time"
	"encoding/json"
	"gopkg.in/mgo.v2"
	"gopkg.in/mgo.v2/bson"
	"log"
	"io/ioutil"
)


type product struct {
	Title       string `json:"title"`
	Price       float32 `json:"price"`
	Zipcode     uint16 `json:"zipcode"`
	Seller      string `json:"seller"`
	Thumbnailhd string `json:"thumbnailHd"`
	Date        time.Time `json:"date"`
}

type history struct {
	ClientId string `json:"client_id"`
	PurchaseId string `json:"purchase_id"`
	Value	uint32 `json:"value"`
	Date	time.Time `json:"date"`
	FinalNumber	uint8 `json:"card_number"`
}


func main() {
	r := mux.NewRouter()
	r.HandleFunc("/", Alive).Methods("GET")
	r.HandleFunc("/starstore/products", Product).Methods("GET")
	r.HandleFunc("/starstore/product", ProductInsert).Methods("POST")
	r.HandleFunc("/starstore/history", Product).Methods("GET")
	r.HandleFunc("/starstore/history/:clientId", Product).Methods("GET")
	r.HandleFunc("/starstore/buy", Product).Methods("POST")
	http.ListenAndServe(":8080", r)
}

/*
Load-Balance Purposes
 */
func Alive(w http.ResponseWriter, r *http.Request) {
	fmt.Fprint(w, "Alive!\n")
}

func Product(w http.ResponseWriter, r *http.Request) {

	products := []product{}

	session, err := mgo.Dial("localhost")
	if err != nil {
		panic(err)
	}
	defer session.Close()

	c := session.DB("GoChallenge").C("Product")

	if err != nil {
		log.Fatal(err)
	}


	err = c.Find(bson.M{}).All(&products)
	if err != nil {
		log.Fatal(err)
	}


	res1B, _ := json.Marshal(products)

	fmt.Fprint(w, string(res1B))
}


func ProductInsert(w http.ResponseWriter, r *http.Request) {


	session, err := mgo.Dial("localhost")
	if err != nil {
		panic(err)
	}
	defer session.Close()

	c := session.DB("GoChallenge").C("Product")

	if err != nil {
		log.Fatal(err)
	}

	prod := product{}
	b, _ := ioutil.ReadAll(r.Body)
	json.Unmarshal(b, &prod)

	err = c.Insert(&prod)
	if err != nil {
		log.Fatal(err)
	}


	res1B, _ := json.Marshal(prod)

	fmt.Fprint(w, string(res1B))
}
