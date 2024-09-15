package modelo;

public class ContaEspecial extends Conta {
	private double limite;

	public ContaEspecial(int id, double saldo, String data, double limite) {
		super(id, saldo, data);
		this.limite = limite;
	}

	public void debitar(double valor) {
		this.saldo += valor;
	}
	
	@Override
	public String toString() {
		String texto = "Conta: id=" + id + ", data=" + data + ", saldo=" + saldo + " ,limite=" + limite;
		
		texto += "correntistas: ";
		for(Correntista c : getCorrentistas())
			texto += c.getCPF() + ", ";
		return texto;
	}

	public double getLimite() {
		return limite;
	}

	public void setLimite(double limite) {
		this.limite = limite;
	}


}
