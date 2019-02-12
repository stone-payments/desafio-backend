const express = require("express");
const app = express();
const config = require("../config");
var port = process.env.PORT || config.PORT;
var host = config.HOST;

app.listen(port, host, function(){
    console.log("Server listening on port " + port);
});

module.exports = app;