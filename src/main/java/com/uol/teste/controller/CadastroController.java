package com.uol.teste.controller;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

import com.uol.teste.dao.JogadorDao;
import com.uol.teste.model.Jogador;
import com.uol.teste.util.GerenciadorDeConexão;

@Named
public class CadastroController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final String VINGADORES_URL = "https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/vingadores.json";
	private static final String LIGA_URL = "https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/liga_da_justica.xml";
	private static final String VINGADORES = "Vingadores";
	private static final String LIGA = "Liga da Justiça";
	private Predicate<Jogador> isVingadores = j -> j.getGrupo().equals(VINGADORES);
	private Predicate<Jogador> isLiga = j -> j.getGrupo().equals(LIGA);
	
	public void criarDatabase() {
		try (Connection con = new GerenciadorDeConexão().getConnection()) {
			JogadorDao jogadorDao = new JogadorDao(con);
			jogadorDao.criarDataBase();
		} catch (SQLException e) {
			 e.printStackTrace();
		}

	}

	public List<String> getVingadores() throws MalformedURLException, IOException {
		JSONArray jsonArray = getJsonObject();
		List<String> listaVingadores = new ArrayList<>();
	
		for (Object object : jsonArray) {
			listaVingadores.add(StringUtils.substringBetween(object.toString(), ":\"", "\""));
		}
		
		return listaVingadores;

	}

	public List<String> getLiga() throws MalformedURLException, IOException {
		List<String> listaLiga = new ArrayList<>();
		listaLiga = Arrays.asList(StringUtils.substringsBetween(getXMLString(), "<codinome>", "</codinome>"));
		return listaLiga;

	}

	private JSONArray getJsonObject() throws MalformedURLException, IOException {

		URL vingadoresRequest = new URL(VINGADORES_URL);
		URLConnection connection = vingadoresRequest.openConnection();
		connection.setDoOutput(true);

		Scanner scanner = new Scanner(vingadoresRequest.openStream(),Charset.forName("UTF-8"));
		String response = scanner.useDelimiter("\\Z").next();
		scanner.close();

		JSONObject json = new JSONObject(response.toString());
		return json.getJSONArray("vingadores");
	}

	private String getXMLString() throws MalformedURLException, IOException {
		URL ligaRequest = new URL(LIGA_URL);
		URLConnection connection = ligaRequest.openConnection();
		connection.setDoOutput(true);

		Scanner scanner = new Scanner(ligaRequest.openStream());
		String response = scanner.useDelimiter("\\Z").next();
		scanner.close();

		return response.toString();
	}

	public void salvarJogador(Jogador jogador) throws SQLException {
		try (Connection con = new GerenciadorDeConexão().getConnection()) {
			JogadorDao jogadorDao = new JogadorDao(con);
			jogadorDao.salvar(jogador);
		}

	}

	public List<Jogador> listarJogadores() throws SQLException {
		try (Connection con = new GerenciadorDeConexão().getConnection()) {
			JogadorDao jogadorDao = new JogadorDao(con);
			return jogadorDao.lista();
		}

	}

	public String getCodinome(String grupoEscolhido) throws SQLException, MalformedURLException, IOException {
		List<Jogador> jogadores = listarJogadores();

		if (grupoEscolhido.equals(VINGADORES)) {
			return getCodinomeVingadores(jogadores);
		} else {
			return getCodinomeLiga(jogadores);
		}
	}

	private String getCodinomeLiga(List<Jogador> jogadores) throws SQLException, MalformedURLException, IOException {
		List<Object> condinomesEmUsoLiga = jogadores.stream().filter(isLiga).map(Jogador::getCodinome).collect(Collectors.toList());

		if (CollectionUtils.isEmpty(condinomesEmUsoLiga)) {
			return getLiga().stream().findFirst().get();
		}

		return getLiga().stream().filter(l -> !condinomesEmUsoLiga.contains(l)).findAny().get();
	}

	private String getCodinomeVingadores(List<Jogador> jogadores) throws MalformedURLException, IOException {
		List<String> condinomesVingadoresEmUso = jogadores.stream().filter(isVingadores).map(Jogador::getCodinome).collect(Collectors.toList());
		
		if (CollectionUtils.isEmpty(condinomesVingadoresEmUso)) {
			return getVingadores().stream().findFirst().get();
		}

		return getVingadores().stream().filter(l -> !condinomesVingadoresEmUso.contains(l)).findAny().get();
	}


	public void excluir(Jogador jogador) throws SQLException {
		try (Connection con = new GerenciadorDeConexão().getConnection()) {
			JogadorDao jogadorDao = new JogadorDao(con);
			jogadorDao.excluir(jogador);
		}
	}

	public void editarJogador(Jogador jogador) throws SQLException {
		try (Connection con = new GerenciadorDeConexão().getConnection()) {
			JogadorDao jogadorDao = new JogadorDao(con);
			jogadorDao.editar(jogador);
		}
	}

}
