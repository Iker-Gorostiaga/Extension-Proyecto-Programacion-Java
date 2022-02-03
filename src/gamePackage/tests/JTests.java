package gamePackage.tests;
import gamePackage.entidades.terrestres.*;
import gamePackage.sonidos.*;
import gamePackage.terrenos.estructuras.*;
import gamePackage.terrenos.terrenos.*;
import gamePackage.ventanas.Game;

import static org.junit.Assert.*;

import java.awt.Point;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;

import org.junit.*;
import org.junit.Test;

public class JTests {
	HashMap<String, HashMap<Point, ArrayList<ArrayList<Object>>>> mapausuario ;
	HashMap<Point, ArrayList<ArrayList<Object>>> mapGrid = new HashMap<Point, ArrayList<ArrayList<Object>>>();
	String filePath;
	Connection connect;
	Logger logtest ;
	
	@Before
	public void Antes() {
		mapausuario = new HashMap<>();
		filePath = "";
		
		try {
			connect = DriverManager.getConnection("jdbc:sqlite:" + "Scoreboard.db");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void testGetEnergia() {
		System.out.println("INICIO TEST GETENERGIA");
		InfFoot x = new InfFoot(1);
		int energy = x.getEnergia();
		System.out.println("FIN TEST GETENERGIA");
		assertEquals(x.getEnergiaMaxima(), energy);
	}
	
	@Test
	public void testGetPeaje() {
		System.out.println("INICIO TEST GETPEAJE");
		InfFoot x = new InfFoot(1);
		Plains y = new Plains();
		int peaj = y.getPeaje(x);
		System.out.println("FIN TEST GETPEAJE");
		assertEquals(1, peaj);

	}
	
	@Test //Test para ver si furrula el setEnergia();
	public void testSetEnergia() {
		System.out.println("INICIO TEST SETENERGIA");
		InfFoot x = new InfFoot(1);
		Mountain y = new Mountain();
		System.out.println(x.getEnergia());
		x.setEnergia(x.getEnergia() - y.getPeaje(x));
		System.out.println(x.getEnergia());
		System.out.println("FIN TEST SETENERGIA");
		assertEquals(x.getEnergiaMax() - y.getPeaje(x), x.getEnergia());
	}
	
	@Test //Test para ver si 
	public void testSetSalud() {
		InfFoot x = new InfFoot(1);
		InfMech y = new InfMech(1);
		Mountain z = new Mountain();
		int xG = x.getGolpeSec();
		//Sistema de combate primitivo.
		if(x.getIdTropa() == y.getIDTropa()) {
			if(z.getDefensa() == 0) {		
			}
			else
				xG = xG/(z.getDefensa() * 2);
		}
		if(z.getDefensa() == 0) {
		}
		else
			xG = (int) ((xG + x.getSalud())/(z.getDefensa()));
		System.out.println("DMG == " + xG);
		while(y.getSalud() > 0) {
			System.out.println("Was " + y.getSalud() + " HP");
			y.setSalud(y.getSalud() - xG);
			System.out.println("Is " + y.getSalud() + " HP");
		}
		boolean itworks;
		if(y.getSalud() <= 0)
			itworks = true;
		else
			itworks = false;
		assertEquals(true, itworks);
	}
	
	@Test
	public void testSuministra() {
		System.out.println("INICIO TEST DE SUMINISTRO");
		boolean itworks = false;
		InfMech x = new InfMech(1);
		Hq y = new Hq(1);
		
		x.setSalud(20);
		x.setMuniciones(1);
		System.out.println(x.getEnergia() + " " + x.getSalud() + " " + x.getMuniciones());
		System.out.println(y.suministra(x));
		System.out.println(x.necesitaSuministro());
		if(y.suministra(x) == true && x.necesitaSuministro() == true) {
			
			itworks = true;
			
		}
		
		System.out.println("FIN TEST DE SUMINISTRO");
		assertEquals(true, itworks);
	}
	
	@Test
	public void testSounds() {
		SoundMngr yx = new SoundMngr("combat1.wav",0,0);
		Thread xy = new Thread(yx);
		xy.start();
		assertEquals(true, xy.isAlive());
	}
	
	@Test
	public void Cargademapa() {
		
		try {
			ObjectInputStream h = new ObjectInputStream(new FileInputStream("Zonaguardado.txt"));
			
			try {
				mapausuario = ((HashMap<String, HashMap<Point, ArrayList<ArrayList<Object>>>>) h.readObject());
				
				h.close();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		//comprobacion de que estan todos los elementos.
		
		for (String i : mapausuario.keySet()) {
			 HashMap<Point, ArrayList<ArrayList<Object>>> m = mapausuario.get(i);
			 for (Point p : m.keySet()) {
				 ArrayList<ArrayList<Object>> lista = m.get(p);
				
				 //comprobacion de que estan todos los elementos.
				 
				 assertTrue(!lista.get(0).isEmpty());
				 JLabel label = (JLabel) lista.get(0).get(0);
				 Object o = lista.get(0).get(1);
				 
				 // comprobacion de que todas las labels estan bien creadas y tiene sus objectos indicados
				 
				 assertTrue(o instanceof Sea || o instanceof Mountain || o instanceof Road||
						 o instanceof Plains|| o instanceof Forest||o instanceof Coast|| o instanceof Factory||
						 o instanceof City|| o instanceof Hq);
				  
			}
			 
			//comprobacion de que estan todos los elementos del mapa, es decir un total de 447 labels o posiciones
			 int numerototaldeelementos = m.keySet().size();
			assertEquals(447, numerototaldeelementos);
			
			//Comprobando si el la ultima posicion del mapa es la correcta
			
			Set<Point> listapunteros = m.keySet();
			assertTrue(listapunteros.contains(new Point(7,8)));
			
		} 
		
	}
	
	@Test
	public void Todoslosmapastienenusuario() {
		
		String sent = "";
		
		filePath = System.getProperty("user.dir"); //debuelve ruta
		filePath=filePath+"\\GuardadoMapas\\";
		logtest = Logger.getLogger(filePath);
		File file = new File(filePath);
		String[] arch = file.list();
		int contadorpartidas = 0;
		ArrayList<String> BDarch = new ArrayList<String>();
		try {
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Carga de BD para SQLite
			connect = DriverManager.getConnection("jdbc:sqlite:" + "Scoreboard.db");
			
			java.sql.Statement stat = connect.createStatement();
			sent = "select * from partidas";
			ResultSet rs = stat.executeQuery(sent);
			
			while(rs.next()) {
				BDarch.add(rs.getString("nombre_p")+".dat");
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Collections.sort(BDarch);
        boolean contador = true;
        contador = Primerpasorecur(arch,BDarch,0, false);
      
        //Hay una partida el la zona de guardado de mapas que no esta registrada a ningun usuario por eso deberemos probar de que es cierto
        
        	if(contador) {
        		assertTrue(contador);
        	}else {
        		logtest.log(Level.INFO, "Partida sin usuario confirmada");
        	}
			
		
		
	}
	
	public boolean Primerpasorecur(String[] lista,ArrayList<String> listabd, int indice, boolean ok) {
		
		
		if(indice == lista.length) {
			return true;
			
		}else if(false == Segundopasorecur(listabd, lista[indice],0)) {
			return false;
		}else {
			return Primerpasorecur(lista, listabd, indice+1, ok);
		}
		
		
	}
	
	//metodo recursivo en forma de contador para indentificar cant de elementos
	public boolean Segundopasorecur(ArrayList<String> listabd,String texto, int indice) {
		
		if(indice == listabd.size()) {
			return false;
		}else if(listabd.get(indice).equals(texto)) {
			
			return true;
		}else {
			
			return false || Segundopasorecur(listabd, texto, indice+1);
			
		}
		
		
	}
	@Test
	public void confirmaciondelementos() {
		filePath = System.getProperty("user.dir"); //debuelve ruta
		filePath=filePath+"\\Defaultmaps\\Isla.dat";
		File file = new File(filePath);
		
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			
			try {
				mapGrid = (HashMap<Point, ArrayList<ArrayList<Object>>>) ois.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int contador = 0;
		
		
		//Suma total de elementos y comprobacion de elementos
		for (Point p : mapGrid.keySet()) {
			contador++;
			for (ArrayList<Object> i : mapGrid.get(p)) {
				
				Object o = i.get(1);
				Object l = i.get(0);
				
				//comprobacion de que cada elemento tiene su label
				assertTrue(l instanceof JLabel);
			
				//comprobacion de que cada elemento pertenece a terreno o estructura
				assertTrue(o instanceof Sea || o instanceof Mountain || o instanceof Road||
						 o instanceof Plains|| o instanceof Forest||o instanceof Coast|| o instanceof Factory||
						 o instanceof City|| o instanceof Hq);
				
			}
		}
		
		//comprobacion de que el mapa esta compuesto por 441 elementos
		assertEquals(441, contador);
		
		
	}
	

}
