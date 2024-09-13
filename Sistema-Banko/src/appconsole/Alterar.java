package appconsole;

/**
 * SI - POO - Prof. Fausto Ayres
 * Teste da Fachada
 * 
 */

import regras_negocio.Fachada;

public class Alterar {

	public Alterar() {
		try {
			Fachada.adicionarContaCorrentista("joao", 1);
			Fachada.adicionarContaCorrentista("maria", 1);
			Fachada.adicionarContaCorrentista("jose", 1);
			Fachada.adicionarContaCorrentista("ana", 1);
			Fachada.adicionarContaCorrentista("ana", 1);
			
			Fachada.adicionarContaCorrentista("joao", 2);
			Fachada.adicionarContaCorrentista("maria", 2);
			System.out.println("adicionou participantes aos eventos");
		} catch (Exception e) {
			System.out.println("--->" + e.getMessage());
		}

		try {
			Fachada.adiarEvento("24/10/2024", "30/10/2024");
			System.out.println("adiou evento");

		} catch (Exception e) {
			System.out.println("--->" + e.getMessage());
		}
	}

	public static void main(String[] args) {
		new Alterar();
	}
}
