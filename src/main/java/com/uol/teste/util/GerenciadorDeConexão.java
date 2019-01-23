package com.uol.teste.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hsqldb.jdbc.JDBCPool;

public class GerenciadorDeConex�o {

		
		private final DataSource dataSource;

		public GerenciadorDeConex�o() {
			JDBCPool pool = new JDBCPool();
			pool.setUrl("jdbc:hsqldb:mem:cadastro");
			pool.setUser("SA");
			pool.setPassword("");
			dataSource = pool;
		}
		
		public Connection getConnection() throws SQLException {
			Connection connection = dataSource.getConnection();
			return connection;
		}	

	

}
