package br.com.starstore.dao;

import java.sql.Connection;

import org.apache.log4j.Logger;
import org.postgresql.ds.PGConnectionPoolDataSource;

public class ConnectionDao {

	private static Logger LOGGER = Logger.getLogger(ConnectionDao.class);

	// private static final String DRIVER = "org.postgresql.Driver";
	private static final String USER = "postgres";
	private static final String PASSWORD = "treinamento";
	private static final String URL = "jdbc:postgresql://localhost:5432/desafioStone";

	private PGConnectionPoolDataSource pool;
	private static ConnectionDao connectionDao;

	public ConnectionDao() {
		PGConnectionPoolDataSource pool = new PGConnectionPoolDataSource();
		pool.setUrl(URL);
		pool.setUser(USER);
		pool.setPassword(PASSWORD);
		this.pool = pool;
	}

	public static ConnectionDao getInstance() {
		return connectionDao == null ? new ConnectionDao() : connectionDao;
	}

	public Connection getConnection() throws Exception {
		try {
			return pool.getConnection();
		} catch (Exception e) {
			LOGGER.error("Erro ao tentar conectar com o banco de dados:", e);
			throw new Exception("Erro ao tentar conectar com o banco de dados.");
		}
	}
}
