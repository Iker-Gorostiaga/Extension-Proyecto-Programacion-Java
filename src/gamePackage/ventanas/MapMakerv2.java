package gamePackage.ventanas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import javax.swing.border.TitledBorder;

import gamePackage.baseDatos.DataBase;
import gamePackage.terrenos.estructuras.*;
import gamePackage.terrenos.terrenos.*;

import javax.swing.border.EtchedBorder;


public class MapMakerv2 extends JFrame implements ActionListener, MouseMotionListener {
/**
		 * 
		 */
		private static final long serialVersionUID = 1L;


private JLayeredPane layeredPane;
HashMap<Point, ArrayList<ArrayList<Object>>> mapGrid = new HashMap<>();
String seleccionado = "";
JButton cargar = new JButton("Cargar");
JButton guardar = new JButton("Guardar");
JButton borrar = new JButton("Vaciar");
Object terre ;
JComboBox<String> equipo;
Logger logmaker = Logger.getGlobal();
VentanaAvisos aviso = new VentanaAvisos();
DataBase scores = new DataBase();
DefaultListModel<String> modelo;
JList listadefault;

/***********************************************************************************************************************************
IKER
Este en un Map Maker creado desde 0 por mi.
Lo interesante de este creador de mapas es que es ajustable(Gracias a un Jlabel especial) y tiene varios métodos nuevos.
En el sentido de ajustable es que da igual cuanto estires o encojas la ventana que las imágenes y elementos se ajustaran a ella.
También he implementado un método de guardado nuevo que se basa en los índices, es decir, no guardo por coordenadas como en el otro
si no que cojo la posición de la label selecciona y la divido entre 21 para conseguir sacar la cantidad de filas y elementos que hay
hasta su posición correcta.
**************************************************************************************************************************************/



public MapMakerv2(int difidinero,int movili,int juga1,int juga2,String nombre,int codigo)    {
	setBackground(new Color(95, 158, 160));
	setIconImage(Toolkit.getDefaultToolkit().getImage(MapMakerv2.class.getResource("/gamePackage/ventanas/img/troop/blue/ToaBLUE.png")));
setTitle("Creador de Mapas Adaptativo");
setDefaultCloseOperation(EXIT_ON_CLOSE);
//panel seleccion
JPanel seleccion = new JPanel();
seleccion.setBackground(new Color(95, 158, 160));
//seleccion.setPreferredSize(new Dimension(400, 200));		//Se define el tamaÃƒÂ±o preferido del panel
seleccion.setLayout(new BoxLayout(seleccion, BoxLayout.Y_AXIS));
seleccion.setLayout(new GridLayout(2,1));

JPanel Arriba = new JPanel();
Arriba.setBorder(new TitledBorder(null, "Botones de control", TitledBorder.CENTER, TitledBorder.TOP, null, Color.LIGHT_GRAY));
Arriba.setBackground(new Color(95, 158, 160));
seleccion.add(Arriba);
JPanel Abajo = new JPanel();
Abajo.setBackground(new Color(95, 158, 160));
seleccion.add(Abajo);

JPanel centro = new JPanel();
centro.setBackground(new Color(95, 158, 160));

// creacion de boton mar

boton_fondo sea = new boton_fondo("img/terrain/Sea.png");
boton_fondo mountain = new boton_fondo("img/terrain/mountain.png");
boton_fondo plain = new boton_fondo("img/terrain/Plains.png");
boton_fondo roadx = new boton_fondo("img/terrain/RoadX.png");
boton_fondo bosque = new boton_fondo("img/terrain/Forest.png");
boton_fondo botonEdificio = new boton_fondo("img/structure/CityRED.png");
boton_fondo botonFabrica = new boton_fondo("img/structure/FactoryRED.png");
boton_fondo botonBase = new boton_fondo("img/structure/HqRED.png");
equipo = new JComboBox<String>();
equipo.setBackground(new Color(192, 192, 192));

sea.setContentAreaFilled(false);
Arriba.setLayout(new GridLayout(2, 5, 15, 15));
Arriba.add(sea);
Arriba.add(mountain);
Arriba.add(plain);
Arriba.add(roadx);
Arriba.add(bosque);
Arriba.add(botonEdificio);
Arriba.add(botonFabrica);
Arriba.add(botonBase);
Arriba.add(equipo);

equipo.addItem("Equipo Azul");
equipo.addItem("Equipo Rojo");
equipo.setSelectedIndex(1);

/***********************************************************************************************************************************
IKER
Zona donde se sabe que elemento has seleccionado para pintar
**************************************************************************************************************************************/

sea.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		seleccionado = "img/terrain/Sea.png";
		terre = new Sea();
	}
});
mountain.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		seleccionado = "img/terrain/mountain.png";
		terre = new Mountain();
	}
});
plain.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		seleccionado = "img/terrain/Plains.png";
		terre = new Plains();
	}
});
roadx.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		seleccionado = "img/terrain/RoadX.png";
		terre = new Road();
	}
});
bosque.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		seleccionado = "img/terrain/Forest.png";
		terre = new Forest();
		
	}
});
botonEdificio.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String eq = (String) equipo.getSelectedItem();
		if(eq.equals("Equipo Rojo")) {
			seleccionado = "img/structure/CityRED.png";
			terre = new City(1);
		}else {
			seleccionado = "img/structure/CityBLUE.png";
			terre = new City(2);
			
		}
	}
});
botonFabrica.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String eq = (String) equipo.getSelectedItem();
		if(eq.equals("Equipo Rojo")) {
			seleccionado = "img/structure/FactoryRED.png";
			terre = new Factory(1);
		}else {
			seleccionado = "img/structure/FactoryBLUE.png";
			terre = new Factory(2);
			
		}
	}
});
botonBase.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String eq = (String) equipo.getSelectedItem();
		if(eq.equals("Equipo Rojo")) {
			seleccionado = "img/structure/HqRED.png";
			terre = new Hq(1);
		}else {
			seleccionado = "img/structure/HqBLUE.png";
			terre = new Hq(2);
			
		}
	}
});

