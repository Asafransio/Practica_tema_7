package Practica2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class LogIn extends WindowAdapter implements ActionListener{
	Frame login = new Frame ("Login");
	Frame menuPrincipal = new Frame ("Menú Principal");
	Dialog errorLogin = new Dialog (login, "ERROR", true);
	Label u = new Label ("Usuario");
	TextField user = new TextField(25);
	Label p = new Label ("Contraseña");
	TextField password = new TextField(22);
	Button aceptar = new Button("Aceptar");
	Button limpiar = new Button("Limpiar");
	
	LogIn(){
		login.add(u);
		login.add(user);
		login.add(p);
		login.add(password);
		login.add(aceptar);
		login.add(limpiar);
		aceptar.addActionListener(this);
		limpiar.addActionListener(this);
		
		login.setLayout(new FlowLayout());
		login.setSize(290,150);
		login.setResizable(false);
		login.setLocationRelativeTo(null);
		login.addWindowListener(this);
		login.setVisible(true);
		
	}
	
	public void windowClosing(WindowEvent arg0) {
		if(errorLogin.isActive()) {
			errorLogin.setVisible(false);
		}
		else {
		System.exit(0);
		}
	}
	
	public void actionPerformed (ActionEvent evento) {
		if(evento.getSource().equals(aceptar)) {
			if ((user.getText().equals("user"))&&(password.getText().equals("user"))) {
				new menuPrincipal();
				login.setVisible(false);
			}
			else {
				errorLogin.setLayout(new FlowLayout());
				errorLogin.setSize(175,75);
				errorLogin.addWindowListener(this);
				errorLogin.add(new Label ("Credenciales incorrectos"));
				Button volver = new Button ("Volver");
				errorLogin.add(volver);
				volver.addActionListener(this);
				errorLogin.setLocationRelativeTo(null);
				errorLogin.setResizable(false);
				errorLogin.setVisible(true);
				aceptar.setEnabled(false);
			}
		}
		else if (evento.getSource().equals(limpiar)) {
			user.selectAll();
			user.setText("");
			password.selectAll();
			password.setText("");
			user.requestFocus();
		}
		
	}
	
	
	public static void main(String[] args) {
		
		new LogIn();
		
		
		
		
	}

}

