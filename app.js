"use strict"

const app = require("./bin/server");
const bodyParser = require("body-parser");
const authController = require("./controllers/authController");

app.set('json spaces', 2);
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

//Load Routes
const buy = require("./routes/buyRoute");
const history = require("./routes/historyRoute");
const products = require("./routes/productsRoute");
const product = require("./routes/productRoute");
const auth = require("./routes/authRoute");

app.use("/starstore/auth", auth);
app.use("/starstore/buy", authController.checkToken, buy);
app.use("/starstore/history", authController.checkToken, history);
app.use("/starstore/products", authController.checkToken, products);
app.use("/starstore/product", authController.checkToken, product);

