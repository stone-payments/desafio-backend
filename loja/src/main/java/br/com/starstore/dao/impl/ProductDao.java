package br.com.starstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import br.com.starstore.dao.BasicDao;
import br.com.starstore.dao.ConnectionDao;
import br.com.starstore.model.ProductModel;

@Repository
public class ProductDao implements BasicDao<ProductModel> {

	private static Logger LOGGER = Logger.getLogger(ProductDao.class);

	private Connection conn;
	private StringBuilder sql;

	@Override
	public void adicionar(ProductModel t) throws Exception {
		try (Connection con = ConnectionDao.getInstance().getConnection()) {
			con.setAutoCommit(false);
			conn = con;

			sql = new StringBuilder(200);
			sql.append(" INSERT INTO product (title, price, zipcode, seller, thumbnailhd, date) ");
			sql.append(" VALUES (?, ?, ?, ?, ?, ?) ");

			try (PreparedStatement preparedStatement = con.prepareStatement(sql.toString())) {

				preparedStatement.setString(1, t.getTitle());
				preparedStatement.setInt(2, t.getPrice());
				preparedStatement.setString(3, t.getZipcode());
				preparedStatement.setString(4, t.getSeller());
				preparedStatement.setString(5, t.getThumbnailHd());
				preparedStatement.setString(6, t.getDate());

				preparedStatement.execute();
				con.commit();
			}

		} catch (Exception e) {
			conn.rollback();
			LOGGER.error("Erro no metodo adicionar(ProductModel t): ", e);
			throw new Exception("Erro ao adicionar produto.");
		}
	}

	@Override
	public List<ProductModel> buscar(ProductModel productModel) throws Exception {

		List<ProductModel> productModels = new ArrayList<>();

		try (Connection con = ConnectionDao.getInstance().getConnection()) {
			con.setAutoCommit(false);

			sql = new StringBuilder(200);
			sql.append(" SELECT title, price, zipcode, seller, thumbnailhd, date FROM product ");

			try (PreparedStatement preparedStatement = con.prepareStatement(sql.toString())) {

				try (ResultSet resultSet = preparedStatement.executeQuery()) {

					while (resultSet.next()) {
						productModel = new ProductModel(resultSet.getString("title"), resultSet.getInt("price"),
								resultSet.getString("zipcode"), resultSet.getString("seller"),
								resultSet.getString("thumbnailhd"), resultSet.getString("date"));
						productModels.add(productModel);
					}
				}
			}

		} catch (Exception e) {
			LOGGER.error("Erro no metodo buscar(ProductModel productModel): ", e);
			throw new Exception("Erro ao tentar buscar produto.");
		}
		return productModels;
	}

}
