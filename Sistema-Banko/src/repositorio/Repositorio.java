/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POO
 * Prof. Fausto Maranh�o Ayres
 **********************************/
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

	public int getTotalParticipante()	{
		return participantes.size();
	}

	public int getTotalEventos()	{
		return eventos.size();
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
		// carregar para o repositorio os objetos dos arquivos csv
		try {
			//caso os arquivos nao existam, serao criados vazios
			File f1 = new File( new File(".\\eventos.csv").getCanonicalPath() ) ; 
			File f2 = new File( new File(".\\participantes.csv").getCanonicalPath() ) ; 
			if (!f1.exists() || !f2.exists() ) {
				//System.out.println("criando arquivo .csv vazio");
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
		Correntista ev;
		Conta p;

		try	{
			String data, descricao, id, preco ;
			File f = new File( new File(".\\eventos.csv").getCanonicalPath() )  ;
			Scanner arquivo1 = new Scanner(f);	 
			while(arquivo1.hasNextLine()) 	{
				linha = arquivo1.nextLine().trim();		
				partes = linha.split(";");	
				//System.out.println(Arrays.toString(partes));
				id = partes[0];
				data = partes[1];
				descricao = partes[2];
				preco = partes[3];
				ev = new Correntista(Integer.parseInt(id), descricao, data, Double.parseDouble(preco));
				this.adicionar(ev);
			} 
			arquivo1.close();
		}
		catch(Exception ex)		{
			throw new RuntimeException("leitura arquivo de eventos:"+ex.getMessage());
		}

		try	{
			String tipo,nome, email, empresa, idade, ids;
			File f = new File( new File(".\\participantes.csv").getCanonicalPath())  ;
			Scanner arquivo2 = new Scanner(f);	 
			while(arquivo2.hasNextLine()) 	{
				linha = arquivo2.nextLine().trim();	
				partes = linha.split(";");
				//System.out.println(Arrays.toString(partes));
				tipo = partes[0];
				email = partes[1];
				nome = partes[2];
				idade = partes[3];
				ids="";
				if(tipo.equals("PARTICIPANTE")) {
					p = new Conta(email,nome,Integer.parseInt(idade));
					this.adicionar(p);
					if(partes.length>4)
						ids = partes[4];		//ids dos eventos separados por ","
				}
				else {
					empresa = partes[4];
					p = new ContaEspecial(email,nome,Integer.parseInt(idade),empresa);
					this.adicionar(p);
					if(partes.length>5)
						ids = partes[5];		//ids dos eventos separados por ","
				}

				//relacionar participante com os seus eventos
				if(!ids.isEmpty()) {	
					for(String idevento : ids.split(",")){	//converter string em array
						ev = this.localizarEvento(Integer.parseInt(idevento));
						ev.adicionar(p);
						p.adicionar(ev);
					}
				}
			}
			arquivo2.close();
		}
		catch(Exception ex)		{
			throw new RuntimeException("leitura arquivo de participantes:"+ex.getMessage());
		}
	}

	//--------------------------------------------------------------------
	public void	salvarObjetos()  {
		//gravar nos arquivos csv os objetos que est�o no reposit�rio
		try	{
			File f = new File( new File(".\\eventos.csv").getCanonicalPath())  ;
			FileWriter arquivo1 = new FileWriter(f); 
			for(Correntista e : eventos) 	{
				arquivo1.write(e.getId()+";"+e.getData()+";"+e.getDescricao()+";"+e.getPreco()+"\n");	
			} 
			arquivo1.close();
		}
		catch(Exception e){
			throw new RuntimeException("problema na cria��o do arquivo  eventos "+e.getMessage());
		}

		try	{
			File f = new File( new File(".\\participantes.csv").getCanonicalPath())  ;
			FileWriter arquivo2 = new FileWriter(f) ; 
			ArrayList<String> lista ;
			String listaId;
			for(Conta p : participantes) {
				//montar uma lista com os id dos eventos do participante
				lista = new ArrayList<>();
				for(Correntista e : p.getEventos()) {
					lista.add(e.getId()+"");
				}
				listaId = String.join(",", lista);

				if(p instanceof ContaEspecial c )
					arquivo2.write("CONVIDADO;" +p.getEmail() +";" + p.getNome() +";" 
							+ p.getIdade() +";"+ c.getEmpresa() +";"+ listaId +"\n");	
				else
					arquivo2.write("PARTICIPANTE;" +p.getEmail() +";" + p.getNome() +";" 
							+ p.getIdade() +";"+ listaId +"\n");	

			} 
			arquivo2.close();
		}
		catch (Exception e) {
			throw new RuntimeException("problema na cria��o do arquivo  participantes "+e.getMessage());
		}

	}
}

