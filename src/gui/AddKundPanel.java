package gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 * @author Klein
 *
 */
public class AddKundPanel extends JPanel implements ActionListener{

	private JButton btnSaveCustomer = new JButton("Spara");
	
	private JTextField txtName;
	private JTextField txtMail;
	private JTextField txtAddress;
	private JTextField txtTel;
	
	private JLabel lblName;
	private JLabel lblMail;
	private JLabel lblAddress;
	private JLabel lblTel;
	
	public AddKundPanel () {
		setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(400,300));
		kundPanelTextFields();
		kundPanelLabels();
		addButtonPanel();
		btnSaveCustomer.addActionListener(this);
	}
	
	public void kundPanelTextFields (){
		JPanel kundPanelTextFields = new JPanel(new GridLayout(6,10));
		kundPanelTextFields.setPreferredSize(new Dimension(200, 100));
		
		txtName = new JTextField();
		txtMail = new JTextField();
		txtAddress = new JTextField();
		txtTel = new JTextField();
		
		kundPanelTextFields.add(txtName);
		kundPanelTextFields.add(txtMail);
		kundPanelTextFields.add(txtAddress);
		kundPanelTextFields.add(txtTel);
		
		add(kundPanelTextFields, BorderLayout.CENTER);
	}
	
	public void kundPanelLabels (){
		JPanel kundPanelLabels = new JPanel(new GridLayout(6,10));
		kundPanelLabels.setPreferredSize(new Dimension(150, 100));
		
		lblName = new JLabel("Namn");
		lblMail = new JLabel ("Mail");
		lblAddress = new JLabel ("Adress");
		lblTel = new JLabel ("Telefon nummer");
		
		kundPanelLabels.add(lblName);
		kundPanelLabels.add(lblMail);
		kundPanelLabels.add(lblAddress);
		kundPanelLabels.add(lblTel);
		
		add(kundPanelLabels, BorderLayout.WEST);
	}
	
	public void addButtonPanel (){
		JPanel labelBtnPanel = new JPanel();
		labelBtnPanel.setPreferredSize(new Dimension(50, 50));
		
		labelBtnPanel.add(btnSaveCustomer);
		
		add(labelBtnPanel, BorderLayout.SOUTH);
	}
	
	public void actionPerformed(ActionEvent e){
			if (e.getSource() == btnSaveCustomer){
				//Spara till tabell kund
			}
			
		}
		
	  public static void main(String[] args) {
	        JFrame frame = new JFrame();
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.add(new AddKundPanel());
	        frame.pack();
	        frame.setVisible(true);
	    }
	
	
	
}
