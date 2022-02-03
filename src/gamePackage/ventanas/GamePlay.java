package gamePackage.ventanas;
import  gamePackage.entidades.terrestres.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import gamePackage.baseDatos.DataBase;
import  gamePackage.entidades.*;
import  gamePackage.ventanas.*;
import  gamePackage.logica.*;
import gamePackage.terrenos.Estructura;
import gamePackage.terrenos.Terreno;



public abstract class GamePlay {
	
	
	
// metodos para la partida	
	public static boolean capture(Tropa t,Estructura c) {
	
		c.setHp(c.getHp()-	t.getHP());
		if (c.getHp()==0) {
			if (c.getCG()==true) {
				return true;
			}
			c.setHp(2);
			return false;
			
		}
		return false;
	}

	/********************************************************************************************************************
	IKER
	Este el el apartado de combate en el cual nos llegan 2 tropas.
	El dano es seleccionado por sus ataques y una ves conseguimos saber quien gana esta "partida" la guardamos en la BD
	*********************************************************************************************************************/
	
	
	public static int combat(Tropa atacante, Tropa defensor,String nombre,int codigo,int turno){
		
		DataBase scores = new DataBase();
		scores.iniciaDB();
		int resultado = 0;
		int dano1 = 0;
		int dano2 = 0;
		
		
		switch ((int)Math.random()*3) {
		case 1:
			dano1 = atacante.getGolpePrim();
			dano2 = defensor.getGolpePrim();
			break;

		case 2:
			dano1 = atacante.getGolpeSec();
			dano2 = defensor.getGolpeSec();
			break;
		}
		
		int plus1 = (int) Math.floor(Math.random()*26);
		int plus2 = (int) Math.floor(Math.random()*26);
		
		dano1+=plus1;
		dano2+=plus2;
		
		if(dano1<dano2) {
			
			resultado = 1;
			
		}else {
			resultado = 2;
		}
		
		String rojo = "";
		String azul = "";
		if(atacante.getTeam() == 1) {
			rojo = atacante.getNombre();
			azul = defensor.getNombre();
			
		}else {
			azul = defensor.getNombre();
			rojo = atacante.getNombre();
		}
		
		
		
		int cod_p = scores.codigopartidaBD(codigo);
		
		scores.controlbajas(codigo, cod_p, turno, rojo, dano1, azul, dano2, resultado);
		
		return resultado;
	}
	
	//guerra detecto dos tropas de distinta equipo
	//destino con tropa??
	
	//arma que usa cada tropa en combate 
	public static int weaponChoice(Tropa  a , Tropa d) {
		int attack = 0;
		if (d.getIDTropa()==ListaIDTropa.INF) {
			switch (a.getArmas()[0]) {
			case ARMA_AA:
				attack= 0;
				break;
			case CANNON:
				attack= 0;
				break;
			case COHETE:
				attack= 0;
				break;
			default:
				if (a.getArmas()[1]==ListaArmas.AMETRALLADORA) {
					attack= 1;
				}
				else {
					attack=2;
					}	
				break;
			}
		}
		else {
			attack=0;
			}
		if (attack==0) {
			if (a.getMuniciones() == 0) {
				if (a.getArmas()[1]==ListaArmas.AMETRALLADORA) {
					attack= 1;
				}
				else {
					attack=2;
					}	
				
			}
		}
		return attack;
	}
	public static float damageNumbers(ListaArmas a , ListaBlindaje d) {
		float reduction = 100;
		switch (a) {
		case AMETRALLADORA:
			switch (d) {
			case INFANTERIA:
				reduction= 100;
				break;
			case VH_L:
				reduction= 75;
				break;
			case VH_H:
				reduction= 45;
				break;
			case TK:
				reduction= 10;
				break;
			}		
			break;
		case TCANNON:
			switch (d) {
			case VH_L:
				reduction= 120;
				break;
			case VH_H:
				reduction= 100;
				break;
			case TK:
				reduction= 100;
				break;
			default:
				break;
			}
			
			break;
		case TCANNON_MED:
			switch (d) {
			case VH_L:
				reduction= 120;
				break;
			case VH_H:
				reduction= 100;
				break;
			case TK:
				reduction= 100;
				break;
			default:
				break;	
			}
			
			break;
		case CANNON:
			switch (d) {
			case INFANTERIA:
				reduction= 50;
				break;
			case VH_L:
				reduction= 100;
				break;
			case VH_H:
				reduction= 100;
				break;
			case TK:
				reduction= 100;
				break;
			}
			
			break;
		case ARMA_AA:
			switch (d) {
			case INFANTERIA:
				reduction= 100;
				break;
			case VH_L:
				reduction= 75;
				break;
			case VH_H:
				reduction= 60;
				break;
			case TK:
				reduction= 10;
				break;
			default:
				break;

			}
			break;
		case COHETE:
			switch (d) {
			case INFANTERIA:
				reduction= 50;
				break;
			case VH_L:
				reduction= 80;
				break;
			case VH_H:
				reduction= 80;
				break;
			case TK:
				reduction= 100;
				break;
			}
			
			break;
			
			default:
				reduction=100;
				break;
		}
		reduction=reduction/100;
		return reduction;
	}
	
}

