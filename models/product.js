var MongoClient = require('mongodb').MongoClient;
var config = require('config');
 
var Product = {
    create: async function(obj) {
        if(this.isValid(obj)){
            try {
                var db = await this.connectDB();
                let result = await db
                                  .collection('products')
                                  .insert(this.normalize(obj));
            } catch(e){
                console.error(e);
                throw new Error('Error in Operation');
            }
        } else {
            throw new Error('Object is not valid');
        }
    },
    list: async function(){
        try {
            var db = await this.connectDB();
            
            return new Promise(function(resolve, reject){
                db
                  .collection('products')
                  .find({})
                  .project({ _id: 0})
                  .toArray(function(err, items){
                        if(err){ reject(err); }
                        resolve(items);
                });
            });
        } catch(e){
            console.error(e);
            throw new Error('Error in Operation');
        }
    },
    connectDB: async function (){
        return new Promise(function(resolve, reject){
            MongoClient.connect(config.get('mongoURL'), function(err, db) {
                if(err){ return reject(err); }
                resolve(db);

            })
        });        
    },
    normalize: function(obj){
        return {
            "title": obj.title,
            "price": Number(obj.price),
            "zipcode": obj.zipcode,
            "seller": obj.seller,
            "thumbnailHd": obj.thumbnailHd,
            "date": obj.date
        };
    },
    isValid: function(obj){
        if(typeof obj.title == "undefined" ||
           isNaN(obj.price) || 
           typeof obj.zipcode == "undefined" ||
           typeof obj.seller == "undefined" || 
           typeof obj.thumbnailHd == "undefined" ||
           typeof obj.date == "undefined") { return false; }

        return true;
    }
}

module.exports = Product;

