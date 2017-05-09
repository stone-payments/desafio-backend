package glx.com.StarWarsStore.DTOs;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HistoryDTO {

	@JsonProperty("client_id")
	private String clientId;
	@JsonProperty("purchase_id")
	private UUID purchaseId;
	@JsonProperty("value")
	private Integer value;
	@JsonProperty("date")
	private String date;
	@JsonProperty("card_number")
	private String cardNumber;

public String getClientId() {
return clientId;
}

public void setClientId(String clientId) {
this.clientId = clientId;
}

public UUID getPurchaseId() {
return purchaseId;
}

public void setPurchaseId(UUID purchaseId) {
this.purchaseId = purchaseId;
}

public Integer getValue() {
return value;
}

public void setValue(Integer value) {
this.value = value;
}

public String getDate() {
return date;
}

public void setDate(String date) {
this.date = date;
}

public String getCardNumber() {
return cardNumber;
}

public void setCardNumber(String cardNumber) {
this.cardNumber = cardNumber;
}



}
