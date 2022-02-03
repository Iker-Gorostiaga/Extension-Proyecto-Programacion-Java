package gamePackage.ventanas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gamePackage.baseDatos.DataBase;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;

import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JList;

public class creacion_cargar_mapas extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	String filePath;
	DataBase scores = new DataBase();
	HashMap<Point, ArrayList<ArrayList<Object>>> mapGrid = new HashMap<>();
	Logger logcargar = Logger.global;
	VentanaAvisos aviso = new VentanaAvisos();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			creacion_cargar_mapas dialog = new creacion_cargar_mapas(0,"",3,3,1000,1000);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public creacion_cargar_mapas(int codigo,String nombre,int dificul,int movili,int jugador1,int jugador2) {
		setBounds(100, 100, 557, 423);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage(creacion_cargar_mapas.class.getResource("/gamePackage/ventanas/img/troop/blue/ToaBLUE.png")));
		setTitle("Carga de Mapas");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(95, 158, 160));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel usuariolabel = new JLabel("Bienvenido "+nombre+", que desea hacer?");
		usuariolabel.setBounds(159, 28, 238, 44);
		contentPanel.add(usuariolabel);
		
		JRadioButton defaultmaps = new JRadioButton("Cargar un mapa default");
		defaultmaps.setMnemonic('0');
		defaultmaps.setFont(new Font("Tahoma", Font.BOLD, 14));
		defaultmaps.setBackground(new Color(95, 158, 160));
		buttonGroup.add(defaultmaps);
		defaultmaps.setBounds(30, 113, 202, 21);
		contentPanel.add(defaultmaps);
		
		JRadioButton cargarmaps = new JRadioButton("Cargar mapa guardado");
		cargarmaps.setMnemonic('1');
		cargarmaps.setFont(new Font("Tahoma", Font.BOLD, 14));
		cargarmaps.setBackground(new Color(95, 158, 160));
		buttonGroup.add(cargarmaps);
		cargarmaps.setBounds(326, 113, 200, 21);
		contentPanel.add(cargarmaps);
		
		DefaultListModel listmodel = new DefaultListModel<>();
		DefaultListModel listmodel2 = new DefaultListModel<>();
		
		JList list = new JList(listmodel);
		list.setBounds(40, 140, 145, 157);
		contentPanel.add(list);
		
		JList list_1 = new JList(listmodel2);
		list_1.setBounds(326, 140, 158, 157);
		contentPanel.add(list_1);
		
		JButton aceptarboton = new JButton("Aceptar");
		aceptarboton.setBounds(212, 199, 85, 21);
		contentPanel.add(aceptarboton);
		
		JButton botonmenu = new JButton("Main Menu");
		botonmenu.setBounds(195, 276, 121, 21);
		contentPanel.add(botonmenu);
		
		/********************************************************************************************************************
		IKER
		Este boton nos devuelve al menu principal
		*********************************************************************************************************************/
		
		botonmenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainMenu menu = new MainMenu(dificul,movili,jugador1,jugador2,0,nombre,codigo);
				menu.setLocationRelativeTo(null);
				menu.setResizable(false);
				menu.setVisible(true);
				
			}
		});
		
		/********************************************************************************************************************
		IKER
		Este radioboton al ser presionado nos coge los mapas de usuario logeado y los muestra por una lista.
		Al seleccionar el otro radioboton esta se borrara
		*********************************************************************************************************************/
		
		cargarmaps.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				scores.iniciaDB();
				ArrayList<String> listamapas = scores.conseguirmapasusuario(codigo);
				scores.finalizaDB();
				int numero = buttonGroup.getSelection().getMnemonic();
				if(numero == 49) {

			        
			        for (String s : listamapas) {
			        	
			        	listmodel2.addElement(s);
					}
			        listmodel.clear();
					
					
				}
				
			}
		});
		
		/********************************************************************************************************************
		IKER
		Este radioboton nos mostrara los mapas default que estan disponible para el caso de que seas un usuario nuevo y no tengas partidas
		almacenadas.
		*********************************************************************************************************************/
		
		
		defaultmaps.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int numero = buttonGroup.getSelection().getMnemonic();
				if(numero == 48) {
					
					filePath = System.getProperty("user.dir"); //debuelve ruta
					filePath=filePath+"\\DefaultMaps";
					File file = new File(filePath);
			        String[] arch = file.list();
			        
			        for (String s : arch) {
			        	
			        	listmodel.addElement(s);
					}
			        listmodel2.clear();
					
					
				}
				
			}
		});
		
		
		/********************************************************************************************************************
		IKER
		Este boton cogera el elemento seleccionado de cualquiera de las 2 listas y automaticamente cogera de ficheros el mapa y los cargara
		
		*********************************************************************************************************************/
		
		
		aceptarboton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int numero = 0;
				try {
					numero = buttonGroup.getSelection().getMnemonic();
				} catch (Exception e2) {
					
				}
				
				
				if(numero == 48) {
					String selec = (String) list.getSelectedValue();
					
					if(selec != null) {
						filePath = System.getProperty("user.dir"); //debuelve ruta
						filePath=filePath+"\\Defaultmaps\\";
						
						selec = filePath+selec;
						
						try {
							
							ObjectInputStream ois = new ObjectInputStream(new FileInputStream(selec));
							
							 try {
								mapGrid = (HashMap<Point, ArrayList<ArrayList<Object>>>) ois.readObject();
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							ois.close();
							
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						scores.iniciaDB();
						scores.guardarpartidasporpersona(codigo, "");
						scores.finalizaDB();
						
						Game mmaker = new Game(codigo,mapGrid,selec,dificul,movili,jugador1,jugador2);
						mmaker.setLocationRelativeTo(null);
						mmaker.pack();
						mmaker.setResizable(false);
						mmaker.setVisible(true);
						dispose();
						
					}else {
						
						logcargar.log(Level.WARNING, "Ningun mapa seleccionado");
						aviso.VentanaDeAvisos(3, "Ningun mapa seleccionado", 1);
						
					}
					
				}else if(numero == 49){
					
					String selec = (String) list_1.getSelectedValue();
					
					if(selec != null) {
						filePath = System.getProperty("user.dir"); //debuelve ruta
						filePath=filePath+"\\GuardadoMapas\\"+selec+".dat";
						
						
						try {
							
							ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
							
							 try {
								mapGrid = (HashMap<Point, ArrayList<ArrayList<Object>>>) ois.readObject();
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							ois.close();
							
						} catch (FileNotFoundException e1) {
							logcargar.log(Level.INFO,e.toString());
						} catch (IOException e1) {
							
							logcargar.log(Level.INFO,e1.toString());
						}
						
						scores.iniciaDB();
						
						String nombrepos = scores.nombrepartidaBD(codigo);
						
						if(nombrepos != null) {
							
							scores.finalizaDB();
						}else {
							scores.guardarpartidasporpersona(codigo, "");
							scores.finalizaDB();
						}
						
						Game mmaker = new Game(codigo,mapGrid,selec,dificul,movili,jugador1,jugador2);
						mmaker.setLocationRelativeTo(null);
						mmaker.pack();
						mmaker.setResizable(false);
						mmaker.setVisible(true);
						dispose();
						
					}else {
						
						logcargar.log(Level.WARNING, "Ningun mapa seleccionado");
						aviso.VentanaDeAvisos(3, "Ningun mapa seleccionado", 1);
						
					}
					
				}else {
					logcargar.log(Level.WARNING, "Ninguna opcion seleccionada");
					aviso.VentanaDeAvisos(3, "Ninguna opcion seleccionada", 1);
					
				}
				
			}
		});
	}
}
