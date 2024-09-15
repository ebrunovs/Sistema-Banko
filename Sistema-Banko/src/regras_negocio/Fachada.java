package regras_negocio;
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
		repositorio.salvarObjetos();
	}
	
	public static void criarConta(String CPF) throws Exception {
		CPF = CPF.trim();
		
		Correntista correntista = repositorio.localizarCorrentista(CPF);
		for (Conta c: repositorio.getContas()) {
			if(c.getCorrentistas().get(0).getCPF().equals(CPF)) {
				throw new Exception ("criar conta: o correntista não pode ser vinculado, pois já é titular em outra conta.");
			};
		}
		if(correntista==null) {
			throw new Exception ("criar conta: não há o correntista para vincular titulo.");
		}
		
		int id = repositorio.gerarIdConta();
		String data = repositorio.gerarData();
		
		Conta conta = new Conta(id,0,data);
		conta.adicionar(correntista);
		correntista.adicionar(conta);
		
		repositorio.adicionar(conta);
		repositorio.salvarObjetos();
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
		repositorio.salvarObjetos();
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
	
	public static void removerCorrentistaConta(int id,String CPF) throws Exception {
		CPF = CPF.trim();
		
		Correntista correntista = repositorio.localizarCorrentista(CPF);
		Conta conta = repositorio.localizarConta(id);
		if(correntista==null) {
			throw new Exception ("remover correntista conta: não existe o correntista para remover vinculo.");
		}
		
		if(conta==null) {
			throw new Exception ("remover correntista conta: não existe a conta para remover vinculo.");
		}
		for (Conta c: repositorio.getContas()) {
			if(c.getId() == id) {				
				if(c.getCorrentistas().get(0).getCPF().equals(CPF)) {
					throw new Exception ("remover correntista: o correntista não pode ser removido, pois ele é o titular da conta.");
				};
			}
		}
		
		correntista.remover(conta);
		conta.remover(correntista);
	}
	
	public static void apagarConta(int id) throws Exception{
		Conta conta = repositorio.localizarConta(id);
		
		if(conta==null) {
			throw new Exception ("apagar conta: conta inexistente.");
		}
		
		for(Conta c: repositorio.getContas()) {
			if(c.getId() == id && c.getSaldo() != 0) {
				throw new Exception ("apagar conta: a conta nao pode ser apagada pois seu saldo não é zero.");
			}
		}
		
		for (Correntista c: conta.getCorrentistas()) {
			c.remover(conta);
		}
		
		conta.getCorrentistas().clear();
		
		repositorio.remover(conta);
	}

	public static void creditarValor(int id, String CPF, String senha, double valor ) throws Exception {
		CPF = CPF.trim();
		senha = senha.trim();
		
		Correntista correntista = repositorio.localizarCorrentista(CPF);
		if(correntista==null) {
			throw new Exception ("creditar valor: correntista inexistente.");
		}
		if(!correntista.getSenha().equals(senha)) {
			throw new Exception ("creditar valor: senha incorreta.");
		}
		
		for(Conta c: correntista.getContas()) {
			if(c.getId() == id) {
				if(c instanceof ContaEspecial && c.getSaldo()-valor >= (-((ContaEspecial) c).getLimite()) ) {
					c.creditar(valor);
				}
				else if(c.getSaldo()-valor >= 0) {
					c.creditar(valor);
				}
				else {
					throw new Exception ("creditar valor: não é possivel creditar esse valor.");
				}
			}
		}
	}
	
	public static void debitarValor(int id, String CPF, String senha, double valor ) throws Exception {
		CPF = CPF.trim();
		senha = senha.trim();
		
		Correntista correntista = repositorio.localizarCorrentista(CPF);
		if(correntista==null) {
			throw new Exception ("debitar valor: correntista inexistente.");
		}
		if(!correntista.getSenha().equals(senha)) {
			throw new Exception ("debitar valor: senha incorreta.");
		}
		
		for(Conta c: correntista.getContas()) {
			if(c.getId() == id) {
				c.debitar(valor);
			}
		}
	}
	
	public static void transferirValor(int id1, String CPF, String senha, double valor,int id2 ) throws Exception {
		CPF = CPF.trim();
		senha = senha.trim();
		
		Correntista correntista = repositorio.localizarCorrentista(CPF);
		if(correntista==null) {
			throw new Exception ("transferir valor: correntista inexistente.");
		}
		if(!correntista.getSenha().equals(senha)) {
			throw new Exception ("transferir valor: senha incorreta.");
		}
		
		for(Conta c1: correntista.getContas()) {
			if(c1.getId() == id1) {
				c1.creditar(valor);
				for(Conta c2: correntista.getContas()) {
					if(c2.getId() == id2) {
						c2.debitar(valor);
					}
				}
			}
		}
	}
}
