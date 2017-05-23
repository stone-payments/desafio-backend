package br.com.starstore.util.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import br.com.starstore.model.TransactionModel;

public class TransactionSerializer extends StdSerializer<TransactionModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6897449863023614642L;

	public TransactionSerializer() {
        this(null);
    }
   
    public TransactionSerializer(Class<TransactionModel> t) {
        super(t);
    }
 
    @Override
    public void serialize(
    		TransactionModel value, JsonGenerator jgen, SerializerProvider provider) 
      throws IOException, JsonProcessingException {
  
        jgen.writeStartObject();
        jgen.writeStringField("client_id", value.getClientId());
        jgen.writeStringField("purchase_id", value.getPurchaseId());
        jgen.writeNumberField("value", value.getTotalToPay());
        jgen.writeStringField("date", value.getDate());
        jgen.writeStringField("card_number", value.getCreditCard().getCardNumber());
        jgen.writeEndObject();
    }
	
}
