package jwtauthorization

import (
	"net/http"
	"fmt"
	"encoding/json"
	"log"
	"strings"
	"time"
	"github.com/dgrijalva/jwt-go"
	"github.com/dgrijalva/jwt-go/request"
	"errors"
)

/* Set up a global string for our secret */
var mySigningKey = []byte("b0HMdArafow2NR83a2lpeu8nGVSbxJmr")


//STRUCT DEFINITIONS


type UserCredentials struct {
	Username	string  `json:"username"`
	Password	string	`json:"password"`
}

type User struct {
	ID			int 	`json:"id"`
	Name		string  `json:"name"`
	Username	string  `json:"username"`
	Password	string	`json:"password"`
}

type Response struct {
	Data	string	`json:"data"`
}

type Token struct {
	Token 	string    `json:"token"`
}



func LoginHandler(w http.ResponseWriter, r *http.Request) (interface{}, error){

	var user UserCredentials

	//decode request into UserCredentials struct
	err := json.NewDecoder(r.Body).Decode(&user)
	if err != nil {
		w.WriteHeader(http.StatusForbidden)
		return nil, errors.New("Error in request")
	}

	fmt.Println(user.Username, user.Password)

	//validate user credentials
	if strings.ToLower(user.Username) != "alexcons" {
		if user.Password != "kappa123" {
			w.WriteHeader(http.StatusForbidden)
			fmt.Println("Error logging in")
			return nil, errors.New("Invalid credentials")
		}
	}

	//create a rsa 256 signer
	signer := jwt.New(jwt.SigningMethodHS256)

	//set claims
	signer.Claims = jwt.MapClaims{
		"iss" : "admin",
		"exp" : time.Now().Add(time.Minute * 20).Unix(),
		"iat": time.Now().Unix(),
		"CustomUserInfo": struct {
			Name	string
			Role	string
		}{user.Username, "Member"},
	}

	tokenString, err := signer.SignedString(mySigningKey)

	if err != nil {
		w.WriteHeader(http.StatusInternalServerError)
		fmt.Fprintln(w, "Error while signing the token")
		log.Printf("Error signing token: %v\n", err)
	}

	//create a token instance using the token string
	response := Token{tokenString}
	return response, nil
}



//AUTH TOKEN VALIDATION


func ValidateTokenMiddleware(w http.ResponseWriter, r *http.Request, next http.HandlerFunc) {

	//validate token
	token, err := request.ParseFromRequest(r, request.OAuth2Extractor, keyLookupFunc);

	if err == nil {

		if token.Valid{
			next(w, r)
		} else {
			w.WriteHeader(http.StatusUnauthorized)
			fmt.Fprint(w, "Token is not valid")
		}
	} else {
		w.WriteHeader(http.StatusUnauthorized)
		fmt.Fprint(w,err)
	}

}

func keyLookupFunc(token *jwt.Token) (interface{}, error) {
	// Don't forget to validate the alg is what you expect:
	if _, ok := token.Method.(*jwt.SigningMethodHMAC); !ok {
		return nil, fmt.Errorf("Unexpected signing method: %v", token.Header["alg"])
	}

	// Unpack key from PEM encoded PKCS8
	return mySigningKey, nil
}