/***********************************************************************************************************************************
IKER
Zona que pinta los botones a sus respectivos equipos
**************************************************************************************************************************************/

equipo.addItemListener(new ItemListener() {
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		String te = (String) e.getItem();
		if(te.equals("Equipo Rojo")) {
			botonBase.setIcon(new ImageIcon(getClass().getResource("img/structure/HqRED.png")));
			botonEdificio.setIcon(new ImageIcon(getClass().getResource("img/structure/CityRED.png")));
			botonFabrica.setIcon(new ImageIcon(getClass().getResource("img/structure/FactoryRED.png")));
		}else {
			botonBase.setIcon(new ImageIcon(getClass().getResource("img/structure/HqBLUE.png")));
			botonEdificio.setIcon(new ImageIcon(getClass().getResource("img/structure/CityBLUE.png")));
			botonFabrica.setIcon(new ImageIcon(getClass().getResource("img/structure/FactoryBLUE.png")));
		}
		
	}
});
guardar.setBackground(new Color(255, 255, 255));

/***********************************************************************************************************************************
IKER
He hecho que los mapas creados en este creador de mapas se guarden en otro lado para que no interfieran con los mapas antiguos y funcionales

**************************************************************************************************************************************/

guardar.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String nombrearch = JOptionPane.showInputDialog("Que nombre le ponemos al archivo");
		
		int contador = 0;
		
		for (Point p : mapGrid.keySet()) {
			if(mapGrid.get(p).get(0).get(1) instanceof Hq) {
				contador++;
				
			}
		}
		
		int cont = 0;
		String nombre = (String) listadefault.getSelectedValue();
		String filePath = System.getProperty("user.dir"); //debuelve ruta
		filePath=filePath+"\\Defaultmaps";
		File file = new File(filePath);
        String[] arch = file.list();
		for (String s : arch) {
			if(s.equals(nombrearch+".dat")) {
				cont++;
				
			}
		}
		
		if(cont != 0) {
			aviso.VentanaDeAvisos(3, "Ya hay un archivo con ese mismo nombre", 1);
			
		}else {
			if(contador < 2) {
				logmaker.log(Level.WARNING,"Error introduce los centros de control que faltan");
				aviso.VentanaDeAvisos(3, "Error introduce los centros de control que faltan", 1);
				
			}else if(contador>2){
				logmaker.log(Level.WARNING,"Demasiado centros de control");
				aviso.VentanaDeAvisos(3, "Demasiado centros de control", 1);
				
				
			}else {
				if(!nombrearch.equals("")) {

					filePath = System.getProperty("user.dir"); 
					filePath = filePath +"\\Defaultmaps\\"+nombrearch+".dat";
					
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
							logmaker.log(Level.INFO,"Archivo guardado con exito");
							aviso.VentanaDeAvisos(1, "Archivo guardado con exito", 1);
					
				}else {
					
					logmaker.log(Level.WARNING,"Nombre de Archivo nulo o vacio");
					aviso.VentanaDeAvisos(3, "Nombre de Archivo nulo o vacio", 1);
				     
			        
					
				}
				
				
				
			}
			refrescarlistadefaults();
			
		}
		
			
		
		
	}
});
cargar.setBackground(new Color(255, 255, 255));

//Create and set up the layered pane.
layeredPane = new JLayeredPane();
//layeredPane.setPreferredSize(new Dimension(672, 672));
//layeredPane.setBorder(new TitledBorder(null, "Creacion de mapa", TitledBorder.CENTER, TitledBorder.TOP, null, null));
layeredPane.addMouseMotionListener(this);

