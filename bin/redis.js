const redis = require("redis");
const clientRedis = redis.createClient(6379, 'redis');

clientRedis.on('error', function (err) {
    console.log('redis error – ' + client.host + ':' + client.port + ' – ' + err);
});

module.exports = clientRedis;