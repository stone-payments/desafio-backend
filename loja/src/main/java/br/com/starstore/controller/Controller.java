package br.com.starstore.controller;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.com.starstore.dao.impl.ProductDao;
import br.com.starstore.dao.impl.TransactionDao;
import br.com.starstore.domain.TransactionDomain;
import br.com.starstore.model.ProductModel;
import br.com.starstore.model.TransactionModel;

@Path("/")
public class Controller {

	// --------- INICIO PRODUTO ---------

	@POST
	@Path("product")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response adicionarProduto(String json) throws Exception {
		ProductModel productModel = new Gson().fromJson(json, ProductModel.class);
		new ProductDao().adicionar(productModel);
		return Response.created(URI.create("products")).build();
	}

	@GET
	@Path("products")
	@Produces(MediaType.APPLICATION_JSON)
	public String buscarProduto() throws Exception {
		return new Gson().toJson(new ProductDao().buscar(new ProductModel()));
	}

	// --------- FIM PRODUTO ---------

	// --------- INICIO TRANSACAO ---------

	@POST
	@Path("buy")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response adicionarTransacao(String json) throws Exception {
		TransactionModel transactionModel = new Gson().fromJson(json, TransactionModel.class);
		new TransactionDao().adicionar(transactionModel);
		return Response.created(URI.create("/history/")).build();
	}

	@GET
	@Path("history")
	@Produces(MediaType.APPLICATION_JSON)
	public String buscarTransacao() throws Exception {
		return new TransactionDomain().buscarTransacao(new TransactionModel());
	}

	@GET
	@Path("history/{clientId}")
	@Produces(MediaType.APPLICATION_JSON)
	public String buscarTransacao(@PathParam("clientId") String clientId) throws Exception {
		TransactionModel transactionModel = new TransactionModel();
		transactionModel.setClientId(clientId);
		return new TransactionDomain().buscarTransacao(transactionModel);
	}

	// --------- FIM TRANSACAO ---------
}
