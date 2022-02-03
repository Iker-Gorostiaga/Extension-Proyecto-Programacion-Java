package gamePackage.baseDatos;

import java.sql.*; //Import all related to SQL
import java.text.SimpleDateFormat;
import java.util.logging.Level; //Logger Level import, so we can designate what is critical
import java.util.logging.Logger; //Logger Logger import, so we can log what critical is


import java.util.ArrayList;
import java.util.Date;

/*FOR THIS
 * SECTION
 * MUST DOWNLOAD
 * SQLite
 * FROM MAVEN REPOSITORIES
 *  LINK: https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc
 */

public class DataBase {
	static Date date = new Date();

	public static Date getDate() {
		return date;
	}

	private static Logger logDB = Logger.getLogger("Scoreboard");
	private static Connection connect = null;
	
	/********************************************************************************************************************
	IKER
	Hemos añadido nuevas tablas como clientes,partidas y controldebajaspartidas.
	Metodos para obtener variebles de la BD.
	Metodos para guardar datos como guardarclientes, actualizarusuario, guardarpartidasporpersona, controlbajas,
	actualizarpartida.
	
	*********************************************************************************************************************/
	
	public static Connection iniciaDB() {
		try {
			Class.forName("org.sqlite.JDBC"); // Carga de BD para SQLite
			connect = DriverManager.getConnection("jdbc:sqlite:" + "Scoreboard.db");
			logDB.log(Level.INFO, "Successfully connected to Scoreboard");
		} catch (ClassNotFoundException e1) {
			logDB.log(Level.SEVERE, e1.toString());
		} catch (SQLException e2) {
			logDB.log(Level.SEVERE, e2.toString());
		}
		
		return connect;
	}

	public static void creaTablas() {
		try {
			Statement stat = connect.createStatement();
			String stt = "create table if not exists gamestats(todat date not null primary key, redwin integer(3) default 0, bluwin integer(3) default 0, amberwin integer(3) default 0, greenwin integer(3) default 0, gamesplayed integer(3) default 0);";
			stat.execute(stt);
			logDB.log(Level.INFO, "Executed " + stt);
			stt = "create table if not exists teamstats(tdate int(20) not null primary key, rfunds integer(9) default 0, bfunds integer(9) default 0, afunds integer(9) default 0, gfunds integer(9) default 0);";
			stat.execute(stt);
			stt = "create table if not exists clientes(cod integer primary key AUTOINCREMENT , contra varchar(10) not null,nombre varchar(15) not null, dificuldinero int(1), movimientos int(2), dinerojugador1 int(5),dinerojugador2 int(5));";
			stat.execute(stt);
			stt = "create table if not exists partidas(cod integer , cod_p integer primary key AUTOINCREMENT, nombre_p varchar(15) not null,fecha varchar(20),Foreign key (cod) references clientes(cod));";
			stat.execute(stt);
			stt = "create table if not exists controldebajaspartidas(cod_b integer primary key AUTOINCREMENT, cod integer,cod_p integer,turno int(1),nombrerojo varchar(15),dano1 int(3),nombreazul varchar(15),"
					+ "dano2 int(3),ganador int(1),foreign key (cod,cod_p) references partidas(cod,cod_p) );";
			stat.execute(stt);
			
			logDB.log(Level.INFO, "Executed " + stt);
			stat.close();
			stt = null;
			logDB.log(Level.INFO, "Successfully created tables");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logDB.log(Level.SEVERE, e.toString());
		}
	}

	public static void eliminaDB() {
		try {
			Statement stat = connect.createStatement();
			String stt = "drop table if exists teamstats;";
			stat.execute(stt);
			stt = "drop table if exists gamestats;";
			stat.execute(stt);
			stt = "drop table if exists clientes;";
			stat.execute(stt);
			stt = "drop table if exists partidas;";
			stat.execute(stt);
			stt = "drop table if exists controldebajaspartidas;";
			stat.execute(stt);
			logDB.log(Level.INFO, "Executed " + stt);
			logDB.log(Level.WARNING, "The Database's living status has been revoked");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logDB.log(Level.SEVERE, e.toString());
		}
	}

	public static void finalizaDB() {
		try {
			if (connect != null) {
				connect.close();
				logDB.log(Level.INFO, "Successfully ended connection");
			}
		} catch (SQLException e) {
			logDB.log(Level.SEVERE, e.toString());
		}
		connect = null;
	}

