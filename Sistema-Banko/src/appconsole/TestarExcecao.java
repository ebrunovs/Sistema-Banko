package appconsole;
/**
 * SI - POO - Prof. Fausto Ayres
 * Teste da Fachada
 *
 */

import regras_negocio.Fachada;

public class TestarExcecao {

	public static void main (String[] args) {

		System.out.println("\n-------TESTE DE EXCE��ES LAN�ADAS PELOS METODOS DA FACHADA--------");
		try {
			Fachada.criarConta("p1@gmail.com","p1", 10);
			Fachada.criarConta("p1@gmail.com","p1", 10);
			System.out.println("*************1--->Nao lan�ou exce��o para: criar participante existente "); 
		}catch (Exception e) {System.out.println("1ok--->"+e.getMessage());}

		try {
			Fachada.criarContaEspecial("c1@gmail.com","c1", 30, "empresa");
			Fachada.criarContaEspecial("c1@gmail.com","c1", 30, "empresa");
			System.out.println("*************2--->Nao lan�ou exce��o para: criar convidado existente "); 
		}catch (Exception e) {System.out.println("2ok--->"+e.getMessage());}

		try {
			Fachada.criarCorrentista("01/01/2030","e1", 100);
			Fachada.criarCorrentista("01/01/2030","e1", 100);
			System.out.println("*************3--->Nao lan�ou exce��o para: criar evento existente "); 
		}catch (Exception e) {System.out.println("3ok--->"+e.getMessage());}

		try {
			Fachada.criarCorrentista("02/01/2030","e2", -10.0);
			System.out.println("*************4--->Nao lan�ou exce��o para: criar evento preco negativo "); 
		}catch (Exception e) {System.out.println("4ok--->"+e.getMessage());}

		try {
			Fachada.criarCorrentista("03/01/2030","e3", 0.0);
			Fachada.adiarCorrentista("03/01/2030","01/01/2030");
			System.out.println("*************5--->Nao lan�ou exce��o para: adiar evento data existente"); 
		}catch (Exception e) {System.out.println("5ok--->"+e.getMessage());}

		try 
		{
			Fachada.adicionarContaCorrentista("p1", 1);	
			Fachada.adicionarContaCorrentista("p1", 1);	
			System.out.println("*************6--->Nao lan�ou exce��o: adicionar participante evento que participa"); 
		}catch (Exception e) {System.out.println("6ok--->"+e.getMessage());}

		try 
		{
			Fachada.removerContaCorrentista("p1", 1);	
			Fachada.removerContaCorrentista("p1", 1);	
			System.out.println("*************7--->Nao lan�ou exce��o: remover participante evento que nao participa"); 
		}catch (Exception e) {System.out.println("7ok--->"+e.getMessage());}

		try 
		{
			Fachada.adicionarContaCorrentista("p2", 1);	
			System.out.println("*************8--->Nao lan�ou exce��o: adicionar participante inexistente"); 
		}catch (Exception e) {System.out.println("8ok--->"+e.getMessage());}

		try 
		{
			Fachada.removerContaCorrentista("p2", 1);	
			System.out.println("*************9--->Nao lan�ou exce��o: remover participante inexistente "); 
		}catch (Exception e) {System.out.println("9ok--->"+e.getMessage());}

		try 
		{
			Fachada.apagarCorrentista("03/01/2030");	
			Fachada.apagarCorrentista("03/01/2030");	
			System.out.println("*************10--->Nao lan�ou exce��o: apagar evento inexistente"); 
		}catch (Exception e) {System.out.println("10ok--->"+e.getMessage());}

		try 
		{
			Fachada.apagarConta("p2");	
			System.out.println("*************11--->Nao lan�ou exce��o: apagar participante inexistente"); 
		}catch (Exception e) {System.out.println("11ok--->"+e.getMessage());}

		//apagar dados usados no teste
		try {Fachada.apagarCorrentista("01/01/2030");}	catch (Exception e){}		
		try {Fachada.apagarCorrentista("02/01/2030");}	catch (Exception e){}		
		try {Fachada.apagarCorrentista("03/01/2030");}	catch (Exception e) {}		
		try {Fachada.apagarConta("p1");} catch (Exception e){}
		try {Fachada.apagarConta("p2");} catch (Exception e){}
		try {Fachada.apagarConta("c1");} catch (Exception e){}
	}
}


