package modelo;

import java.util.ArrayList;

public class Conta { 
	protected int id;
	protected String data;
	protected double saldo;
	private ArrayList<Correntista> correntistas = new ArrayList<>();
	
	public Conta(int id, double saldo, String data) {
		super();
		this.id = id;
		this.saldo = saldo;
		this.data = data;
	}

	public void adicionar(Correntista c){
		correntistas.add(c);
	}
	public void remover(Correntista c){
		correntistas.remove(c);
	}
	public Correntista localizar(String CPF){
		for(Correntista c : correntistas){
			if(c.getCPF().equals(CPF))
				return c;
		}
		return null;
	}
	

	public void creditar(double valor) {
		this.saldo += valor;
	}

	public void debitar(double valor) throws Exception {
		if(!(this.saldo - valor <= 0)) {
			this.saldo -= valor;
		} else {
			throw new Exception ("debitar valor: não é possivel debitar esse valor.");
		}
	}

	public void transferir(double valor, Conta destino) throws Exception {
		this.debitar(valor);
		destino.creditar(valor);
	}
	

	@Override
	public String toString() {
		String texto = "Conta: id=" + id + ", data=" + data + ", saldo=" + saldo;
		
		texto += "correntistas: ";
		for(Correntista c : correntistas)
			texto += c.getCPF() + ", ";
		return texto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public ArrayList<Correntista> getCorrentistas() {
		return correntistas;
	}

}
