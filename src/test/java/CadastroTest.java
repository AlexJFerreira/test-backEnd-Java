import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;

import javax.inject.Named;

import org.junit.BeforeClass;
import org.junit.Test;

import com.uol.teste.controller.CadastroController;
import com.uol.teste.model.Jogador;

@Named
public class CadastroTest {
	
	static CadastroController controlador;
	
	@BeforeClass
	    public static void init() throws SQLException, ClassNotFoundException, IOException {	 
		 controlador = new CadastroController();
		 controlador.criarDatabase();		
	    }

	//Teria sido mais legal e elegante fazer um builder para criar esses objetos Jogador, mas não consegui fazer a tempo :( 
	@Test(expected = NoSuchElementException.class)
	public void testaLimiteCadastroCodinomes() throws MalformedURLException, IOException, SQLException {
		Jogador jogador1 = new Jogador();
		jogador1.setGrupo("Vingadores");
		jogador1.setCodinome(controlador.getCodinome("Vingadores"));
		controlador.salvarJogador(jogador1);
		
		Jogador jogador2 = new Jogador();
		jogador2.setGrupo("Vingadores");
		jogador2.setCodinome(controlador.getCodinome("Vingadores"));
		controlador.salvarJogador(jogador2);

		Jogador jogador3 = new Jogador();
		jogador3.setGrupo("Vingadores");
		jogador3.setCodinome(controlador.getCodinome("Vingadores"));
		controlador.salvarJogador(jogador3);
		
		Jogador jogador4 = new Jogador();
		jogador4.setGrupo("Vingadores");
		jogador4.setCodinome(controlador.getCodinome("Vingadores"));
		controlador.salvarJogador(jogador4);
		
		Jogador jogador5 = new Jogador();
		jogador5.setGrupo("Vingadores");
		jogador5.setCodinome(controlador.getCodinome("Vingadores"));
		controlador.salvarJogador(jogador5);
		
		Jogador jogador6 = new Jogador();
		jogador6.setGrupo("Vingadores");
		jogador6.setCodinome(controlador.getCodinome("Vingadores"));
		controlador.salvarJogador(jogador6);
		
		Jogador jogador7 = new Jogador();
		jogador7.setGrupo("Vingadores");
		jogador7.setCodinome(controlador.getCodinome("Vingadores"));
		controlador.salvarJogador(jogador7);
		
		Jogador jogador8 = new Jogador();
		jogador8.setGrupo("vingadores");
		jogador8.setCodinome(controlador.getCodinome("Vingadores"));
		controlador.salvarJogador(jogador8);
		
		Jogador jogador9 = new Jogador();
		jogador9.setGrupo("Vingadores");
		jogador9.setCodinome(controlador.getCodinome("Vingadores"));
		controlador.salvarJogador(jogador9);
	}
	
	@Test(expected = SQLIntegrityConstraintViolationException.class)
	public void testaUnicidadeCodinomeGrupo() throws MalformedURLException, SQLException, IOException  {
		Jogador jogadorDuplo = new Jogador();
		jogadorDuplo.setGrupo("Vingadores");
		jogadorDuplo.setCodinome("Hulk");
		controlador.salvarJogador(jogadorDuplo);
		
	}

}
