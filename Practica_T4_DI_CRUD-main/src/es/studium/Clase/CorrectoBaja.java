package es.studium.Clase;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

public class CorrectoBaja extends JFrame {

	private JPanel contentPane;


	public CorrectoBaja() {
		setTitle("Correcto");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 348, 181);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u00A1El art\u00EDculo se ha eliminado correctamente!");
		lblNewLabel.setBounds(22, 33, 300, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Aceptar");
		btnNewButton.setBounds(102, 84, 89, 23);
		contentPane.add(btnNewButton);
	}

}
