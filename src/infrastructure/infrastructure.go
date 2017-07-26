package infrastructure

import (
	"gopkg.in/mgo.v2"
	"jwtauthorization"
	"purchase"
)

type MongoRepository struct {
	db      string
	session *mgo.Session
}

type Context struct {
	User	  jwtauthorization.User
	Purchase  purchase.Purchase
}