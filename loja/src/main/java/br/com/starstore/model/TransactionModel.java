package br.com.starstore.model;

import com.google.gson.annotations.SerializedName;

public class TransactionModel {

	@SerializedName("purchase_id")
	private Long id;

	@SerializedName("client_id")
	private String clientId;

	@SerializedName("client_name")
	private String clientName;

	@SerializedName("total_to_pay")
	private Integer totalToPay;

	@SerializedName("date")
	private String date;

	@SerializedName("credit_card")
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
