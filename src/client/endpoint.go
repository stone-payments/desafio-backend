package client

import (
	"net/http"
	"encoding/json"
	"io/ioutil"
	"infrastructure"
	"crypto/sha1"
	"io"
	"fmt"
	"crypto/rand"
	"bytes"
)

func (repository *ClientRepository) StoreClient(r *http.Request) (interface{}, *infrastructure.AppError) {
	c := Client{}
	b, _ := ioutil.ReadAll(r.Body)
	json.Unmarshal(b, &c)
	ps, sal := protectPassword([]byte(c.Password))
	c.Password = string(ps)
	c.Salt =  string(sal)
	c.ClientId = NextClientID()
	p, err := repository.Store(&c)

	if err != nil {
		return nil, &infrastructure.AppError{err, "Error trying to save", 500}
	}
	return p, nil
}


const saltSize = 16

func generateSalt(secret []byte) []byte {
	buf := make([]byte, saltSize, saltSize+sha1.Size)
	_, err := io.ReadFull(rand.Reader, buf)

	if err != nil {
		fmt.Printf("random read failed: %v", err)
	}

	hash := sha1.New()
	hash.Write(buf)
	hash.Write(secret)
	return hash.Sum(buf)
}


func protectPassword(password []byte) ([]byte, []byte){
	// generate salt from given password
	salt := generateSalt(password)
	fmt.Printf("Salt : %x \n", salt)

	// generate password + salt hash to store into database
	combination := string(salt) + string(password)
	passwordHash := sha1.New()
	io.WriteString(passwordHash, combination)
	ph := passwordHash.Sum(nil)
	fmt.Printf("Password Hash : %x \n", ph)
	return ph, salt
}


func IsPassCo(ps []byte, password []byte, salt []byte) bool {

	correctCombination := string(salt) + string(ps)
	correctHash := sha1.New()
	io.WriteString(correctHash, correctCombination)
	fmt.Printf("%x \n", correctHash.Sum(nil))

	c:= correctHash.Sum(nil)
	m := bytes.Equal(c, password)
	return m
}
