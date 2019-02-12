const mongoose = require("mongoose");
const config = require("../config");

mongoose.connect(config.MONGO_URI, 
                { useNewUrlParser: true },
                err => {
                    if (err) throw err;
                    console.log("BD connected.");
                }

);
mongoose.set('useCreateIndex', true);

module.exports = mongoose;