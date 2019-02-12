"use strict"

const express = require("express");
const router = express.Router();
const ProductController = require("../controllers/productController");

router.post("/", ProductController.insert_product);

module.exports = router;