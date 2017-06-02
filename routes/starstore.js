var express = require('express');
var router = express.Router();
var products = require('../models/product');
var purchases = require('../models/purchase');

var routeStore = {
	insertProd: function(req, res, next) {
		products
		.create(req.body)
		.then(function(){
			res.status(201);
			res.send('Success!');
		})
		.catch(function(e){
			console.error(e);
			res.status(400);
			res.send('não foi possível executar a operação.');
		});
	},

	listProds: function(req, res, next){
		products
		.list()
		.then(function(list){
			res.status(201);
			res.send(list);
		})
		.catch(function(e){
			console.error(e);
			res.status(400);
			res.send('não foi possível executar a operação.');
		});
	},

	insertPurchase: function(req, res, next) {
		purchases
		.create(req.body)
		.then(function(){
			res.status(201)
			res.send('Success!');
		})
		.catch(function(e){
			console.error(e);
			res.status(400);
			res.send('não foi possível executar a operação.');
		});
	},

	listPurchases: function(req, res, next){
		purchases
		.list()
		.then(function(list){
			res.send(list);
		})
		.catch(function(e){
			console.error(e);
			res.status(400);
			res.send('não foi possível executar a operação.');
		});
	},

	listPurchasesByClient: function(req, res, next){
		purchases
		.listByClient(req.params.client_id)
		.then(function(list){
			res.send(list);
		})
		.catch(function(e){
			console.error(e);
			res.status(400);
			res.send('não foi possível executar a operação.');
		});
	},
}

router.post('/product', routeStore.insertProd);
router.get('/products', routeStore.listProds);
router.post('/buy', routeStore.insertPurchase);
router.get('/history', routeStore.listPurchases);
router.get('/history/:client_id', routeStore.listPurchasesByClient);

module.exports = router;
