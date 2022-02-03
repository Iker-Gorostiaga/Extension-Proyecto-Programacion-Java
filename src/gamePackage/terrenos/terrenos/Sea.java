package gamePackage.terrenos.terrenos;

import java.awt.Point;
import java.io.Serializable;

import gamePackage.entidades.Tropa;
import gamePackage.logica.*;
import gamePackage.terrenos.Terreno;

public class Sea implements Terreno, Serializable{

	// Atributos Logicos
	ListaIDTerreno idTerreno = ListaIDTerreno.SEA;
	boolean construibleSobre = false;
	int defensa = 0;
	// Atributos Fisicos
	String nombre = "Mar";
	Point pos = new Point(0, 0);
	String imagen = "img/Sea.png"; //Imagen predeterminada

	// Constructor
	public Sea() {
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
		switch (trop.getTipoMov()) {
		case ACUATICO:
			peaje = 1;
			break;
		case SUBACUATICO:
			peaje = 1;
			break;
		case AEREO:
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
