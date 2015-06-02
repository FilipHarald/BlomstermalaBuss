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
	private JButton showDetailsButton;
	private JButton getBokningarButton;
    private JButton deleteRow;
	private JTable dataTable;
    private JPanel addBokningPanel;
    private JPanel addKundPanel;
    private InfoPanel infoPanel;
    private JTextField txtInfo;
    private String currentTable = "kunder";
	
	
	public ApplicationGUI(DatabaseController dbC) {
		super();
		this.dbC = dbC;
		setLayout(new BorderLayout());
		add(createTablePanel(), BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.SOUTH);
        add(createRightTabPanel(), BorderLayout.EAST);
        add(createTopPanel(), BorderLayout.NORTH);
        add(infoPanel = new InfoPanel(this), BorderLayout.WEST);
        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		pack();
		setVisible(true);
	}

    public DatabaseController getDbc() {
        return dbC;
    }

    public void setInfo(String text) {
        txtInfo.setText(text);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel();

        JLabel lblInfo = new JLabel("Info:");
        txtInfo = new JTextField();
        txtInfo.setEditable(false);
        txtInfo.setPreferredSize(new Dimension(300, 20));

        panel.add(lblInfo);
        panel.add(txtInfo);

        return panel;
    }

    private JTabbedPane createRightTabPanel() {
        JTabbedPane tabPane = new JTabbedPane();

        addBokningPanel = new AddBokningPanel(this);
        addKundPanel = new AddKundPanel(this);
        
        tabPane.addTab("Boka resa", addBokningPanel);
        tabPane.addTab("Registrera kund", addKundPanel);

        return tabPane;
    }
    
	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel();
		ButtonListener listener = new ButtonListener();
		getTurerButton = new JButton("Visa turer");
		getKunderButton = new JButton("Visa kunder");
		getBokningarButton = new JButton("Visa bokningar");
        getPaketresorButton = new JButton("Visa paketresor");
        deleteRow = new JButton("Ta bort rad");
		getBokningarButton.addActionListener(listener);
		getTurerButton.addActionListener(listener);
		getKunderButton.addActionListener(listener);
		getPaketresorButton.addActionListener(listener);
        deleteRow.addActionListener(listener);
		buttonPanel.add(getTurerButton);
		buttonPanel.add(getBokningarButton);
		buttonPanel.add(getKunderButton);
		buttonPanel.add(getPaketresorButton);
        buttonPanel.add(deleteRow);
		
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
		tablePanel.add(showDetailsButton = new JButton("<--Visa detaljer<--"));
		showDetailsButton.addActionListener(new ButtonListener());
		JScrollPane paneTable = new JScrollPane(dataTable);
		paneTable.setPreferredSize(new Dimension(700, 500));
		tablePanel.add(paneTable);
		return tablePanel;
	}

    public String getCurrentSelection() {
        int row = dataTable.getSelectedRow();
        row = dataTable.convertRowIndexToModel(row);
        if (row >= 0){
            return (String)dataTable.getModel().getValueAt(row, 0);
        } else
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
				
			} else if (e.getSource() == getBokningarButton){
				currentTable = "bokningar";
				
				ArrayList<Bokning> bookings = dbC.getBokningar();
				DefaultTableModel tableModel = new DefaultTableModel(new String[0][0], Bokning.getColumnNames());
				
				for(Bokning b : bookings) {
					tableModel.addRow(b.toString().split(","));
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
			} else if (e.getSource() == showDetailsButton) {
                switch (currentTable) {
                    case "kunder":

                        break;
                    case "paketresor":
                        infoPanel.setText(dbC.getPaketresaDetails(getCurrentSelection()));
                        break;
                    case "bokningar":
                        infoPanel.setText(dbC.getBokningDetails(Integer.parseInt(getCurrentSelection())));
                        break;
                    case "turer":

                        break;
                }
			} else if (e.getSource() == deleteRow) {
                String id = getCurrentSelection();

                String result = "";

                switch (currentTable) {
                    case "kunder":
                        result = dbC.removeKund(id);
                        break;
                    case "paketresor":
                        result = dbC.removePaketresa(id);
                        break;
                    case "bokningar":
                        result = dbC.removeBokning(Integer.parseInt(id));
                        break;
                    case "turer":
                        result = dbC.removeTur(Integer.parseInt(id));
                        break;
                }

                setInfo(result);
            }
			
		}
		
	}
//	private class TableListener implements TableModelListener{
//		
//		public void tableChanged(TableModelEvent e) {
//			System.out.println("denna metod och print är på rad 149 i ApplicationGUI");
//			
//		}
//		
//	}
	
}
