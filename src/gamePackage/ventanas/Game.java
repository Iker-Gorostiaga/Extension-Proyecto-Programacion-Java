package gamePackage.ventanas;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import gamePackage.sonidos.SoundMngr;
import gamePackage.baseDatos.DataBase;
import gamePackage.entidades.*;
import gamePackage.logica.*;
import gamePackage.entidades.terrestres.*;
import gamePackage.terrenos.*;
import gamePackage.terrenos.estructuras.*;
import gamePackage.terrenos.terrenos.*;




@SuppressWarnings("serial")
public class Game extends JFrame implements Serializable{
	
	public static void main(String[] args){
		//creaciÃ³n de la instancia de la ventana y modificacion de algunos de sus atributos para que sea visible 
//		Game juego = new Game("BeanIsland");
//		juego.pack();
//		juego.setLocationRelativeTo(null);
//		juego.setResizable(false);
//		juego.setVisible(true);
//		Logger logger = Logger.getLogger(Game.class.getName());

	}
	//public static int tiles = 17;  <-- Viejo tamaÃ±o de las casillas
		//TamaÃ±o de las casillas y valor por el que se multiplica el valor de x e y de los labels
	public volatile HashMap<Point, ArrayList<ArrayList<Object>>> mapGrid = new HashMap<>();
	public int turn = 1;
	public boolean estadoMov = false;
	public boolean funcionando = true;
	DataBase scores = new DataBase();
	VentanaAvisos aviso = new VentanaAvisos();
	
	Thread explo;
	Point ogPos = new Point();
	Player p1 = new Player(1);
	Player p2 = new Player(2);
	SoundMngr sic = new SoundMngr("combat1.wav",0,0);
	Thread mus = new Thread(sic);
	JPanel derecha = new JPanel();
	JPanel abajo = new JPanel();
	JPanel troopInfo = new JPanel();
	JLabel gold = new JLabel();
	JLabel vital = new JLabel();
	JLabel jugadorinfo = new JLabel();
	JPanel info = new JPanel();	
	Tropa t = null;
	
	JLabel name = new JLabel("Nombre: ");
	JLabel Nivel = new JLabel("Nivel: ");
	JLabel attack1 = new JLabel("Da�o ataque principal:");
	JLabel attack2 = new JLabel("Da�o ataque secundario:");
	JLabel Tipo = new JLabel("Tipo de tropa: ");
	Tropa trclick;
	