//Add several labels to the layered pane.

layeredPane.setLayout(new GridLayout(21,21));


for (int i = 0; i < 21; i++) {
	for (int j = 0; j < 21; j++) {
		ArrayList<ArrayList<Object>> fuera = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> dentro = new ArrayList<Object>();
		Label_fondo lb = new Label_fondo("","img/terrain/Plains.png");
		Plains pl = new Plains();
		dentro.add(lb);
		dentro.add(pl);
		fuera.add(dentro);
		mapGrid.put(new Point(j,i), fuera);
		layeredPane.add(lb);
	}
	
}


modelo = new DefaultListModel();

listadefault = new JList(modelo);
listadefault.setBackground(new Color(102, 205, 170));
refrescarlistadefaults();
JScrollPane scrol = new JScrollPane(listadefault);
centro.setLayout(new GridLayout(0, 1, 0, 0));
//

centro.add(layeredPane);
Abajo.setLayout(new GridLayout(2, 1, 10, 10));
borrar.setBackground(new Color(255, 255, 255));

/***********************************************************************************************************************************
IKER
Zona que elimina todo para que puedas empezar a pintar de 0 otra vez
**************************************************************************************************************************************/

borrar.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		layeredPane.removeAll();
		mapGrid = new HashMap<>();
		for (int i = 0; i < 21; i++) {
			for (int j = 0; j < 21; j++) {
				ArrayList<ArrayList<Object>> fuera = new ArrayList<ArrayList<Object>>();
				ArrayList<Object> dentro = new ArrayList<Object>();
				Label_fondo lb = new Label_fondo("","img/terrain/Plains.png");
				Plains pl = new Plains();
				dentro.add(lb);
				dentro.add(pl);
				fuera.add(dentro);
				mapGrid.put(new Point(j,i), fuera);
				layeredPane.add(lb);
			}
			
		}
		pack();
		
	}
});
JButton botonmainmenu = new JButton("Main Menu");
botonmainmenu.setBackground(new Color(255, 255, 255));
botonmainmenu.setVerticalAlignment(SwingConstants.BOTTOM);

botonmainmenu.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		MainMenu menu = new MainMenu(difidinero,movili,juga1,juga2,1,nombre,codigo);
		menu.setLocationRelativeTo(null);
		menu.setResizable(false);
		menu.setVisible(true);
		dispose();
	
	}
});

JPanel abajoarriba = new JPanel();
abajoarriba.setBackground(new Color(95, 158, 160));
abajoarriba.setLayout(new GridLayout(1, 2, 10, 10));
abajoarriba.add(scrol);
JPanel panelbotones = new JPanel();
panelbotones.setBackground(new Color(95, 158, 160));
abajoarriba.add(panelbotones);
panelbotones.setLayout(new GridLayout(3, 1, 10, 10));
panelbotones.add(guardar);
panelbotones.add(cargar);
panelbotones.add(borrar);
Abajo.add(abajoarriba);
JPanel abajoabajo = new JPanel();
abajoabajo.setBackground(new Color(95, 158, 160));
abajoabajo.setLayout(new GridLayout(1, 2, 0, 0));
JLabel lblNewLabel = new JLabel("");
abajoabajo.add(lblNewLabel);
JPanel panelmainmenu = new JPanel();
panelmainmenu.setBackground(new Color(95, 158, 160));
abajoabajo.add(panelmainmenu);
panelmainmenu.setLayout(new BorderLayout(0, 0));
panelmainmenu.add(botonmainmenu, BorderLayout.SOUTH);
Abajo.add(abajoabajo);
getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
getContentPane().add(centro);
getContentPane().add(seleccion);

cargar.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String nombre = (String) listadefault.getSelectedValue();
		String filePath = System.getProperty("user.dir"); //debuelve ruta
		filePath=filePath+"\\Defaultmaps";
		File file = new File(filePath);
        String[] arch = file.list();
		for (String s : arch) {
			if(s.equals(nombre)) {
				 
				try {
					ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath+"\\"+s));
					try {
						mapGrid =  (HashMap<Point, ArrayList<ArrayList<Object>>>) ois.readObject();
						layeredPane.removeAll();
						if(mapGrid.get(new Point(0,0)).get(0).get(0) instanceof JLabel) {
							JLabel lb = new JLabel();
							for (int i = 0; i < 21; i++) {
								for (int j = 0; j < 21; j++) {
									Label_fondo fondo = new Label_fondo("","");
									lb = (JLabel) mapGrid.get(new Point(j,i)).get(0).get(0);
									fondo.setIcon(lb.getIcon());
									layeredPane.add(fondo);
									
								}
							}
							
						}else {
							Label_fondo exis;
							
							for (int i = 0; i < 21; i++) {
								for (int j = 0; j < 21; j++) {
									exis = (Label_fondo) mapGrid.get(new Point(j,i)).get(0).get(0);
									
									layeredPane.add(exis);
								}
							}
							layeredPane.repaint();
							
						}
						
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
				
			}
		}
		
		

		pack();
	}
});

