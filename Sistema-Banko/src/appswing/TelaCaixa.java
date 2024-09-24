package appswing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import regras_negocio.Fachada;

public class TelaCaixa {
    private JPanel contentPane;
    private JFrame frame; // Altere para JFrame
    private JTextField textField;     // Campo ID
    private JTextField textField_1;   // Campo SENHA
    private JTextField textField_2;   // Campo CPF
    private JTextField textField_3;   // Campo VALOR
    private JTextField textField_4;   // Campo ID 2

    /**
     * Construtor.
     */
    public TelaCaixa() {
        initialize(); // Inicialize os componentes
        frame.setVisible(true); // Exiba o frame após a inicialização
    }

    /**
     * Método de inicialização da interface gráfica.
     */
    public void initialize() {
        // Inicializando o JFrame
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 911, 313);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        frame.setContentPane(contentPane);
        contentPane.setLayout(null);

        // Labels de erro e outros
        JLabel lblNewLabel_5 = new JLabel("");
        lblNewLabel_5.setBounds(662, 214, 200, 13); // Aumentando o espaço para exibir mensagens de erro
        contentPane.add(lblNewLabel_5);

        // Botão DEBITAR
        JButton btnNewButton = new JButton("DEBITAR");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = textField.getText();
                String senha = textField_1.getText();
                String cpf = textField_2.getText();
                String valor = textField_3.getText();

                try {
                    Fachada.debitarValor(Integer.parseInt(id), cpf, senha, Double.parseDouble(valor));
                } catch (Exception e3) {
                    lblNewLabel_5.setText(e3.getMessage());
                }
            }
        });
        btnNewButton.setBounds(597, 80, 110, 21);
        contentPane.add(btnNewButton);

        // Botão TRANSFERIR
        JButton btnNewButton_1 = new JButton("TRANSFERIR");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = textField.getText();
                String senha = textField_1.getText();
                String cpf = textField_2.getText();
                String valor = textField_3.getText();
                String id2 = textField_4.getText();

                try {
                    Fachada.transferirValor(Integer.parseInt(id), cpf, senha, Double.parseDouble(valor), Integer.parseInt(id2));
                } catch (Exception e2) {
                    lblNewLabel_5.setText(e2.getMessage());
                }
            }
        });
        btnNewButton_1.setBounds(597, 137, 110, 21);
        contentPane.add(btnNewButton_1);

        // Botão CREDITAR
        JButton btnNewButton_2 = new JButton("CREDITAR");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = textField.getText();
                String senha = textField_1.getText();
                String cpf = textField_2.getText();
                String valor = textField_3.getText();

                try {
                    Fachada.creditarValor(Integer.parseInt(id), cpf, senha, Double.parseDouble(valor));
                } catch (Exception e1) {
                    lblNewLabel_5.setText(e1.getMessage());
                }
            }
        });
        btnNewButton_2.setBounds(597, 20, 110, 21);
        contentPane.add(btnNewButton_2);

        // Campos de texto
        textField = new JTextField();
        textField.setBounds(341, 21, 96, 19);
        contentPane.add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField();
        textField_1.setBounds(341, 62, 96, 19);
        contentPane.add(textField_1);
        textField_1.setColumns(10);

        textField_2 = new JTextField();
        textField_2.setBounds(341, 105, 96, 19);
        contentPane.add(textField_2);
        textField_2.setColumns(10);

        textField_3 = new JTextField();
        textField_3.setBounds(341, 151, 96, 19);
        contentPane.add(textField_3);
        textField_3.setColumns(10);

        textField_4 = new JTextField();
        textField_4.setBounds(341, 198, 96, 19);
        contentPane.add(textField_4);
        textField_4.setColumns(10);

        // Labels de campo
        JLabel lblNewLabel = new JLabel("ID");
        lblNewLabel.setBounds(235, 24, 45, 13);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("SENHA");
        lblNewLabel_1.setBounds(235, 65, 45, 13);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("CPF");
        lblNewLabel_2.setBounds(235, 108, 45, 13);
        contentPane.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("VALOR");
        lblNewLabel_3.setBounds(235, 154, 45, 13);
        contentPane.add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("ID 2");
        lblNewLabel_4.setBounds(235, 201, 45, 13);
        contentPane.add(lblNewLabel_4);
    }

    // Método principal para executar a aplicação
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaCaixa window = new TelaCaixa();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
