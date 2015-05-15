package application;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import application.models.Tur;


public class SimpleUI extends JFrame{
	private static DatabaseController dbC;
	private JButton getTripButton;
	private JButton saveButton;
	private JButton registerButton;
	private JTable tripTable;
	private String [] tripColumnNames;
	
	public SimpleUI(DatabaseController dbC) {
		super();
		this.dbC = dbC;
		initColumnNames();
		setLayout(new BorderLayout());
		add(createListPanel(), BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		pack();
		setVisible(true);
		
	}

		
	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel();
		getTripButton = new JButton("Visa turer");
		saveButton = new JButton("Boka");
		registerButton = new JButton("Registrera användare");
		return buttonPanel;
	}

	private JPanel createListPanel() {
		JPanel listPanel = new JPanel();
		tripTable = new JTable();
		listPanel.add(new JScrollPane(tripTable));
		return listPanel;
		
	}
	
	
	
	private class ButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == getTripButton){
				ArrayList<Tur> trips = dbC.getTurer();
				DefaultTableModel tableModel = new DefaultTableModel();
				for(Tur t : trips){
					tableModel.addRow(t.toString().split(","));
				}
				tripTable.setModel(tableModel);
			} else if (e.getSource() ==saveButton){
				
			}
			
		}
		
	}
		
	private void initColumnNames() {
		String [] tripColumnNames = {"ID",
				"Pris",
				"Avreseort",
				"Avresedag",
				"Avresetid",
				"Ankomstort",
				"Ankomstdag",
				"Ankomsttid",
		"Kapacitet"};	
		this.tripColumnNames = tripColumnNames;
	}
}
