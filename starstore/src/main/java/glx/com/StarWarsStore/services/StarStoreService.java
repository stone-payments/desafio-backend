package glx.com.StarWarsStore.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import glx.com.StarWarsStore.entities.CreditCard;
import glx.com.StarWarsStore.entities.Product;
import glx.com.StarWarsStore.entities.Transaction;
import glx.com.StarWarsStore.repositories.ProductRepository;
import glx.com.StarWarsStore.repositories.TransactionRespository;

@Service
public class StarStoreService {

	private final ProductRepository productRepository;
	private final TransactionRespository transactionRepository;

	private Logger log = LoggerFactory.getLogger(StarStoreService.class);

	@Autowired
	public StarStoreService(ProductRepository productRepository, TransactionRespository transactionRepository) {
		this.productRepository = productRepository;
		this.transactionRepository = transactionRepository;
	}

	public Product addProduct(Product input) {
		Product product = null;
		try {
			product = productRepository.save(new Product(input.getTitle(), input.getPrice(), input.getZipcode(),
					input.getSeller(), input.getZipcode(), input.getDate()));
		} catch (Exception e) {
			log.error("Falha ao cadastrar o Produto , no nivel do SERVICE: " + e);
		}
		return product;
	}

	@Cacheable("products")
	public Product readProduct(Long productId) {
		Product product = null;
		try {
			product = this.productRepository.findOne(productId);
		} catch (Exception e) {
			log.error("Falha ao ler o Produto, no nivel do SERVICE : " + e);
		}
		return product;
	}

	public List<Product> readProdcuts() {
		List<Product> products = null;
		try {
			products = this.productRepository.findAll();
		} catch (Exception e) {
			log.error("Falha ao cadastrar a transacao, no nivel do SERVICE : " + e);
		}
		return products;

	}

	public Transaction addTransaction(Transaction input) {
		Transaction transaction = null;
		try {
			transaction = transactionRepository
					.save(new Transaction(input.getClientId(), input.getClientName(), input.getTotalToPay(),
							(new CreditCard(input.getCreditCard().getCardNumber(), input.getCreditCard().getValue(),
									input.getCreditCard().getCvv(), input.getCreditCard().getCardHolderName(),
									input.getCreditCard().getExpDate()))));

		} catch (Exception e) {
			log.error("Falha ao ler os Produtos, no nivel do SERVICE : " + e);
		}
		return transaction;
	}

	@CacheEvict(value = "products", allEntries = true)
	public void refreshAllProductsCache() {
		log.info("Limpando todo o cache de produtos..");
	}

}
