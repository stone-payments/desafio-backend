package br.com.starstore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class CreditCardModel {

	@JsonProperty(access = Access.WRITE_ONLY)
	private Long id;

	@JsonProperty(value = "card_number")
	private String cardNumber;

	@JsonProperty(value = "card_holder_name", access = Access.WRITE_ONLY)
	private String cardHolderName;

	@JsonProperty(access = Access.WRITE_ONLY)
	private Integer value;

	@JsonProperty(access = Access.WRITE_ONLY)
	private Integer cvv;

	@JsonProperty(value = "exp_date", access = Access.WRITE_ONLY)
	private String expDate;

	public CreditCardModel() {
	}

	public CreditCardModel(String cardNumber, String cardHolderName, Integer value, Integer cvv, String expDate) {
		super();
		this.cardNumber = cardNumber;
		this.cardHolderName = cardHolderName;
		this.value = value;
		this.cvv = cvv;
		this.expDate = expDate;
	}

	@Override
	public String toString() {
		return "CreditCardModel [cardNumber=" + cardNumber + ", cardHolderName=" + cardHolderName + ", value=" + value
				+ ", cvv=" + cvv + ", expDate=" + expDate + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getCvv() {
		return cvv;
	}

	public void setCvv(Integer cvv) {
		this.cvv = cvv;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

}
