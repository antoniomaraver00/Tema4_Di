package es.studium.Clase;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class AltaArticulos extends JFrame implements WindowListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtCantidad;
	private JTextField txtArticulo;
	ConfirmacionAltaArticulo correcto;

	
	public AltaArticulos() {
		setTitle("Alta Art\u00EDculos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//Con el DISPOSE consigo que no se vea y evito que se cierre la app entera
		setBounds(100, 100, 434, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setVisible(true);
		
		JLabel lblNombre = new JLabel("Nombre del Art\u00EDculo:");
		lblNombre.setBounds(52, 35, 148, 14);
		contentPane.add(lblNombre);
		
		JLabel lblCantidad = new JLabel("Cantidad del art\u00EDculo:");
		lblCantidad.setBounds(52, 89, 148, 14);
		contentPane.add(lblCantidad);
		
		JLabel lblArticulo = new JLabel("Precio del Art\u00EDculo:");
		lblArticulo.setBounds(52, 144, 132, 14);
		contentPane.add(lblArticulo);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(193, 32, 139, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtCantidad = new JTextField();
		txtCantidad.setBounds(193, 86, 139, 20);
		contentPane.add(txtCantidad);
		txtCantidad.setColumns(10);
		
		txtArticulo = new JTextField();
		txtArticulo.setBounds(193, 141, 139, 20);
		contentPane.add(txtArticulo);
		txtArticulo.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		
		
		
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				//Conectar a la BD
				Connection con = conectar();
				//Hacer el INSERT
				int respuesta = insertar(con,"Articulos", txtNombre.getText(), txtCantidad.getText(), txtArticulo.getText());
				//Mostramos resultado
				if(respuesta == 0)
				{
				System.out.println("Alta del articulo correcto");
				correcto = new ConfirmacionAltaArticulo();
				correcto.setResizable(false);
				correcto.setLocationRelativeTo(null);
				correcto.setVisible(true);
				dispose();
				}
				else
				{
					ErrorAltaArticulos error = new ErrorAltaArticulos();
					error.setResizable(false);
					error.setLocationRelativeTo(null);
					error.setVisible(true);
					
				}
				desconectar(con);
			}
			
		});
		btnAceptar.setBounds(99, 206, 89, 23);
		contentPane.add(btnAceptar);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				txtNombre.selectAll();
				txtNombre.setText("");
				txtCantidad.selectAll();
				txtCantidad.setText("");
				txtArticulo.selectAll();
				txtArticulo.setText("");
			}
		});
		btnLimpiar.setBounds(230, 206, 89, 23);
		contentPane.add(btnLimpiar);
		
		
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
	
	private int insertar(Connection con, String Articulos, String Nombre, String Cantidad, String Precio) {

		int respuesta = 0;
		try
		{
			//Creamos un STATEMENT para una consulta SQL INSERT
			Statement sta = con.createStatement();
			String cadenaSQL = "INSERT INTO " + Articulos
					+ " (`DescripcionArticulos`, `CantidadStock`,`PrecioArticulos`)"
					+ "VALUES ('" + Nombre + "', '" + Cantidad + "', '" + Precio +  "')";
			
			System.out.println(cadenaSQL);
			sta.executeUpdate(cadenaSQL);
			sta.close();
		}
	    catch (SQLException ex) {
		System.out.println("ERROR:al hacer un Insert");
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
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	

	public void cerrar() {
		this.setVisible(false);
	}
	
}
