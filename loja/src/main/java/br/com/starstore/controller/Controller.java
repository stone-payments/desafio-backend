package br.com.starstore.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.starstore.dao.impl.ProductDao;
import br.com.starstore.dao.impl.TransactionDao;
import br.com.starstore.model.ProductModel;
import br.com.starstore.model.TransactionModel;

@RestController
@RequestMapping("/starstore")
public class Controller {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private TransactionDao transactionDao;
	
	// --------- INICIO PRODUTO ---------
	@GetMapping(value = "products", produces = MediaType.APPLICATION_JSON_VALUE)
	@Cacheable("products")
	public List<ProductModel> buscarProduto() throws Exception {
		return productDao.buscar(new ProductModel());
	}
	
	@PostMapping(value = "product", consumes = MediaType.APPLICATION_JSON_VALUE)
	@CacheEvict(cacheNames = "products", allEntries = true)
	public ResponseEntity<ProductModel> adicionarProduto(@RequestBody ProductModel productModel) throws Exception {
		productDao.adicionar(productModel);
		return ResponseEntity.created(URI.create("/products")).build();
	}
	
	// --------- FIM PRODUTO ---------

	// --------- INICIO TRANSACAO ---------
	
	@GetMapping(value = "history", produces = MediaType.APPLICATION_JSON_VALUE)
	@Cacheable("history")
	public List<TransactionModel> buscarTransacao() throws Exception {
		return transactionDao.buscar(new TransactionModel());
	}

	@GetMapping(value = "history/{clientId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public TransactionModel buscarTransacao(@PathVariable("clientId") String clientId) throws Exception {
		TransactionModel transactionModel = new TransactionModel();
		transactionModel.setClientId(clientId);
		return transactionDao.buscar(transactionModel).get(0);
	}
	
	@PostMapping(value = "buy", consumes = MediaType.APPLICATION_JSON_VALUE)
	@CacheEvict(cacheNames = "history", allEntries = true)
	public ResponseEntity<TransactionModel> adicionarTransacao(@RequestBody TransactionModel transactionModel) throws Exception {
		transactionDao.adicionar(transactionModel);
		return ResponseEntity.created(URI.create("/history")).build();
	}
	
	// --------- FIM TRANSACAO ---------
}
