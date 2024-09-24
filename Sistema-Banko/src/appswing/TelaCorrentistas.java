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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
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
import javax.swing.text.MaskFormatter;

import modelo.Conta;
import modelo.Correntista;
import regras_negocio.Fachada;

public class TelaCorrentistas {
	
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton button;
	private JButton button_1;
	private JButton button_4;
	private JButton button_2;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel label;
	private JLabel lblNome;
	private JLabel label_8;
	private JLabel lblCpf;
	private JButton button_3;
	private JButton button_5;
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
	public TelaCorrentistas() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				listagem();
			}
		});
		frame.setTitle("Correntistas");
		frame.setBounds(100, 100, 911, 353);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 42, 844, 120);
		frame.getContentPane().add(scrollPane);

		table = new JTable();
		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 12));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);


		button = new JButton("Criar Correntista");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(textField.getText().isEmpty() || 
							textField_1.getText().isEmpty() ||
							textField_2.getText().isEmpty()) 
					{
						label.setText("campo vazio");
						return;
					}

					String senha = textField.getText();
					String nome = textField_1.getText();
					String CPF = textField_2.getText();
					Fachada.criarCorrentista(CPF, nome,senha);
					label.setText("Correntista Criado: ");
					listagem();
				}
				catch(Exception ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.setBounds(281, 238, 95, 23);
		frame.getContentPane().add(button);

	

		label = new JLabel("");
		label.setForeground(Color.BLUE);
		label.setBackground(Color.RED);
		label.setBounds(26, 287, 830, 14);
		frame.getContentPane().add(label);

		lblNome = new JLabel("Nome");
		lblNome.setHorizontalAlignment(SwingConstants.LEFT);
		lblNome.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblNome.setBounds(26, 207, 71, 14);
		frame.getContentPane().add(lblNome);

		

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_1.setColumns(10);
		textField_1.setBounds(91, 204, 169, 20);
		frame.getContentPane().add(textField_1);

		label_8 = new JLabel("selecione");
		label_8.setBounds(26, 163, 561, 14);
		frame.getContentPane().add(label_8);

		lblCpf = new JLabel("CPF");
		lblCpf.setHorizontalAlignment(SwingConstants.LEFT);
		lblCpf.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblCpf.setBounds(26, 242, 43, 14);
		frame.getContentPane().add(lblCpf);

		textField_2 = new JTextField();
		textField_2.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_2.setColumns(10);
		textField_2.setBounds(89, 239, 71, 20);
		frame.getContentPane().add(textField_2);

		button_4 = new JButton("Listar");
		button_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagem();
			}
		});
		button_4.setBounds(410, 8, 95, 23);
		frame.getContentPane().add(button_4);


		//button_5 = new JButton("Ver Contas de Correntista");
		//button_5.addActionListener(new ActionListener() {
			//public void actionPerformed(ActionEvent e) {
				//try {
				//	if (table.getSelectedRow() >= 0){
					//	String cpf = (String) table.getValueAt( table.getSelectedRow(), 0);
						
					//	JOptionPane.showMessageDialog(frame, Fachada.listarContas(cpf));
					//}
					//else
					//	label.setText("selecione uma linha");
			//	}
				//catch(Exception erro) {
				///	label.setText(erro.getMessage());
			//	}
			//}
	//	});
		
		
		textField = new JTextField();
		textField.setBounds(91, 282, 96, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		lblNewLabel = new JLabel("Senha");
		lblNewLabel.setBounds(24, 287, 45, 13);
		frame.getContentPane().add(lblNewLabel);

	

	}

	//*****************************
	public void listagem () {
		try{
			ArrayList<Correntista> lista = Fachada.listarCorrentistas();

			//model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();
			//colunas
			model.addColumn("CPF");
			model.addColumn("Nome");
			model.addColumn("Senha");
			model.addColumn("Contas");
		
			
			String texto = "";
			
			//linhas
			for(Correntista c : lista) {
				if(c.getContas().isEmpty()) {
					texto = "Sem contas";
				}
				
				else {
					for(Conta co : c.getContas()) {
						texto += co.getId() + " ";
					}
				}
				model.addRow(new Object[]{c.getCPF()+"", c.getNome(), c.getSenha(), texto});
			}

			table.setModel(model);
			label_8.setText("resultados: "+lista.size()+ " Correntistas  - selecione uma linha");
		}
		catch(Exception erro){
			label.setText(erro.getMessage());
		}

	}
}