/***********************************************************************************************************************************
IKER
Zona que pinta mediante el click sobre el panel y detecta sobre que equipo estas para que no se mezclen los colores
**************************************************************************************************************************************/

layeredPane.addMouseListener(new MouseListener() {
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
	
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
		ArrayList<ArrayList<Object>> fuera = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> dentro = new ArrayList<Object>();
		
		try {
			Label_fondo c= (Label_fondo) layeredPane.findComponentAt(e.getX(), e.getY());
			
			if(terre instanceof City) {
				if(equipo.getSelectedIndex() == 1) {
					seleccionado = "img/structure/CityRED.png";
				}else {
					seleccionado = "img/structure/CityBLUE.png";
				}
			}else if(terre instanceof Factory) {
				if(equipo.getSelectedIndex() == 1) {
					seleccionado = "img/structure/FactoryRED.png";
				}else {
					seleccionado = "img/structure/FactoryBLUE.png";
				}
			}else if(terre instanceof Hq) {
				if(equipo.getSelectedIndex() == 1) {
					seleccionado = "img/structure/HqRED.png";
				}else {
					seleccionado = "img/structure/HqBLUE.png";
				}
			}
			
			
			c.setIcon(new ImageIcon(getClass().getResource(seleccionado)));
			dentro.add(c);
			dentro.add(terre);
			fuera.add(dentro);
			int numero = layeredPane.getPosition(c);
			int coci = numero / 21;
			int resto = numero % 21;
			mapGrid.remove(new Point(resto,coci));
			mapGrid.put(new Point(resto,coci),fuera);
			layeredPane.setPosition(c, layeredPane.getPosition(c));
		} catch (Exception e2) {
			logmaker.log(Level.INFO,"Fuera de limites");
		}
		
		
	}
	});




}

/***********************************************************************************************************************************
IKER
Zona que refresca la lista de los mapas guardaos
**************************************************************************************************************************************/


public void refrescarlistadefaults() {
	
	modelo.removeAllElements();
	
	String filePath = System.getProperty("user.dir"); //debuelve ruta
	filePath=filePath+"\\Defaultmaps";
	File file = new File(filePath);
    String[] arch = file.list();
    
    for (String s : arch) {
		modelo.addElement(s);
	}
	
}

public static void main(String[] args) {
//Schedule a job for the event-dispatching thread:
//creating and showing this application's GUI.
	MapMakerv2 frame = new MapMakerv2(3,10,10000,10000,"",1);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	//Display the window.
	frame.pack();
	frame.setVisible(true);
}

/***********************************************************************************************************************************
IKER
Zona que pinta mediante el dragg del raton sobre el panel y detecta sobre que equipo estas para que no se mezclen los colores
**************************************************************************************************************************************/
@Override
public void mouseDragged(MouseEvent e) {
	ArrayList<ArrayList<Object>> fuera = new ArrayList<ArrayList<Object>>();
	ArrayList<Object> dentro = new ArrayList<Object>();
	
	try {
		Label_fondo c= (Label_fondo) layeredPane.findComponentAt(e.getX(), e.getY());
		
		if(terre instanceof City) {
			if(equipo.getSelectedIndex() == 1) {
				seleccionado = "img/structure/CityRED.png";
			}else {
				seleccionado = "img/structure/CityBLUE.png";
			}
		}else if(terre instanceof Factory) {
			if(equipo.getSelectedIndex() == 1) {
				seleccionado = "img/structure/FactoryRED.png";
			}else {
				seleccionado = "img/structure/FactoryBLUE.png";
			}
		}else if(terre instanceof Hq) {
			if(equipo.getSelectedIndex() == 1) {
				seleccionado = "img/structure/HqRED.png";
			}else {
				seleccionado = "img/structure/HqBLUE.png";
			}
		}
		
		
		c.setIcon(new ImageIcon(getClass().getResource(seleccionado)));
		dentro.add(c);
		dentro.add(terre);
		fuera.add(dentro);
		int numero = layeredPane.getPosition(c);
		int coci = numero / 21;
		int resto = numero % 21;
		mapGrid.remove(new Point(resto,coci));
		mapGrid.put(new Point(resto,coci),fuera);
		layeredPane.setPosition(c, layeredPane.getPosition(c));
	} catch (Exception e2) {
		logmaker.log(Level.INFO,"Fuera de limites");
	}
	
	
	
	
	
	
}

@Override
public void mouseMoved(MouseEvent e) {
	// TODO Auto-generated method stub
	
}



@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
}


	
	
	

}

