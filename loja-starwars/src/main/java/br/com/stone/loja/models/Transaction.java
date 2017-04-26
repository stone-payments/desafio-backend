package br.com.stone.loja.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.stone.loja.conf.TransactionSerializer;

@Entity
@JsonPropertyOrder({ "clientId", "id" })
@JsonSerialize(using = TransactionSerializer.class)
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String clientId;
	private String clientName;
	private Integer totalToPay;
	@OneToOne(cascade = CascadeType.PERSIST)
	private CreditCard creditCard;
	private String date;

	@JsonProperty("purchase_id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@JsonIgnore
	public Integer getTotalToPay() {
		return totalToPay;
	}

	@JsonSetter("total_to_pay")
	public void setTotalToPay(Integer totalToPay) {
		this.totalToPay = totalToPay;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	@JsonSetter("credit_card")
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public String getClientId() {
		return clientId;
	}

	@JsonSetter("client_id")
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@JsonIgnore
	public String getClientName() {
		return clientName;
	}

	@JsonSetter("client_name")
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
