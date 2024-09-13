package appconsole;
/**
 * SI - POO - Prof. Fausto Ayres
 * Teste da Fachada
 * 
 */

import regras_negocio.Fachada;

public class Cadastrar {

	public Cadastrar() {
		try {
			Fachada.criarCorrentista();
			Fachada.criarCorrentista();

			Fachada.criarConta();
			Fachada.criarConta();

			Fachada.criarContaEspecial();
			Fachada.criarContaEspecial();
			
			System.out.println("Cadastrou");
		} catch (Exception e) {
			System.out.println("--->"+e.getMessage());
		}		
	}

	public static void main (String[] args) 
	{
		new Cadastrar();
	}
}


