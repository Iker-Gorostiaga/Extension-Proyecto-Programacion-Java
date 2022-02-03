package gamePackage.ventanas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gamePackage.baseDatos.DataBase;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.Menu;

import javax.swing.border.BevelBorder;
import java.awt.Button;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.TitledBorder;
import javax.swing.SwingConstants;

public class Login_Register extends JDialog{

	private final JPanel contentPanel = new JPanel();
	private JTextField textousuario;
	private JTextField textocontrasena;
	DataBase scores = new DataBase();
	String nombre = "";
	String contra = "";
	Logger logsesion = Logger.global;
	
	VentanaAvisos aviso = new VentanaAvisos();
	MainMenu menu = new MainMenu(3,10,10000,10000,0,"b",1);
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Login_Register dialog = new Login_Register(0,0,1000,1000,0);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Login_Register(int dificul,int movili,int jugador1,int jugador2,int jugavilidad) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login_Register.class.getResource("/gamePackage/ventanas/img/troop/blue/ToaBLUE.png")));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 443, 285);
		setLocationRelativeTo(null);
		setTitle("Log in/Registro");
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel.setBackground(new Color(95, 158, 160));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{208, 208, 0};
		gbl_contentPanel.rowHeights = new int[]{59, 59, 59, 59, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		JLabel lblNewLabel = new JLabel("Nombre de Usuario:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBackground(new Color(95, 158, 160));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		textousuario = new JTextField();
		textousuario.setBackground(new Color(192, 192, 192));
		textousuario.setText("Invitado");
		GridBagConstraints gbc_textousuario = new GridBagConstraints();
		gbc_textousuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_textousuario.insets = new Insets(0, 0, 5, 0);
		gbc_textousuario.gridx = 1;
		gbc_textousuario.gridy = 0;
		contentPanel.add(textousuario, gbc_textousuario);
		textousuario.setColumns(10);
		
		JLabel lblContrasena = new JLabel("Contrasena:");
		lblContrasena.setForeground(Color.WHITE);
		lblContrasena.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblContrasena = new GridBagConstraints();
		gbc_lblContrasena.insets = new Insets(0, 0, 5, 5);
		gbc_lblContrasena.gridx = 0;
		gbc_lblContrasena.gridy = 1;
		contentPanel.add(lblContrasena, gbc_lblContrasena);
		
		textocontrasena = new JTextField();
		textocontrasena.setColumns(10);
		textocontrasena.setBackground(Color.LIGHT_GRAY);
		textocontrasena.setText("Invitado");
		GridBagConstraints gbc_textocontrasena = new GridBagConstraints();
		gbc_textocontrasena.fill = GridBagConstraints.HORIZONTAL;
		gbc_textocontrasena.insets = new Insets(0, 0, 5, 0);
		gbc_textocontrasena.gridx = 1;
		gbc_textocontrasena.gridy = 1;
		contentPanel.add(textocontrasena, gbc_textocontrasena);
		
		JButton Cerrar = new JButton("Cerrar");
		
		/********************************************************************************************************************
		IKER
		Este boton nos regresara al menu principal
		*********************************************************************************************************************/
		
		Cerrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				MainMenu menu = new MainMenu(dificul,movili,jugador1,jugador2,0,"b",1);
				menu.setLocationRelativeTo(null);
				menu.setResizable(false);
				menu.setVisible(true);
			}
		});
		
		JButton log_in = new JButton("Log in");
		GridBagConstraints gbc_log_in = new GridBagConstraints();
		gbc_log_in.anchor = GridBagConstraints.WEST;
		gbc_log_in.insets = new Insets(0, 15, 5, 5);
		gbc_log_in.gridx = 0;
		gbc_log_in.gridy = 2;
		contentPanel.add(log_in, gbc_log_in);
		
		/********************************************************************************************************************
		IKER
		Este boton va a comprobar si los datos introducidos son correctos para poder acceder bien a la BD.
		Despues coge los ajustes del usuario almacenados y los aplica.
		Acontinuacion nos llevara a la modalidad que hallamos seleccionado desde el menu principal 
		*********************************************************************************************************************/
		
		log_in.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<Integer> numeros = new ArrayList<>();
				ArrayList<Integer> listaconfig = new ArrayList<>();
				int numero = 0;
				nombre = textousuario.getText();
				contra = textocontrasena.getText();
				
				try {
					scores.iniciaDB();
					numero = scores.Confirmarclientes(nombre, contra);
					
					if(numero != 0) {
						numeros.add(numero);
						
					}else {
						
					}
					
					
					
				} catch (Exception e2) {
					
				}
				
				if(numeros.size() == 1) {
					
					listaconfig = scores.conseguirajustesusuario(nombre,contra);
					scores.finalizaDB();
					
					aviso.VentanaDeAvisos(1, "Comfirmacion de usuario", 1);
					setVisible(false);
					
					if(numeros.size() == 1 && jugavilidad == 0) {
						creacion_cargar_mapas ccm = new creacion_cargar_mapas(numero,nombre,listaconfig.get(0),listaconfig.get(1),listaconfig.get(2),listaconfig.get(3));
						ccm.setVisible(true);
						dispose();
						
					}else if(jugavilidad == 1){
						Settings sets = new Settings(jugavilidad,nombre,numero);
						sets.setVisible(true);
						dispose();
						
					}else if(jugavilidad == 2){
						
						aviso.VentanaDeAvisos(2, "Desea ir al creador de mapa en version 2?", 2);
						
						if(aviso.getResultados() == 1) {
							MapMakerv2 juego = new MapMakerv2(listaconfig.get(0),listaconfig.get(1),listaconfig.get(2),listaconfig.get(3),nombre,numero);
							juego.pack();
							juego.setLocationRelativeTo(null);
							juego.setVisible(true);
							
						}else {
							MapMaker juego = new MapMaker(listaconfig.get(0),listaconfig.get(1),listaconfig.get(2),listaconfig.get(3),nombre,numero);
							juego.pack();
							juego.setLocationRelativeTo(null);
							juego.setVisible(true);
							
						}
						
					}else {
						logsesion.log(Level.WARNING, "Error al introduccir los datos");
						aviso.VentanaDeAvisos(3, "Error al introduccir los datos", 1);
					}
					menu.setVisible(false);
				}else {
					scores.finalizaDB();
					logsesion.log(Level.WARNING, "Error al introduccir los datos");
					aviso.VentanaDeAvisos(3, "Error al introduccir los datos", 1);
					
				}
				
				
				
				
				
			}
		});
		
		JLabel label = new JLabel("");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.fill = GridBagConstraints.BOTH;
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 1;
		gbc_label.gridy = 2;
		contentPanel.add(label, gbc_label);
		
		JButton Botonregistro = new JButton("Register");
		GridBagConstraints gbc_Botonregistro = new GridBagConstraints();
		gbc_Botonregistro.anchor = GridBagConstraints.WEST;
		gbc_Botonregistro.insets = new Insets(0, 15, 0, 5);
		gbc_Botonregistro.gridx = 0;
		gbc_Botonregistro.gridy = 3;
		contentPanel.add(Botonregistro, gbc_Botonregistro);
		
		/********************************************************************************************************************
		IKER
		Con este boton nos llevara a la zona de registro
		*********************************************************************************************************************/
		
		Botonregistro.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				Registro dialog = new Registro(dificul,movili,jugador1,jugador2,jugavilidad);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				
			}
		});
		
		GridBagConstraints gbc_Cerrar = new GridBagConstraints();
		gbc_Cerrar.gridx = 1;
		gbc_Cerrar.gridy = 3;
		contentPanel.add(Cerrar, gbc_Cerrar);
		
		
		
		
		
		
		setModal(true);
		
		
	}
}
