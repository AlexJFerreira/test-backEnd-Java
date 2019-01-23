package com.uol.teste.view;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.uol.teste.controller.CadastroController;
import com.uol.teste.model.Jogador;

@Named
@ViewScoped
public class CadastroJogadorMB implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String CONSULTA_LISTA = "consultaJogador.xhtml";
	private Jogador jogador;

	@Inject
	CadastroController controlador;

	@PostConstruct
	public void init() {
		controlador.criarDatabase();
		inicializaNovoJogador();
	}

	//Publicos---------------------------------------------------------------
	public void cadastrar() {
		try {
			jogador.setCodinome(controlador.getCodinome(jogador.getGrupo()));
			controlador.salvarJogador(jogador);
			inicializaNovoJogador();
			exibeMensagemSucesso();
		} catch (NoSuchElementException | SQLException | IOException e) {
			exibeMensagemFalha();
		}

	}

	//Privados---------------------------------------------------------------
	private void exibeMensagemFalha() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage("Falha: ",
				"Nenhum herói do grupo " + jogador.getGrupo() + " disponivel");
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		context.addMessage(null, message);
	}

	private void exibeMensagemSucesso() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage("Sucesso: ", "Cadastro efetuado.");
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		context.addMessage(null, message);
	}
	
	private void inicializaNovoJogador() {
		jogador = new Jogador();
		jogador.setGrupo("Vingadores");
	}

	public String consulta() {
		return CONSULTA_LISTA;
	}
	
	//Propriedades---------------------------------------------------------------
	public Jogador getJogador() {
		return jogador;
	}

	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}

}
