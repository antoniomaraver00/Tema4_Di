package es.studium.Clase;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Choice;
import java.awt.Dialog;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ModificiarArticulos extends JFrame implements ItemListener{

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtCantidad;
	private JTextField txtPrecio;
   ConfirmacionAltaArticulo confirmar = new ConfirmacionAltaArticulo();
   SeguroBaja seguro = new SeguroBaja();
   ErrorAltaArticulos error = new ErrorAltaArticulos();
   Choice choiceArticulos = new Choice();

	public ModificiarArticulos() {
		setTitle("Modificar Art\u00EDculos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 355);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		seguro.setVisible(false);
		
		
		JLabel lblNewLabel = new JLabel("Art\u00EDculo:");
		lblNewLabel.setBounds(57, 51, 66, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNombreDelArtculo = new JLabel("Nombre del art\u00EDculo:");
		lblNombreDelArtculo.setBounds(34, 103, 148, 14);
		contentPane.add(lblNombreDelArtculo);
		
		JLabel lblCantidadDelArtculo = new JLabel("Cantidad del Art\u00EDculo:");
		lblCantidadDelArtculo.setBounds(34, 151, 148, 14);
		contentPane.add(lblCantidadDelArtculo);
		
		JLabel lblPrecioDelArtculo = new JLabel("Precio del Art\u00EDculo:");
		lblPrecioDelArtculo.setBounds(34, 198, 148, 14);
		contentPane.add(lblPrecioDelArtculo);
		
		
		choiceArticulos.setBounds(188, 51, 194, 20);
		contentPane.add(choiceArticulos);
		choiceArticulos.addItemListener(this);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(188, 100, 194, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtCantidad = new JTextField();
		txtCantidad.setBounds(188, 148, 194, 20);
		contentPane.add(txtCantidad);
		txtCantidad.setColumns(10);
		
		txtPrecio = new JTextField();
		txtPrecio.setBounds(188, 195, 194, 20);
		contentPane.add(txtPrecio);
		txtPrecio.setColumns(10);
		
		// Montar el Choice
				choiceArticulos.add("Seleccionar uno...");
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

						choiceArticulos.add(rs.getInt("idArticulos") 
								+ "-" + rs.getString("DescripcionArticulos") 
								+ ", "+ rs.getInt("CantidadStock"));
					}
					rs.close();
					stmt.close();
				} catch (SQLException ex) {
					System.out.println("ERROR:al consultar");
					ex.printStackTrace();
				}
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String nombre = txtNombre.getText();
				int cantidad =Integer.parseInt(txtCantidad.getText());
				int precio = Integer.parseInt(txtPrecio.getText());
				String articulos = choiceArticulos.getSelectedItem(); // curso="1-5,15"
				// arrayCho curso.split -
				String[] arrayCho = articulos.split("-");
				String idArticulos = arrayCho[0];

				try {

					Connection con = conectar();
					
					String sql = "UPDATE Articulos SET"
							+ " DescripcionArticulos=? ,"
							+ " CantidadStock=? ,"
							+ " PrecioArticulos=? "
							+ "WHERE idArticulos=? "  ;	
					
					PreparedStatement pStatement = con.prepareStatement(sql);
				
					
			
					pStatement.setString(4, arrayCho[0]);
					pStatement.setString(1, txtNombre.getText());
					pStatement.setInt(2, cantidad);
					pStatement.setInt(3, precio);
				
				
					
					int result = pStatement.executeUpdate();
					pStatement.close();
					con.close();

					if (result > 0) {

						confirmar.setVisible(true);
					} else {
						//incorrecto.setVisible(true);
					}
				}
					catch (SQLException sqle) {
						System.out.println("Error 2-" + sqle.getMessage());
						error.setVisible(true);
					}
			}
		});
		btnAceptar.setBounds(34, 258, 89, 34);
		contentPane.add(btnAceptar);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtNombre.selectAll();
				txtNombre.setText("");
				txtNombre.requestFocus();
				txtPrecio.selectAll();
				txtPrecio.setText("");
				txtCantidad.selectAll();
				txtCantidad.setText("");
				choiceArticulos.select(0);
			}
		});
		btnLimpiar.setBounds(177, 258, 89, 34);
		contentPane.add(btnLimpiar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				baja();
			}
			
		
	});
		btnEliminar.setBounds(311, 258, 89, 34);
		contentPane.add(btnEliminar);
	}
	public void baja()
	{
		setTitle("\u00BFSeguro?");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 305, 169);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		
		JLabel lblNewLabel = new JLabel("\u00BFEst\u00E1 seguro de eliminar este art\u00EDculo?");
		lblNewLabel.setBounds(35, 25, 227, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnSi = new JButton("Si");
		btnSi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Conectar a BD
				Connection con = conectar(); 
				// Borrar
				String[] Articulos =choiceArticulos.getSelectedItem().split("-");
				int respuesta = borrar(con, Integer.parseInt(Articulos[0]));
				
				// Mostramos resultado
				if(respuesta == 0)
				{
					System.out.println("Borrado de Artículo correcto");
				}
				else
				{
					System.out.println("Error en Artículo ");
				}
				// Actualizar el Choice
				choiceArticulos.removeAll();
				choiceArticulos.add("Seleccionar uno...");
				String sqlSelect = "SELECT * FROM Articulos";
				try {
					// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(sqlSelect);
					while (rs.next()) 
					{

						choiceArticulos.add(rs.getInt("idArticulos") 
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
				setVisible(false);
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
				lblNewLabel.setBounds(43, 23, 248, 14);
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
			private void desconectar(Connection con) {
				// TODO Auto-generated method stub
				try
				{
					con.close();
				}
				catch(Exception e) {}
			}
		});
		btnSi.setBounds(48, 77, 68, 23);
		contentPane.add(btnSi);
		
		JButton btnNo = new JButton("No");
		btnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			  setVisible(false);
			}
		});
		btnNo.setBounds(174, 77, 68, 23);
		contentPane.add(btnNo);
	        
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
	
	public void itemStateChanged(ItemEvent e) {
		// Conectar a la base de datos
		Connection con2 = conectar();
		/*
		 * Poner en el campo de texto, los datos del alumno que hemos seleccionado de la
		 * lista
		 */
		String[] array = e.getItem().toString().split("-");
		// idAlumno = array[0] --> SELECT * FROM articulos WHERE idArticulos = array[0]
		String sqlSelect2 = "SELECT DescripcionArticulos, CantidadStock, PrecioArticulos FROM Articulos WHERE idArticulos = "+array[0];

		System.out.println(sqlSelect2);
		
		try {
			Statement stmt2 = con2.createStatement();
			ResultSet rs2 = stmt2.executeQuery(sqlSelect2);
			rs2.next();
				txtNombre.setText(rs2.getString("DescripcionArticulos"));
				txtCantidad.setText(rs2.getString("CantidadStock"));
				txtPrecio.setText(rs2.getString("PrecioArticulos"));
			
			rs2.close();
			stmt2.close();
			
			

		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();

		}

	
	}

}
