"use strict"

const mongoose = require("../bin/db");
const uuidv1 = require("uuid/v1");
const moment = require("moment");
const Schema = mongoose.Schema;

const schema = new Schema({
   purchase_id: {
      type: String,
      default: uuidv1(),
   },
   client_id: {
      type: String,
      required: true
   },
   client_name: {
      type: String,
      required: true
   },
   total_to_pay: {
      type: Number,
      required: true
   },
   credit_card: {
      card_number: {
         type: String,
         required: true
      },
      value: {
         type: Number,
         required: true
      },
      cvv: {
         type: Number,
         required: true
      },
      card_holder_name: {
         type: String,
         required: true
      },
      exp_date: {
         type: String,
         required: true
      }
   },
   createdAt: {
		type: Date,
		default: Date.now
	}
});

schema.set('toJSON', {
   transform: function(doc, schema, options) {
       schema.value = schema.credit_card.value;
       schema.date = moment(schema.createdAt).format('DD/MM/YYYY');
       schema.card_number = "**** **** **** " + schema.credit_card.card_number.slice(-4);
       delete schema.createdAt;
       delete schema.client_name;
       delete schema.total_to_pay;
       delete schema.credit_card;
       return schema;
   }
})

module.exports = mongoose.model("Transaction", schema);