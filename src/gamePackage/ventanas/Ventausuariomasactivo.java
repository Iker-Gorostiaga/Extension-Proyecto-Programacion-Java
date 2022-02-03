package gamePackage.ventanas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import gamePackage.baseDatos.DataBase;

import java.awt.Color;
import java.awt.Component;
import javax.swing.table.*;
import javax.swing.ListSelectionModel;

public class Ventausuariomasactivo extends JFrame {

	private JPanel contentPane;
	DataBase scores = new DataBase();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventausuariomasactivo frame = new Ventausuariomasactivo();
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
	public Ventausuariomasactivo() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Jugadores mas activos");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		DefaultTableModel dtmModelo = new DefaultTableModel();
		
		
		DefaultTableModel model = new DefaultTableModel();
        model.addColumn("nombre");
        model.addColumn("partidas");
        model.addColumn("ganadas");
        model.addColumn("perdidas");
        
        JTable table = new JTable(model);
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		table.setBackground(new Color(95, 158, 160));
		JScrollPane scrol = new JScrollPane(table);
		contentPane.add(scrol, BorderLayout.CENTER);
		
	
		/********************************************************************************************************************
		IKER
		Conseguimos todas las partidas de los usuarios.
		*********************************************************************************************************************/
		
		scores.iniciaDB();
		ArrayList<ArrayList<String>> lista = new ArrayList<>();
		lista = scores.usuariomasactivo();
		scores.finalizaDB();
		
		/********************************************************************************************************************
		IKER
		La lista la ordenamos descendente con el metod del quicksort
		*********************************************************************************************************************/
		
		quickSort(lista,0,lista.size()-1);
		
		for (ArrayList<String> a : lista) {
			model.addRow(new Object[] {a.get(0),a.get(1),a.get(2),a.get(3)});
		}
		
		
		setVisible(true);
		contentPane.repaint();
	}
	
	public static void quickSort(ArrayList<ArrayList<String>> arr, int start, int end) {

        int partition = partition(arr, start, end);

        if (partition - 1 > start) {
            quickSort(arr, start, partition - 1);
        }
        if (partition + 1 < end) {
            quickSort(arr, partition + 1, end);
        }
    }

    public static int partition(ArrayList<ArrayList<String>> arr, int start, int end) {
    	ArrayList<String> pivot =arr.get(end);

        for (int i = start; i < end; i++) {
            if (Integer.parseInt(arr.get(i).get(1)) >= Integer.parseInt(pivot.get(1))) { ////// cambio de signo cambio de orden
                ArrayList<String> temp = arr.get(start);
                arr.set(start, arr.get(i));
                arr.set(i, temp);
                start++;
            }
        }

        ArrayList<String> temp = arr.get(start);
        arr.set(start, pivot);
        arr.set(end, temp);

        return start;
    }

}
