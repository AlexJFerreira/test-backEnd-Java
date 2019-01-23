package com.uol.teste.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import com.uol.teste.model.Jogador;

@Named
public class JogadorDao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final Connection connection;

	public JogadorDao(Connection connection) {
		this.connection = connection;
	}
	
	public void criarDataBase() throws SQLException {
	      Statement  stmt1 = connection.createStatement();

		 stmt1.executeUpdate("CREATE TABLE IF NOT EXISTS jogador (\r\n" + 
				"   nome VARCHAR(50),\r\n" + 
				"   email VARCHAR(320) ,\r\n" + 
				"   telefone VARCHAR(25) ,\r\n" + 
				"   codinome VARCHAR(50) NOT NULL,\r\n" + 
				"   grupo VARCHAR(50) NOT NULL,\r\n" + 
				"  PRIMARY KEY(codinome, grupo) \r\n" + 
				");");
	}

	public void salvar(Jogador jogador) throws SQLException {

		
		try (PreparedStatement stmt = connection.prepareStatement("insert into jogador(nome, email, telefone, codinome, grupo) values (?, ?, ?, ?, ?)")) {
			stmt.setString(1, jogador.getNome());
			stmt.setString(2, jogador.getEmail());
			stmt.setString(3, jogador.getTelefone());
			stmt.setString(4, jogador.getCodinome());
			stmt.setString(5, jogador.getGrupo());
			stmt.execute();		
		}
		
	}
	
	public List<Jogador> lista() throws SQLException {

		String sql = "select * from jogador";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.execute();
			ResultSet resultSet = stmt.getResultSet();
			ArrayList<Jogador> jogadores = new ArrayList<>();
			
			while(resultSet.next()) {
				Jogador jogador = new Jogador();

				jogador.setNome(resultSet.getString("nome"));
				jogador.setGrupo(resultSet.getString("grupo"));
				jogador.setEmail(resultSet.getString("email"));
				jogador.setCodinome(resultSet.getString("codinome"));
				jogador.setTelefone(resultSet.getString("telefone"));
				jogadores.add(jogador);
			}
			return jogadores;
		}
	}

	public void excluir(Jogador jogador) throws SQLException {
		PreparedStatement  stmt = connection.prepareStatement("Delete from jogador where codinome = (?) and grupo = (?)");
		stmt.setString(1, jogador.getCodinome());
		stmt.setString(2, jogador.getGrupo());
		 stmt.executeUpdate();		
	}

	public void editar(Jogador jogador) throws SQLException {
		PreparedStatement  stmt = connection.prepareStatement("Update jogador set nome = (?), email = (?), telefone = (?) where codinome = (?) and grupo = (?)");
		stmt.setString(1, jogador.getNome());
		stmt.setString(2, jogador.getEmail());
		stmt.setString(3, jogador.getTelefone());
		stmt.setString(4, jogador.getCodinome());
		stmt.setString(5, jogador.getGrupo());
		stmt.executeUpdate();	
	}

}
