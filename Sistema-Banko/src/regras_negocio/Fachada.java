package regras_negocio;
/**********************************
 * POO - Fausto Ayres
 **********************************/

import java.util.ArrayList;
import java.util.regex.Pattern;

import modelo.ContaEspecial;
import modelo.Correntista;
import modelo.Conta;
import repositorio.Repositorio;

public class Fachada {
	private Fachada() {}		
	private static Repositorio repositorio = new Repositorio();	
	
	public static ArrayList<Correntista> listarCorrentistas() 	{
		return repositorio.getCorrentistas();
	}
	public static ArrayList<Conta> listarContas(String nome) {
		return repositorio.getContas();
	}
	
	public static void criarCorrentista (String CPF, String nome, String senha) throws Exception {
		CPF = CPF.trim();
		nome = nome.trim();
		senha = senha.trim();

		if (senha.length() != 4) {
			throw new Exception ("criar correntista: senha deve ter 4 digitos.");
		}
		
		if(!Pattern.matches("[0-9]",senha)) {
			throw new Exception ("criar correntista: senha deve ser numerica.");
		}
		
		Correntista c = new Correntista(CPF, nome, senha) ;
		
		repositorio.adicionar(c);
		//repositorio.salvarObjetos();
		
	}
	
	public static void criarConta(String CPF) throws Exception {
		CPF = CPF.trim();
		
		Correntista correntista = repositorio.localizarCorrentista(CPF);
		if(correntista==null) {
			throw new Exception ("criar conta: não há o correntista para vincular.");
		}
		
		int id = repositorio.gerarIdConta();
		String data = repositorio.gerarData();
		
		Conta conta = new Conta(id,0,data);
		conta.adicionar(correntista);
		correntista.adicionar(conta);
		
		repositorio.adicionar(conta);
	}	

	public static void criarContaEspecial(String CPF, double limite) throws Exception {
		CPF = CPF.trim();
		
		Correntista correntista = repositorio.localizarCorrentista(CPF);
		if(correntista==null) {
			throw new Exception ("criar conta especial: não há o correntista para vincular.");
		}
		if(!(limite >= 50)) {
			throw new Exception ("criar conta especial: o limite nao pode ser inferior a 50.");
		}
		
		int id = repositorio.gerarIdConta();
		String data = repositorio.gerarData();
		
		ContaEspecial conta = new ContaEspecial(id,0,data,limite);
		conta.adicionar(correntista);
		correntista.adicionar(conta);
		
		repositorio.adicionar(conta);
	}
	
	public static void inserirCorrentistaConta(int id,String CPF) throws Exception {
		CPF = CPF.trim();
		
		Correntista correntista = repositorio.localizarCorrentista(CPF);
		Conta conta = repositorio.localizarConta(id);
		if(correntista==null) {
			throw new Exception ("inserir correntista conta: não existe o correntista para vincular.");
		}
		
		if(conta==null) {
			throw new Exception ("inserir correntista conta: não existe a conta para vincular.");
		}
		
		correntista.adicionar(conta);
		conta.adicionar(correntista);
	}
	
	/*
	 * public static void adicionarParticipanteEvento(String nome, int id) throws
	 * Exception { nome = nome.trim();
	 * 
	 * //localizar participante no repositorio, usando o nome Conta p =
	 * repositorio.localizarParticipante(nome); if(p == null) throw new
	 * Exception("adicionar participante:  " + nome + " inexistente");
	 * 
	 * //localizar evento no repositorio, usando id Correntista ev =
	 * repositorio.localizarEvento(id); if(ev == null) throw new
	 * Exception("adicionar participante: evento " + id + " inexistente");
	 * 
	 * //localizar o participante no evento, usando o nome Conta paux =
	 * ev.localizar(nome); if(paux != null) throw new
	 * Exception("N�o adicionou participante: " + nome + " j� participa do evento "
	 * + id);
	 * 
	 * //adicionar o participante ao evento ev.adicionar(p); //adicionar o evento ao
	 * participante p.adicionar(ev); //gravar reposit�rio
	 * repositorio.salvarObjetos(); }
	 * 
	 * public static void removerParticipanteEvento(String nome, int id) throws
	 * Exception { nome = nome.trim();
	 * 
	 * //localizar participante no repositorio, usando o nome Conta p =
	 * repositorio.localizarParticipante(nome); if(p == null) throw new
	 * Exception("remover participante: participante " + nome + " inexistente");
	 * 
	 * 
	 * //localizar evento no repositorio, usando id Correntista ev =
	 * repositorio.localizarEvento(id); if(ev == null) throw new
	 * Exception("remover participante: evento " + id + " inexistente");
	 * 
	 * 
	 * //localizar o participante no evento, usando o nome Conta paux =
	 * ev.localizar(nome); if(paux == null) throw new
	 * Exception("remover participante: " + nome + " nao participa do evento " +
	 * id);
	 * 
	 * //remover o participante do evento ev.remover(p); //remover o evento do
	 * participante p.remover(ev); //gravar reposit�rio repositorio.salvarObjetos();
	 * }
	 * 
	 * public static void apagarEvento(String data) throws Exception { //localizar
	 * evento no repositorio, usando id Correntista ev =
	 * repositorio.localizarEvento(data); if (ev == null) throw new
	 * Exception("apagar evento: data " + data + " inexistente");
	 * 
	 * //Remover todos os participantes deste evento for(Conta p :
	 * ev.getParticipantes()) { p.remover(ev); } ev.getParticipantes().clear();
	 * 
	 * //remover evento do reposit�rio repositorio.remover(ev); //gravar reposit�rio
	 * repositorio.salvarObjetos(); }
	 * 
	 * public static void adiarEvento(String data, String novadata) throws Exception
	 * { //localizar evento no repositorio, usando data Correntista ev =
	 * repositorio.localizarEvento(data); if (ev == null) throw new
	 * Exception("adiar evento: data inexistente " + data);
	 * 
	 * //localizar evento no repositorio, usando novadata Correntista ev2 =
	 * repositorio.localizarEvento(novadata); if (ev2 != null) throw new
	 * Exception("adiar evento: ja tem evento na nova data " + novadata);
	 * 
	 * //alterar a data do evento ev.setData(novadata); //gravar reposit�rio
	 * repositorio.salvarObjetos(); }
	 * 
	 * public static void apagarParticipante(String nome) throws Exception { nome =
	 * nome.trim();
	 * 
	 * //localizar participante no repositorio, usando o nome Conta p =
	 * repositorio.localizarParticipante(nome); if(p == null) throw new
	 * Exception("apagar participante: participante " + nome + " inexistente");
	 * 
	 * //participante nao pode ser deletado caso participe de algum evento
	 * if(!p.getEventos().isEmpty()) throw new
	 * Exception("apagar participante: participante " + nome + " ainda tem evento");
	 * 
	 * //remover o participante do repositorio repositorio.remover(p); //gravar
	 * reposit�rio repositorio.salvarObjetos(); }
	 */
}
