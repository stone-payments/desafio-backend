package br.com.stone.loja.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.stone.loja.daos.ProductDao;
import br.com.stone.loja.daos.TransactionDAO;
import br.com.stone.loja.models.Product;
import br.com.stone.loja.models.Transaction;

@RestController
@Transactional
public class StarStoreController {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private TransactionDAO transactionDAO;

	private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@RequestMapping(value = "/product", method = RequestMethod.POST)
	@CacheEvict(value = "products", allEntries = true)
	public ResponseEntity<Void> add(@RequestBody Product product) {
		productDao.save(product);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/products", method = RequestMethod.GET)
	@Cacheable("products")
	public ResponseEntity<List<Product>> listAllProducts() {
		List<Product> products = productDao.all();

		if (products.isEmpty()) {
			return new ResponseEntity<List<Product>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}

	@RequestMapping(value = "/buy", method = RequestMethod.POST)
	@CacheEvict(value = "transactions", allEntries = true)
	public ResponseEntity<Void> buy(@RequestBody Transaction transaction) {
		transaction.setDate(LocalDate.now().format(format));
		transactionDAO.save(transaction);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "/history", method = RequestMethod.GET)
	@Cacheable("transactions")
	public ResponseEntity<List<Transaction>> listAllBuy() {
		List<Transaction> transactions = transactionDAO.all();

		if (transactions.isEmpty()) {
			return new ResponseEntity<List<Transaction>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);
	}

	@RequestMapping(value = "/history/{id}", method = RequestMethod.GET)
	@Cacheable(value = "transactions")
	public ResponseEntity<List<Transaction>> getHistoryClientID(@PathVariable("id") String id) {
		List<Transaction> transactions = transactionDAO.findByClientId(id);

		if (transactions.isEmpty()) {
			return new ResponseEntity<List<Transaction>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Transaction>>(transactions, HttpStatus.OK);
	}

}
