package glx.com.StarWarsStore.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import glx.com.StarWarsStore.DTOs.HistoryDTO;
import glx.com.StarWarsStore.entities.Transaction;
import glx.com.StarWarsStore.repositories.TransactionRespository;

@Service
public class HistoryService {

	private Logger log = LoggerFactory.getLogger(StarStoreService.class);
	private final TransactionRespository transactionRepository;
	private List<HistoryDTO> historys;

	public HistoryService(TransactionRespository transactionRepository) {
		this.transactionRepository = transactionRepository;
		log.info("Creating repositories...." + StarStoreService.class.getName());
	}

	public List<HistoryDTO> readHistory() {
		historys = new ArrayList<>();
		try {
			transactionRepository.findAll().forEach(t -> prepareListDTO(prepareDTO(t)));
		} catch (Exception e) {
			log.error("Failed to read the History: " + e);
		}

		return historys;
	}



	@Cacheable("historyCliente")
	public List<HistoryDTO> readHistoryClient(String clientId) {
		historys = new ArrayList<>();
		try {
			transactionRepository.findByClientId(clientId).forEach(t -> prepareListDTO(prepareDTO(t)));
		} catch (Exception e) {
		
			log.error("Failed to read the Historys: " + e);
		}
		
		return historys;

	}

	private List<HistoryDTO> prepareListDTO(HistoryDTO dto) {
		try {
			historys.add(dto);
		} catch (Exception e) {
			log.error("Failed to read the History: " + e);
		}
		
		return historys;
	}
	
	private HistoryDTO prepareDTO(Transaction transaction) {

		HistoryDTO dto = new HistoryDTO();
		dto.setClientId(transaction.getClientId());
		dto.setPurchaseId(transaction.getId());
		dto.setValue(transaction.getCreditCard().getValue());
		dto.setDate(transaction.getDate());
		dto.setCardNumber(setCreditCardMask(transaction.getCreditCard().getCardNumber()));
		return dto;
	}

	private String setCreditCardMask(String cardNumber) {
		String res = "**** **** **** ";
		return res + cardNumber.substring(12);
	}

	@CacheEvict(value = "historyCliente", allEntries = true)
	public void refreshAllhistoryClienteCache() {
		log.info("Cleaning cache... "  + HistoryService.class.getName());
	}

}
