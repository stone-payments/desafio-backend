"use strict"

const express = require("express");
const router = express.Router();
const ProductController = require("../controllers/productController");

router.get("/", ProductController.list_all_products);

module.exports = router;