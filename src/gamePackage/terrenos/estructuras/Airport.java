package gamePackage.terrenos.estructuras;

import gamePackage.entidades.Tropa;
import gamePackage.logica.*;
import gamePackage.terrenos.Estructura;
import java.awt.Point;
import java.io.Serializable;

public class Airport implements Estructura, Serializable{

	// Logicos
	ListaIDTerreno idTerreno = ListaIDTerreno.AIRPORT;
	boolean construibleSobre = false;
	int defensa = 3;
	int ingresos = 1000;
	float hp= 2;
	boolean cuartelGeneral = false;
	boolean construible = false;
	boolean capturable = true;
	boolean fabrica = true;
	boolean visibleEncontrado = true;
	int team = 0;

	// Fisicos
	String nombre = "Aeropuerto";
	Point pos = new Point(0, 0);
	String imagen = "";

	// Constructor
	public Airport(int team) {
		super();
		this.team=team;

	}
	public float getHp() {
		return hp;
	}
	public void setHp(float hp) {
		this.hp=hp;
		if (getHp()<=0) {
			if (getTeam()!=1) {
				setTeam(1);
			}else if (getTeam()!=2) {
				setTeam(2);
				
			}
		}
	}
	public int getTeam() {
		return team;
	}

	public void setTeam(int team) {
		this.team = team;
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
		case INFANTERIA:
			peaje = 1;
			break;
		case V_LIGERO:
			peaje = 1;
			break;
		case V_PESADO:
			peaje = 2;
			break;
		case ACORAZADO:
			peaje = 1;
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
	public boolean getCapturable() {
		return capturable;
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getFabrica() {
		return fabrica;
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getCG() {
		return cuartelGeneral;
		// TODO Auto-generated method stub

	}

	@Override
	public int getIngresos() {
		return ingresos;
		// TODO Auto-generated method stub

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

	@Override
	public boolean getConstruible() {
		// TODO Auto-generated method stub
		return construible;
	}

	@Override // Intento de hacer un metodo recursivo. Hay error de Stack Overflow.
	public boolean suministra(Tropa trop) {
		// TODO Auto-generated method stub
		boolean sumi = false;
		try {
			if (suministra(trop) == true) {
				if (trop.getEnergia() <= trop.getEnergiaMax()) {
					trop.setEnergia((trop.getEnergia() + 1));
				}
				if (trop.getMuniciones() <= trop.getMunicionesMax()) {
					trop.setMuniciones((trop.getMuniciones() + 1));
				}
				if (trop.getSalud() <= trop.getSaludMax()) {
					trop.setSalud((trop.getSalud() + 1));
				}
			}
		}

		catch (Exception e) {
			// TODO: handle exception
		}

		if (sumi = false) {
			switch (trop.getTipoTropa()) {
			case TERRESTRE:
				sumi = true;
				break;
			case INFANTERIA:
				sumi = true;
				break;
			case V_LIGERO:
				sumi = true;
				break;
			case V_PESADO:
				sumi = true;
				break;
			default:
				sumi = false;
				break;
			}
		}
		return sumi;
	}


}
