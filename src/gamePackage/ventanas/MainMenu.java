package gamePackage.ventanas;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;

import gamePackage.baseDatos.DataBase;
import gamePackage.sonidos.SoundMngr;

public class MainMenu extends JFrame implements Serializable{
	
	HashMap<String, HashMap<Point, ArrayList<ArrayList<Object>>>> mapausuario = new HashMap<>();
	HashMap<Point, ArrayList<ArrayList<Object>>> mapa = new HashMap<Point, ArrayList<ArrayList<Object>>>();
	String nombre = "";
	String contra = "";
	String[] arch ;
	VentanaAvisos aviso = new VentanaAvisos();
	Logger logmain = Logger.getGlobal();
	
	public static void main(String[] args) {
		MainMenu menu = new MainMenu(3,10,10000,10000,0,"b",1);
		menu.setLocationRelativeTo(null);
		menu.setResizable(false);
		menu.setVisible(true);
		
	}

	public MainMenu(int dificult,int movili,int jugador1,int jugador2,int login,String nombre,int codigo) {
		DataBase scores = new DataBase();
		scores.iniciaDB();
		scores.creaTablas();
		//scores.eliminaDB();
		//Inicializa valores de: HOY, basado en fecha de sistema
//		scores.actualizaGlobal(false, false, false, false, false);
//		scores.actualizaTEquipos(0, 0, 0, 0);
		SoundMngr sic = new SoundMngr("mainmenu.wav",1,0);
		//Thread mus = new Thread(sic);
		Container cp = this.getContentPane();
		cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS));
		JLayeredPane layeredMenuPanel = new JLayeredPane();
		layeredMenuPanel.setBounds(0, 0, 1088, 672);
		layeredMenuPanel.setBackground(Color.green);
		//mus.start();

		JPanel options = new JPanel();
		options.setLayout(null);
		options.setBounds(0, 0, 1088, 672);
		
		JButton invisible = new JButton("Jugadore mas activos");
		invisible.setBounds(50, 50, 170, 50);
		options.add(invisible);
		invisible.setOpaque(true);
		scores.finalizaDB();
		/********************************************************************************************************************
		IKER
		Este boton nos va a mostrar todos los jugadores registrados en la BD y los ordena por el que mas partidas tiene al que menos
		*********************************************************************************************************************/
		
		
		invisible.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Ventausuariomasactivo frame = new Ventausuariomasactivo();
				frame.setVisible(true);
				
			}
		});
		
		// JLabel filter, playSingle, playMulti, settings, exit = new JLabel();
		JLabel background = new JLabel();
		background.setIcon(new ImageIcon(getClass().getResource("img/MainMenu.png")));
		background.setBounds(0, 0, 1088, 672);

		JLabel title = new JLabel();
		JButton play = new JButton("");
		play.setBounds(137, 524, 100, 100);
		play.setOpaque(false);
		play.setContentAreaFilled(false);
		play.setBorderPainted(false);

		/********************************************************************************************************************
		IKER
		Este botón nos llevara a la zona de juego no sin antes comprobar que estemos logineado.
		Por ello en el caso de no estarlo nos abre la ventana de login.
		Posteriormente nos llevara a la zona de crear la partida.

		*********************************************************************************************************************/
		
		
		play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				if(login == 1) {
					creacion_cargar_mapas ccm = new creacion_cargar_mapas(codigo,nombre,dificult,movili,jugador1,jugador2);
					ccm.setVisible(true);
					
				}else {
					Login_Register lg = new Login_Register(dificult,movili,jugador1,jugador2,0);
					lg.setVisible(true);
					
					
				}
				dispose();
				
			}
		});

		/********************************************************************************************************************
		IKER
		Este boton nos llevara a la zona de creacion de mapa no sin antes comprobar si estamos logeados.
		Una vez logeados automaticamente nos llevara.
		*********************************************************************************************************************/
		
		
		JButton mapMaker = new JButton("");
		mapMaker.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				if(login == 1) {
					aviso.VentanaDeAvisos(2, "Desea ir al creador de mapa version 2?", 2);
					
					if(aviso.getResultados() == 1) {
						MapMakerv2 juego = new MapMakerv2(dificult,movili,jugador1,jugador2,nombre,codigo);
						juego.pack();
						juego.setLocationRelativeTo(null);
						juego.setResizable(false);
						juego.setVisible(true);
						
					}else {
						MapMaker juego = new MapMaker(dificult,movili,jugador1,jugador2,nombre,codigo);
						juego.pack();
						juego.setLocationRelativeTo(null);
						juego.setResizable(false);
						juego.setVisible(true);
						
					}
					
					
					
				}else {
					Login_Register lg = new Login_Register(dificult,movili,jugador1,jugador2,2);
					lg.setVisible(true);
					//scores.actualizaGlobal(false, false, false, false, true);
					DataBase.finalizaDB();
					
				}
				
				scores.finalizaDB();
				new Thread(new SoundMngr("weegee.wav", 0, 0)).start();
				//mus.interrupt();
				try {
					sic.stop();
				} catch (IOException e1) {
					logmain.log(Level.WARNING,e.toString());
				}
				
			
			}
		});
		mapMaker.setBounds(292, 536, 115, 160);
		mapMaker.setOpaque(false);
		mapMaker.setContentAreaFilled(false);
		mapMaker.setBorderPainted(false);

		JButton settings = new JButton("");
		settings.setBounds(632, 541, 160, 160);
		settings.setOpaque(false);
		settings.setContentAreaFilled(false);
		settings.setBorderPainted(false);
		
		/********************************************************************************************************************
		IKER
		Este boton nos llevara a la configuracion no sin antes preguntar si estamos logeados.
		Despues nos introducira en ellos
		*********************************************************************************************************************/
		

		settings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				Login_Register lg = new Login_Register(dificult,movili,jugador1,jugador2,1);
				lg.setVisible(true);
				
				new Thread(new SoundMngr("weegee.wav", 0, 0)).start();
				//mus.interrupt();
				try {
					sic.stop();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});

		JButton exit = new JButton("");
		exit.setBounds(848, 492, 200, 200);
		exit.setOpaque(false);
		exit.setContentAreaFilled(false);
		exit.setBorderPainted(false);
		
		/********************************************************************************************************************
		IKER
		Boton de cerrar el programa
		*********************************************************************************************************************/
		

		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				scores.finalizaDB();
			//	mus.interrupt();
				try {
					sic.stop();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				new Thread(new SoundMngr("weegee.wav", 0, 0)).start();
				System.exit(0);

			}
		});

		background.setBackground(Color.magenta);
		title.setBackground(Color.cyan);


		options.add(play);
		options.add(mapMaker);
		options.add(settings);
		options.add(exit);

		options.setOpaque(false);

		layeredMenuPanel.add(background, 0, 0);
		layeredMenuPanel.add(options, 1, 0);

		options.setPreferredSize(new Dimension(1088, 672));
		layeredMenuPanel.setPreferredSize(new Dimension(1088, 672));
		cp.setPreferredSize(new Dimension(1088, 672));
		cp.add(layeredMenuPanel);

		this.setTitle("B.A.S.E.D Tactics");
		this.setIconImage(new ImageIcon(getClass().getResource("img/tankicon.png")).getImage());
		this.setSize(new Dimension(1088, 672));
		this.pack();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}
