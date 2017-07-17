package infrastructure

import "gopkg.in/mgo.v2"

type MongoRepository struct {
	db      string
	session *mgo.Session
}
