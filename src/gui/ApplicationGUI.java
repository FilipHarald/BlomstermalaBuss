package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import application.DatabaseController;
import application.models.*;


public class ApplicationGUI extends JFrame{
	private DatabaseController dbC;
	private JButton getTurerButton;
	private JButton getKunderButton;
	private JButton getPaketresorButton;
	private JButton registerButton;
	private JTable dataTable;
    private JPanel addBokningPanel;
    private JPanel infoPanel;
    private String currentTable = "kunder";
	
	
	public ApplicationGUI(DatabaseController dbC) {
		super();
		this.dbC = dbC;
		setLayout(new BorderLayout());
		add(createTablePanel(), BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.SOUTH);
        add(createRightTabPanel(), BorderLayout.EAST);
        add(infoPanel = new InfoPanel(this), BorderLayout.WEST);
        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		pack();
		setVisible(true);
	}

    public DatabaseController getDbc() {
        return dbC;
    }

    private JTabbedPane createRightTabPanel() {
        JTabbedPane tabPane = new JTabbedPane();

        addBokningPanel = new AddBokningPanel(this);
        
        tabPane.addTab("Boka resa", addBokningPanel);
        tabPane.addTab("Registrera kund", null);

        return tabPane;
    }
    
	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel();
		ButtonListener listener = new ButtonListener();
		getTurerButton = new JButton("Visa turer");
		getKunderButton = new JButton("Visa kunder");
		registerButton = new JButton("Registrera användare");
		getTurerButton.addActionListener(listener);
		getKunderButton.addActionListener(listener);
		getPaketresorButton = new JButton("Visa paketresor");
		getPaketresorButton.addActionListener(listener);
		buttonPanel.add(getTurerButton);
		buttonPanel.add(getKunderButton);
		buttonPanel.add(getPaketresorButton);
		buttonPanel.add(registerButton);
		
		return buttonPanel;
	}

	private JPanel createTablePanel() {
		dataTable = new JTable(new String[0][0], new String[0]) {

			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
		};
		JPanel tablePanel = new JPanel();
		dataTable.setAutoCreateRowSorter(true);
		dataTable.getModel().addTableModelListener(new TableListener());
		JScrollPane paneTable = new JScrollPane(dataTable);
		paneTable.setPreferredSize(new Dimension(700, 500));
		tablePanel.add(paneTable);
		return tablePanel;
	}

    public String getCurrentSelection() {
        int row = dataTable.getSelectedRow();
        if (row >= 0)
            return (String)dataTable.getModel().getValueAt(row, 0);
        else
            return null;
    }

    public String getCurrentTable() {
        return this.currentTable;
    }
	
	
	private class ButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == getTurerButton){
				currentTable = "turer";
				
				ArrayList<Tur> trips = dbC.getTurer();
				DefaultTableModel tableModel = new DefaultTableModel(new String[0][0], Tur.getColumnNames());
				
				for(Tur t : trips) {
					tableModel.addRow(t.toString().split(","));
				}
				dataTable.setModel(tableModel);
				
			} else if (e.getSource() == getKunderButton){
                currentTable = "kunder";

				ArrayList<Kund> kunder = dbC.getKunder();
				DefaultTableModel tableModel = new DefaultTableModel(new String[0][0], Kund.getColumnNames());
				
				for(Kund k : kunder) {
					tableModel.addRow(k.toString().split(","));
				}
				dataTable.setModel(tableModel);
			} else if (e.getSource() == getPaketresorButton){
                currentTable = "paketresor";

				ArrayList<String> paketresor = dbC.getPaketresorFormatted();
				DefaultTableModel tableModel = new DefaultTableModel(new String[0][0], Paketresa.getColumnNames());
				
				for(String p : paketresor) {
					tableModel.addRow(p.split(","));
				}
				dataTable.setModel(tableModel);
			}
			
		}
		
	}
	private class TableListener implements TableModelListener{
		
		public void tableChanged(TableModelEvent e) {
			System.out.println("denna metod och print är på rad 149 i ApplicationGUI");
			
		}
		
	}
	
}
