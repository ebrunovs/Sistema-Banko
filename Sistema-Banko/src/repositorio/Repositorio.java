package repositorio;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

import modelo.ContaEspecial;
import modelo.Correntista;
import modelo.Conta;

public class Repositorio {
	private ArrayList<Conta> contas = new ArrayList<>();
	private ArrayList<Correntista> correntistas = new ArrayList<>(); 

	public Repositorio() {
		carregarObjetos();
	}
	public void adicionar(Conta c)	{
		contas.add(c);
	}

	public void remover(Conta c)	{
		contas.remove(c);
	}

	public Conta localizarConta(int id)	{
		for(Conta c : contas)
			if(c.getId() == id)
				return c;
		return null;
	}

	public void adicionar(Correntista c)	{
		correntistas.add(c);
		ArrayList<Correntista> ordenarCorrentistas = new ArrayList<>(this.correntistas);
		ordenarCorrentistas.sort(Comparator.comparing(Correntista::getCPF));
	}
	public void remover(Correntista c)	{
		correntistas.remove(c);
	}

	public Correntista localizarCorrentista(String CPF)	{
		for(Correntista c : correntistas)
			if(c.getCPF().equals(CPF))
				return c;
		return null;
	}

	public ArrayList<Conta> getContas() 	{
		return contas;
	}
	
	public ArrayList<Correntista> getCorrentistas() 	{
		return correntistas;
	}

	public int gerarIdConta() {
		if (contas.isEmpty())
			return 1;
		else {
			Conta ultimo = contas.get(contas.size()-1);
			return ultimo.getId() + 1;
		}
	}
	
	public String gerarData() {
		LocalDate hoje = LocalDate.now();
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dataFormatada = hoje.format(formato);
		return dataFormatada;
	}
	
	public void carregarObjetos()  	{
		try {
			File f1 = new File( new File(".\\correntistas.csv").getCanonicalPath() ) ; 
			File f2 = new File( new File(".\\contas.csv").getCanonicalPath() ) ; 
			if (!f1.exists() || !f2.exists() ) {
				FileWriter arquivo1 = new FileWriter(f1); arquivo1.close();
				FileWriter arquivo2 = new FileWriter(f2); arquivo2.close();
				return;
			}
		}
		catch(Exception ex)		{
			throw new RuntimeException("criacao dos arquivos vazios:"+ex.getMessage());
		}

		String linha;	
		String[] partes;	
		Correntista corr;
		Conta con;

		try	{
			String cpf, nome, senha, ids;
			File f = new File( new File(".\\correntista.csv").getCanonicalPath() )  ;
			Scanner arquivo1 = new Scanner(f);	 
			while(arquivo1.hasNextLine()) 	{
				linha = arquivo1.nextLine().trim();		
				partes = linha.split(";");	
				//System.out.println(Arrays.toString(partes));
				cpf = partes[0];
				nome = partes[1];
				senha = partes[2];
				ids = partes[3];
				corr = new Correntista(cpf, nome, senha);
				this.adicionar(corr);
				
				if(!ids.isEmpty()) {	
					for(String idconta : ids.split(",")){	//converter string em array
						con = this.localizarConta(Integer.parseInt(idconta));
						con.adicionar(corr);
						corr.adicionar(con);
					}
				}
			} 
			arquivo1.close();
		}
		catch(Exception ex)		{
			throw new RuntimeException("leitura arquivo de eventos:"+ex.getMessage());
		}

		try	{
			String tipo,id,data, saldo, limite;
			File f = new File( new File(".\\contas.csv").getCanonicalPath())  ;
			Scanner arquivo2 = new Scanner(f);	 
			while(arquivo2.hasNextLine()) 	{
				linha = arquivo2.nextLine().trim();	
				partes = linha.split(";");
				tipo = partes[0];
				id = partes[1];
				data = partes[2];
				saldo = partes[3];
				if(tipo.equals("ESPECIAL")) {
					limite = partes[4];
					con = new ContaEspecial(Integer.parseInt(id),Double.parseDouble(saldo),data,Double.parseDouble(limite));
				}
				else {
					con = new Conta(Integer.parseInt(id),Double.parseDouble(saldo),data);
				}
				
				this.adicionar(con);
			}
			arquivo2.close();
		}
		catch(Exception ex)		{
			throw new RuntimeException("leitura arquivo de participantes:"+ex.getMessage());
		}
	}

	//--------------------------------------------------------------------
	public void	salvarObjetos()  {
		try	{
			File f = new File( new File(".\\correntistas.csv").getCanonicalPath())  ;
			FileWriter arquivo1 = new FileWriter(f); 
			ArrayList<String> lista ;
			String listaId;
			for(Correntista c : correntistas) 	{
				lista = new ArrayList<>();
				for(Conta co : c.getContas()) {
					lista.add(co.getId()+"");
				}
				
				listaId = String.join(",", lista);
				
				arquivo1.write(c.getCPF()+";"+c.getNome()+";"+c.getSenha()+";"+ listaId +"\n");	
			} 
			arquivo1.close();
		}
		catch(Exception e){
			throw new RuntimeException("problema na criação do arquivo  eventos "+e.getMessage());
		}

		try	{
			File f = new File( new File(".\\contas.csv").getCanonicalPath())  ;
			FileWriter arquivo2 = new FileWriter(f) ; 
			for(Conta c : contas) {
				if(c instanceof ContaEspecial coe )
					arquivo2.write("ESPECIAL;" +c.getId() +";" + c.getData() +";" 
							+ c.getSaldo() +";"+ coe.getLimite()+"\n");	
				else
					arquivo2.write("NORMAL;" +c.getId() +";" + c.getData() +";" 
							+ c.getSaldo() + "\n");
			} 
			arquivo2.close();
		}
		catch (Exception e) {
			throw new RuntimeException("problema na cria��o do arquivo  participantes "+e.getMessage());
		}

	}
}

