package br.com.starstore.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import br.com.starstore.dao.BasicDao;
import br.com.starstore.dao.ConnectionDao;
import br.com.starstore.model.CreditCardModel;
import br.com.starstore.model.TransactionModel;

@Repository
public class TransactionDao implements BasicDao<TransactionModel> {

	private static Logger LOGGER = Logger.getLogger(TransactionDao.class);

	private Connection conn;
	private StringBuilder sql;

	@Override
	public void adicionar(TransactionModel t) throws Exception {

		verificarCreditCardCadastrado(t.getCreditCard());
		if (t.getCreditCard().getId() == null) {
			adicionarCreditCard(t.getCreditCard());
		}

		try (Connection con = ConnectionDao.getInstance().getConnection()) {
			con.setAutoCommit(false);
			conn = con;
			sql = new StringBuilder(200);
			sql.append(" INSERT INTO transaction (clientid, clientname, totaltopay, date, creditcardid, purchaseid) ");
			sql.append(" VALUES (?, ?, ?, ?, ?, ?) ");

			try (PreparedStatement preparedStatement = con.prepareStatement(sql.toString())) {

				preparedStatement.setString(1, t.getClientId());
				preparedStatement.setString(2, t.getClientName());
				preparedStatement.setInt(3, t.getTotalToPay());
				preparedStatement.setDate(4, new Date(System.currentTimeMillis()));
				preparedStatement.setLong(5, t.getCreditCard().getId());
				preparedStatement.setString(6, UUID.randomUUID().toString());
				
				preparedStatement.execute();
				con.commit();
			}
		} catch (Exception e) {
			conn.rollback();
			LOGGER.error("Erro no metodo adicionar(TransactionModel t): ", e);
			throw new Exception("Erro ao adicionar transaction.");
		}
	}

	/**
	 * Recuperar o id do creditCard caso exista.
	 * 
	 * @param creditCardModel
	 * @return id
	 * @throws Exception
	 */
	private void verificarCreditCardCadastrado(CreditCardModel creditCardModel) throws Exception {
		try (Connection con = ConnectionDao.getInstance().getConnection()) {
			con.setAutoCommit(false);

			sql = new StringBuilder(200);
			sql.append(" SELECT id FROM creditcard WHERE cardnumber = ? ");

			try (PreparedStatement preparedStatement = con.prepareStatement(sql.toString())) {

				preparedStatement.setString(1, creditCardModel.getCardNumber());

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					while (resultSet.next())
						creditCardModel.setId(resultSet.getLong(1));
				}
			}
		} catch (Exception e) {
			LOGGER.error("Erro ao verificar creditCard: ", e);
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Inserir creditCard caso não exista.
	 * 
	 * @param creditCardModel
	 * @param con
	 * @throws Exception
	 */
	private void adicionarCreditCard(CreditCardModel creditCardModel) throws Exception {

		try (Connection con = ConnectionDao.getInstance().getConnection()) {
			con.setAutoCommit(false);
			conn = con;

			String[] returnId = { "id" };
			sql = new StringBuilder(200);
			sql.append(" INSERT INTO creditcard (cardnumber, cardholdername, value, cvv, expdate) ");
			sql.append(" VALUES (?, ?, ?, ?, ?) ");

			try (PreparedStatement preparedStatement = con.prepareStatement(sql.toString(), returnId)) {

				preparedStatement.setString(1, creditCardModel.getCardNumber());
				preparedStatement.setString(2, creditCardModel.getCardHolderName());
				preparedStatement.setInt(3, creditCardModel.getValue());
				preparedStatement.setInt(4, creditCardModel.getCvv());
				preparedStatement.setString(5, creditCardModel.getExpDate());

				preparedStatement.executeUpdate();

				try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
					while (resultSet.next())
						creditCardModel.setId(resultSet.getLong(1));
				}
			}
			con.commit();

		} catch (Exception e) {
			conn.rollback();
			LOGGER.error("Erro ao adicionar creditCard: ", e);
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<TransactionModel> buscar(TransactionModel transactionModel) throws Exception {
		List<TransactionModel> transactionModels = new ArrayList<>();

		boolean appendClientId = false;

		try (Connection con = ConnectionDao.getInstance().getConnection()) {
			con.setAutoCommit(false);

			sql = new StringBuilder(200);
			sql.append(" SELECT t.id, t.clientid, t.totaltopay, t.date, c.cardnumber, t.purchaseid ");
			sql.append(" FROM transaction t ");
			sql.append(" JOIN creditcard c ");
			sql.append(" ON t.creditcardid = c.id ");
			sql.append(" WHERE 1=1 ");

			if (transactionModel.getClientId() != null && !transactionModel.getClientId().equals("")) {
				sql.append(" AND clientid = ? ");
				appendClientId = true;
			}

			try (PreparedStatement preparedStatement = con.prepareStatement(sql.toString())) {

				if (appendClientId)
					preparedStatement.setString(1, transactionModel.getClientId());

				ResultSet resultSet = preparedStatement.executeQuery();

				while (resultSet.next()) {
					transactionModel = new TransactionModel();
					transactionModel.setId(resultSet.getLong(1));
					transactionModel.setClientId(resultSet.getString(2));
					transactionModel.setTotalToPay(resultSet.getInt(3));
					transactionModel.setDate(resultSet.getString(4));
					transactionModel.getCreditCard().setCardNumber(resultSet.getString(5).replaceAll ("\\d{4}\\d{4}\\d{4}", "**** **** **** "));
					transactionModel.setPurchaseId(resultSet.getString(6));
					
					transactionModels.add(transactionModel);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Erro no metodo buscar: ", e);
			throw new Exception(e.getMessage());
		}
		return transactionModels;
	}

}
