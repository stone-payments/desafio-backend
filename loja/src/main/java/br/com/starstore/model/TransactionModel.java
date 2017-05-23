package br.com.starstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.starstore.util.json.TransactionSerializer;

@JsonSerialize(using = TransactionSerializer.class)
public class TransactionModel {

	@JsonIgnore
	private Long id;

	@JsonProperty(value = "client_id")
	private String clientId;

	@JsonProperty(value = "client_name", access = Access.WRITE_ONLY)
	private String clientName;

	@JsonProperty(value = "purchase_id")
	private String purchaseId;

	@JsonProperty(value = "total_to_pay")
	private Integer totalToPay;

	private String date;

	@JsonProperty(value = "credit_card")
	private CreditCardModel creditCard = new CreditCardModel();

	public TransactionModel() {
	}

	public TransactionModel(String clientId, String clientName, Integer totalToPay, CreditCardModel creditCard) {
		super();
		this.clientId = clientId;
		this.clientName = clientName;
		this.totalToPay = totalToPay;
		this.creditCard = creditCard;
	}

	@Override
	public String toString() {
		return "TransactionModel [clientId=" + clientId + ", clientName=" + clientName + ", totalToPay=" + totalToPay
				+ ", date=" + date + ", creditCard=" + creditCard + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}

	public Integer getTotalToPay() {
		return totalToPay;
	}

	public void setTotalToPay(Integer totalToPay) {
		this.totalToPay = totalToPay;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public CreditCardModel getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCardModel creditCard) {
		this.creditCard = creditCard;
	}

}
