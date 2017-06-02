var should = require('chai').should()
  , httpMocks = require('node-mocks-http')
  , auth = require('../')
  , fixtures = require('./fixtures')
  , UnauthorizedError = require('../lib/errors/UnauthorizedError')
  , request = require('supertest')
  , next = function(){}
  , req 
  , res

describe('ensureAuthorizationHeader', function(){
  it('should add the split authorization header to req.auth', function(done){
    req = httpMocks.createRequest()
    res = httpMocks.createResponse()
    req.headers.authorization = fixtures.authorization

    auth.ensureAuthorizationHeader(req, res, function(err){
      if (err) {
        throw err
      }
      should.exist(req.auth)
      req.auth.should.be.instanceof(Array)
      req.auth.length.should.be.equal(2)
      done()
    })
  })
  describe('should throw an UnauthorizedError when', function(){
    beforeEach(function(){
      req = httpMocks.createRequest()
      res = httpMocks.createResponse()
    })
    it('request headers are not defined', function(done){
      req.headers = undefined
      auth.ensureAuthorizationHeader(req, res, next)
      res.statusCode.should.be.equal(401)
      res._getData().name.should.be.equal('UnauthorizedError')
      done()
    })
    it('authorization headers are not defined', function(done){
      auth.ensureAuthorizationHeader(req, res, next)
      res.statusCode.should.be.equal(401)
      res._getData().name.should.be.equal('UnauthorizedError')
      done()
    })
    it('the authorization header is not name spaced', function(done){
      req.headers.authorization = fixtures.NotNameSpacedAuth
      auth.ensureAuthorizationHeader(req, res, next)
      res.statusCode.should.be.equal(401)
      res._getData().name.should.be.equal('UnauthorizedError')
      done()
    })
    it('the credentials string contains spaces', function(done){
      req.headers.authorization = fixtures.jwtAuthWithSpaces
      auth.ensureAuthorizationHeader(req, res, next)
      res.statusCode.should.be.equal(401)
      res._getData().name.should.be.equal('UnauthorizedError')
      done()
    })      
  })
})
describe('validateJWTAuth', function(){
  beforeEach(function(){
    req = httpMocks.createRequest()
    res = httpMocks.createResponse()
  })
  it('should exit early if authorization is not JWT', function(done){
    req.auth = fixtures.OAUTH.split(' ')
    auth.validateJWTAuth(req, res, done)
  })
  it('should fail with an invalid JWT token', function(done){
    req.auth = fixtures.invalidJWTAuth.split(' ')
    setTimeout(function(){
      res.statusCode.should.be.equal(401)
      var data = res._getData()
      data.name.should.be.equal('JsonWebTokenError')
      data.message.should.be.equal('invalid token')
      done()
    }, 100)
    auth.validateJWTAuth(req, res, next)
  })
  it('should assign the decoded JWT token to req.user', function(done){
    req.auth = fixtures.authorization.split(' ')
    auth.validateJWTAuth(req, res, next)
    setTimeout(function(){
      should.exist(req.user)
      req.user.id.should.equal(fixtures.user.id)
      req.user.name.should.equal(fixtures.user.name)
      done()
    }, 200)
  })
})
describe('ensureAuthorized', function(){
  beforeEach(function(){
    req = httpMocks.createRequest()
    res = httpMocks.createResponse()
  })
  it('should fail when req.user is not defined', function(done){
    auth.ensureAuthorized(req, res, next)
    res.statusCode.should.be.equal(401)
    res._getData().name.should.be.equal('UnauthorizedError')
    done()
  })
  it('should succeed when req.user is defined', function(done){
    req.user = true
    auth.ensureAuthorized(req, res, done)
  })
})
