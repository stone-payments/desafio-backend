package glx.com.StarWarsStore.services;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

	private static final int TEN_MINUTES = 360000;
	private StarStoreService starStoreService;
	private HistoryService historyService; 

	public ScheduledTasks(StarStoreService starStoreService, HistoryService historyService) {
		this.starStoreService = starStoreService;
		this.historyService = historyService;
	}
	
	@Scheduled(fixedRate = TEN_MINUTES)
	public void refreshAllhistoryClienteCache() {
		historyService.refreshAllhistoryClienteCache();
	}
	
	@Scheduled(fixedRate = TEN_MINUTES)
	public void refreshAllProductsCache(){
		starStoreService.refreshAllProductsCache();
	}



}
