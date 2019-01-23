package com.uol.teste.view;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.uol.teste.controller.CadastroController;
import com.uol.teste.model.Jogador;

@Named
@ViewScoped
public class ConsultaJogadoresMB implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Jogador> jogadores;
	private Jogador jogadorEdicao;

	private static final String CADASTRO = "cadastroJogador.xhtml";

    @Inject CadastroController controlador;
    
    @PostConstruct
    public void init() {
    	try {
			jogadores = controlador.listarJogadores();
		} catch (SQLException e) {

		}
    }
    
	//Publicos---------------------------------------------------------------
    public String excluirJogador(Jogador jogador) {
    	try {
			controlador.excluir(jogador);
			return "consultaJogador.xhtml";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
    }
    
    public void editaJogador(Jogador jogador) {
    	jogadorEdicao = jogador;
    }

    
    public String salvarEdicao() {
    	try {
			controlador.editarJogador(jogadorEdicao);
			return "consultaJogador.xhtml";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
    }
    
    public String cadastrar() {
    	return CADASTRO;
    }
    
	//Propriedades---------------------------------------------------------------
	public List<Jogador> getJogadores() {
		return jogadores;
	}

	public void setJogadores(List<Jogador> jogadores) {
		this.jogadores = jogadores;
	}

	public Jogador getJogadorEdicao() {
		return jogadorEdicao;
	}

	public void setJogadorEdicao(Jogador jogadorEdicao) {
		this.jogadorEdicao = jogadorEdicao;
	}
    
    
}
