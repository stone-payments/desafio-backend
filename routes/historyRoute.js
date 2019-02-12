"use strict"

const express = require("express");
const router = express.Router();
const TransactionController = require("../controllers/transactionController");

router.get("/:clientID", TransactionController.list_by_client);
router.get("/", TransactionController.list_all_transaction);

module.exports = router;