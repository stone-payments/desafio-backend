package glx.com.StarWarsStore.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class CreditCard {
	
	@Id
	@GeneratedValue
	private Long id;
	@NotNull
	@JsonProperty("card_number")
	private String cardNumber;
	@NotNull
	@Min(1)
	@JsonProperty("value")
	private Integer value;
	@NotNull
	@JsonProperty("cvv")
	private Integer cvv;
	@NotNull
	@JsonProperty("card_holder_name")
	private String cardHolderName;
	@NotNull
	@JsonProperty("exp_date")
	private String expDate;
	
	CreditCard() {
	}
	

	public CreditCard(String cardNumber, Integer value, Integer cvv, String cardHolderName, String expDate) {
		super();
		this.cardNumber = cardNumber;
		this.value = value;
		this.cvv = cvv;
		this.cardHolderName = cardHolderName;
		this.expDate = expDate;
	}


	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
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

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

}
