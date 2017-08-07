package infrastructure

import (
	"gopkg.in/mgo.v2"
	"net/http"
	"github.com/codegangsta/negroni"
	"fmt"
	"encoding/json"
)

type MongoRepository struct {
	DB      string
	Session *mgo.Session
}

type Context struct {
	User	  struct{}
	Purchase  struct{}
}

type AppError struct {
	Error   error
	Message string
	Code    int
}

type AppHandler func(*http.Request) (interface{}, *AppError)



func Filter(vs []string, f func(string) bool) []string {
	vsf := make([]string, 0)
	for _, v := range vs {
		if f(v) {
			vsf = append(vsf, v)
		}
	}
	return vsf
}


func Any(vs []string, f func(string) bool) bool {
	for _, v := range vs {
		if f(v) {
			return true
		}
	}
	return false
}

func (fn AppHandler) Build() negroni.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request, next http.HandlerFunc){
		_, err := fn(r)
		if err != nil {
			w.WriteHeader(err.Code)
			fmt.Fprint(w, err.Message)
		} else {
			next(w,r)
		}
	}
}


func (fn AppHandler) BuildResponse() negroni.HandlerFunc {
	return func(w http.ResponseWriter, r *http.Request, next http.HandlerFunc){
		resp, err := fn(r)
		if err != nil {
			w.WriteHeader(err.Code)
			fmt.Fprint(w, err.Message)
		} else {
			res1B, _ := json.Marshal(resp)
			fmt.Fprint(w, string(res1B))
		}
	}
}