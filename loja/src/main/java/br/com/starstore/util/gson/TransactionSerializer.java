package br.com.starstore.util.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import br.com.starstore.model.TransactionModel;

public class TransactionSerializer implements JsonSerializer<TransactionModel> {

	public JsonElement serialize(TransactionModel transactionModel, Type type, JsonSerializationContext context) {
		JsonObject json = new JsonObject();
		json.addProperty("client_id", transactionModel.getClientId());
		json.addProperty("purchase_id", transactionModel.getId());
		json.addProperty("value", transactionModel.getTotalToPay());
		json.addProperty("date", transactionModel.getDate());
		json.addProperty("card_number", transactionModel.getCreditCard().getCardNumber());
		return json;
	}
}
