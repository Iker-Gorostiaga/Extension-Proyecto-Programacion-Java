package gamePackage.ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Action;
import javax.swing.JButton;

public class Settings extends JFrame {

	private JPanel contentPane;
	int dificultad ;
	int jugador1;
	int jugador2;
	int movilidad ;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Settings frame = new Settings(1,"b",1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Settings(int jugavilidad,String nombre,int codigo) {
		setTitle("Configuracion");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 555, 302);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(95, 158, 160));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JSlider dineroslider = new JSlider();
		dineroslider.setMinorTickSpacing(1);
		dineroslider.setMajorTickSpacing(1);
		dineroslider.setForeground(new Color(0, 0, 205));
		dineroslider.setBackground(new Color(95, 158, 160));
		dineroslider.setSnapToTicks(true);
		dineroslider.setPaintLabels(true);
		dineroslider.setPaintTicks(true);
		dineroslider.setValue(3);
		dineroslider.setMinimum(1);
		dineroslider.setMaximum(3);
		dineroslider.setBounds(36, 69, 200, 39);
		contentPane.add(dineroslider);
		
		JSlider slidermovimientos = new JSlider();
		slidermovimientos.setMinimum(1);
		slidermovimientos.setMinorTickSpacing(1);
		slidermovimientos.setForeground(new Color(0, 0, 255));
		slidermovimientos.setMajorTickSpacing(3);
		slidermovimientos.setMaximum(10);
		slidermovimientos.setValue(10);
		slidermovimientos.setBackground(new Color(95, 158, 160));
		slidermovimientos.setSnapToTicks(true);
		slidermovimientos.setPaintLabels(true);
		slidermovimientos.setPaintTicks(true);
		slidermovimientos.setBounds(36, 147, 200, 45);
		contentPane.add(slidermovimientos);
		
		JLabel labeldinero = new JLabel("Dificultad de dinero");
		labeldinero.setHorizontalAlignment(SwingConstants.CENTER);
		labeldinero.setBounds(60, 33, 146, 32);
		contentPane.add(labeldinero);
		
		JLabel labelmovimientos = new JLabel("Cantidad de movimientos");
		labelmovimientos.setHorizontalAlignment(SwingConstants.CENTER);
		labelmovimientos.setBounds(60, 118, 150, 28);
		contentPane.add(labelmovimientos);
		
		JButton botonaplicar = new JButton("Aplicar");
		botonaplicar.setBounds(95, 227, 89, 23);
		contentPane.add(botonaplicar);
		
	
		
		JButton botonMainMenu = new JButton("Main Menu");
		botonMainMenu.setBounds(327, 227, 112, 23);
		contentPane.add(botonMainMenu);
		
		JSlider capital1 = new JSlider();
		capital1.setValue(4000);
		capital1.setSnapToTicks(true);
		capital1.setPaintTicks(true);
		capital1.setPaintLabels(true);
		capital1.setMinorTickSpacing(1000);
		capital1.setMinimum(1000);
		capital1.setMaximum(10000);
		capital1.setMajorTickSpacing(3000);
		capital1.setForeground(new Color(0, 0, 205));
		capital1.setBackground(new Color(95, 158, 160));
		capital1.setBounds(263, 69, 266, 39);
		contentPane.add(capital1);
		
		JLabel lblCapitalInicialJugador = new JLabel("Capital inicial jugador 1");
		lblCapitalInicialJugador.setHorizontalAlignment(SwingConstants.CENTER);
		lblCapitalInicialJugador.setBounds(327, 33, 146, 32);
		contentPane.add(lblCapitalInicialJugador);
		
		JSlider capital2 = new JSlider();
		capital2.setValue(4000);
		capital2.setSnapToTicks(true);
		capital2.setPaintTicks(true);
		capital2.setPaintLabels(true);
		capital2.setMinorTickSpacing(1000);
		capital2.setMinimum(1000);
		capital2.setMaximum(10000);
		capital2.setMajorTickSpacing(3000);
		capital2.setForeground(new Color(0, 0, 205));
		capital2.setBackground(new Color(95, 158, 160));
		capital2.setBounds(263, 150, 266, 39);
		contentPane.add(capital2);
		
		JLabel lblCapitalInicialJugador_2 = new JLabel("Capital inicial jugador 2");
		lblCapitalInicialJugador_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblCapitalInicialJugador_2.setBounds(324, 115, 146, 32);
		contentPane.add(lblCapitalInicialJugador_2);
		
		/********************************************************************************************************************
		IKER
		Aplicara los ajustes seleccionados al usuario logeado
		*********************************************************************************************************************/
		
		botonaplicar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dificultad = dineroslider.getValue();
				movilidad = slidermovimientos.getValue();
				jugador1 = capital1.getValue();
				jugador2 = capital2.getValue();
				
				
			}
		});
		/********************************************************************************************************************
		IKER
		Nos lleva la menu principal
		*********************************************************************************************************************/
		
		botonMainMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainMenu menu = new MainMenu(dificultad,movilidad,jugador1,jugador2,jugavilidad,nombre,codigo);
				menu.setLocationRelativeTo(null);
				menu.setResizable(false);
				menu.setVisible(true);
				dispose();
				
			}
		});
	}
}