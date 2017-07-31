package memcached


type Repository interface {
	Store(h *interface{})  (*interface{}, error)
	FindAll() []*interface{}
	FindByClientId() []*interface{}
}
