package br.com.starstore.domain;

import java.util.List;

import org.apache.log4j.Logger;

import com.google.gson.GsonBuilder;

import br.com.starstore.dao.impl.TransactionDao;
import br.com.starstore.model.TransactionModel;
import br.com.starstore.util.gson.TransactionSerializer;

public class TransactionDomain {

	private static final Logger LOGGER = Logger.getLogger(TransactionDomain.class);
	
	public String buscarTransacao(TransactionModel transactionModel) throws Exception {
		
		try {
			List<TransactionModel> transactionModels = new TransactionDao().buscar(transactionModel);
			GsonBuilder gson = new GsonBuilder();
			gson.registerTypeAdapter(TransactionModel.class, new TransactionSerializer());
			return gson.create().toJson(transactionModels);	
		} catch (Exception e) {
			LOGGER.error("Erro ao serializar json transaction.", e);
			throw new Exception("Erro ao serializarz json transaction.");
		}
	}
}
