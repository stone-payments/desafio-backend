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
		log.info("Criando os Serviços de apoio ao StarStoreController...");
	}

	@RequestMapping(method = RequestMethod.POST, path = "/product")
	public ResponseEntity<?> addProduct(@Valid @RequestBody Product input, Errors errors) {
		String errorMessage = null;
		errorMessage = validateProduct(errors);
		if (errorMessage.length() == 0) {
			Product product = starStoreService.addProduct(input);
				URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
						.buildAndExpand(product.getId()).toUri();
				log.info("PRODUTO cadastrado com sucesso. ID do produto: " + product.getId());
				return ResponseEntity.created(location).build();
		} else {
			throw new ValiationCustonException(errorMessage, "PRODUTO");
		}

	}

	@RequestMapping(method = RequestMethod.POST, path = "/buy")
	public ResponseEntity<?> addTransaction(@Valid @RequestBody Transaction input, Errors errors) {
		String errorMessage = null;
		errorMessage = validateTransaction(errors);
		if (errorMessage.length() == 0) {
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(starStoreService.addTransaction(input).getId()).toUri();
			log.info("Transação realizada com sucesso.");
			return ResponseEntity.created(location).build();
		} else {
			throw new ValiationCustonException(errorMessage, "TRANSACAO");
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/product/{productId}")
	public Product readProduct(@PathVariable Long productId) {
		Product product = starStoreService.readProduct(productId);
		log.info("Iniciando a pesquisa por PRODUTOS DA LOJA...");
		if (product == null) {
			throw new SearchNotFoundException(productId, "PRODUTO");
		}
		return product;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/products")
	public List<Product> readProducts() {
		List<Product> productList = starStoreService.readProdcuts();
		log.info("Iniciando a pesquisa por PRODUTOS DA LOJA...");
		if (productList.size() == 0) {
			throw new SearchNotFoundException("PRODUTOS");
		}
		return productList;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/history")
	public List<HistoryDTO> readHistory() {

		List<HistoryDTO> historyList = historyService.readHistory();

		log.info("Iniciando a pesquisa por HISTORICO DE COMPRAS DA LOJA...");
		if (historyList.size() == 0) {
			throw new SearchNotFoundException("TRANSACTION");
		}
		return historyList;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/history/{clientId}")
	public List<HistoryDTO> readHistoryClient(@PathVariable String clientId) {
		List<HistoryDTO> historyClient = historyService.readHistoryClient(clientId);
		log.info("Iniciando a pesquisa por HISTORICO DE COMPRA DO CLIENTE...");
		if (historyClient.size() == 0) {
			throw new SearchNotFoundException(clientId, "TRANSACTION_CLIENT");
		}
		return historyClient;
	}

	@RequestMapping(method = RequestMethod.HEAD, value = "/cleanCacheProducts")
	public HttpStatus refreshAllProductsCache() {
		try {
			starStoreService.refreshAllProductsCache();
			log.info("Iniciando a llimpeza do cash do PRODUTO...");
		} catch (Exception e) {
			log.error("Falha ao iniciar a limpeza do cache do PRODUTO : " + e);
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return HttpStatus.ACCEPTED;

	}

	@RequestMapping(method = RequestMethod.HEAD, value = "/cleanCacheHistoryClient")
	public HttpStatus refreshAllhistoryClienteCache() {
		try {
			historyService.refreshAllhistoryClienteCache();
			log.info("Iniciando a llimpeza do CASH DO HISTORY CLIENTE...");
		} catch (Exception e) {
			log.error("Falha ao iniciar a limpeza do CACHE HISTORY CLIENTE: " + e);
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return HttpStatus.ACCEPTED;
	}

	private String validateTransaction(Errors errors) {
		String errJason = null;

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
		String errJason = "0";

		errJason = "{'";
		for (FieldError field : errors.getFieldErrors()) {
			errJason += field.getField();
			errJason += "':'";
			errJason += field.getCode() + "','";
		}
		errJason = errJason.substring(0, errJason.length() - 2);
		if (errJason.length() > 1) {
			errJason += "}";
		}
		return errJason;
	}

}
