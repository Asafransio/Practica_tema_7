package Practica2;

import java.awt.Button;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AltaParadas extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;

	Label lblFechaFactura = new Label("Fecha:");
	Label lblImporteFactura = new Label("Importe:");
	Label lblEdificioFactura = new Label("Edificio:");
	TextField txtFechaFactura = new TextField(20);
	TextField txtImporteFactura = new TextField(20);
	Choice choEdificio = new Choice();
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");

	AltaParadas()
	{
		setTitle("ALTA de factura");
		setLayout(new FlowLayout());
		add(lblFechaFactura);
		add(txtFechaFactura);
		add(lblImporteFactura);
		add(txtImporteFactura);
		add(lblEdificioFactura);
		// Montar el Choice
		choEdificio.add("Seleccionar uno...");
		// Conectar a la base de datos
		Connection con = conectar();
		// Sacar los datos de la tabla edificios
		// Rellenar el Choice
		String sqlSelect = "SELECT * FROM edificios";
		try {
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelect);
			while (rs.next()) 
			{
				choEdificio.add(rs.getInt("idEdificio")+
						"-"+rs.getString("direccionEdificio")+
						", "+rs.getString("localidadEdificio"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}
		// Cerrar la conexión
		desconectar(con);
		add(choEdificio);
		add(btnAceptar);
		add(btnLimpiar);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		addWindowListener(this);
		setSize(200,300);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		CENTER_ALIGNMENT(true);
	}

	private void CENTER_ALIGNMENT(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		Object objetoPulsado = e.getSource();
		if(objetoPulsado.equals(btnLimpiar))
		{
			txtFechaFactura.selectAll();
			txtFechaFactura.setText("");
			txtImporteFactura.selectAll();
			txtImporteFactura.setText("");
			choEdificio.select(0);
			txtFechaFactura.requestFocus();
		}
		else if(objetoPulsado.equals(btnAceptar))
		{
			// Validación del choice
			// if(choEdificio.getSelectedIndex()==0)
			// Validación del importe
			// Double importeTotal = Double.parseDouble(txtImporteFactura.getText());
			// Validación de la fecha
			// DateFormat
			// Conectar BD
			Connection con = conectar();
			// Hacer el INSERT
			String[] Edificio=choEdificio.getSelectedItem().split("-");
			String fechaAmericana = txtFechaFactura.getText();
			int respuesta = insertar(con, "facturas", fechaAmericana, txtImporteFactura.getText(), Edificio[0]);
			// Mostramos resultado
			if(respuesta == 0)
			{
				System.out.println("ALTA de factura correcta");
			}
			else
			{
				System.out.println("Error en ALTA de factura");
			}
			// Desconectar de la base
			desconectar(con);
		}
	}

	@Override
	public void windowActivated(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		// TODO Auto-generated method stub
		setVisible(false);
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e)
	{
		// TODO Auto-generated method stub

	}
	public Connection conectar()
	{
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/practica1ertrimestre20192020?autoReconnect=true&useSSL=false";
		String user = "root";
		String password = "Studium2019;";
		Connection con = null;
		try {
			// Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			// Establecer la conexión con la BD empresa
			con = DriverManager.getConnection(url, user, password);
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
	public int insertar(Connection con, String tabla, String fechaFactura, String importeTotal, String idEdificioFK) 
	{
		int respuesta = 0;
		try 
		{
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = con.createStatement();
			String cadenaSQL = "INSERT INTO " + tabla 
					+ " VALUES (null, '" + fechaFactura 
					+ "', " + importeTotal 
					+ ", " + idEdificioFK + ")";
			System.out.println(cadenaSQL);
			sta.executeUpdate(cadenaSQL);
			sta.close();
		} 
		catch (SQLException ex) 
		{
			System.out.println("ERROR:al hacer un Insert");
			ex.printStackTrace();
			respuesta = 1;
		}
		return respuesta;
	}
	public void desconectar(Connection con)
	{
		try
		{
			con.close();
		}
		catch(Exception e) {}
	}
}
