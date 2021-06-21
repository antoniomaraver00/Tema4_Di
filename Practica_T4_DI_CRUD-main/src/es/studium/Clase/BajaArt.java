package es.studium.Clase;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.awt.Choice;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BajaArt extends JFrame {

	private JPanel contentPane;
	Choice choArticulos = new Choice();
	ConfirmacionBaja confirmar = new ConfirmacionBaja();
	SeguroBaja seguro = new SeguroBaja();
	ErrorBaja error = new ErrorBaja();


	public BajaArt() {
		setTitle("Baja Art\u00EDculos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		
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
				// Cerrar la conexión
				desconectar(con);
		
		
		choArticulos.setBounds(176, 29, 132, 34);
		contentPane.add(choArticulos);
		
		JLabel lblElegir = new JLabel("Elija un Art\u00EDuclo:");
		lblElegir.setBounds(45, 35, 112, 14);
		contentPane.add(lblElegir);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			SeguroBaja();
			
			}
		});
		btnAceptar.setBounds(72, 112, 89, 36);
		contentPane.add(btnAceptar);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choArticulos.select(0);
			}
		});
		btnLimpiar.setBounds(233, 112, 89, 36);
		contentPane.add(btnLimpiar);
	}
	
	public void SeguroBaja() {
		
		seguro.setTitle("\u00BFSeguro?");
		seguro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		seguro.setBounds(100, 100, 305, 169);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		seguro.setContentPane(contentPane);
		contentPane.setLayout(null);
		seguro.setVisible(true);
		
		JLabel lblNewLabel = new JLabel("\u00BFEst\u00E1 seguro de eliminar este art\u00EDculo?");
		lblNewLabel.setBounds(35, 25, 227, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnSi = new JButton("Si");
		btnSi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Conectar a BD
				Connection con = conectar(); 
				// Borrar
				String[] Articulos =choArticulos.getSelectedItem().split("-");
				int respuesta = borrar(con, Integer.parseInt(Articulos[0]));
				
				// Mostramos resultado
				if(respuesta == 0)
				{
					System.out.println("Borrado de Artículo correcto");
				}
				else
				{
					System.out.println("Error en Artículo ");
					error.setVisible(true);
				}
				// Actualizar el Choice
				choArticulos.removeAll();
				choArticulos.add("Seleccionar uno...");
				String sqlSelect = "SELECT * FROM Articulos";
				try {
					// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(sqlSelect);
					while (rs.next()) 
					{

						choArticulos.add(rs.getInt("idArticulos") 
								+ "-" + rs.getString("DescripcionArticulos") 
								+ ", "+ rs.getString("CantidadStock"));
					}
					ConfirmacionBaja();
					rs.close();
					stmt.close();
				} catch (SQLException ex) {
					System.out.println("ERROR:al consultar");
					ex.printStackTrace();
					
				}
				
				// Desconectar
				desconectar(con);
				
			
			}
		});
		btnSi.setBounds(48, 77, 68, 23);
		contentPane.add(btnSi);
		
		JButton btnNo = new JButton("No");
		btnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				seguro.setVisible(false);
			}
		});
		btnNo.setBounds(174, 77, 68, 23);
		contentPane.add(btnNo);
	}
	
   
	public void ConfirmacionBaja() {
		confirmar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		confirmar.setBounds(100, 100, 317, 161);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		confirmar.setContentPane(contentPane);
		contentPane.setLayout(null);
		confirmar.setVisible(true);
		
		JLabel lblNewLabel = new JLabel("\u00A1La baja del art\u00EDculo se realiz\u00F3 correctamente!");
		lblNewLabel.setBounds(43, 23, 260, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Aceptar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmar.setVisible(false);
				seguro.setVisible(false);
				setVisible(false);
			}
		});
		btnNewButton.setBounds(99, 64, 89, 23);
		contentPane.add(btnNewButton);
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
	public void ErrorBaja() {
		error.setTitle("Error");
		error.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		error.setBounds(100, 100, 284, 151);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		error.setContentPane(contentPane);
		contentPane.setLayout(null);
		error.setVisible(true);
		
		JLabel lblNewLabel = new JLabel("\u00A1No se ha podido dar de baja!");
		lblNewLabel.setBounds(49, 11, 209, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Aceptar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				error.setVisible(false);
			}
		});
		btnNewButton.setBounds(88, 55, 89, 23);
		contentPane.add(btnNewButton);
	}
	
	private int borrar(Connection con, int idArticulos) {
		int respuesta = 0;
		String sql = "DELETE FROM articulos WHERE idArticulos = " + idArticulos;
		System.out.println(sql);
		try 
		{
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = con.createStatement();
			sta.executeUpdate(sql);
			sta.close();
		} 
		catch(MySQLIntegrityConstraintViolationException fk)
		{
			System.out.println("ERROR:No se puede dar de baja al alumno");
			respuesta = 1;
		}
		catch (SQLException ex) 
		{
			System.out.println("ERROR:al hacer un Delete");
			ex.printStackTrace();
			respuesta = 1;
		}
		return respuesta;
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
