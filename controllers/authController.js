"use strict"

const config = require("../config");
const User = require("../models/user");
const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");

module.exports.login = function(req, res){
    User.findOne({email: req.body.email}, function(err, user){
        if(err)
            return res.status(500).json({"status": "error"});
        if(!user)
            return res.status(401).json({"status": "user not found"});

        var passwordIsValid = bcrypt.compareSync(req.body.password, user.password);
        
        if(!passwordIsValid)
            return res.status(401).json({ "auth": false, "token": null });
        
        var token = jwt.sign({ id: user._id }, config.secret, {
            expiresIn: 86400 // expires in 24 hours
        });

        res.status(200).json({ "auth": true, "token": token });
    });
};

module.exports.register = function(req, res){
    var hashedPassword = bcrypt.hashSync(req.body.password, 8);

    const newUser = new User({
        name: req.body.name,
        email: req.body.email,
        password: hashedPassword
    });

    newUser.save()
        .then(user => {
            var token = jwt.sign({ id: user._id }, config.secret, {
                expiresIn: 86400 // expires in 24 hours
            });
            res.status(200).json({ "auth": true, "token": token });
        }).catch(e => {
            res.status(500).json({"status": "error"});
        });
};

module.exports.checkToken = function(req, res, next){
    var token = req.headers['x-access-token'];
    if (!token) 
        return res.status(403).send({ "auth": false, "message": "No token provided." });
    
    jwt.verify(token, config.secret, function(err, decoded) {      
        if (err) 
            return res.status(500).send({ "auth": false, "message": "Failed to authenticate token." });    
        next();
    });
};