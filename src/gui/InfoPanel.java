package gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;

/**
 * 
 * @author Filip
 *
 */
public class InfoPanel extends JPanel{
	private ApplicationGUI appGUI;
	private JTextArea ta;

	public InfoPanel(ApplicationGUI applicationGUI) {
		super();
		setBorder(BorderFactory.createTitledBorder("Detailed information"));
		setPreferredSize(new Dimension(300,300));
		setLayout(new GridLayout(0,1));
		appGUI = applicationGUI;
		ta = new JTextArea();
		ta.setEditable(false);
		add(ta);
	}

	public void setText(String s) {
		ta.setText("");
		ta.append(s);

	}
}
