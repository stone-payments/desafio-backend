package glx.com.StarWarsStore.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import glx.com.StarWarsStore.DTOs.HistoryDTO;
import glx.com.StarWarsStore.entities.Product;
import glx.com.StarWarsStore.entities.Transaction;
import glx.com.StarWarsStore.exceptions.SearchNotFoundException;
import glx.com.StarWarsStore.exceptions.ValiationCustonException;
import glx.com.StarWarsStore.services.HistoryService;
import glx.com.StarWarsStore.services.StarStoreService;

@RestController
@RequestMapping("/starstore")
public class StarStoreController {

	private Logger log = LoggerFactory.getLogger(StarStoreController.class);
	private final String NOT_NULL_MSG = "NotNull";

	private final StarStoreService starStoreService;
	private final HistoryService historyService;

	@Autowired
	public StarStoreController(StarStoreService starStoreService, HistoryService historyService) {
		this.starStoreService = starStoreService;
		this.historyService = historyService;
		log.info("Creating services instances...." + StarStoreController.class.getName());
	}

	@RequestMapping(method = RequestMethod.POST, path = "/product")
	public ResponseEntity<?> addProduct(@Valid @RequestBody Product input, Errors errors) {
		String errorMessage = null;
		errorMessage = validateProduct(errors);
		if (errorMessage.length() == 0) {
			Product product = starStoreService.addProduct(input);
				URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
						.buildAndExpand(product.getId()).toUri();
				log.info("Registered successfully [PRODUCT ID: " + product.getId()+"]");
				return ResponseEntity.created(location).build();
		} else {
			throw new ValiationCustonException(errorMessage, "product");
		}

	}

	@RequestMapping(method = RequestMethod.POST, path = "/buy")
	public ResponseEntity<?> addTransaction(@Valid @RequestBody Transaction input, Errors errors) {
		String errorMessage = null;
		errorMessage = validateTransaction(errors);
		if (errorMessage.length() == 0) {
			Transaction transaction = starStoreService.addTransaction(input);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(transaction.getId()).toUri();
			log.info("Successful transaction..");
			return ResponseEntity.created(location).build();
		} else {
			throw new ValiationCustonException(errorMessage, "transaction");
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/product/{productId}")
	public Product readProduct(@PathVariable Long productId) {
		Product product = starStoreService.readProduct(productId);
		log.info("Searching for products.");
		if (product == null) {
			throw new SearchNotFoundException(productId, "product");
		}
		return product;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/products")
	public List<Product> readProducts() {
		List<Product> productList = starStoreService.readProdcuts();
		log.info("Searching fo products.");
		if (productList.size() == 0) {
			throw new SearchNotFoundException("products");
		}
		return productList;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/history")
	public List<HistoryDTO> readHistory() {

		List<HistoryDTO> historyList = historyService.readHistory();

		log.info("Searching for shitory by id.");
		if (historyList.size() == 0) {
			throw new SearchNotFoundException("transaction");
		}
		return historyList;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/history/{clientId}")
	public List<HistoryDTO> readHistoryClient(@PathVariable String clientId) {
		List<HistoryDTO> historyClient = historyService.readHistoryClient(clientId);
		log.info("Searching for history clients by id");
		if (historyClient.size() == 0) {
			throw new SearchNotFoundException(clientId, "transactio_client");
		}
		return historyClient;
	}

	@RequestMapping(method = RequestMethod.HEAD, value = "/cleanCacheProducts")
	public HttpStatus refreshAllProductsCache() {
		try {
			starStoreService.refreshAllProductsCache();
			log.info("Start clearing product cache.");
		} catch (Exception e) {
			log.error("Clearing cache failed: " + e);
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return HttpStatus.ACCEPTED;

	}

	@RequestMapping(method = RequestMethod.HEAD, value = "/cleanCacheHistoryClient")
	public HttpStatus refreshAllhistoryClienteCache() {
		try {
			historyService.refreshAllhistoryClienteCache();
			log.info("Start clearing history cache.");
		} catch (Exception e) {
			log.error("Clearing cache failed: " + e);
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return HttpStatus.ACCEPTED;
	}

	private String validateTransaction(Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "creditCard.cardNumber", NOT_NULL_MSG);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "creditCard.value", NOT_NULL_MSG);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "creditCard.cvv", NOT_NULL_MSG);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "creditCard.cardHolderName", NOT_NULL_MSG);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "creditCard.expDate", NOT_NULL_MSG);

		return errorsToJSON(errors);
	}

	private String validateProduct(Errors errors) {
		return errorsToJSON(errors);
	}

	private String errorsToJSON(Errors errors) {
		String errJson = "0";

		errJson = "{'";
		for (FieldError field : errors.getFieldErrors()) {
			errJson += field.getField();
			errJson += "':'";
			errJson += field.getCode() + "','";
		}
		errJson = errJson.substring(0, errJson.length() - 2);
		if (errJson.length() > 1) {
			errJson += "}";
		}
		return errJson;
	}

}
