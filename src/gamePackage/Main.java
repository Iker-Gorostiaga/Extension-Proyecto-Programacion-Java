package gamePackage;

import gamePackage.ventanas.MainMenu;

public class Main {
	
	static MainMenu menu = new MainMenu(0, 0, 0, 0,0,"",0);
	
	public static void main(String[] args) {
		
		menu.setLocationRelativeTo(null);
		menu.setResizable(false);
		menu.setVisible(true);
	}
	

}
