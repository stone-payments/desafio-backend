package br.com.stone.loja.conf;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import br.com.stone.loja.models.Transaction;

public class TransactionSerializer extends StdSerializer<Transaction> {

	 public TransactionSerializer() {
	        this(null);
	    }
	   
	    public TransactionSerializer(Class<Transaction> t) {
	        super(t);
	    }

		@Override
		public void serialize(Transaction t, JsonGenerator jgen, SerializerProvider arg2)
				throws IOException, JsonGenerationException {
			jgen.writeStartObject();
	        jgen.writeStringField("client_id", t.getClientId());
	        jgen.writeNumberField("purchase_id", t.getId());
	        jgen.writeStringField("date", t.getDate());
	        jgen.writeNumberField("value", t.getCreditCard().getValue());
	        jgen.writeStringField("exp_date", t.getCreditCard().getExpDate());
	        jgen.writeStringField("card_number", t.getCreditCard().getCardNumber());
	        jgen.writeEndObject();
			
		}
		
	
}
