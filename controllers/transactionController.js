"use strict"

const Transaction = require("../models/transaction");
const clientRedis = require("../bin/redis");

module.exports.insert_transaction = function(req, res){
    //Create transaction
    const newTransaction = new Transaction({
        client_id: req.body.client_id,
        client_name: req.body.client_name,
        total_to_pay: req.body.total_to_pay,
        credit_card: {
            card_number: req.body.credit_card.card_number,
            value: req.body.credit_card.value,
            cvv: req.body.credit_card.cvv,
            card_holder_name: req.body.credit_card.card_holder_name,
            exp_date: req.body.credit_card.exp_date
        }
    });

    //Save in BD
    newTransaction.save()
        .then(x => {
            res.json({
                "status": "done"
            });
        }).catch(e => {
            res.json({
                "status": "undone"
            });
        });
};

module.exports.list_all_transaction = function(req, res){
    clientRedis.get('alltransactions', function (err, transactions) {
        if (transactions) {
            res.setHeader('content-type', 'application/json');
            res.send(transactions);
        } else {
            Transaction.find({}, "-_id -__v", (err, transactions) => {
                if (err) 
                    return res.status(500).send(err);
                clientRedis.set('alltransactions', JSON.stringify(transactions));
                clientRedis.expire('alltransactions', 20);
                return res.json(transactions);
            });
        }
    });
};

module.exports.list_by_client = function(req, res){
    clientRedis.get(req.params.clientID, function (err, transactions) {
        if (transactions) {
            res.setHeader('content-type', 'application/json');
            res.send(transactions);
        } else {
            Transaction.find({ client_id: req.params.clientID }, "-_id -__v", (err, transactions) => {
                if (err) 
                    return res.status(500).send(err);
                clientRedis.set(req.params.clientID, JSON.stringify(transactions));
                clientRedis.expire(req.params.clientID, 20);
                return res.json(transactions);
            });
        }
    });
};