	public static void actualizaGlobal(boolean rw, boolean bw, boolean aw, boolean gw, boolean played) {
		String stt;
		int plays = 0;
		int red = 0;
		int blue = 0;
		int green = 0;
		int amber = 0;
		if (played == true)
			plays = 1;
		if (rw == true)
			red = 1;
		if (gw == true)
			green = 1;
		if (bw == true)
			blue = 1;
		if (aw == true)
			amber = 1;

		try {
			Statement stat = connect.createStatement();
			try {
				stt = ("update gamestats set redwin = redwin + " + red + ", bluwin = bluwin + " + blue
						+ ", amberwin = amberwin + " + amber + ", greenwin = greenwin + " + green
						+ ", gamesplayed = gamesplayed +" + plays +" where todat = date('now');");
				stat.execute(stt);
				logDB.log(Level.INFO, "Executed " + stt);
			} catch (Exception e) {
				logDB.log(Level.WARNING, e.toString());
				try {
					stt = (";");
					stat.execute(stt);
					logDB.log(Level.INFO, "Executed " + stt);
				} catch (Exception e2) {
					logDB.log(Level.SEVERE, e2.toString());
				}
			}

		} catch (SQLException e) {
			logDB.log(Level.SEVERE, e.toString());
		}

	}

	public static void actualizaTEquipos(int rf, int gf, int bf, int af) {
		String stt;
		try {
			
			Statement stat = connect.createStatement();
			try {
				long a = System.currentTimeMillis();
				
				stt = "insert into teamstats(tdate,rfunds,bfunds,afunds,gfunds) values("+a+", " + rf + ", " + bf + ", " + af + ", " + gf+ ");";
				stat.execute(stt);
				
				
				logDB.log(Level.INFO, "Executed " + stt);
			} catch (Exception e) {
				logDB.log(Level.WARNING, e.toString());
				try {
					
					
					
					stt = "update teamstats set rfunds = rfunds + " + rf + ";";
					stat.execute(stt);
					
					logDB.log(Level.INFO, "Executed " + stt);
				} catch (Exception e2) {
					logDB.log(Level.SEVERE, e2.toString());
				}

			}

		} catch (SQLException e) {
			logDB.log(Level.SEVERE, e.toString());
		}
		

	}

	public Connection getConn() {
		return connect;
	}
	
	public void guardarclientes(String nombre,String contra,int dinerodifcul,int movili,int cap1,int cap2) {
		String sent = "";
		try {
			Statement stat = connect.createStatement();
			sent = "insert into clientes(contra,nombre,dificuldinero,movimientos,dinerojugador1,dinerojugador2) values('"+contra+"','"+nombre+"',"+dinerodifcul+","+movili+","+cap1+","+cap2+")";
			stat.execute(sent);
		} catch (SQLException e) {
			logDB.log(Level.WARNING,e.toString());
		}
		
	}
	
	public int Confirmarclientes(String nombre,String contra) {
		
		int a = 0;
		String sent = "";
		try {
			Statement stat = connect.createStatement();
			sent = "select cod from clientes where contra ='"+contra+"' and nombre ='"+nombre+"';";
			ResultSet rs = stat.executeQuery(sent);
			
			if(rs.next()) {
				
				a = rs.getInt("cod");
			}
			
			
		} catch (SQLException e) {
			logDB.log(Level.WARNING,e.toString());
		}
		return a ;
		
	}
	
	public ArrayList<Integer> conseguirajustesusuario(String nombre,String contra){
		ArrayList<Integer> lista = new ArrayList<>();
		
		String sent = "";
		try {
			Statement stat = connect.createStatement();
			sent = "select dificuldinero,movimientos,dinerojugador1,dinerojugador2 from clientes where nombre ='"+nombre+"' and contra ='"+contra+"';";
			ResultSet rs = stat.executeQuery(sent);
			lista.add(rs.getInt("dificuldinero"));
			lista.add(rs.getInt("movimientos"));
			lista.add(rs.getInt("dinerojugador1"));
			lista.add(rs.getInt("dinerojugador2"));
			
		} catch (SQLException e) {
			logDB.log(Level.WARNING,e.toString());
		}
		
		return lista;
	} 
	public int conseguircodigousuario(String nombre,String contra) {
		int numero = 0;
		
		String sent = "";
		try {
			Statement stat = connect.createStatement();
			sent = "select cod from clientes where nombre ='"+nombre+"' and contra ='"+contra+"';";
			ResultSet rs = stat.executeQuery(sent);
			numero = rs.getInt("cod");
			
		} catch (SQLException e) {
			logDB.log(Level.WARNING,e.toString());
		}
		
		return numero;
		
		
	}
	
