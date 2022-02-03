package gamePackage.ventanas;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;


/***********************************************************************************************************************************
IKER
Un nuevo tipo de JLabel.
Este tipo de JLabel nos permita sobre escribir el método Paint de las labels para poder pintar la imagen correctamente y
para que se autoajustable.
También esta implementado para la utilización de gif con un timer que los hace desaparecer al de x tiempo

**************************************************************************************************************************************/


public class Label_fondo extends JLabel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
    public int valor = 0;

	public Label_fondo(String text,String imagen) { //
		//super(text,JLabel.CENTER);
		ImageIcon Img = new ImageIcon(getClass().getResource(imagen));   
		this.setIcon(Img);
				     
	   }
	public Label_fondo(String text,String imagen, int segundos) { //mumero de segundos que permanece visible
		//super(text,JLabel.CENTER);
		ImageIcon Img = new ImageIcon(imagen);   
		this.setIcon(Img);
		Timer t = new Timer(segundos*1000, this);
		t.setRepeats(false); //evita que se repita
		t.start();
		     
	   }
	
	   @Override
	   public void paintComponent(Graphics g) {
		    //super.paintComponent(g);  //ejecuta el pintado normal de un jlabel
		  	    		  
		    Graphics2D g2D = (Graphics2D) g;
		    Dimension height = getSize(); //obtengo el tamaño de la label
		    
		   //Se selecciona la imagen actual de la label
		   ImageIcon Img = (ImageIcon) this.getIcon(); 
		    
		   //Se redibuja la imagen con el tamaño de la label (Se produce deformidad);
		   g2D.drawImage(Img.getImage(), 0, 0, height.width, height.height, this); //this  muy importante poner observador para que funcionen los gif animados
		   setOpaque(false);
		   		  		   
		   g2D.dispose();

	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		
		this.setVisible(false);
		try {
			this.finalize();
		} catch (Throwable e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	} 
}



