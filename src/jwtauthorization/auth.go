package jwtauthorization

import (
	"net/http"
	"fmt"
	"encoding/json"
	"strings"
	"time"
	"github.com/dgrijalva/jwt-go"
	"github.com/dgrijalva/jwt-go/request"
	"infrastructure"
	"client"
)

/* Set up a global string for our secret */
var mySigningKey = []byte("b0HMdArafow2NR83a2lpeu8nGVSbxJmr")



var filteredUriAuthToken = []string{"/starstore/auth", "/starstore/client"}

type JWTAuth struct {
	CR *client.ClientRepository
}

//STRUCT DEFINITIONS

type Response struct {
	Data	string	`json:"data"`
}

type Token struct {
	Token 	string    `json:"token"`
}

type AuthLogin struct {
	Username string `json:"username"`
	Password string `json:"password"`
}



func (auth *JWTAuth) LoginHandler(r *http.Request) (interface{}, *infrastructure.AppError){

	var user AuthLogin

	//decode request into UserCredentials struct
	err := json.NewDecoder(r.Body).Decode(&user)
	if err != nil {
		return nil, &infrastructure.AppError{err, "Error in request", 401}
	}

	fmt.Println(user.Username, user.Password)

	//validate user credentials
	u, err := auth.CR.FindByUsername(user.Username)
	if err != nil {
		return nil, &infrastructure.AppError{err, "Invalid credentials", 401}
	}

	if !client.IsPassCo([]byte(user.Password), []byte(u.Password), []byte(u.Salt)){
		return nil, &infrastructure.AppError{err, "Invalid credentials", 401}
	}

	//create a rsa 256 signer
	signer := jwt.New(jwt.SigningMethodHS256)

	//set claims
	signer.Claims = jwt.MapClaims{
		"iss" : "admin",
		"exp" : time.Now().Add(time.Minute * 20).Unix(),
		"iat": time.Now().Unix(),
	}

	tokenString, err := signer.SignedString(mySigningKey)

	if err != nil {
		return nil, &infrastructure.AppError{err, "Error signing token", 500};
	}

	//create a token instance using the token string
	response := Token{tokenString}
	return response, nil
}



//AUTH TOKEN VALIDATION
func ValidateTokenMiddleware(r *http.Request) (interface{}, *infrastructure.AppError) {

	if infrastructure.Any(filteredUriAuthToken, func(v string) bool {
		return strings.HasPrefix(r.RequestURI, v)
	}) {
		return nil, nil;
	}
	//validate token
	token, err := request.ParseFromRequest(r, request.OAuth2Extractor, keyLookupFunc);

	if err == nil {

		if token.Valid{
			return nil, nil;
		} else {
			return nil, &infrastructure.AppError{nil,"Token is not valid", http.StatusUnauthorized};
		}
	} else {
		return nil, &infrastructure.AppError{err,"Token is not valid", http.StatusUnauthorized};
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

