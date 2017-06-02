var MongoClient = require('mongodb').MongoClient;
var config = require('config');
 
var Purchase = {
    create: async function(obj) {
        if(this.isValid(obj)){
            try {
                var db = await this.connectDB();
                let result = await db
                                  .collection('purchases')
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
            var output = this.formatOutputPurchase();

            return new Promise(function(resolve, reject){
                db
                  .collection('purchases')
                  .aggregate([{ $project : output }])
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
    listByClient: async function(client_id){
        try {
            var db = await this.connectDB();
            var output = this.formatOutputPurchase();
            
            return new Promise(function(resolve, reject){
                db
                  .collection('purchases')
                  .aggregate([
                      { $match: { client_id: client_id }},
                      { $project : output }
                  ])
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
            "client_id": obj.client_id,
            "client_name": obj.client_name,
            "total_to_pay": Number(obj.total_to_pay),
            "date": new Date(),
            "credit_card": {
                "card_number": obj.credit_card.card_number,
                "card_holder_name": obj.credit_card.card_holder_name,
                "value": Number(obj.credit_card.value),
                "cvv": Number(obj.credit_card.cvv),
                "exp_date": obj.credit_card.exp_date
            }
        };
    },
    formatOutputPurchase: function(){
        return { 
            client_id : 1,
            purchase_id: "$_id",
            value: "$credit_card.value",
            date: { $dateToString: { format: "%d/%m/%Y", date: "$date" } },
            card_number: {
              $concat: [
                  '**** **** **** ',
                  {$substrBytes: [
                    "$credit_card.card_number",  
                        { $subtract: [ { $strLenBytes: "$credit_card.card_number" }, 4 ]},
                        { $strLenBytes: "$credit_card.card_number" }
                  ]}
              ]
            }
        }  
    },
    isValid: function(obj){
        if(typeof obj.client_id == "undefined" ||
           isNaN(obj.total_to_pay) || 
           typeof obj.client_name == "undefined" ||
           typeof obj.credit_card == "undefined" ||
           typeof obj.credit_card.card_number == "undefined" || 
           typeof obj.credit_card.card_holder_name == "undefined" ||
           typeof obj.credit_card.exp_date == "undefined" ||
           isNaN(obj.credit_card.value) || isNaN(obj.credit_card.cvv)) { return false; }

        return true;
    }
}

module.exports = Purchase;

