exports.jwtSecret = process.env.JWT_SECRET_KEY || 'secret'
exports.jwtAuthHeaderPrefix = (process.env.JWT_AUTH_HEADER_PREFIX || 'jwt').toLowerCase() 
