package gamePackage.entidades;

import gamePackage.logica.*;
import java.awt.Point;

public interface Tropa {
	
	/*
	[Datos Logicos]
	nombre — Nombre de la tropa. Esto es lo que se muestra
	idTropa — Identidad de la tropa. Esto para metodos a futuro
	tipoTropa — Identidad de tipo de tropa. Esto para metodos a futuro
	tipoMov — Identidad de tipo de movimiento. Esto para metodos a futuro
	captura — Si puede capturar o no
	construye — Si puede construir estructuras o no. Usado solo por una tropa de momento, pero implementable a futuro para mas.
	salud — Cuanto golpe puede absorver
	golpePrim — Cuanto duele cuando da con arma primaria.
	golpeSec — Cuanto duele cuando da con arma secundaria.
	armaPrincipal — Arma principal, la que siempre llevaria. Municiones limitadas.
	armaSecundaria — Arma secundaria, un arma opcional que se dispara contra ciertos enemigos, o en ciertos escenarios. Municiones infinitas.
	precio — Cuanto cuesta
	nivel — Se obtiene por victoria en combate. Incrementa el golpe o algo.
	vision — Numero de casillas que puede ver
	alcanceMin — Limite minimo de casillas en las que puede ataquar
	alcanceMax — Limite maximo de casillas en las que puede ataquar
	municionesPrim — Numero de municiones que tiene el arma primaria.
	energiaMaxima — Numero de movimientos maximos
	energia — Numero de casillas que se puede mover la tropa cada momento. Sistema TBD, @Rodrigo
	distanciaMaxima — Numero de casillas total que la tropa puede moverse en un turno
	
	[Datos Fisicos]
	posX — Posicion en eje X
	posY — Posicion en eje Y
	imagen — Fichero de imagen 
	
	 */
	
	/* Atributos a copiar en las clases:
	
	String nombre = "";
	ListaIDTropa idTropa = null;
	ListaTipos tipoTropa = null;
	ListaTipos tipoMov = null;
	boolean captura = false;
	boolean construye = false;
	int saludMaxima = 0;
	int salud = this.saludMaxima;
	int golpePrim = 0;
	int golpeSec = 0;
	ListaArmas armaPrincipal = null;
	ListaArmas armaSecundaria = null;
	int precio = 0;
	int nivel = 0;
	int vision = 0;
	int alcanceMin = 0;
	int alcanceMax = 0;
	int municionesPrim = 0;
	int energiaMaxima = 0;
	int energia = this.energiaMaxima;
	int distanciaMaxima = 0;
	Point pos = new Point(0,0);
	String imagen = "";
	
	*/
	
	//Metodos Fisicos
		//Setters
	public String getNombre();
	public void setTropa();
	public void setPos();
	public void setImagen();
		//Getters
	public Point getPos();
	
	//Metodos Logicos
		//Setters
	public void setTeam(int team);
	public void setTipoMov(ListaTipos tipoMov);
	public void setTipoTropa(ListaTipos tipoTropa);
	public void setSalud(float f);
	public void setGolpePrim(int golpePrim);
	public void setGolpeSec(int golpeSec);
	public void setEnergia(int energia);
	public void setMuniciones(int municiones);
	public void setNivel(int nivel);
		//Getters
	public float getHP();
	public int getTeam();
	public ListaIDTropa getIDTropa();
	public ListaTipos getTipoTropa();
	public ListaTipos getTipoMov();
	public ListaArmas[] getArmas();
	public ListaBlindaje getBlindaje();
	public boolean getCaptura();
	public int getPrecio();
	public boolean getConstruye();
	public int getSaludMax();
	public float getSalud();
	public int getGolpePrim();
	public int getGolpeSec();
	public int getDistMax();
	public int getEnergiaMax();
	public int getEnergia();
	public int[] getAlcance();
	public int getMunicionesMax();
	public int getMuniciones();
	public int getVision();
	public int getNivel();
	
		//Metodos
	public boolean necesitaSuministro();
	public boolean atacaA(ListaTipos tipoTropa);
	void setSalud(int salud);
	
}