	int dinero = p1.getfunds();
	int vitalidad;
	int conquista = 0;
	int movilid;
	Logger logjuego = Logger.global;
	private final JLabel lblNewLabel = new JLabel("");
	private final JLabel lblNewLabel_1 = new JLabel("");
	private final JButton botonataque2 = new JButton("+20");
	private final JButton botonataque1 = new JButton("+20");
	private final JLabel lblNewLabel_2 = new JLabel("");
	private final JLabel lblNewLabel_3 = new JLabel("");
	private final JLabel lblNewLabel_4 = new JLabel("");
	private final JLabel lblNewLabel_5 = new JLabel("");
	private final JLabel lblNewLabel_6 = new JLabel("");
	private final JLabel lblNewLabel_7 = new JLabel("");
	private final JButton turnEnd = new JButton("TERMINAR TURNO");
	private final JButton Guardar = new JButton("Guardar");
	private final JLabel lblNewLabel_8 = new JLabel("");
	private final JLabel lblNewLabel_9 = new JLabel("");
	
	
	public Game(int codigo,HashMap<Point, ArrayList<ArrayList<Object>>> mapa,String Archivo,int dificult,int movili,int jugador1,int jugador2) {
		//mus.start();
		p1.setfunds(jugador1);
		vitalidad = movili;
		movilid = movili;
		
		scores.getConn();
		p2.setfunds(jugador2);
		Container cp = this.getContentPane();
		cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS));		//Se le pone un BoxLayout al contenedor de la ventana en el eje X que coloca los componentes en serie horizontalmente
		JLayeredPane layeredGamePanel = new JLayeredPane();		//CreaciÃ³n de un panel que permite colocar unos componentes por encima de otros
		//layeredGamePanel.setBackground(Color.green);		//for testing
		layeredGamePanel.setBounds(0, 0, 672, 672);		//PosiciÃ³n y tamaÃ±o del panel del juego
		layeredGamePanel.setPreferredSize(new Dimension(672, 672));		//TamaÃ±o preferido para el panel que hace que alguna instrucciÃ³n no ignore este valor
		
		JLabel cursor = new JLabel();		//Label que contiene el cursor
		
		//Instrucciones para colocar las imagenes en los labels
		cursor.setIcon(new ImageIcon(getClass().getResource("img/Cursor.gif")));
		
		//mapLabel.setPreferredSize(new Dimension(672, 672));		//De nuevo se coloca el tamaÃ±o preferido para que las instrucciones tiendan a usar este valor
		
		JPanel mapPanel = new JPanel();		//CreaciÃ³n del panel en el que estÃ¡ el label del mapa (mÃ¡s tarde serÃ¡n muchos componentes que forman un mapa)
		mapPanel.setLayout(null);		//Se le pone un BoxLayout al panel del mapa en el eje X que coloca los componentes en serie horizontalmente
		//mapPanel.add(mapLabel);		//AÃ±adir el label que contiene el mapa
		mapPanel.setBounds(0, 0, 672, 672);		//PosiciÃ³n y tamaÃ±o del panel del mapa
		//mapPanel.setOpaque(true);		//Hace que se pueda ver lo que haya detrÃ¡s del panel
		
		JPanel troopPanel = new JPanel();
		troopPanel.setLayout(null);
		troopPanel.setBounds(0, 1, 672, 672);
		troopPanel.setOpaque(false);

		cursor.setPreferredSize(new Dimension(32, 32));		//TamaÃ±o preferido del label que contiene el gif del cursor
		cursor.setBounds(32*8, 32*8, 32, 32);		//Lo mismo de antes pero siendo la posiciÃ³n el centro del mapa
		
		JLayeredPane entityPanel = new JLayeredPane();		//CreaciÃ³n del panel que contiene las entidades como tropas o edificios
		entityPanel.setLayout(null);		//Se le pone layout nulo para que deje poner componentes mediante posiciones absolutas
		entityPanel.setBounds(0, 0, 672, 672);		//PosiciÃ³n y tamaÃ±o del panel de entidades
		entityPanel.add(cursor, 2, 0);		//Se aÃ±ade el label del cursor con una prioridad mayor que hace que estÃ© sobre las tropas y entidades
		entityPanel.add(troopPanel, 1, 0);
		entityPanel.setOpaque(false);		//Se cambia el atributo del panel para hacer que se pueda ver lo que tiene debajo (otro panel)
		
		layeredGamePanel.add(mapPanel, 0, 0);		//Se aÃ±ade el panel que contiene el mapa con prioridad baja para que estÃ© por debajo del resto de cosas que se aÃ±adan
		layeredGamePanel.add(entityPanel, 1, 0);		//Se aÃ±ade el panel de entidades con mayor prioridad que el del mapa para que se vean por encima de este
		
		class ThreadMV implements Runnable{
			volatile boolean stateSwitcher = false;
			volatile boolean on = true;
			@Override
			public void run() {
				
				//mapGrid = loadMap();
				
				Tropa t = (Tropa) mapGrid.get(ogPos).get(1).get(1);
				Point pos = ogPos;
				while (on) {
					while(stateSwitcher == true) {
						Point mouse = MouseInfo.getPointerInfo().getLocation();
						SwingUtilities.convertPointFromScreen(mouse, layeredGamePanel);
						try {
							Thread.sleep(10);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						double x = mouse.getX();
						double y = mouse.getY();
						x = x / 32; y = y / 32;
						x = Math.floor(x); y = Math.floor(y);
						Point cN = new Point((int)pos.getX(), (int)pos.getY()-1);
						Point cS = new Point((int)pos.getX(), (int)pos.getY()+1);
						Point cE = new Point((int)pos.getX()+1, (int)pos.getY());
						Point cO = new Point((int)pos.getX()-1, (int)pos.getY());
						int peajeN = ((Terreno) mapGrid.get(cN).get(0).get(1)).getPeaje(t);
						int peajeS = ((Terreno) mapGrid.get(cS).get(0).get(1)).getPeaje(t);
						int peajeE = ((Terreno) mapGrid.get(cE).get(0).get(1)).getPeaje(t);
						int peajeO = ((Terreno) mapGrid.get(cO).get(0).get(1)).getPeaje(t);
						if (((t.getDistMax()-peajeN)>=0) && (t.getEnergia() != 0) && (mapGrid.get(cN).size() == 1) && (mouse.equals(cN))) {
							cursor.setLocation(cN);
						} else if (((t.getDistMax()-peajeS)>=0) && (t.getEnergia() != 0) && (mapGrid.get(cS).size() == 1) && (mouse.equals(cS))) {
							cursor.setLocation(cS);
						} else if (((t.getDistMax()-peajeE)>=0) && (t.getEnergia() != 0) && (mapGrid.get(cE).size() == 1) && (mouse.equals(cE))) {
							cursor.setLocation(cE);
						} else if (((t.getDistMax()-peajeO)>=0) && (t.getEnergia() != 0) && (mapGrid.get(cO).size() == 1) && (mouse.equals(cO))) {
							cursor.setLocation(cO);
						}
						if (x>= 0 && x <= 21 && y>= 0 && y <= 21) {
							int xCT = (int) (x * 32); int yCT = (int) (y * 32); //xCT = x Cursor Tile 
							cursor.setLocation(xCT, yCT);
						}
						
					}
				}
			}
			public void pause() {
				stateSwitcher = false;
			}
			
			public void resume() {
				stateSwitcher = true;
			}
			

			
		}

		ThreadMV mv = new ThreadMV();
		Thread tMV = new Thread(mv);
		
		
		class CursorMovement implements Runnable{
			volatile boolean stateSwitcher = false;
			volatile boolean on = true;
			@Override
			public void run() {
				while (on) {
					while(stateSwitcher == true) {
						Point mouse = MouseInfo.getPointerInfo().getLocation();
						SwingUtilities.convertPointFromScreen(mouse, layeredGamePanel);
						try {
							Thread.sleep(10);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						double x = mouse.getX();
						double y = mouse.getY();
						x = x / 32; y = y / 32;
						x = Math.floor(x); y = Math.floor(y);
						if (x>= 0 && x <= 21 && y>= 0 && y <= 21) {
							int xCT = (int) (x * 32); int yCT = (int) (y * 32); //xCT = x Cursor Tile 
							cursor.setLocation(xCT, yCT);
						}
					}
				}
			}
			public void pause() {
				stateSwitcher = false;
			}
			
			public void resume() {
				stateSwitcher = true;
			}
		}

		CursorMovement cm = new CursorMovement();
		Thread tCM = new Thread(cm);
		tCM.start();
		
		//sistema de rellenado del mapa al cargar la partida al principio
		mapGrid = mapa;
		
		mapPanel.removeAll();
		
		Set<Point> keys = mapGrid.keySet();
		JLabel jl = new JLabel();
		JLabel o = new JLabel();
		for (Point i : keys) {
			jl = (JLabel) mapGrid.get(i).get(0).get(0);
			jl.setBounds((int)i.getX()*32, (int)i.getY()*32, 32, 32);
			if(mapGrid.get(i).size()>1) {
				o = (JLabel) mapGrid.get(i).get(1).get(0);
				
				troopPanel.add(o);
			}
			mapPanel.add(jl);
		}
		mapPanel.repaint();
		
		MouseListener ml = new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				cm.pause();	
				
				Point casilla = new Point((int) cursor.getX()/32, (int) cursor.getY()/32);
				
				int teamTester;
				switch (turn%2) {
				case 0:
					teamTester = 2;
					break;
				default:
					teamTester = 1;
					break;
				}
				if (mapGrid.get(casilla).size() == 2) {
					if (estadoMov == false) {
						if (((Tropa) mapGrid.get(casilla).get(1).get(1)).getTeam() != teamTester) {
								estadoMov = true;
								ogPos = casilla;
								cm.pause();
								if (tMV.isAlive()) {
									mv.resume();
								} else {
									tMV.start();
								}
						} 
						
				
					}
				 else {
					mv.pause();
					cm.resume();
					estadoMov = false;
				 }
				} else if (!(((Terreno) mapGrid.get(casilla).get(0).get(1)).getIDTerreno() != ListaIDTerreno.FACTORY || mapGrid.get(casilla).size() == 2)) {
					if (((Factory) mapGrid.get(casilla).get(0).get(1)).getTeam() == teamTester) {
						createTropa(cursor.getLocation(), mapGrid, layeredGamePanel, troopPanel, turn, casilla);
						} 
				}
				
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
				cm.resume();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				/********************************************************************************************************************
				IKER
				La mayor parte de programa ocurre aqui.
				La movilidad de la tropa.
				Detectar si se a movido la tropa o no y porque.
				Comprovar la vitalidad a cada movimiento.
				Comprovar cada batalla entre las tropas de la partida

				*********************************************************************************************************************/
				
				
				///movimiento de 1 casilla Iker
				Point origen = new Point((int) cursor.getX()/32, (int) cursor.getY()/32);

				//bussco tropas alrededor
				int x=(int) cursor.getX()/32;
				int y=(int) cursor.getY()/32;
				int mov=1; //permito un movimiento
				Tropa t =null;
				Hq cast=null;
				conquista = 0;
				int turv = (turn % 2);
				int tur;
				 if (turv==0)
				    {tur=1;}else {tur=2;}
				
				 //funcion 1
				 t = movimientotropas(x, y, tur, origen,t,mov);
				 // fin funcion 1
				 
				 if(t!=null&&conquista!=1) {
					 mov = 0;
				 }
				 
				 //funcion 2
				detectarmovimiento(x, y, mov, Archivo, codigo, tur, troopPanel, layeredGamePanel, mapPanel);
				//fin funcion 2
				
				//funcion 3
				vitalidadrestante(tur, t, cursor, mapPanel, troopPanel);
				//fin funcion 3
			}
			
		};
		
		
		//KeyListener para mover el cursor
		KeyListener kListener = new KeyListener() {
			boolean released = true;		//Redundante de momento pero sirve para nno dejar que se mantenga pulsada la tecla y haya que soltar la tecla antes de que se vuelva a registrar
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				released = true;
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println("fuck");
				int x = cursor.getLocation().x;
				int y = cursor.getLocation().y;
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_LEFT && x > 0 && released) {
					cursor.setLocation(x - 32, y);
			        //System.out.println("left");
					//released = false;		//quitar el comentario del inicio de esta linea para dar uso a la variable released
			    }

			    if (key == KeyEvent.VK_RIGHT && x < 640) {
			    	cursor.setLocation(x + 32, y);
			    	//System.out.println("right");
			    	//released = false;
			    }

			    if (key == KeyEvent.VK_UP && y > 0) {
			    	cursor.setLocation(x, y - 32);
			    	//System.out.println("up");
			    	//released = false;
			    }

			    if (key == KeyEvent.VK_DOWN && y < 640) {
			    	cursor.setLocation(x, y + 32);
			    	//System.out.println("down");
			    	//released = false;
			    }
			    
			    if (key == KeyEvent.VK_SPACE) {
			    	
			    }
			}
		};
		
		
		
		
		derecha = new JPanel();		//CreaciÃ³n del panel de la derecha que contiene otros paneles que mostrarÃ¡n la informaciÃ³n de tropas y terrenos
		derecha.setPreferredSize(new Dimension(416, 672));
		/********************************************************************************************************************
		IKER
		Hilo para actualizar seg�n el turno la informaci�n de cada jugador cada 2 segundos y a�adiendo la cantidad de dinero 
		seleccionada en los ajustes del men� principal.

		*********************************************************************************************************************/
		
		
		class informacion extends Thread {
			
			public void run() {
				
				
				while(funcionando) {
					switch (turn % 2) {
					case 1:
						try {
							info.setBackground(Color.RED);
							jugadorinfo.setText("JUGADOR ROJO");
							Thread.sleep(2000);
							int dinero = p1.getfunds();
							dinero += dificult*15 + 5;
							p1.setfunds(dinero);
							dinero = p2.getfunds();
							dinero += dificult*15 + 5;
							p2.setfunds(dinero);
							gold.setText("FONDOS: "+Integer.toString(p1.getfunds()));
							derecha.repaint();
							info.repaint();
							layeredGamePanel.repaint();
						} catch (InterruptedException e) {
							logjuego.log(Level.WARNING,e.toString());
						}
						
						break;
					default:
						try {
							info.setBackground(Color.CYAN);
							jugadorinfo.setText("JUGADOR AZUL");
							Thread.sleep(2000);
							int dinero = p2.getfunds();
							dinero += dificult*15 + 5;
							p2.setfunds(dinero);
							dinero = p1.getfunds();
							dinero += dificult*15 + 5;
							p1.setfunds(dinero);
							gold.setText("FONDOS: "+Integer.toString(p2.getfunds()));
							info.repaint();
							derecha.repaint();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						break;
					}
					
				}
				
			}
			
			
			
		}
		
		informacion in = new informacion();
		Thread informa = new Thread(in);
		informa.start();
		GridBagLayout gbl_derecha = new GridBagLayout();
		gbl_derecha.columnWidths = new int[] {432};
		gbl_derecha.rowHeights = new int[] {224, 344, 130, 30};
		gbl_derecha.columnWeights = new double[]{0.0};
		gbl_derecha.rowWeights = new double[]{0.0, 0.0, 0.0};
		derecha.setLayout(gbl_derecha);
		
		info = new JPanel();		//CreaciÃ³n del panel superior de informaciÃ³n general de la partida
		info.setBackground(Color.GRAY);		//InstrucciÃ³n de prueba para diferenciar los paneles mientras no estÃ©n programados
		info.setPreferredSize(new Dimension(416, 416));		//TamaÃ±o preferido del panel de informaciÃ³n general
		info.setLayout(new GridLayout(6, 1));
		//troopInfo.setLayout(new BoxLayout(troopInfo, BoxLayout.X_AXIS));
		
		
		//Labels de los atributos del panel info
		
		gold = new JLabel("Fondos que quedan: "+Integer.toString(p1.getfunds()));
		gold.setFont(new Font("Tahoma", Font.BOLD, 10));
		jugadorinfo = new JLabel("");
		jugadorinfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		vital = new JLabel("ROJO:  Tienes "+Integer.toString(vitalidad)+" movimientos mas");
		vital.setFont(new Font("Tahoma", Font.BOLD, 10));
		
				
				info.add(jugadorinfo);
				info.add(gold);
				info.add(vital);
				
				GridBagConstraints gbc_info = new GridBagConstraints();
				gbc_info.fill = GridBagConstraints.BOTH;
				gbc_info.insets = new Insets(0, 0, 5, 0);
				gbc_info.gridx = 0;
				gbc_info.gridy = 0;
				derecha.add(info, gbc_info);		//Se aÃ±ade el panel superior que contiene informaciÃ³n general de la partida al panel que contiene toda la parte derecha (informacion)
		
		abajo = new JPanel();		//CreaciÃ³n del panel inferior de la seccion de informaciÃ³n (parte derecha de la pantalla)
		abajo.setLayout(new BoxLayout(abajo, BoxLayout.X_AXIS));
		//movData.setLayout(new BoxLayout(movData, BoxLayout.Y_AXIS));
		
		troopInfo = new JPanel();
		troopInfo.setBackground(Color.LIGHT_GRAY);		//InstrucciÃ³n de prueba para diferenciar los paneles mientras no estÃ©n programados
		troopInfo.setPreferredSize(new Dimension(256, 256));		//TamaÃ±o preferido del panel de informaciÃ³n de tropas
		GridLayout gl_troopInfo = new GridLayout(5, 2);
		troopInfo.setLayout(gl_troopInfo);
		
		//Labels de los atributos del panel troopInfo
		
		
		
		troopInfo.add(name);
		
		troopInfo.add(lblNewLabel);
		troopInfo.add(Tipo);
		
		troopInfo.add(lblNewLabel_1);
		troopInfo.add(Nivel);
		
		troopInfo.add(lblNewLabel_2);
		troopInfo.add(attack1);
		
		
		troopInfo.add(botonataque1);
		troopInfo.add(attack2);
		abajo.add(troopInfo);
		
		
		
		troopInfo.add(botonataque2);
		GridBagConstraints gbc_abajo = new GridBagConstraints();
		gbc_abajo.fill = GridBagConstraints.BOTH;
		gbc_abajo.insets = new Insets(0, 0, 5, 0);
		gbc_abajo.gridx = 0;
		gbc_abajo.gridy = 1;
		derecha.add(abajo, gbc_abajo);		//Se aÃ±ade el panel inferior que contiene los paneles movData y troopInfo al panel que contiene toda la parte derecha (informacion)
		
		JPanel movData = new JPanel();
		movData.setPreferredSize(new Dimension(160, 100));
		movData.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_movData = new GridBagConstraints();
		gbc_movData.insets = new Insets(0, 0, 5, 0);
		gbc_movData.fill = GridBagConstraints.BOTH;
		gbc_movData.gridx = 0;
		gbc_movData.gridy = 2;
		derecha.add(movData, gbc_movData);
		movData.setLayout(new GridLayout(3, 3, 0, 0));
		
		movData.add(lblNewLabel_3);
		
		movData.add(lblNewLabel_4);
		
		movData.add(lblNewLabel_5);
		turnEnd.setFont(new Font("Tahoma", Font.PLAIN, 8));
		turnEnd.setMinimumSize(new Dimension(50, 50));
		turnEnd.setMaximumSize(new Dimension(50, 50));
		
		movData.add(turnEnd);
		
		movData.add(lblNewLabel_6);
		Guardar.setFont(new Font("Tahoma", Font.PLAIN, 8));
		
		movData.add(Guardar);
		
		movData.add(lblNewLabel_7);
		
		movData.add(lblNewLabel_8);
		
		movData.add(lblNewLabel_9);
		
		cp.add(layeredGamePanel);		//Se aÃ±ade el panel del juego al contenedor de la ventana
		cp.add(derecha);

		this.pack();		//Se asegura de que todos los componentes estÃ¡n por lo menos a su tamaÃ±o preferido
		addKeyListener(kListener);		//Se aÃ±ade el KeyListener a la ventana
		addMouseListener(ml);
		this.setTitle("B.A.S.E.D Tactics");		//Se cambia el titulo de la ventana
		this.setIconImage(new ImageIcon(getClass().getResource("img/tankicon.png")).getImage());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);		//El proceso se termina cuando se cierra la ventana
		
		/********************************************************************************************************************
		IKER
		El bot�n de guardar nos va a pedir un nombre para el archivo a guardar. Para ello hemos hecho que compruebe si el nombre ya esta 
		en uso para que no se sobrescriba. Tambi�n guardamos en la BD el usuario con el nombre del mapa.

		*********************************************************************************************************************/
		
		Guardar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String nombrearch = JOptionPane.showInputDialog("Que nombre le ponemos al archivo");
				scores.iniciaDB();
				if(!nombrearch.equals("")) {
					
					
					int s = scores.Confirmarnombremultiple(codigo, nombrearch);
					
					if(s!=0) {
						
						aviso.VentanaDeAvisos(3, "El nombre de archivo ya esta en uso por otro usuario", 1);
					}else {
						int cod_p = scores.codigopartidaBD(codigo);
						scores.actualizarpartida(codigo,nombrearch,cod_p);

						String filePath = System.getProperty("user.dir"); 
						filePath = filePath +"\\Guardadomapas\\"+nombrearch+".dat";

								try {
									
									ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
							
									oos.writeObject(mapGrid);
									
									oos.close();
									
								} catch (FileNotFoundException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (IOException e1) {
									
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								aviso.VentanaDeAvisos(1, "El archivo a sido guardado con exito", 1);
					}
					
					
					
				}else {
					
					logjuego.log(Level.WARNING,"Nombre de Archivo nulo o vacio");
					aviso.VentanaDeAvisos(3, "Nombre de Archivo nulo o vacio", 1);
				}
				scores.finalizaDB();
						
			}
		});
		
		/********************************************************************************************************************
		IKER
		Este boton nos lleva a la funcion de terminar el turno mas abajo.
		*********************************************************************************************************************/
		
		
		turnEnd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				terminaTurno(turn, mapPanel, mapGrid);
				System.out.println(turn);
				scores.iniciaDB();
				scores.actualizaTEquipos(p1.getfunds(),0,p2.getfunds(),0);
				scores.finalizaDB();
				
			}
		});
		
		/********************************************************************************************************************
		IKER
		Aumenta la propiedad dano del ataque primario de la tropa	
		*********************************************************************************************************************/
		botonataque1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				switch (turn % 2) {
				case 1: {
					dinero = p1.getfunds();
					dinero = dinero-20;
					p1.setfunds(dinero);
					
					
					break;
				}
				default:
					dinero = p2.getfunds();
					dinero = dinero-20;
					p2.setfunds(dinero);

					break;
				}
				
				Point casilla = new Point((int) cursor.getX()/32, (int) cursor.getY()/32);
				int cant = mapGrid.get(casilla).size();
				if(cant > 1) {
				for (ArrayList<Object> a : mapGrid.get(casilla)) {
					if(a.get(1) instanceof Tropa) {
						trclick = (Tropa) a.get(1);
						int att1 = trclick.getGolpePrim();
						att1 += 20;
						trclick.setGolpePrim(att1);
						
				}
				}
				}
				try {
					int g1 = trclick.getGolpePrim();
					attack1.setText("Da�o ataque principal: "+Integer.toString(g1));
					abajo.repaint();
				} catch (Exception e2) {
					logjuego.log(Level.INFO,"Ninguna tropa seleccionada");
				}
				
			}
			
		});
		/********************************************************************************************************************
		IKER
		Aumenta la propiedad dano del ataque secundario de la tropa	
		*********************************************************************************************************************/
		botonataque2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				switch (turn % 2) {
				case 1: {
					dinero = p1.getfunds();
					dinero = dinero-20;
					p1.setfunds(dinero);
					
					
					break;
				}
				default:
					dinero = p2.getfunds();
					dinero = dinero-20;
					p2.setfunds(dinero);

					break;
				}
				
				
				Point casilla = new Point((int) cursor.getX()/32, (int) cursor.getY()/32);
				int cant = mapGrid.get(casilla).size();
				if(cant > 1) {
				for (ArrayList<Object> a : mapGrid.get(casilla)) {
					if(a.get(1) instanceof Tropa) {
						trclick = (Tropa) a.get(1);
						int att2 = trclick.getGolpeSec();
						att2 += 20;
						trclick.setGolpeSec(att2);
						
				}
				}
				}
				try {
					int g1 = trclick.getGolpeSec();
					attack2.setText("Da�o ataque Secundario: "+Integer.toString(g1));
					abajo.repaint();
				} catch (Exception e2) {
					logjuego.log(Level.INFO,"Ninguna tropa seleccionada");
				}
			}
			
		});
		
		
		
		}
	/********************************************************************************************************************
	IKER
	En el momento que se hace clic alrededor de una tropa, s�lo horizontal y verticalmente, este lo detecta.
	A continuaci�n, este va a revisar si la casilla en la que clicaste no es mar o base principal para no poder avanzar atreves de ellas.
	Si no es ninguna de las anteriores la tropa del equipo procede a avanzar a la casilla seleccionada.
	Tambi�n comprobamos que si la casilla a la que se quiere mover es una base enemiga para habilitar la conquista.	

	*********************************************************************************************************************/
	
	public Tropa movimientotropas(int x,int y,int tur,Point origen,Tropa t,int mov) {
		
		 if(!(mapGrid.get(new Point(x,y)).get(0).get(1) instanceof Sea)) {
				try {
				if(!(mapGrid.get(new Point(x,y-1)).size() == 1 || ((Tropa) mapGrid.get(new Point(x,y-1)).get(1).get(1)).getTeam() != tur ) && vitalidad != 0 && (y-1)>=0 && mov==1 )//hacia abajo
				{//Hay tropa encima
	
					t = (Tropa) mapGrid.get(new Point(x,y-1)).get(1).get(1);
	
					if(t.getTeam()==tur) {
	
					if(!(mapGrid.get(new Point(x,y)).get(0).get(1) instanceof Hq)) {
					JLabel tl = (JLabel) mapGrid.get(new Point(x,y-1)).get(1).get(0);
					ArrayList<Object> al = new ArrayList<>();
					al.add(tl);
					al.add(t);
					mapGrid.get(new Point(x,y-1)).remove(1); //borro en el mapset
					/// acyualizo la posicion
					Point Final = new Point((int) origen.getX(), (int) origen.getY());
	
					tl.setBounds((int) Final.getX()*32, (int) Final.getY()*32, 32, 32);
					mapGrid.get(Final).add(al);
					mov=0;
					vitalidad--;
					}else {
					conquista = 1;
					}
					}



				}
				}
				catch(Exception e1) {
					logjuego.log(Level.INFO,"Ninguna tropa al alcance");
				}
				try {
				if(!(mapGrid.get(new Point(x,y+1)).size() == 1 || ((Tropa) mapGrid.get(new Point(x,y+1)).get(1).get(1)).getTeam() != tur ) && vitalidad != 0 && (y+1)<=20 && mov==1)//hacia arriba
				{//Hay tropa debajo
					t = (Tropa) mapGrid.get(new Point(x,y+1)).get(1).get(1);
	
					if(t.getTeam()==tur) {
					//miro si es un castillo
	
					if(!(mapGrid.get(new Point(x,y)).get(0).get(1) instanceof Hq)) {
					JLabel tl = (JLabel) mapGrid.get(new Point(x,y+1)).get(1).get(0);
					ArrayList<Object> al = new ArrayList<>();
					al.add(tl);
					al.add(t);
					mapGrid.get(new Point(x,y+1)).remove(1); //borro en el mapset
					/// acyualizo la posicion
					Point Final = new Point((int) origen.getX(), (int) origen.getY());
					tl.setBounds((int) Final.getX()*32, (int) Final.getY()*32, 32, 32);
					mapGrid.get(Final).add(al);
					mov=0;
					vitalidad--;
					}else {
					conquista = 1;
					}
					}
				}
				}
				catch(Exception e1) {
					logjuego.log(Level.INFO,"Ninguna tropa al alcance");
				}
				try {
				if(!(mapGrid.get(new Point(x-1,y)).size() == 1 || ((Tropa) mapGrid.get(new Point(x-1,y)).get(1).get(1)).getTeam() != tur ) && vitalidad != 0 && (x-1)>=0 && mov==1)//Avanza
				{//Hay tropa derecha
					t = (Tropa) mapGrid.get(new Point(x-1,y)).get(1).get(1);
	
					if(t.getTeam()==tur) {
					//miro si es un castillo
	
					if(!(mapGrid.get(new Point(x,y)).get(0).get(1) instanceof Hq)) {//miro si No es castillo
					JLabel tl = (JLabel) mapGrid.get(new Point(x-1,y)).get(1).get(0);
					ArrayList<Object> al = new ArrayList<>();
					al.add(tl);
					al.add(t);
					mapGrid.get(new Point(x-1,y)).remove(1); //borro en el mapset
					/// acyualizo la posicion
					Point Final = new Point((int) origen.getX(), (int) origen.getY());
					tl.setBounds((int) Final.getX()*32, (int) Final.getY()*32, 32, 32);
					mapGrid.get(Final).add(al);
					mov=0;
					vitalidad--;
					}else {
					conquista = 1;
					}
					}

				}
				}
				catch(Exception e1) {
					logjuego.log(Level.INFO,"Ninguna tropa al alcance");
				}
				try {
				if(!(mapGrid.get(new Point(x+1,y)).size() == 1 || ((Tropa) mapGrid.get(new Point(x+1,y)).get(1).get(1)).getTeam() != tur ) && vitalidad != 0 && (x+1) <=20 && mov==1 )//Retrocede
				{//Hay tropa delante
					t = (Tropa) mapGrid.get(new Point(x+1,y)).get(1).get(1);
	
	
					if(t.getTeam()==tur) {
	
					if(!(mapGrid.get(new Point(x,y)).get(0).get(1) instanceof Hq)) {
	
					JLabel tl = (JLabel) mapGrid.get(new Point(x+1,y)).get(1).get(0);
					ArrayList<Object> al = new ArrayList<>();
					al.add(tl);
					al.add(t);
					mapGrid.get(new Point(x+1,y)).remove(1); //borro en el mapset
					/// acyualizo la posicion
					Point Final = new Point((int) origen.getX(), (int) origen.getY());
					tl.setBounds((int) Final.getX()*32, (int) Final.getY()*32, 32, 32);
					mapGrid.get(Final).add(al);
					mov=0;
					vitalidad--;
					}else {
					conquista = 1;
					}
					}
				}
				}
				catch(Exception e1) {
					logjuego.log(Level.INFO,"Ninguna tropa al alcance");
				}

				 }
		
		
		
		return t;
		
	}
	
	
	/********************************************************************************************************************
	IKER
	En esta funci�n comprobamos si la tropa se a llegado a mover. En el caso de que no se haya movido comprobamos se la variable
	conquista esta activa. Si esta activa esta tropa atacara a la base quitando 5 de vida por ataque.
	La base al llegar a 0 o inferior de vida se destruir� y el juego acabara llev�ndolo al men� principal

	*********************************************************************************************************************/
	
	public void detectarmovimiento(int x,int y,int mov,String Archivo,int codigo,int tur,JPanel troopPanel,JLayeredPane layeredGamePanel,JPanel mapPanel) {
		//Cuento los elementos de una celda seleccionada
		if (mov==0){//si ha habido un movimiento verifico el combate

				ArrayList<ArrayList<Object>> listaguerra = mapGrid.get(new Point(x,y));
				int cont =0;

				for (ArrayList<Object> it : listaguerra) {
				for (Object p : it) {
				if(p instanceof Tropa) {
				cont++;
				}
				}
				}

		try {

			Tropa trop1 = (Tropa)listaguerra.get(2).get(1);
			Tropa trop2 = (Tropa)listaguerra.get(1).get(1);
			if(cont == 2 &&(trop1.getTeam() !=trop2.getTeam() ) ) {
			//guerra detecto dos tropas de distinta equipo en misma casilla
			//destino con tropa??

			int victoria = GamePlay.combat((Tropa)listaguerra.get(2).get(1),(Tropa)listaguerra.get(1).get(1),Archivo,codigo,tur);
			
			String filePath = System.getProperty("user.dir"); 
			filePath = filePath +"\\Imagenes\\peque.gif";
			
			Label_fondo explo = new Label_fondo("",filePath, 2);
			
			
			if(victoria == 1) {

				ArrayList<ArrayList<Object>> lista = mapGrid.get(new Point(x,y));

				System.out.println("Victoria del defensor");
				
				Tropa trop =  (Tropa) mapGrid.get(new Point(x,y)).get(1).get(1);
				int nivel = trop.getNivel();
				nivel ++;
				trop.setNivel(nivel);
				JLabel lab = (JLabel) mapGrid.get(new Point(x,y)).get(2).get(0);
				mapGrid.get(new Point(x,y)).remove(2);
				for (Component tr : troopPanel.getComponents()) {
				if(tr.equals(lab)) {
				troopPanel.remove(tr);
				}
				}

			}else if(victoria == 2){
				System.out.println("Victoria del Atacante");
				Tropa trop =  (Tropa) mapGrid.get(new Point(x,y)).get(2).get(1);
				int nivel = trop.getNivel();
				nivel ++;
				trop.setNivel(nivel);
				
				ArrayList<ArrayList<Object>> lista = mapGrid.get(new Point(x,y));

				JLabel lab = (JLabel) mapGrid.get(new Point(x,y)).get(1).get(0);
				mapGrid.get(new Point(x,y)).remove(1);
				for (Component tr : troopPanel.getComponents()) {
				if(tr.equals(lab)) {
				troopPanel.remove(tr);
				}
				}

		}
		troopPanel.add(explo);
		explo.setBounds(x*32-16, y*32-16, 64, 64);
		layeredGamePanel.repaint();
		troopPanel.repaint();
		mapPanel.repaint();

		}

		} catch (Exception e2) {
			
		}

		//fin combate

		// si no hay movimiento reduzco la vitalidad del castillo
		}else {
			Hq castillo = new Hq(1);
			try {
				castillo = (Hq) mapGrid.get(new Point(x,y)).get(0).get(1);
				
			}catch(Exception e1) {
				logjuego.log(Level.INFO,"Sin base al alcance");
			}
			
			
		
			if(conquista == 1 && castillo.getTeam() == tur) {
			

			int vida = (int) castillo.getHp();
			vida = vida -5;
			castillo.setHp(vida);

			if(vida <= 0) {


				JLabel lab = (JLabel) mapGrid.get(new Point(x,y)).get(0).get(0);
				mapGrid.get(new Point(x,y)).remove(0);
				for (Component tr : mapPanel.getComponents()) {
				if(tr.equals(lab)) {
				mapPanel.remove(tr);
				}
				}
				ArrayList<ArrayList<Object>> tierra = new ArrayList<ArrayList<Object>>();
				ArrayList<Object> aire = new ArrayList<Object>();
				JLabel suelo = new JLabel();


				Plains planicie = new Plains();

				suelo.setIcon(new ImageIcon(getClass().getResource("img/terrain/Plains.png")));
				aire.add(suelo);
				aire.add(planicie);
				tierra.add(aire);
				mapGrid.put(new Point(x,y),tierra);
				suelo.setBounds((int) x * 32, (int) y * 32, 32, 32);
				mapPanel.add(suelo);

				mapPanel.repaint();

				
				aviso.VentanaDeAvisos(1,"Victoria de jugador "+tur+".", 1);
				MainMenu menu = new MainMenu(3,10,10000,10000,0,"b",1);
				menu.setLocationRelativeTo(null);
				menu.setResizable(false);
				menu.setVisible(true);

				}
		}

		} //fin gestion de movimiento
		
	}
	
	/********************************************************************************************************************
	IKER
	En esta funci�n comprobamos la vitalidad por cada movimiento y la va restando.
	Al llegar a 0 dejaras de poder moverte y saldr� un mensaje para avisarlo.
	Tambi�n es la funci�n que muestra las caracter�sticas principales de cada tropa. Este apartado solo funciona al clicar en la tropa

	*********************************************************************************************************************/
	
	
	public void vitalidadrestante(int tur,Tropa t,JLabel cursor,JPanel cp,JPanel troopPanel) {

		if( t !=null && vitalidad > -1) {
			switch (tur) {
				case 1:

				vital.setText("MOVIMINETOS RESTANTES: "+Integer.toString(vitalidad));
				break;
				case 2:

				vital.setText("MOVIMINETOS RESTANTES: "+Integer.toString(vitalidad));
				break;
			}
			

		}else if(t !=null) {


		}else if(vitalidad == 0){
		JOptionPane.showMessageDialog(cp,"Sin movimientos restantes");

		}

		troopPanel.repaint();
		
		

		//Informacion de las tropas que se muestra en pantalla cuando clicas sobre ellas

		Point casilla = new Point((int) cursor.getX()/32, (int) cursor.getY()/32);
		int cant = mapGrid.get(casilla).size();
		if(cant > 1) {
		for (ArrayList<Object> a : mapGrid.get(casilla)) {
			if(a.get(1) instanceof Tropa) {
				trclick = (Tropa) a.get(1);
				name.setText("Nombre: "+trclick.getNombre());
				Tipo.setText("Tipo de tropa: "+trclick.getTipoTropa().toString());
				Nivel.setText("Nivel: "+Integer.toString(trclick.getNivel()));
				int f1 = trclick.getGolpePrim();
				attack1.setText("Da�o ataque principal: "+Integer.toString(f1));
				int f2 = trclick.getGolpeSec();
				attack2.setText("Da�o ataque secundario:"+Integer.toString(f2));
				derecha.repaint();

		}
		}

		}
		
		
	}
	
	public int buildCount(int team, JPanel mapPanel, HashMap<Point, ArrayList<ArrayList<Object>>> mapGridFunc3) {
			mapPanel.repaint();
			ArrayList<ListaIDTerreno> buildings = new ArrayList<>(); buildings.add(ListaIDTerreno.CITY);buildings.add(ListaIDTerreno.FACTORY);buildings.add(ListaIDTerreno.HQ);
			int building1 = 0;
			int building2 = 0;
			int building3 = 0;
			int building4 = 0;
			for (Point p : mapGridFunc3.keySet()) {
				try {
					Estructura struc = (Estructura) mapGridFunc3.get(p).get(0).get(1);
					if (buildings.contains(struc.getIDTerreno())) {
						switch (struc.getTeam()) {
						case 1: 
							building1+=1;
							break;
						case 2:
							building2+=1;
							break;
						case 3:
							building3+=1;
							break;
						default:
							building4+=1;
							break;
						}
					}
				} catch (Exception e) {
					
				}
			}
			//System.out.println(building1 + "-" + building2);	
			switch (team) {
			case 1:
				return building1;
			case 2:
				return building2;
			case 3:
				return building3;
			default:
				return building4;
			}
		
	}
	
	/********************************************************************************************************************
	IKER
	Al terminar cada turno la cantidad de movimientos es reseteada.
	Tambi�n actualizamos el panel de la informaci�n sobre que jugador est� jugando

	*********************************************************************************************************************/
	
	
	public void terminaTurno(int team, JPanel mapPanel, HashMap<Point, ArrayList<ArrayList<Object>>> mapGridFunc3) {
		vitalidad = movilid;
		int teamSetter;
		switch (turn % 2) {
		case 0:
			teamSetter = 2;
			break;
		default:
			teamSetter = 1;
			break;
		}
		switch (teamSetter) {
		case 1:
			vital.setText("AZUL: Tienes "+Integer.toString(vitalidad)+" movimientos mas");
			this.turn++;
			sic.changeSound("combat2.wav");
//			mus.interrupt();
//			mus.start();
			break;
		default:
			vital.setText("ROJO:  Tienes "+Integer.toString(vitalidad)+" movimientos mas");
			this.turn++;
			sic.changeSound("combat1.wav");
//			mus.interrupt();
//			mus.start();
			break;
		}
		derecha.repaint();
		info.repaint();
	}
	
	public void createTropa(Point pos, HashMap<Point, ArrayList<ArrayList<Object>>> mapGrid, JLayeredPane lp, JPanel troopPanel, int turn, Point casilla) {
		JInternalFrame jif = new JInternalFrame();
		
		jif.pack();
		jif.setLocation(192, 160);
		jif.setResizable(false);
		jif.setVisible(true);
		Container jifCP = jif.getContentPane();
		JLabel jm = new JLabel();
		JButton infFoot = new JButton("1500");
		infFoot.setVerticalTextPosition(JButton.BOTTOM);
		switch (turn%2) {
		case 0:
			infFoot.setIcon(new ImageIcon(getClass().getResource("img/troop/blue/InftBLUE.png")));
			break;
		default:
			infFoot.setIcon(new ImageIcon(getClass().getResource("img/troop/red/InftRED.png")));
			break;
		}
		
		
		
		switch (turn % 2) {
		case 0:
			if(p2.getfunds()>=1500) {
				infFoot.setEnabled(true);
			}else {
				infFoot.setEnabled(false);
			}
			break;
		default:
			if(p1.getfunds()>=1500) {
				infFoot.setEnabled(true);
			}else {
				infFoot.setEnabled(false);
			}
			break;
			
		}
		
		
		infFoot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InfFoot tr = new InfFoot(0);
				switch (turn%2){
					case 0:
						tr.setTeam(1);
						jm.setIcon(new ImageIcon(getClass().getResource("img/troop/blue/InftBLUE.png")));
						break;
					default:
						tr.setTeam(2);
						jm.setIcon(new ImageIcon(getClass().getResource("img/troop/red/InftRED.png")));
						break;
				}		
				ArrayList<Object> troopsList = new ArrayList<>();
				
				
				jm.setBounds((int) pos.getX(), (int) pos.getY(), 32, 32);
				troopsList.add(jm);
				troopsList.add(tr);
				mapGrid.get(casilla).add(troopsList);
				troopPanel.add(jm);
				jif.dispose();
				
				switch (turn % 2) {
				case 0:
					int dinero = p2.getfunds();
					if(p2.getfunds()>=1500) {
						dinero = dinero-1500;
						p2.setfunds(dinero);
						gold.setText("Fondos que quedan: "+Integer.toString(dinero));
						System.out.println(dinero);
						derecha.repaint();
						info.repaint();
					}else {
						System.out.println(dinero);
						
					}
					
					
					break;
				default:
					dinero = p1.getfunds();
					if(p1.getfunds()>=1500) {
					    dinero = dinero-1500;
					    p1.setfunds(dinero);
						gold.setText("Fondos que quedan: "+Integer.toString(dinero));
						System.out.println(dinero);
						derecha.repaint();
						info.repaint();
					}else {
						System.out.println(dinero);
						
					}
					break;
					
				}
				
				
				
				
				
			}
		});
		JButton infMech = new JButton("2500");
		infMech.setVerticalTextPosition(JButton.BOTTOM);
		switch (turn%2) {
		case 0:
			infMech.setIcon(new ImageIcon(getClass().getResource("img/troop/blue/MecBLUE.png")));
			break;
		default:
			infMech.setIcon(new ImageIcon(getClass().getResource("img/troop/red/MecRED.png")));
			break;
		}
		
		switch (turn % 2) {
		case 0:
			if(p2.getfunds()>=2500) {
				infMech.setEnabled(true);
			}else {
				infMech.setEnabled(false);
			}
			break;
		default:
			if(p1.getfunds()>=2500) {
				infMech.setEnabled(true);
			}else {
				infMech.setEnabled(false);
			}
			break;
			
		}
		
		infMech.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InfMech tr = new InfMech(0);
				switch (turn%2){
					case 0:
						tr.setTeam(1);
						jm.setIcon(new ImageIcon(getClass().getResource("img/troop/blue/MecBLUE.png")));
						break;
					default:
						tr.setTeam(2);
						jm.setIcon(new ImageIcon(getClass().getResource("img/troop/red/MecRED.png")));
						break;
				}		
				ArrayList<Object> troopsList = new ArrayList<>();
				
				jm.setBounds((int) pos.getX(), (int) pos.getY(), 32, 32);
				troopsList.add(jm);
				troopsList.add(tr);
				mapGrid.get(casilla).add(troopsList);
				troopPanel.add(jm);
				jif.dispose();
				
				switch (turn % 2) {
				case 0:
					int dinero = p2.getfunds();
					if(p2.getfunds()>=2500) {
						dinero = dinero-2500;
						p2.setfunds(dinero);
						gold.setText("Fondos que quedan: "+Integer.toString(dinero));
						System.out.println(dinero);
					}else {
						System.out.println(dinero);
						
					}
					
					
					break;
				default:
					dinero = p1.getfunds();
					if(p1.getfunds()>=2500) {
					    dinero = dinero-2500;
					    p1.setfunds(dinero);
						gold.setText("Fondos que quedan: "+Integer.toString(dinero));
						System.out.println(dinero);
						
					}else {
						System.out.println(dinero);
						
					}
					break;
					
				}
				
				
			}
		});
		
		JButton infBike = new JButton("2500");
		infBike.setVerticalTextPosition(JButton.BOTTOM);
		switch (turn%2) {
		case 0:
			infBike.setIcon(new ImageIcon(getClass().getResource("img/troop/blue/BikeBLUE.png")));
			break;
		default:
			infBike.setIcon(new ImageIcon(getClass().getResource("img/troop/red/BikeRED.png")));
			break;
		}
		switch (turn % 2) {
		case 0:
			if(p2.getfunds()>=2500) {
				infBike.setEnabled(true);
			}else {
				infBike.setEnabled(false);
			}
			break;
		default:
			if(p1.getfunds()>=2500) {
				infBike.setEnabled(true);
			}else {
				infBike.setEnabled(false);
			}
			break;
			
		}
		
		infBike.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InfBike tr = new InfBike(0);
				switch (turn%2){
					case 0:
						tr.setTeam(1);
						jm.setIcon(new ImageIcon(getClass().getResource("img/troop/blue/BikeBLUE.png")));
						break;
					default:
						tr.setTeam(2);
						jm.setIcon(new ImageIcon(getClass().getResource("img/troop/red/BikeRED.png")));
						break;
				}		
				ArrayList<Object> troopsList = new ArrayList<>();
				
				jm.setBounds((int) pos.getX(), (int) pos.getY(), 32, 32);
				troopsList.add(jm);
				troopsList.add(tr);
				mapGrid.get(casilla).add(troopsList);
				troopPanel.add(jm);
				jif.dispose();
				
				switch (turn % 2) {
				case 0:
					int dinero = p2.getfunds();
					if(p2.getfunds()>=2500) {
						dinero = dinero-2500;
						p2.setfunds(dinero);
						gold.setText("Fondos que quedan: "+Integer.toString(dinero));
						System.out.println(dinero);
					}else {
						System.out.println(dinero);
						
					}
					
					
					break;
				default:
					dinero = p1.getfunds();
					if(p1.getfunds()>=2500) {
					    dinero = dinero-2500;
					    p1.setfunds(dinero);
						gold.setText("Fondos que quedan: "+Integer.toString(dinero));
						System.out.println(dinero);
						
					}else {
						System.out.println(dinero);
						
					}
					break;
					
				}
			}
		});
		
		JButton rocl = new JButton("15000");
		rocl.setVerticalTextPosition(JButton.BOTTOM);
		switch (turn%2) {
		case 0:
			rocl.setIcon(new ImageIcon(getClass().getResource("img/troop/blue/RocketBLUE.png")));
			break;
		default:
			rocl.setIcon(new ImageIcon(getClass().getResource("img/troop/red/RocketRED.png")));
			break;
		}
		
		switch (turn % 2) {
		case 0:
			if(p2.getfunds()>=15000) {
				rocl.setEnabled(true);
			}else {
				rocl.setEnabled(false);
			}
			break;
		default:
			if(p1.getfunds()>=15000) {
				rocl.setEnabled(true);
			}else {
				rocl.setEnabled(false);
			}
			break;
			
		}
		
		rocl.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Rocl tr = new Rocl(0);
				switch (turn%2){
					case 0:
						tr.setTeam(1);
						jm.setIcon(new ImageIcon(getClass().getResource("img/troop/blue/RocketBLUE.png")));
						break;
					default:
						tr.setTeam(2);
						jm.setIcon(new ImageIcon(getClass().getResource("img/troop/red/RocketRED.png")));
						break;
				}		
				ArrayList<Object> troopsList = new ArrayList<>();
				
				jm.setBounds((int) pos.getX(), (int) pos.getY(), 32, 32);
				troopsList.add(jm);
				troopsList.add(tr);
				mapGrid.get(casilla).add(troopsList);
				troopPanel.add(jm);
				jif.dispose();
				
				switch (turn % 2) {
				case 0:
					int dinero = p2.getfunds();
					if(p2.getfunds()>=15000) {
						dinero = dinero-15000;
						p2.setfunds(dinero);
						gold.setText("Fondos que quedan: "+Integer.toString(dinero));
						System.out.println(dinero);
					}else {
						System.out.println(dinero);
						
					}
					
					
					break;
				default:
					dinero = p1.getfunds();
					if(p1.getfunds()>=15000) {
					    dinero = dinero-15000;
					    p1.setfunds(dinero);
						gold.setText("Fondos que quedan: "+Integer.toString(dinero));
						System.out.println(dinero);
						
					}else {
						System.out.println(dinero);
						
					}
					break;
					
				}
			}
		});
		
		JButton antiA = new JButton("8000");
		antiA.setVerticalTextPosition(JButton.BOTTOM);
		switch (turn%2) {
		case 0:
			antiA.setIcon(new ImageIcon(getClass().getResource("img/troop/blue/AaBLUE.png")));
			break;
		default:
			antiA.setIcon(new ImageIcon(getClass().getResource("img/troop/red/AaRED.png")));
			break;
		}
		
		switch (turn % 2) {
		case 0:
			if(p2.getfunds()>=8000) {
				antiA.setEnabled(true);
			}else {
				antiA.setEnabled(false);
			}
			break;
		default:
			if(p1.getfunds()>=8000) {
				antiA.setEnabled(true);
			}else {
				antiA.setEnabled(false);
			}
			break;
			
		}
		
		antiA.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AntiA tr = new AntiA(0);
				switch (turn%2){
					case 0:
						tr.setTeam(1);
						jm.setIcon(new ImageIcon(getClass().getResource("img/troop/blue/AaBLUE.png")));
						break;
					default:
						tr.setTeam(2);
						jm.setIcon(new ImageIcon(getClass().getResource("img/troop/red/AaRED.png")));
						break;
				}		
				ArrayList<Object> troopsList = new ArrayList<>();
				
				jm.setBounds((int) pos.getX(), (int) pos.getY(), 32, 32);
				troopsList.add(jm);
				troopsList.add(tr);
				mapGrid.get(casilla).add(troopsList);
				troopPanel.add(jm);
				jif.dispose();
				
				switch (turn % 2) {
				case 0:
					int dinero = p2.getfunds();
					if(p2.getfunds()>=8000) {
						dinero = dinero-8000;
						p2.setfunds(dinero);
						gold.setText("Fondos que quedan: "+Integer.toString(dinero));
						System.out.println(dinero);
					}else {
						System.out.println(dinero);
						
					}
					
					
					break;
				default:
					dinero = p1.getfunds();
					if(p1.getfunds()>=8000) {
					    dinero = dinero-8000;
					    p1.setfunds(dinero);
						gold.setText("Fondos que quedan: "+Integer.toString(dinero));
						System.out.println(dinero);
						
					}else {
						System.out.println(dinero);
						
					}
					break;
					
				}
			}
		});
		
		JButton vAPC = new JButton("5000");
		vAPC.setVerticalTextPosition(JButton.BOTTOM);
		switch (turn%2) {
		case 0:
			vAPC.setIcon(new ImageIcon(getClass().getResource("img/troop/blue/ToaBLUE.png")));
			break;
		default:
			vAPC.setIcon(new ImageIcon(getClass().getResource("img/troop/red/ToaRED.png")));
			break;
		}
		switch (turn % 2) {
		case 0:
			if(p2.getfunds()>=5000) {
				vAPC.setEnabled(true);
			}else {
				vAPC.setEnabled(false);
			}
			break;
		default:
			if(p1.getfunds()>=5000) {
				vAPC.setEnabled(true);
			}else {
				vAPC.setEnabled(false);
			}
			break;
			
		}
		
		vAPC.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VApc tr = new VApc(0);
				switch (turn%2){
					case 0:
						tr.setTeam(1);
						jm.setIcon(new ImageIcon(getClass().getResource("img/troop/blue/ToaBLUE.png")));
						break;
					default:
						tr.setTeam(2);
						jm.setIcon(new ImageIcon(getClass().getResource("img/troop/red/ToaRED.png")));
						break;
				}		
				ArrayList<Object> troopsList = new ArrayList<>();
				
				jm.setBounds((int) pos.getX(), (int) pos.getY(), 32, 32);
				troopsList.add(jm);
				troopsList.add(tr);
				mapGrid.get(casilla).add(troopsList);
				troopPanel.add(jm);
				jif.dispose();
				
				switch (turn % 2) {
				case 0:
					int dinero = p2.getfunds();
					if(p2.getfunds()>=5000) {
						dinero = dinero-5000;
						p2.setfunds(dinero);
						gold.setText("Fondos que quedan: "+Integer.toString(dinero));
						System.out.println(dinero);
					}else {
						System.out.println(dinero);
						
					}
					
					
					break;
				default:
					dinero = p1.getfunds();
					if(p1.getfunds()>=5000) {
					    dinero = dinero-5000;
					    p1.setfunds(dinero);
						gold.setText("Fondos que quedan: "+Integer.toString(dinero));
						System.out.println(dinero);
						
					}else {
						System.out.println(dinero);
						
					}
					break;
					
				}
			}
		});
		
		JButton arty = new JButton("6000");
		arty.setVerticalTextPosition(JButton.BOTTOM);
		switch (turn%2) {
		case 0:
			arty.setIcon(new ImageIcon(getClass().getResource("img/troop/blue/ArtilleryBLUE.png")));
			break;
		default:
			arty.setIcon(new ImageIcon(getClass().getResource("img/troop/red/ArtilleryRED.png")));
			break;
		}
		switch (turn % 2) {
		case 0:
			if(p2.getfunds()>=6000) {
				arty.setEnabled(true);
			}else {
				arty.setEnabled(false);
			}
			break;
		default:
			if(p1.getfunds()>=6000) {
				arty.setEnabled(true);
			}else {
				arty.setEnabled(false);
			}
			break;
			
		}
		
		arty.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Arty tr = new Arty(0);
				switch (turn%2){
					case 0:
						tr.setTeam(1);
						jm.setIcon(new ImageIcon(getClass().getResource("img/troop/blue/ArtilleryBLUE.png")));
						break;
					default:
						tr.setTeam(2);
						jm.setIcon(new ImageIcon(getClass().getResource("img/troop/red/ArtilleryRED.png")));
						break;
				}		
				ArrayList<Object> troopsList = new ArrayList<>();
				
				jm.setBounds((int) pos.getX(), (int) pos.getY(), 32, 32);
				troopsList.add(jm);
				troopsList.add(tr);
				mapGrid.get(casilla).add(troopsList);
				troopPanel.add(jm);
				jif.dispose();
				
				switch (turn % 2) {
				case 0:
					int dinero = p2.getfunds();
					if(p2.getfunds()>=6000) {
						dinero = dinero-6000;
						p2.setfunds(dinero);
						gold.setText("Fondos que quedan: "+Integer.toString(dinero));
						System.out.println(dinero);
					}else {
						System.out.println(dinero);
						
					}
					
					
					break;
				default:
					dinero = p1.getfunds();
					if(p1.getfunds()>=6000) {
					    dinero = dinero-6000;
					    p1.setfunds(dinero);
						gold.setText("Fondos que quedan: "+Integer.toString(dinero));
						System.out.println(dinero);
						
					}else {
						System.out.println(dinero);
						
					}
					break;
					
				}
			}
		});
		
		JButton tankL = new JButton("6000");
		tankL.setVerticalTextPosition(JButton.BOTTOM);
		switch (turn%2) {
		case 0:
			tankL.setIcon(new ImageIcon(getClass().getResource("img/troop/blue/LTankBLUE.png")));
			break;
		default:
			tankL.setIcon(new ImageIcon(getClass().getResource("img/troop/red/LTankRED.png")));
			break;
		}
		switch (turn % 2) {
		case 0:
			if(p2.getfunds()>=6000) {
				tankL.setEnabled(true);
			}else {
				tankL.setEnabled(false);
			}
			break;
		default:
			if(p1.getfunds()>=6000) {
				tankL.setEnabled(true);
			}else {
				tankL.setEnabled(false);
			}
			break;
			
		}
		
		tankL.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TankL tr = new TankL(0);
				switch (turn%2){
					case 0:
						tr.setTeam(1);
						jm.setIcon(new ImageIcon(getClass().getResource("img/troop/blue/LTankBLUE.png")));
						break;
					default:
						tr.setTeam(2);
						jm.setIcon(new ImageIcon(getClass().getResource("img/troop/red/LTankRED.png")));
						break;
				}		
				ArrayList<Object> troopsList = new ArrayList<>();
				
				jm.setBounds((int) pos.getX(), (int) pos.getY(), 32, 32);
				troopsList.add(jm);
				troopsList.add(tr);
				mapGrid.get(casilla).add(troopsList);
				troopPanel.add(jm);
				jif.dispose();
				
				switch (turn % 2) {
				case 0:
					int dinero = p2.getfunds();
					if(p2.getfunds()>=6000) {
						dinero = dinero-6000;
						p2.setfunds(dinero);
						gold.setText("Fondos que quedan: "+Integer.toString(dinero));
						System.out.println(dinero);
					}else {
						System.out.println(dinero);
						
					}
					
					
					break;
				default:
					dinero = p1.getfunds();
					if(p1.getfunds()>=6000) {
					    dinero = dinero-6000;
					    p1.setfunds(dinero);
						gold.setText("Fondos que quedan: "+Integer.toString(dinero));
						System.out.println(dinero);
						
					}else {
						System.out.println(dinero);
						
					}
					break;
					
				}
			}
		});
		
		JButton tankM = new JButton("12000");
		tankM.setVerticalTextPosition(JButton.BOTTOM);
		switch (turn%2) {
		case 0:
			tankM.setIcon(new ImageIcon(getClass().getResource("img/troop/blue/MTankBLUE.png")));
			break;
		default:
			tankM.setIcon(new ImageIcon(getClass().getResource("img/troop/red/MTankRED.png")));
			break;
		}
		switch (turn % 2) {
		case 0:
			if(p2.getfunds()>=12000) {
				tankM.setEnabled(true);
			}else {
				tankM.setEnabled(false);
			}
			break;
		default:
			if(p1.getfunds()>=12000) {
				tankM.setEnabled(true);
			}else {
				tankM.setEnabled(false);
			}
			break;
			
		}
		
		tankM.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TankM tr = new TankM(2);
				switch (turn%2){
					case 0:
						tr.setTeam(1);
						jm.setIcon(new ImageIcon(getClass().getResource("img/troop/blue/MTankBLUE.png")));
						break;
					default:
						tr.setTeam(2);
						jm.setIcon(new ImageIcon(getClass().getResource("img/troop/red/MTankRED.png")));
						break;
				}		
				ArrayList<Object> troopsList = new ArrayList<>();
				
				jm.setBounds((int) pos.getX(), (int) pos.getY(), 32, 32);
				troopsList.add(jm);
				troopsList.add(tr);
				mapGrid.get(casilla).add(troopsList);
				troopPanel.add(jm);
				jif.dispose();
				
				switch (turn % 2) {
				case 0:
					int dinero = p2.getfunds();
					if(p2.getfunds()>=12000) {
						dinero = dinero-12000;
						p2.setfunds(dinero);
						gold.setText("Fondos que quedan: "+Integer.toString(dinero));
						System.out.println(dinero);
					}else {
						System.out.println(dinero);
						
					}
					
					
					break;
				default:
					dinero = p1.getfunds();
					if(p1.getfunds()>=12000) {
					    dinero = dinero-12000;
					    p1.setfunds(dinero);
						gold.setText("Fondos que quedan: "+Integer.toString(dinero));
						System.out.println(dinero);
						
					}else {
						System.out.println(dinero);
						
					}
					break;
					
				}
			}
		});
		
		JButton tankH = new JButton("16000");
		tankH.setVerticalTextPosition(JButton.BOTTOM);
		switch (turn%2) {
		case 0:
			tankH.setIcon(new ImageIcon(getClass().getResource("img/troop/blue/HTankBLUE.png")));
			break;
		default:
			tankH.setIcon(new ImageIcon(getClass().getResource("img/troop/red/HTankRED.png")));
			break;
		}
		switch (turn % 2) {
		case 0:
			if(p2.getfunds()>=16000) {
				tankH.setEnabled(true);
			}else {
				tankH.setEnabled(false);
			}
			break;
		default:
			if(p1.getfunds()>=16000) {
				tankH.setEnabled(true);
			}else {
				tankH.setEnabled(false);
			}
			break;
			
		}
		
		tankH.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TankH tr = new TankH(1);
				switch (turn%2){
					case 0:
						tr.setTeam(1);
						jm.setIcon(new ImageIcon(getClass().getResource("img/troop/blue/HTankBLUE.png")));
						break;
					default:
						tr.setTeam(2);
						jm.setIcon(new ImageIcon(getClass().getResource("img/troop/red/HTankRED.png")));
						break;
				}		
				ArrayList<Object> troopsList = new ArrayList<>();
				
				jm.setBounds((int) pos.getX(), (int) pos.getY(), 32, 32);
				troopsList.add(jm);
				troopsList.add(tr);
				mapGrid.get(casilla).add(troopsList);
				troopPanel.add(jm);
				jif.dispose();
				
				switch (turn % 2) {
				case 0:
					int dinero = p2.getfunds();
					if(p2.getfunds()>=16000) {
						dinero = dinero-16000;
						p2.setfunds(dinero);
						gold.setText("Fondos que quedan: "+Integer.toString(dinero));
						System.out.println(dinero);
					}else {
						System.out.println(dinero);
						
					}
					
					
					break;
				default:
					dinero = p1.getfunds();
					if(p1.getfunds()>=16000) {
					    dinero = dinero-16000;
					    p1.setfunds(dinero);
						gold.setText("Fondos que quedan: "+Integer.toString(dinero));
						System.out.println(dinero);
						
					}else {
						System.out.println(dinero);
						
					}
					break;
					
				}
			}
		});
		
		
		
		JButton vRecon = new JButton("4000");
		vRecon.setVerticalTextPosition(JButton.BOTTOM);
		switch (turn%2) {
		case 0:
			vRecon.setIcon(new ImageIcon(getClass().getResource("img/troop/blue/ReconBLUE.png")));
			break;
		default:
			vRecon.setIcon(new ImageIcon(getClass().getResource("img/troop/red/ReconRED.png")));
			break;
		}
		switch (turn % 2) {
		case 0:
			if(p2.getfunds()>=4000) {
				vRecon.setEnabled(true);
			}else {
				vRecon.setEnabled(false);
			}
			break;
		default:
			if(p1.getfunds()>=4000) {
				vRecon.setEnabled(true);
			}else {
				vRecon.setEnabled(false);
			}
			break;
			
		}
		
		vRecon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VRecon tr = new VRecon(1);
				switch (turn%2){
					case 0:
						tr.setTeam(1);
						jm.setIcon(new ImageIcon(getClass().getResource("img/troop/blue/ReconBLUE.png")));
						break;
					default:
						tr.setTeam(2);
						jm.setIcon(new ImageIcon(getClass().getResource("img/troop/red/ReconRED.png")));
						break;
				}		
				ArrayList<Object> troopsList = new ArrayList<>();
				jm.setBounds((int) pos.getX(), (int) pos.getY(), 32, 32);
				troopsList.add(jm);
				troopsList.add(tr);
				mapGrid.get(casilla).add(troopsList);
				troopPanel.add(jm);
				jif.dispose();
				
				switch (turn % 2) {
				case 0:
					int dinero = p2.getfunds();
					if(p2.getfunds()>=4000) {
						dinero = dinero-4000;
						p2.setfunds(dinero);
						gold.setText("Fondos que quedan: "+Integer.toString(dinero));
						System.out.println(dinero);
					}else {
						System.out.println(dinero);
						
					}
					
					
					break;
				default:
					dinero = p1.getfunds();
					if(p1.getfunds()>=4000) {
					    dinero = dinero-4000;
					    p1.setfunds(dinero);
						gold.setText("Fondos que quedan: "+Integer.toString(dinero));
						System.out.println(dinero);
						
					}else {
						System.out.println(dinero);
						
					}
					break;
					
				}
			}
		});
		
		JButton Cerrar = new JButton("Cerrar");
		
		Cerrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jif.dispose();
				
			}
		});
				
		jifCP.setLayout(new GridLayout(4, 3));
		
		jifCP.add(infFoot);
		jifCP.add(infMech);
		jifCP.add(infBike);
		jifCP.add(vRecon);
		jifCP.add(antiA);
		jifCP.add(arty);
		jifCP.add(rocl);
		jifCP.add(tankL);
		jifCP.add(tankM);
		jifCP.add(tankH);
		jifCP.add(vAPC);
		jifCP.add(Cerrar);
		
		jif.pack();		//Se asegura de que todos los componentes estÃ¡n por lo menos a su tamaÃ±o preferido
		jif.setSize(new Dimension(336, 336));		//Se cambia el tamaÃ±o de la ventana
		lp.add(jif, 4, 0);
	}
	
	public synchronized HashMap<Point, ArrayList<ArrayList<Object>>> reloadHMap(){
		return this.mapGrid;
	}
	
	public void moverTropa(Point pos, HashMap<Point, ArrayList<ArrayList<Object>>> mapGrid, JLayeredPane lp, JPanel troopPanel, int turn, Point casilla, Point og) {
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		Tropa t = (Tropa) mapGrid.get(og).get(1).get(1);
		JLabel tl = (JLabel) mapGrid.get(og).get(1).get(0);
		ArrayList<Object> al = new ArrayList<>();
		al.add(t); al.add(tl);
		//mapGrid.get(og).remove(1);
		mapGrid.get(casilla).add(al);
		tl.setBounds((int) mouse.getX()/32, (int) mouse.getY()/32, 32, 32);
		troopPanel.repaint();
		estadoMov = false;
	}
	
	public HashMap<Point, ArrayList<ArrayList<Object>>> loadMap() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("BeanIsland.dat"));

			mapGrid = (HashMap) ois.readObject();

			ois.close();

		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return mapGrid;
	}
}

/*	
	public static int grafoVert(int trop) {
		int n = 1;
		int vert = 1;
		for (int i = 0; i < (trop - 1); i++) {
			n += 2;
			vert += n;		
			//System.out.println("n: " + n + " vert: " + vert);
		}
		vert *= 2;
		vert += n + 2;
		return vert;
	}
	public static int grafoArist(int trop) {
		int n = 1;
		int arist = 1;
		for (int i = 0; i < ((trop * 2) - 2); i++) {
			n += 1;
			arist += n;		
			//System.out.println("n: " + n + " arist: " + arist);
		}
		arist *= 2;
		arist += n + 1;
		return arist;
	}
	public static Dimension grafoVertPos() {
		
		
		
		return new Dimension(1,2);
	}
	
*/	
	
	


//TODO list
/*
Â·Panel de informaciÃ³n para quien quiera hacerlo
 
Â·Methods (...)
Â·Graph:
	-node positions relative to map
	-positions in map relative to troop



Problems:
·detecting things that are on top of eachother
·movement -> graph detecting what the troop can be on top of and calculate where it cant reach anymore
·








 
 */