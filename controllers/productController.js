"use strict"

const Product = require("../models/product");
const clientRedis = require("../bin/redis");

module.exports.insert_product = function(req, res){
    const newProduct = new Product({
        title: req.body.title,
        price: req.body.price,
        zipcode: req.body.zipcode,
        seller: req.body.seller,
        thumbnailHd: req.body.thumbnailHd,
        date: req.body.date
    });

    newProduct.save()
        .then(x => {
            res.json({
                "status": "done"
            });
        }).catch(e => {
            res.json({
                "status": "undone"
            });
        });
}

module.exports.list_all_products = function(req, res){
    clientRedis.get('allproducts', function (err, products) {
        if (products) {
            res.setHeader('content-type', 'application/json');
            res.send(products);
        } else {
            Product.find({}, "-_id -__v", (err, products) => {
                if (err) 
                    return res.status(500).send(err);
                clientRedis.set('allproducts', JSON.stringify(products));
                clientRedis.expire('allproducts', 20);
                return res.json(products);
            });
        }
    });
}