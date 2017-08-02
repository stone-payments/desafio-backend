package memcached

import (
	"github.com/bradfitz/gomemcache/memcache"
	"encoding/json"
)

var (
	mc = memcache.New("127.0.0.1:11211")
)

func Store(key string, value interface{}) {
	vj, _ := json.Marshal(value)
	mc.Set(&memcache.Item{Key: key, Value: vj})
}

func Get(key string, v interface{}){
	vj, _ := mc.Get(key)
	if vj != nil {
		json.Unmarshal(vj.Value, v)
	}
}