package Activity;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BDGrafica extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1093183144225566756L;
	private JPanel contentPane;
	private String[] createTables;
	private JTextField tfVenta;
	private JTextField tfCliente;
	private JTextField tfProducto;
	private JTextField tfCantidad;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BDGrafica frame = new BDGrafica();
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
	@SuppressWarnings("unchecked")
	public BDGrafica() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 274);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Selecciona una base de datos: ");
		lblNewLabel.setBounds(42, 0, 214, 30);
		contentPane.add(lblNewLabel);
		
		JComboBox cbBasesDatos = new JComboBox();
		cbBasesDatos.setModel(new DefaultComboBoxModel(new String[] {"MySQL", "Apache Derby", "SQLite"}));
		cbBasesDatos.setBounds(274, 3, 116, 24);
		contentPane.add(cbBasesDatos);
		
		JButton btnConecta = new JButton("CONECTA");
		btnConecta.setBounds(276, 41, 114, 25);
		contentPane.add(btnConecta);
		
		JLabel lblConnected = new JLabel("");
		lblConnected.setBounds(62, 31, 182, 30);
		contentPane.add(lblConnected);
		
		tfVenta = new JTextField();
		tfVenta.setBounds(120, 101, 124, 19);
		contentPane.add(tfVenta);
		tfVenta.setColumns(10);
		
		tfCliente = new JTextField();
		tfCliente.setBounds(120, 132, 124, 19);
		contentPane.add(tfCliente);
		tfCliente.setColumns(10);
		
		tfProducto = new JTextField();
		tfProducto.setBounds(120, 163, 124, 19);
		contentPane.add(tfProducto);
		tfProducto.setColumns(10);
		
		tfCantidad = new JTextField();
		tfCantidad.setBounds(120, 188, 124, 19);
		contentPane.add(tfCantidad);
		tfCantidad.setColumns(10);
		
		JLabel lblIdDeCliente = new JLabel("ID de cliente:");
		lblIdDeCliente.setBounds(12, 129, 90, 24);
		contentPane.add(lblIdDeCliente);
		
		JLabel lblIdDeProducto = new JLabel("ID de producto:");
		lblIdDeProducto.setBounds(12, 150, 107, 44);
		contentPane.add(lblIdDeProducto);
		
		JLabel lblIdDeVenta = new JLabel("ID de venta:");
		lblIdDeVenta.setBounds(12, 95, 90, 30);
		contentPane.add(lblIdDeVenta);
		
		JLabel lblCantidad = new JLabel("Cantidad:");
		lblCantidad.setBounds(12, 190, 90, 15);
		contentPane.add(lblCantidad);
		
		JButton btnInserta = new JButton("INSERTA");
		
		
		btnInserta.setBounds(274, 129, 114, 25);
		contentPane.add(btnInserta);
		
		btnConecta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				switch (cbBasesDatos.getSelectedItem().toString()) {
				case "SQLite":
					 connSQLite("jdbc:sqlite:/home/cinthya/Escritorio/BasesDeDatos/SQLiteEmpresa.db");
					 insertar("jdbc:sqlite:/home/cinthya/Escritorio/BasesDeDatos/SQLiteEmpresa.db", "INSERT INTO clientes VALUES(121212, 'Camilo', 'Calle restrepo', 'Javea', 123456, '9087843N'),(131313,'Melissa', 'Calle luna con calle sol', 'Teulada', 789101, '8463729N'), (141414,'Angel', 'Calle Moral', 'Denia', 674829, '9753456T');",false);
					 insertar("jdbc:sqlite:/home/cinthya/Escritorio/BasesDeDatos/SQLiteEmpresa.db", "INSERT INTO productos VALUES(234567, 'Cargador portatil',20 , 1, 1),(765432,'Funda super hard',15,1,7), (323456789,'Protector de pantalla',12,1,20);",false);
					 lblConnected.setText(cbBasesDatos.getSelectedItem().toString()+" conectado.");
					 
					 
					break;
				case "Apache Derby":
					connApacheDerby("jdbc:derby:/home/cinthya/Escritorio/BasesDeDatos/DerbyEmpresa");
					insertar("jdbc:derby:/home/cinthya/Escritorio/BasesDeDatos/DerbyEmpresa", "INSERT INTO clientes VALUES(121212, 'Camilo', 'Calle restrepo', 'Javea', 123456, '9087843N'),(131313,'Melissa', 'Calle luna con calle sol', 'Teulada', 789101, '8463729N'), (141414,'Angel', 'Calle Moral', 'Denia', 674829, '9753456T')",false);
					insertar("jdbc:derby:/home/cinthya/Escritorio/BasesDeDatos/DerbyEmpresa", "INSERT INTO productos VALUES(234567, 'Cargador portatil',20 , 1, 1),(765432,'Funda super hard',15,1,7), (323456789,'Protector de pantalla',12,1,20)",false);
					lblConnected.setText(cbBasesDatos.getSelectedItem().toString()+" conectado.");
					break;
				case "MySQL":
					try {
						connMySQL("jdbc:mysql://localhost:3306/Empresa");
						insertar("jdbc:mysql://localhost:3306/Empresa", "INSERT INTO clientes VALUES(121212, 'Camilo', 'Calle restrepo', 'Javea', 123456, '9087843N'),(131313,'Melissa', 'Calle luna con calle sol', 'Teulada', 789101, '8463729N'), (141414,'Angel', 'Calle Moral', 'Denia', 674829, '9753456T');", true);
						insertar("jdbc:mysql://localhost:3306/Empresa", "INSERT INTO productos VALUES(234567, 'Cargador portatil',20 , 1, 1),(765432,'Funda super hard',15,1,7), (323456789,'Protector de pantalla',12,1,20);",true);
						lblConnected.setText(cbBasesDatos.getSelectedItem().toString()+" conectado.");
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				default:
					
					break;
				}
			}
		});
		btnInserta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int idVenta=Integer.valueOf(tfVenta.getText());
				int idProducto=Integer.valueOf(tfProducto.getText());
				int idCliente=Integer.valueOf(tfCliente.getText());
				int cantidad= Integer.valueOf(tfCantidad.getText());
				switch (cbBasesDatos.getSelectedItem().toString()) {
				case "SQLite":
					insertar("jdbc:sqlite:/home/cinthya/Escritorio/BasesDeDatos/SQLiteEmpresa.db","INSERT INTO venta VALUES("+idVenta+",CURRENT_TIMESTAMP,"+idCliente+","+idProducto+","+cantidad+");",false);
					break;
				case "Apache Derby":
					insertar("jdbc:derby:/home/cinthya/Escritorio/BasesDeDatos/DerbyEmpresa","INSERT INTO venta VALUES("+idVenta+",CURRENT_DATE,"+idCliente+","+idProducto+","+cantidad+")",false);
					break;
				case "MySQL":
					insertar("jdbc:mysql://localhost:3306/Empresa","INSERT INTO venta VALUES("+idVenta+",CURRENT_TIMESTAMP,"+idCliente+","+idProducto+","+cantidad+");",true);
					break;

				default:
					break;
				}
			}
		});
		
		
		
		
		
	}
	
	public static void insertar (String connection, String insert, boolean isMYSQL) {
		Connection c;
		try {
			if(isMYSQL) {
				c= DriverManager.getConnection(connection,"root","");
			}else {
				c= DriverManager.getConnection(connection);
			}
			Statement s = c.createStatement();
			int numFilas=  s.executeUpdate(insert);
			System.out.println("Se han insertado:"+numFilas+ " filas.");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	
	}

	public static void connSQLite(String urlConexion){
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conexion = DriverManager.getConnection(urlConexion);
			if(conexion != null) {
				System.out.println("Ok-sqlite");
			}
			conexion.close();			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	public static void connApacheDerby(String urlConexion) {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			Connection conexion = DriverManager.getConnection(urlConexion);
			if(conexion != null) {
				System.out.println("Ok-derby");
			}
			
			conexion.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void connMySQL(String urlConexion) throws SQLException,ClassNotFoundException {
		Connection conexion = DriverManager.getConnection(urlConexion,"root","");
		if(conexion != null) {
			System.out.println("Ok-Mysql");
		}
		
		conexion.close();
	}
}
