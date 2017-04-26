package br.com.stone.loja.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
@JsonPropertyOrder({ "value", "expDate", "cardNumber" })
public class CreditCard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String cardNumber;
	private String cardHolderName;
	private Integer value;
	private Integer cvv;
	private String expDate;

	public void setId(Integer id) {
		this.id = id;
	}

	@JsonIgnore
	public Integer getId() {
		return id;
	}

	public String getCardNumber() {
		
		return maskCardNumber();
	}

	@JsonSetter("card_number")
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	@JsonIgnore
	public String getCardHolderName() {
		return cardHolderName;
	}

	@JsonSetter("card_holder_name")
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@JsonIgnore
	public Integer getCvv() {
		return cvv;
	}

	public void setCvv(Integer cvv) {
		this.cvv = cvv;
	}

	public String getExpDate() {
		return expDate;
	}

	@JsonSetter("exp_date")
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	
	private String maskCardNumber(){
		String substring = cardNumber.substring(cardNumber.length()-4,cardNumber.length());
		return "**** **** **** " + substring;
		
		
	}

}
