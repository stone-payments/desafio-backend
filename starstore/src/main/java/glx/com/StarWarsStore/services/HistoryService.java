package glx.com.StarWarsStore.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import glx.com.StarWarsStore.DTOs.HistoryDTO;
import glx.com.StarWarsStore.entities.Transaction;
import glx.com.StarWarsStore.repositories.TransactionRespository;

@Service
public class HistoryService {

	
	private final TransactionRespository transactionRepository;
	private List<HistoryDTO> historys;
	//TODO:log na limpeza do cache
	
	
	public HistoryService(TransactionRespository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	
	public List<HistoryDTO> readHistory() {
		historys = new ArrayList<>();
		transactionRepository.findAll().forEach( t -> 
		prepareListDTO(prepareDTO(t))
		);
		return historys;
	}
	
	
	private List<HistoryDTO> prepareListDTO(HistoryDTO dto){
		historys.add(dto);
		return historys;
	}
	
	@Cacheable("historyCliente")
	public List<HistoryDTO> readHistoryClient(String clientId) {
		historys = new ArrayList<>();
		transactionRepository.findByClientId(clientId).forEach( t -> 
		prepareListDTO(prepareDTO(t))
		);
		return historys;
		
	}
	
	private HistoryDTO prepareDTO(Transaction transaction){

		HistoryDTO dto = new HistoryDTO();
		dto.setClientId(transaction.getClientId());
		dto.setPurchaseId(transaction.getId());
		dto.setValue(transaction.getCreditCard().getValue());
		dto.setDate(transaction.getDate());
		dto.setCardNumber(setCreditCardMask(transaction.getCreditCard().getCardNumber()));
		return dto;
	}

	
	private String setCreditCardMask(String cardNumber){
		String res = "**** **** **** ";
		return res + cardNumber.substring(12);
	}
	 	@CacheEvict(value = "historyCliente", allEntries = true)
	    public void refreshAllhistoryClienteCache() {
	 		
	 	}  



	
}
