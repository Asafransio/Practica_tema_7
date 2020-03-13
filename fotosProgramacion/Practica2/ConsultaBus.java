package Practica2;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConsultaBus extends Frame implements WindowListener, ActionListener 
{
	private static final long serialVersionUID = 1L;
	TextArea consulta = new TextArea(10,60);
	Button btnAceptar = new Button("Aceptar");
	Button btnPdf = new Button("Exportar a PDF");

	ConsultaBus()
	{
		setTitle("ALTA de factura");
		setLayout(new FlowLayout());
		// Conectar a la base de datos
		Connection con = conectar();
		// Seleccionar de la tabla edificios
		// Sacar la informaci�n
		rellenarTextArea(con, consulta);
		// Cerrar la conexi�n
		desconectar(con);
		consulta.setEditable(false);
		add(consulta);
		add(btnAceptar);
		add(btnPdf);
		btnAceptar.addActionListener(this);
		btnPdf.addActionListener(this);
		addWindowListener(this);
		setSize(500,300);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		Object objetoPulsado = e.getSource();
		if(objetoPulsado.equals(btnAceptar))
		{
			setVisible(false);
		}
		else
		{
			System.out.println("Exportando a PDF...");
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
		String url = "jdbc:mysql://localhost:3306/mydb?autoReconnect=true&useSSL=false";
		String user = "root";
		String password = "Studium2019;";
		Connection con = null;
		try {
			// Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			// Establecer la conexi�n con la BD empresa
			con = DriverManager.getConnection(url, user, password);
			if (con != null) {
				System.out.println("Conectado a la base de datos");
			}
		} catch (SQLException ex) {
			System.out.println("ERROR:La direcci�n no es v�lida o el usuario y clave");
			ex.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Error 1-" + cnfe.getMessage());
		}
		return con;
	}
	public void rellenarTextArea(Connection con, TextArea t)
	{
		String sqlSelect = "SELECT * FROM bus";
		try {
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelect);
			while (rs.next()) 
			{
				if(t.getText().length()==0)
				{
					t.setText(rs.getInt("idBus")+
							"-"+rs.getInt("horasServicioBus")+
							", "+rs.getString("modeloBus")+
							", "+rs.getInt("aforoBus")+
							", "+rs.getInt("matriculaBus"));
				}
				else
				{
					t.setText(t.getText() + "\n" +
							(rs.getInt("idBus")+
									"-"+rs.getInt("horasServicioBus")+
									", "+rs.getString("modeloBus")+
									", "+rs.getInt("aforoBus")+
									", "+rs.getInt("matriculaBus")));
				}
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}
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
