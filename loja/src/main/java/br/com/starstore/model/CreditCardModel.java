package br.com.starstore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreditCardModel {

	@Expose(serialize = false)
	private Long id;

	@SerializedName("card_number")
	private String cardNumber;

	@SerializedName("card_holder_name")
	private String cardHolderName;

	@SerializedName("value")
	private Integer value;

	@SerializedName("cvv")
	private Integer cvv;

	@SerializedName("exp_date")
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