	public void actualizarusuario(int dindificul,int movili,int dinero1,int dinero2,String nombre,String contra) {
		
		String sent = "";
		try {
			Statement stat = connect.createStatement();
			sent = "update clientes set dificuldinero ="+dindificul+", movimientos ="+movili+", dinerojugador1 = "+dinero1+",dinerojugador2 ="+dinero2+" where nombre ='"+nombre+"' and contra ='"+contra+"';";
			stat.execute(sent);
			
			
		} catch (SQLException e) {
			logDB.log(Level.WARNING,e.toString());
		}
		
	}
	
	
	public void guardarpartidasporpersona(int codigo,String nombre) {
		
		String sent = "";
		try {
			Statement stat = connect.createStatement();
			
			long fec = System.currentTimeMillis();
			
			SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
			
			Date d1 = new Date(fec);
			String fechacorrecta = f1.format(d1);
			
			sent = "insert into partidas(cod,nombre_p,fecha) values('"+codigo+"','"+nombre+"','"+fechacorrecta+"')";
			stat.execute(sent);
			
			
		} catch (SQLException e) {
			logDB.log(Level.WARNING,e.toString());
		}
		
	}
	
	public ArrayList<String> conseguirmapasusuario(int codigo) {
		ArrayList<String > listanombres = new ArrayList<String>();
		String sent = "";
		try {
			Statement stat = connect.createStatement();
			
			sent = "select nombre_p from partidas where cod ='"+codigo+"';";
			ResultSet rs = stat.executeQuery(sent);
			while(rs.next()) {
				listanombres.add(rs.getString("nombre_p"));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listanombres;
	}
	
	public void controlbajas(int codigo,int codigopartida,int turno,String rojo,int danorojo,String azul,int danoazul,int resultado) {
		String sent = "";
		try {
			Statement stat = connect.createStatement();
			sent = "insert into controldebajaspartidas(cod,cod_p,turno,nombrerojo,dano1,nombreazul,dano2,ganador) values("+codigo+","+codigopartida+","+turno+",'"+rojo+"',"+danorojo+",'"+azul+"',"+danoazul+","+resultado+");";
			stat.execute(sent);
		} catch (SQLException e) {
			logDB.log(Level.WARNING,e.toString());
		}
		
	}
	
	public int codigopartidaBD(int codigo) {
		int resul = 0;
		String sent = "";
		
		try {
			Statement stat = connect.createStatement();
			sent = "select cod_p from partidas where cod ="+codigo+";";
			ResultSet rs = stat.executeQuery(sent);
			while(rs.next()) {
				resul = rs.getInt("cod_p");
				
			}
			
			
		} catch (SQLException e) {
			logDB.log(Level.WARNING,e.toString());
		}
		
		return resul;
	}
	public void actualizarpartida(int codigo,String nombre,int cod_p) {
		int resul = 0;
		String sent = "";
		
		try {
			Statement stat = connect.createStatement();
			sent = "update partidas set nombre_p ='"+nombre+"' where cod ="+codigo+" and cod_p ="+cod_p+";";
			stat.execute(sent);
			sent = "delete from partidas where nombre_p ='';";
			stat.execute(sent);
		} catch (SQLException e) {
			logDB.log(Level.WARNING,e.toString());
		}
		
	}
	
	public int Confirmarnombremultiple(int codigo,String nombre) {
		int resul = 0;
		String sent = "";
		
		try {
			Statement stat = connect.createStatement();
			sent = "select * from partidas where nombre_p ='"+nombre+"';";
			ResultSet rs = stat.executeQuery(sent);
			resul = rs.getInt("cod_p");
			
		} catch (SQLException e) {
			logDB.log(Level.INFO, "cod correcto");
		}
		return resul ;
	}
	public String nombrepartidaBD(int codigo) {
		String resul = "";
		String sent = "";
		
		try {
			Statement stat = connect.createStatement();
			sent = "select nombre_p from partidas where cod ="+codigo+";";
			ResultSet rs = stat.executeQuery(sent);
			resul = rs.getString("nombre_p");
			
		} catch (SQLException e) {
			logDB.log(Level.WARNING,e.toString());
		}
		return resul;
	}
	
	public ArrayList<ArrayList<String>> usuariomasactivo() {
		ArrayList<ArrayList<String>> lista = new ArrayList<>();
		
		String sent = "";
		
		try {
			Statement stat = connect.createStatement();
			sent= "select c.nombre, count(*) as N_partidas, count(CASE WHEN ganador = 2 THEN 1 ELSE null END) as N_partidas_ganadas, count(CASE WHEN ganador = 1 THEN 1 ELSE null END) as N_partidas_perdidas from clientes c,controldebajaspartidas p where c.cod = p.cod group by nombre;";
			ResultSet rs = stat.executeQuery(sent);
			while(rs.next()) {
				ArrayList<String> dentro = new ArrayList<>();
				dentro.add(rs.getString("nombre"));
				dentro.add(rs.getString("N_partidas"));
				dentro.add(rs.getString("N_partidas_ganadas"));
				dentro.add(rs.getString("N_partidas_perdidas"));
				lista.add(dentro);
			}
			
			
			
		} catch (SQLException e) {
			logDB.log(Level.WARNING,e.toString());
		}
		
		return lista;
		
		
	}
	

}
