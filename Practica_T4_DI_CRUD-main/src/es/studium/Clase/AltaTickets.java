package es.studium.Clase;

import java.awt.BorderLayout;
import java.awt.EventQueue;
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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Choice;
import java.awt.Button;

public class AltaTickets extends JFrame {

	
	private JPanel contentPane;
	ConfirmacionAltaArticulo correcto = new ConfirmacionAltaArticulo();
	ErrorAltaArticulos error = new ErrorAltaArticulos();
	private JTextField txtFecha;
	private JTextField txtTotal;
	Choice choArticulos = new Choice();

	
	public AltaTickets() {
		setTitle("Alta Tickets");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//Con el DISPOSE consigo que no se vea y evito que se cierre la app entera
		setBounds(100, 100, 434, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		
		JLabel lblFecha = new JLabel("Fecha Tickets: ");
		lblFecha.setBounds(34, 36, 108, 14);
		getContentPane().add(lblFecha);
		
		JLabel lblTotal = new JLabel("Total Ticket:");
		lblTotal.setBounds(34, 81, 74, 14);
		getContentPane().add(lblTotal);
		
		JLabel lblArticulos = new JLabel("Articulos:");
		lblArticulos.setBounds(51, 132, 74, 14);
		getContentPane().add(lblArticulos);
		
		txtFecha = new JTextField();
		txtFecha.setBounds(152, 33, 145, 20);
		getContentPane().add(txtFecha);
		txtFecha.setColumns(10);
		
		txtTotal = new JTextField();
		txtTotal.setBounds(152, 78, 145, 20);
		getContentPane().add(txtTotal);
		txtTotal.setColumns(10);
		
		
		choArticulos.setBounds(152, 126, 145, 20);
		getContentPane().add(choArticulos);
		
		// Montar el Choice
		choArticulos.add("Seleccionar uno...");
		// Conectar a la base de datos
		Connection con = conectar();
		// Sacar los datos de la tabla empleados
		// Rellenar el Choice
		String sqlSelect = "SELECT * FROM Articulos";
		try {
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelect);
			while (rs.next()) 
			{
				//Queremos que nos aparezca el el choice, el idEmpleado y el nombreEmpleado

				choArticulos.add(rs.getInt("idArticulos") 
						+ "-" + rs.getString("DescripcionArticulos") 
						+ ", "+ rs.getInt("CantidadStock"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}
		
		Button btnAceptar = new Button("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Conectar BD
				Connection con = conectar();
				
				String[] Tickets = choArticulos.getSelectedItem().split("-");
			
				//Hacer el INSERT
				int respuesta = insertar(con, "Tickets",txtFecha.getText(),Integer.parseInt(txtTotal.getText()),Tickets[0]);
				
				//Mostramos resultado
				if(respuesta == 0)
				{
				
				correcto.setVisible(true);
					
				}
				
				
				else 
				{
					error.setVisible(true);
				}
				desconectar(con);
			
			}
		});
		btnAceptar.setBounds(126, 188, 70, 22);
		getContentPane().add(btnAceptar);
		
		Button btnBorrar = new Button("Limpiar");
		btnBorrar.setBounds(249, 188, 70, 22);
		getContentPane().add(btnBorrar);
	

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
	private int insertar(Connection con, String Tickets,  String FechaTickets, int TotalTicket, String idArticulosFK1)
	 {
		int respuesta = 0;
		try {
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = con.createStatement();
			String cadenaSQL = "INSERT INTO " + Tickets
					+ " (`FechaTickets`,`TotalTicket`,`idArticulosFK1`) "
					+ "VALUES ('" +  FechaTickets + "', '" + TotalTicket + "','" + idArticulosFK1 + "')";

			System.out.println(cadenaSQL);
			sta.executeUpdate(cadenaSQL);
			sta.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al hacer un Insert");
			ex.printStackTrace();
			respuesta = 1;
		}
		return  respuesta;
	}
	private void desconectar(Connection con) 
	{
		try
		{
			con.close();
		}
		catch(Exception e) {}
		
	}
}
