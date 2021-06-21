package es.studium.Clase;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;

public class ErrorModificar extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ErrorModificar frame = new ErrorModificar();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public ErrorModificar() {
		setTitle("Error");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 306, 160);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u00A1No se ha podido realizar la modificaci\u00F3n!");
		lblNewLabel.setBounds(27, 25, 254, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Aceptar");
		btnNewButton.setBounds(91, 64, 89, 31);
		contentPane.add(btnNewButton);
	}

}
