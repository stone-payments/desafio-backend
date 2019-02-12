"use strict"

const express = require("express");
const router = express.Router();
const TransactionController = require("../controllers/transactionController");

router.post("/", TransactionController.insert_transaction);

module.exports = router;