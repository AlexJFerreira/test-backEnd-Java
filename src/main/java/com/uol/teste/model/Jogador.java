package com.uol.teste.model;

import java.io.Serializable;

import javax.inject.Named;

@Named
public class Jogador implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String nome;
	private String email;
	private String telefone;
	private String codinome;
	private String grupo;
	
	public String getNome() {
		return nome;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public String getCodinome() {
		return codinome;
	}
	
	public String getGrupo() {
		return grupo;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public void setCodinome(String codinome) {
		this.codinome = codinome;
	}
	
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	@Override
	public String toString() {
		return "Jogador [nome=" + nome + ", email=" + email + ", telefone=" + telefone + ", codinome=" + codinome
				+ ", grupo=" + grupo + "]";
	}
	

}
