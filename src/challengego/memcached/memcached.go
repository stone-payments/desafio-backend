package memcached

import (
	"github.com/bradfitz/gomemcache/memcache"
	"encoding/json"
	"reflect"
	"fmt"
)

var (
	mc = memcache.New("127.0.0.1:11211")
)

func Store(key string, value interface{}) {
	vj, _ := json.Marshal(value)
	mc.Set(&memcache.Item{Key: key, Value: vj})
}

func Get(key string, v interface{}) bool {
	vj, _ := mc.Get(key)
	if vj != nil {
		json.Unmarshal(vj.Value, v)
		return true
	}
	return false
}

// Repository provides access a product store.
type Repository interface {
	Store(in []reflect.Value)  []reflect.Value
	Find(in []reflect.Value) []reflect.Value
	FindAll() []reflect.Value
}

func Decorate(impl interface{}) interface{} {
	fn := reflect.ValueOf(impl)

	inner := func(in []reflect.Value) []reflect.Value {
		f := reflect.ValueOf(impl)

		fmt.Println("Stuff before")
		// ...

		ret := f.Call(in)

		fmt.Println("Stuff after")
		// ...

		return ret
	}

	v := reflect.MakeFunc(fn.Type(), inner)

	return v.Interface()
}

var Add = Decorate(
	func (a, b int) int {
		return a + b
	},
).(func(a, b int) int)

