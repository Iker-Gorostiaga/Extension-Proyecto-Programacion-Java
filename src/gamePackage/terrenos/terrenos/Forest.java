package gamePackage.terrenos.terrenos;

import java.awt.Point;
import java.io.Serializable;

import gamePackage.entidades.Tropa;
import gamePackage.logica.*;
import gamePackage.terrenos.Terreno;

public class Forest implements Terreno, Serializable{

	// Atributos Logicos
	ListaIDTerreno idTerreno = ListaIDTerreno.FOREST;
	boolean construibleSobre = false;
	int defensa = 3;
	// Atributos Fisicos
	String nombre = "Bosque";
	Point pos = new Point(0, 0);
	String imagen = ""; //Imagen predeterminada

	// Constructor
	public Forest() {
			super();
		}

	@Override
	public Point getPos() {
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public int getPeaje(Tropa trop) {
		int peaje = 0;
		switch (trop.getTipoTropa()) {
		case INFANTERIA:
			peaje = 1;
			break;
		case V_LIGERO:
			peaje = 3;
			break;
		case V_PESADO:
			peaje = 3;
			break;
		case ACORAZADO:
			peaje = 2;
			break;
		case AEREO:
			peaje = 1;
			break;
		case MECH:
			peaje = 1;
			break;
		default:
			peaje = 0;
			break;

		}
		// Switch en vez de if-else if-else
		return peaje;
	}

	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return nombre;
	}

	@Override
	public ListaIDTerreno getIDTerreno() {
		// TODO Auto-generated method stub
		return idTerreno;
	}

	@Override
	public int getDefensa() {
		// TODO Auto-generated method stub
		return defensa;
	}

	@Override
	public boolean getConstSobre() {
		// TODO Auto-generated method stub
		return construibleSobre;
	}

}
