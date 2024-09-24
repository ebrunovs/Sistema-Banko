/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * POO
 * Prof. Fausto Maranh�o Ayres
 **********************************/

package appswing;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Conta;
import modelo.ContaEspecial;
import modelo.Correntista;
import regras_negocio.Fachada;
import repositorio.Repositorio;

public class TelaContas{

	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel label;
	private JLabel label_6;
	private JLabel lblCpf;
	private JTextField textField_1;
	private JButton button;
	private JButton button_1;
	private JLabel lblLimitecontaEspecial;
	private JTextField textField_4;
	private JButton button_2;
	private JButton button_3;
	private JButton button_4;
	private JTextField textField;
	private JLabel lblNewLabel;



	/**
	 * Launch the application.
	 */
	//	public static void main(String[] args) {
	//		EventQueue.invokeLater(new Runnable() {
	//			public void run() {
	//				try {
	//					TelaEventos window = new TelaEventos();
	//					window.frame.setVisible(true);
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		});
	//	}

	/**
	 * Create the application.
	 */
	public TelaContas() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);
		frame.setResizable(false);
		frame.setTitle("Contas/ContasEspeciais");
		frame.setBounds(100, 100, 729, 385);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				listagem();
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 43, 674, 148);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		label = new JLabel("");
		label.setForeground(Color.BLUE);
		label.setBounds(21, 321, 688, 14);
		frame.getContentPane().add(label);

		label_6 = new JLabel("selecione");
		label_6.setBounds(21, 190, 431, 14);
		frame.getContentPane().add(label_6);

		lblCpf = new JLabel("CPF");
		lblCpf.setHorizontalAlignment(SwingConstants.LEFT);
		lblCpf.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCpf.setBounds(21, 269, 71, 14);
		frame.getContentPane().add(lblCpf);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_1.setColumns(10);
		textField_1.setBounds(68, 264, 195, 20);
		frame.getContentPane().add(textField_1);

		button_1 = new JButton("Criar Conta");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textField_1.getText().isEmpty())
							{
						label.setText("campo vazio");
						return;
					}
					String CPF = textField_1.getText();
					String limite = textField_4.getText();
					if(limite.isEmpty())
						Fachada.criarConta(CPF);
					else
						Fachada.criarContaEspecial(CPF,Integer.parseInt(limite));
						
					label.setText("Conta criada: ");
					listagem();
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_1.setBounds(440, 273, 181, 23);
		frame.getContentPane().add(button_1);

		button = new JButton("Listar");
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		button.setBounds(306, 9, 89, 23);
		frame.getContentPane().add(button);

		lblLimitecontaEspecial = new JLabel("limite (conta Especial):");
		lblLimitecontaEspecial.setHorizontalAlignment(SwingConstants.LEFT);
		lblLimitecontaEspecial.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLimitecontaEspecial.setBounds(21, 297, 119, 14);
		frame.getContentPane().add(lblLimitecontaEspecial);

		textField_4 = new JTextField();
		textField_4.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_4.setColumns(10);
		textField_4.setBounds(150, 294, 168, 20);
		frame.getContentPane().add(textField_4);

		button_2 = new JButton("Apagar");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (table.getSelectedRow() >= 0){
						String id = (String) table.getValueAt( table.getSelectedRow(), 1);
						Fachada.apagarConta(Integer.parseInt(id));
						label.setText("Apagou Conta " + id);
						listagem();
					}
					else
						label.setText("Conta não selecionada");
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_2.setBounds(309, 213, 86, 23);
		frame.getContentPane().add(button_2);

		button_3 = new JButton("Inserir Correntista");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (table.getSelectedRow() >= 0){
						int id =  Integer.parseInt(table.getValueAt( table.getSelectedRow(), 0).toString());

						String CPF = JOptionPane.showInputDialog(frame, "Digite o CPF");
						Fachada.inserirCorrentistaConta(id,CPF);
						
					

						JOptionPane.showMessageDialog(frame,"Correntista adicionado");
						listagem();
					}
					else
						label.setText("Conta não selecionada");
				}
				catch(NumberFormatException ex) {
					label.setText("formato do id invalido");
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_3.setBounds(411, 213, 137, 23);
		frame.getContentPane().add(button_3);

		button_4 = new JButton("Remover Correntista");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (table.getSelectedRow() >= 0){
						int id = Integer.parseInt(table.getValueAt( table.getSelectedRow(), 0).toString());
						String CPF = JOptionPane.showInputDialog(frame, "Digite o cpf");
						Fachada.removerCorrentistaConta(id,CPF);
						

						JOptionPane.showMessageDialog(frame, "Correntista Removido");
						listagem();

					}
					else
						label.setText("Conta não selecionada");
				}
				catch(NumberFormatException ex) {
					label.setText("formato do id invalido");
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_4.setBounds(558, 213, 137, 23);
		frame.getContentPane().add(button_4);
		
		textField = new JTextField();
		textField.setBounds(141, 12, 96, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		lblNewLabel = new JLabel("Digite o nome");
		lblNewLabel.setBounds(47, 15, 84, 13);
		frame.getContentPane().add(lblNewLabel);
	}

	public void listagem() {
		try{
			List<Conta> lista = Fachada.listarContas();

			//			//***************************************************************
			//			
			//			//Alternativa de ordenacao 1 (por nome)
			//			Collections.sort(lista);
			//						
			//			//Alternativa de ordenacao 2
			//			Collections.sort(lista, new Comparator<Participante>() {
			//				public int compare(Participante p1, Participante p2) {
			//					int idade1 = p1.getIdade();
			//					int idade2 = p2.getIdade();
			//					return Integer.compare(idade1, idade2);
			//				}
			//			});

			//			//Alternativa de ordenacao 3
			//			Collections.sort(lista, new Comparator<Participante>() {
			//				public int compare(Participante p1, Participante p2) {
			//					String nome1 = p1.getNome();
			//					String nome2 = p2.getNome();
			//					return nome1.compareTo(nome2);
			//				}
			//			});
			//
			//			//Alternativa de ordenacao 4
			//			Collections.sort(lista, (p1,p2) -> p1.getNome().compareTo(p2.getNome()));
			//			
			//			//***************************************************************

			//model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();

			//colunas
			model.addColumn("ID");
			model.addColumn("Data");
			model.addColumn("Saldo");
			model.addColumn("Correntistas");
			model.addColumn("limite");
			

			//linhas
			String texto;
			
			for(Conta c : lista) {

				if(c.getData().isEmpty()) {
					texto="sem Contas";
				}
			
				else {
					texto=" ";
					for(Correntista co : c.getCorrentistas()) 
						texto += co.getCPF()+ " " ;
				}

				if(c instanceof ContaEspecial ce)
					
					model.addRow(new Object[]{ce.getId(), ce.getData(), ce.getSaldo(), texto, ce.getLimite()});
				else
					model.addRow(new Object[]{c.getId(), c.getData(), c.getSaldo(), texto});

			}

			table.setModel(model);
			label_6.setText("resultados: "+lista.size()+ " Contas   - selecione uma linha");
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}
	}


}
