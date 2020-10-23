package screen;
import java.awt.EventQueue;


import javax.swing.JInternalFrame;
import java.awt.GridBagLayout;
import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
//import com.jgoodies.forms.factories.DefaultComponentFactory;

import javax.swing.*;

public class MainFrame extends JInternalFrame {
    private JButton button1;
    private JPanel panel1;
    
    private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setBounds(100, 100, 1250, 870);
		getContentPane().setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(32, 36, 810, 379);
		getContentPane().add(textArea);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(916, 477, 308, 352);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(916, 81, 308, 370);
		getContentPane().add(scrollPane_2);
		
		JTextArea console = new JTextArea();
		console.setBounds(53, 541, 698, 124);
		getContentPane().add(console);
		
		JButton btnNewButton = new JButton("Adicionar");
		btnNewButton.setBounds(0, 11, 89, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Salvar");
		btnNewButton_1.setBounds(90, 11, 89, 23);
		getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Executar");
		btnNewButton_2.setBounds(182, 11, 89, 23);
		getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Deletar");
		btnNewButton_3.setBounds(275, 11, 89, 23);
		getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("DEBUG [ON]");
		btnNewButton_4.setBounds(368, 11, 118, 23);
		getContentPane().add(btnNewButton_4);
		
//		btnNewButton_4JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("Pilha de Tokens (a)");
//		lblNewJgoodiesLabel.setBounds(916, 462, 92, 14);
//		getContentPane().add(lblNewJgoodiesLabel);
		
		JLabel lblNewLabel = new JLabel("Console");
		lblNewLabel.setBounds(53, 521, 46, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Pilha de Produ\u00E7\u00F5es");
		lblNewLabel_1.setBounds(916, 64, 112, 14);
		getContentPane().add(lblNewLabel_1);

	}
}
