package gamePackage.ventanas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gamePackage.baseDatos.DataBase;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingConstants;
import java.awt.Font;

public class Registro extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textousuario;
	private JTextField textocontrasena;
	private JTextField textocontrasenaconfir;
	DataBase scores = new DataBase();
	String nombre = "";
	String contra = "";
	String confirmacion = "";
	Logger logsesion = Logger.global;
	
	VentanaAvisos aviso = new VentanaAvisos();
	MainMenu menu = new MainMenu(3,10,10000,10000,0,"b",1);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Registro dialog = new Registro(3,10,10000,10000,2);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Registro(int dificul,int movili,int jugador1,int jugador2,int jugavilidad) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Registro.class.getResource("/gamePackage/ventanas/img/troop/blue/ToaBLUE.png")));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Registro");
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(95, 158, 160));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{212, 212, 0};
		gbl_contentPanel.rowHeights = new int[]{50, 50, 50, 50, 50, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
			JLabel lblNewLabel = new JLabel("Nombre de Usuario:");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblNewLabel.setForeground(new Color(255, 255, 255));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 0;
			gbc_lblNewLabel.gridy = 0;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		
			textousuario = new JTextField();
			textousuario.setBackground(new Color(192, 192, 192));
			GridBagConstraints gbc_textousuario = new GridBagConstraints();
			gbc_textousuario.fill = GridBagConstraints.HORIZONTAL;
			gbc_textousuario.insets = new Insets(0, 0, 5, 0);
			gbc_textousuario.gridx = 1;
			gbc_textousuario.gridy = 0;
			contentPanel.add(textousuario, gbc_textousuario);
			textousuario.setColumns(10);
		
			JLabel lblNewLabel_1 = new JLabel("Contrasena: ");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblNewLabel_1.setForeground(new Color(255, 255, 255));
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.fill = GridBagConstraints.BOTH;
			gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_1.gridx = 0;
			gbc_lblNewLabel_1.gridy = 1;
			contentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
			textocontrasena = new JTextField();
			textocontrasena.setBackground(new Color(192, 192, 192));
			GridBagConstraints gbc_textocontrasena = new GridBagConstraints();
			gbc_textocontrasena.fill = GridBagConstraints.HORIZONTAL;
			gbc_textocontrasena.insets = new Insets(0, 0, 5, 0);
			gbc_textocontrasena.gridx = 1;
			gbc_textocontrasena.gridy = 1;
			contentPanel.add(textocontrasena, gbc_textocontrasena);
			textocontrasena.setColumns(10);
		
			JLabel lblNewLabel_2 = new JLabel("Confirmacion de contrasena: ");
			lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblNewLabel_2.setForeground(new Color(255, 255, 255));
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
			gbc_lblNewLabel_2.fill = GridBagConstraints.BOTH;
			gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_2.gridx = 0;
			gbc_lblNewLabel_2.gridy = 2;
			contentPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
			textocontrasenaconfir = new JTextField();
			textocontrasenaconfir.setBackground(new Color(192, 192, 192));
			GridBagConstraints gbc_textocontrasenaconfir = new GridBagConstraints();
			gbc_textocontrasenaconfir.fill = GridBagConstraints.HORIZONTAL;
			gbc_textocontrasenaconfir.insets = new Insets(0, 0, 5, 0);
			gbc_textocontrasenaconfir.gridx = 1;
			gbc_textocontrasenaconfir.gridy = 2;
			contentPanel.add(textocontrasenaconfir, gbc_textocontrasenaconfir);
			textocontrasenaconfir.setColumns(10);
		
			JButton Botonregistro = new JButton("Registrar");
			GridBagConstraints gbc_Botonregistro = new GridBagConstraints();
			gbc_Botonregistro.insets = new Insets(0, 0, 5, 5);
			gbc_Botonregistro.gridx = 0;
			gbc_Botonregistro.gridy = 3;
			contentPanel.add(Botonregistro, gbc_Botonregistro);
		
			JLabel lblNewLabel_3 = new JLabel("");
			GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
			gbc_lblNewLabel_3.fill = GridBagConstraints.BOTH;
			gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 0);
			gbc_lblNewLabel_3.gridx = 1;
			gbc_lblNewLabel_3.gridy = 3;
			contentPanel.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
			JLabel lblNewLabel_4 = new JLabel("");
			GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
			gbc_lblNewLabel_4.fill = GridBagConstraints.BOTH;
			gbc_lblNewLabel_4.insets = new Insets(0, 0, 0, 5);
			gbc_lblNewLabel_4.gridx = 0;
			gbc_lblNewLabel_4.gridy = 4;
			contentPanel.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
			JButton Botonmenu = new JButton("Main Menu");
			GridBagConstraints gbc_Botonmenu = new GridBagConstraints();
			gbc_Botonmenu.gridx = 1;
			gbc_Botonmenu.gridy = 4;
			contentPanel.add(Botonmenu, gbc_Botonmenu);
			textousuario.setText("Invitado");
			textocontrasena.setText("Invitado");
			textocontrasenaconfir.setText("Invitado");
			/***********************************************************************************************************************************
			 IKER
			 Con este boton lo primero que se hace es comprobar si las contraseñas son iguales y posteriormente 
			 revisara que no exista un usuario con el mismo nombre.
			 Luego lo añadira a la base de datos junto con sus expecificaciones de los ajustes.
			 Acontinuacion el programa te llevara al apartado seleccionado.
			 
			 **************************************************************************************************************************************/
			
			Botonregistro.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					ArrayList<Integer> numeros = new ArrayList<>();
					nombre = textousuario.getText();
					contra = textocontrasena.getText();
					confirmacion = textocontrasenaconfir.getText();
					
					if(contra.equals(confirmacion)) {
						scores.iniciaDB();
						int numero = scores.Confirmarclientes(nombre, contra);
						
						if(numero != 0) {
							numeros.add(numero);
						}
						
						
						scores.finalizaDB();
						
						
						//si existe usuario numero debe ser 0 
						if(numeros.size() == 1 ) {
							scores.iniciaDB();
							
							aviso.VentanaDeAvisos(3, "Usuario existente", 1);
							scores.actualizarusuario(dificul, movili, jugador1, jugador2,nombre,contra);
							scores.finalizaDB();
						}else {
							if(!(nombre.equals("") && contra.equals(""))) {
								try {
									scores.iniciaDB();
									scores.guardarclientes(nombre, contra,dificul,movili,jugador1,jugador2);
									scores.finalizaDB();
								} catch (Exception e2) {
									aviso.VentanaDeAvisos(3, "Error al introduccir los datos", 1);
								}
								aviso.VentanaDeAvisos(1, "Datos correctos", 1);
								
							}else {
								logsesion.log(Level.WARNING,"Error con los datos introducidos");
								aviso.VentanaDeAvisos(3, "Error al introduccir los datos", 1);
							}
							scores.iniciaDB();
							int codigousu = scores.conseguircodigousuario(nombre,contra);
							scores.finalizaDB();
							if(jugavilidad == 0) {
								dispose();
								creacion_cargar_mapas ccm = new creacion_cargar_mapas(codigousu,nombre,3,10,10000,10000);
								ccm.setVisible(true);
								
								
							}else if(jugavilidad == 1){
								dispose();
								Settings sets = new Settings(jugavilidad,nombre,codigousu);
								sets.setVisible(true);
								
								
							}else if(jugavilidad == 2){
								dispose();
								aviso.VentanaDeAvisos(2, "Desea ir al creador de mapa version 2?", 2);
								
								if(aviso.getResultados() == 1) {
									MapMakerv2 juego = new MapMakerv2(3,10,10000,1000,nombre,codigousu);
									juego.pack();
									juego.setLocationRelativeTo(null);
									juego.setVisible(true);
									
								}else {
									MapMaker juego = new MapMaker(3,10,10000,1000,nombre,codigousu);
									juego.pack();
									juego.setLocationRelativeTo(null);
									juego.setVisible(true);
									
								}
							}else {
								
							}
							
							
						}
						
					}else {
						logsesion.log(Level.WARNING,"Error con los datos introducidos");
						aviso.VentanaDeAvisos(3, "Contrasenas diferentes", 1);
						
					}
					
					
					

					
					
					
				}
			});
			/***********************************************************************************************************************************
			 IKER
			 Boton para regresar al menu 
			 **************************************************************************************************************************************/
			
			Botonmenu.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					MainMenu menu = new MainMenu(dificul,movili,jugador1,jugador2,0,"b",1);
					menu.setLocationRelativeTo(null);
					menu.setResizable(false);
					menu.setVisible(true);
					
				}
				
					
			});
		
	}
}


