package gamePackage.ventanas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dialog;

import javax.swing.JLabel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;


public class VentanaAvisos extends JDialog {

	private final JPanel contentPanel = new JPanel();
	JLabel Error = new JLabel("");
	JLabel Interrogante = new JLabel("");
	JLabel Tick = new JLabel("");
	
	JLabel Textoinformacion = new JLabel("");
	
	JButton BotonSi = new JButton("Si");
	JButton BotonAcepter = new JButton("Aceptar");
	JButton BotonNo = new JButton("No");
	int resultados = 0;

	public int getResultados() {
		return resultados;
	}

	public void setResultados(int resultados) {
		this.resultados = resultados;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentanaAvisos dialog = new VentanaAvisos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	
	public VentanaAvisos() {
		setBounds(600, 300, 450, 300);
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaAvisos.class.getResource("/gamePackage/ventanas/img/troop/blue/ToaBLUE.png")));
		setLocationRelativeTo(null);
		setTitle("Avisos");
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(95, 158, 160));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		BotonSi.setBounds(113, 211, 85, 21);
		contentPanel.add(BotonSi);
	
		
		BotonNo.setBounds(231, 211, 85, 21);
		contentPanel.add(BotonNo);
	
		
		BotonAcepter.setBackground(new Color(255, 255, 255));
		BotonAcepter.setBounds(178, 211, 85, 21);
		contentPanel.add(BotonAcepter);
		
		BotonAcepter.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		BotonSi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				resultados = 1;
				dispose();
				
			}
		});
		BotonNo.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			resultados = 0;
			dispose();
			
		}
	});
		
		String filePath = System.getProperty("user.dir"); //debuelve ruta
		filePath=filePath+"\\Imagenes\\";
		
		Tick.setBackground(new Color(95, 158, 160));
		ImageIcon icon = new ImageIcon(filePath+"tick.png");
		Image scale = icon.getImage().getScaledInstance(90, 90, Image.SCALE_DEFAULT);
		icon = new ImageIcon(scale);
		Tick.setIcon(icon);
		Tick.setBounds(321, 10, 92, 92);
		contentPanel.add(Tick);
		Tick.setVisible(false);
		
		Interrogante.setBackground(new Color(95, 158, 160));
		ImageIcon icon2 = new ImageIcon(filePath+"interro.png");
		Image scale2 = icon2.getImage().getScaledInstance(80,80, Image.SCALE_DEFAULT);
		icon2 = new ImageIcon(scale2);
		Interrogante.setIcon(icon2);
		Interrogante.setBounds(321, 10, 92, 92);
		contentPanel.add(Interrogante);
		Interrogante.setVisible(false);

		
		Error.setBackground(new Color(95, 158, 160));
		ImageIcon icon3 = new ImageIcon(filePath +"Error.png");
		Image scale3 = icon3.getImage().getScaledInstance(80,80, Image.SCALE_DEFAULT);
		icon3 = new ImageIcon(scale3);
		Error.setIcon(icon3);
		Error.setBounds(321, 10, 92, 92);
		contentPanel.add(Error);
		
		
		Textoinformacion.setHorizontalAlignment(SwingConstants.CENTER);
		Textoinformacion.setBackground(new Color(95, 158, 160));
		Textoinformacion.setBounds(10, 112, 414, 76);
		contentPanel.add(Textoinformacion);
		Error.setVisible(false);

		contentPanel.repaint();
	}
	// imagen 1-tik 2-interrogante 3 -error , texto, 1 - aceptar 2 -si y no
	public void VentanaDeAvisos(int imagen,String texto,int botones) {
		
		Textoinformacion.setText(texto);
		
		switch (imagen) {
		case 1:
			Tick.setVisible(true);
			Interrogante.setVisible(false);
			Error.setVisible(false);
			
			break;
		case 2:
			Tick.setVisible(false);
			Interrogante.setVisible(true);
			Error.setVisible(false);
			
			
			break;
		case 3:
			Tick.setVisible(false);
			Interrogante.setVisible(false);
			Error.setVisible(true);
			
			
			break;
		}

		switch (botones) {
		case 1:
			BotonAcepter.setVisible(true);
			BotonNo.setVisible(false);
			BotonSi.setVisible(false);
			break;
		case 2:
			BotonAcepter.setVisible(false);
			BotonNo.setVisible(true);
			BotonSi.setVisible(true);
			break;
		}
		contentPanel.repaint();
		
		setModal(true);
		setVisible(true);
		
		
		
		
		
		
	}
	
}

