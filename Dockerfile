FROM golang:1.8

ENV GOPATH /go

COPY src/challengego /go/src/challengego

RUN cd /go/src/challengego \
    && go get 	github.com/pborman/uuid \
    && go get  	gopkg.in/mgo.v2/bson \
    && go get 	gopkg.in/mgo.v2 \
    && go get 	github.com/pborman/uuid \
    && go get 	github.com/bradfitz/gomemcache/memcache \
    && go get   github.com/dgrijalva/jwt-go \
    && go get   github.com/gorilla/context \
    && go get   github.com/dgrijalva/jwt-go/request \
    && go get   github.com/gorilla/mux \
    && go get   github.com/codegangsta/negroni \
    && go build -o /go/challengego main.go



EXPOSE 8000

CMD ["/go/challengego"]
