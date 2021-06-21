package es.studium.Clase;

import java.awt.BorderLayout; 
import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ConsultarTickets extends JFrame {
	TextArea consulta = new TextArea();
	private JPanel contentPane;

	public ConsultarTickets() {
		setTitle("Consultar Tickets");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 629, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		// Conectar a la base de datos
		Connection con = conectar();
		//Sacar la información
		rellenarTextArea(con, consulta);	
		
		JLabel lblNewLabel = new JLabel("Tickets:");
		lblNewLabel.setBounds(20, 92, 59, 14);
		contentPane.add(lblNewLabel);
		
		
		consulta.setBounds(85, 34, 365, 160);
		contentPane.add(consulta);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnAceptar.setBounds(470, 84, 120, 30);
		contentPane.add(btnAceptar);
		
		
	}
	private void rellenarTextArea(Connection con, TextArea t) {
		String sqlSelect = "SELECT * FROM Tickets";
		try {
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelect);
			while (rs.next()) {
				if (consulta.getText().length() == 0) {
					consulta.setText(rs.getInt("idTickets") + "-" + rs.getString("FechaTickets") + ", "
							+ rs.getInt("TotalTicket")+ ", "
									+ rs.getInt("idArticulosFK1"));
				} else {
					consulta.setText(consulta.getText() + "\n" + rs.getInt("idTickets") + "-" + rs.getString("FechaTickets") + ", "
							+ rs.getInt("TotalTicket")+ ", "
							+ rs.getInt("idArticulosFK1"));
				}
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}
	}

	private Connection conectar() {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/Tiendecita?useSSL=false";
		String login = "root";
		String password = "Studium2020;";
		Connection con = null;

		try {
			// Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			// Establecer la conexión con la BD empresa
			con = DriverManager.getConnection(url, login, password);
			if (con != null) {
				System.out.println("Conectado a la base de datos");
			}
		} catch (SQLException ex) {
			System.out.println("ERROR:La dirección no es válida o el usuario y clave");
			ex.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Error 1-" + cnfe.getMessage());
		}
		return con;
	}
	
	private void desconectar(Connection con) {
		// TODO Auto-generated method stub
		try
		{
			con.close();
		}
		catch(Exception e) {}
	}
}
