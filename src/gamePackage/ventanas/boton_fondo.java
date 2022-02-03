package gamePackage.ventanas;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class boton_fondo extends JButton {
	
	private static final long serialVersionUID = 1L;

	/***********************************************************************************************************************************
	IKER
	Un nuevo tipo de JButton.
	Este tipo de JButton nos permite ajustar las imagenes perfectamente debido a que sobre escribivo el metodo paint original
	del JButton por el mio.

	**************************************************************************************************************************************/

	public boton_fondo(String imagen) {
		super();
		ImageIcon Img = new ImageIcon(getClass().getResource(imagen));   
		this.setIcon(Img);
			      
	   }

	   @Override
	   public void paintComponent(Graphics g) {
//		   super.paintComponent(g);  //ejecuta el pintado normal de un jlabel
		   Graphics2D g2D = (Graphics2D) g;
		     
		    Dimension height = getSize(); //obtengo el tamaño de la label
		    
		   //Se selecciona la imagen actual de la label
	
		   ImageIcon Img = (ImageIcon) this.getIcon(); 
		    
		   //Se redibuja la imagen con el tamaño de la label (Se produce deformidad);
		   
		   g2D.drawImage(Img.getImage(), 0, 0, height.width, height.height, this);
		   
		   setOpaque(false);
		   		  		   
		   g2D.dispose();

	} 
}
