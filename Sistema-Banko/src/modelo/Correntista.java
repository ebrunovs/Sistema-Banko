/**
 * SI - POO - Prof. Fausto Ayres
 *
 */
package modelo;

import java.util.ArrayList;

public class Correntista {
	private String CPF;
	private String nome;
	private String senha;
	private ArrayList<Conta> contas = new ArrayList<>();

	public Correntista(String CPF, String nome, String senha) {
		super();
		this.CPF = CPF;
		this.nome = nome;
		this.senha = senha;
	}
	public void adicionar(Conta c){
		contas.add(c);
	}
	public void remover(Conta c){
		contas.remove(c);
	}
	public Conta localizar(int id){
		for(Conta c : contas){
			if(c.getId() == id)
				return c;
		}
		return null;
	}
	
	@Override
	public String toString() {
		String texto =  "Correntista: CPF=" + CPF + ", nome=" + nome + ", senha=" + senha;
		
		texto += ", contas: ";
		for(Conta c : contas)
			texto += c.getId() + ", ";
		return texto;
	}

	public double getSaldoTotal() {
		double total = 0;
		for(Conta c : contas) {
			total += c.getSaldo();
		}
		return total;
	}
	
	public String getCPF() {
		return CPF;
	}
	public void setCPF(String cPF) {
		CPF = cPF;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public ArrayList<Conta> getContas() {
		return contas;
	}

}
