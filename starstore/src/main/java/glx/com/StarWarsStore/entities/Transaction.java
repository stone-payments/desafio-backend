package glx.com.StarWarsStore.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Transaction {

	@Id
	@GeneratedValue
	private UUID id;
	
	@JsonIgnore
	private String date;
	@NotNull
	@JsonProperty("client_id")
	private String clientId;
	@NotNull
	@JsonProperty("client_name")
	private String clientName;
	@NotNull
	@JsonProperty("total_to_pay")
	private Integer totalToPay;
	@NotNull
	@JsonProperty("credit_card")
	@OneToOne(cascade = CascadeType.ALL)
	private CreditCard creditCard;
	
	

	Transaction() {
	}

	public Transaction(String clientId, String clientName, Integer totalToPay, CreditCard creditCard) {

		this.date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		this.clientId = clientId;
		this.clientName = clientName;
		this.totalToPay = totalToPay;
		this.creditCard = creditCard;
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

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}



}
