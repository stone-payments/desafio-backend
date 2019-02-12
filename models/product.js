"use strict"

const mongoose = require('../bin/db');
const Schema = mongoose.Schema;

const schema = new Schema({
    title: {
        type:String
    },
    price: {
        type: Number
    },
    zipcode: {
        type: String
    },
    seller: {
        type: String
    },
    thumbnailHd: {
        type: String
    },
    date: { 
        type: String
    }
});

// Duplicate the ID field.
schema.virtual('product_id').get(function(){
    return this._id.toHexString();
});

module.exports = mongoose.model("Product", schema);