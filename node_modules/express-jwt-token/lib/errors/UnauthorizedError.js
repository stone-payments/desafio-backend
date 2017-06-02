'use strict'

module.exports = function UnauthorizedError (code, message) {
  Error.captureStackTrace(this, this.constructor)
  this.name = this.constructor.name
  this.message = message || 'Invalid Authorization header.'
}

require("util").inherits(module.exports, Error)
