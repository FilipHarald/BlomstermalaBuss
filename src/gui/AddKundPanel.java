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
	
	private JTextField txtSocialNumber;
	private JTextField txtPassword;
	private JTextField txtName;
	private JTextField txtMail;
	private JTextField txtAddress;
	private JTextField txtTel;
	
	private JLabel lblSocialNumber;
	private JLabel lblPassword;
	private JLabel lblName;
	private JLabel lblMail;
	private JLabel lblAddress;
	private JLabel lblTel;
	
	private ApplicationGUI app;
	
	public AddKundPanel () {
		setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(500,400));
		kundPanelTextFields();
		kundPanelLabels();
		addButtonPanel();
		btnSaveCustomer.addActionListener(this);
	}
	
	public void kundPanelTextFields (){
		JPanel kundPanelTextFields = new JPanel(new GridLayout(6,10));
		kundPanelTextFields.setPreferredSize(new Dimension(200, 100));
		
		txtSocialNumber = new JTextField();
		txtPassword = new JTextField();
		txtName = new JTextField();
		txtMail = new JTextField();
		txtAddress = new JTextField();
		txtTel = new JTextField();
		
		kundPanelTextFields.add(txtSocialNumber);
		kundPanelTextFields.add(txtName);
		kundPanelTextFields.add(txtTel);
		kundPanelTextFields.add(txtMail);
		kundPanelTextFields.add(txtPassword);
		kundPanelTextFields.add(txtAddress);
		
		add(kundPanelTextFields, BorderLayout.CENTER);
	}
	
	public void kundPanelLabels (){
		JPanel kundPanelLabels = new JPanel(new GridLayout(6,10));
		kundPanelLabels.setPreferredSize(new Dimension(150, 100));
		
		lblSocialNumber = new JLabel ("Personnummer");
		lblPassword = new JLabel ("Lösenord");
		lblName = new JLabel("Namn");
		lblMail = new JLabel ("Mail");
		lblAddress = new JLabel ("Adress");
		lblTel = new JLabel ("Telefon nummer");
		
		kundPanelLabels.add(lblSocialNumber);
		kundPanelLabels.add(lblName);
		kundPanelLabels.add(lblMail);
		kundPanelLabels.add(lblTel);
		kundPanelLabels.add(lblPassword);
		kundPanelLabels.add(lblAddress);
		
		add(kundPanelLabels, BorderLayout.WEST);
	}
	
	public void addButtonPanel (){
		JPanel labelBtnPanel = new JPanel();
		labelBtnPanel.setPreferredSize(new Dimension(50, 150));
		
		labelBtnPanel.add(btnSaveCustomer);
		
		add(labelBtnPanel, BorderLayout.SOUTH);
	}
	
	public void actionPerformed(ActionEvent e){
			if (e.getSource() == btnSaveCustomer){
				app.getDbc().addKund(txtSocialNumber.getText(),txtName.getText(), txtMail.getText(), txtAddress.getText(),
						txtPassword.getText(), txtTel.getText());
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
