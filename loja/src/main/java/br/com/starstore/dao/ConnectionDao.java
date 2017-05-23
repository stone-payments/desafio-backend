package br.com.starstore.dao;

import java.sql.Connection;

import org.postgresql.ds.PGConnectionPoolDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionDao {

	private static Logger LOGGER = LoggerFactory.getLogger(ConnectionDao.class);

	private PGConnectionPoolDataSource pool;
	private static ConnectionDao connectionDao;

	public ConnectionDao() {
		final String DBURL = System.getenv("DATABASE_URL");
		final String USER = (DBURL.split("//")[1]).split(":")[0];
		final String PASSWORD = ((DBURL.split("//")[1]).split(":")[1]).split("@")[0];
		
		PGConnectionPoolDataSource pool = new PGConnectionPoolDataSource();

		pool.setUrl("jdbc:postgresql://ec2-54-197-232-155.compute-1.amazonaws.com:5432/df12445hrn1eji?sslmode=require&"
					.concat("user=").concat(USER)
					.concat("&password=").concat(PASSWORD));
		
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

	public static void main(String[] args) throws Exception {
		ConnectionDao.getInstance().getConnection();
		System.out.println("OK");
	}

}
