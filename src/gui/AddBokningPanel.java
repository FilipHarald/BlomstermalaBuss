package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBokningPanel extends JPanel {

    private JButton btnGetKund = new JButton("Hämta kund");
    private JButton btnGetTur = new JButton("Hämta tur");
    private JButton btnGetDatum =  new JButton("Välj datum");
    private JButton btnBoka = new JButton("Boka");

    private JTextField txtKund = new JTextField();
    private JTextField txtTur = new JTextField();
    private JTextField txtDatum = new JTextField();

    private ApplicationGUI app;

    public AddBokningPanel(ApplicationGUI app) {
        this.app = app;

        this.setPreferredSize(new Dimension(300, 500));
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        Dimension txtSize= new Dimension(100, 20);
        txtKund.setPreferredSize(txtSize);
        txtTur.setPreferredSize(txtSize);
        txtDatum.setPreferredSize(txtSize);

        c.anchor = GridBagConstraints.NORTH;
        c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.BOTH;

        c.gridx = 0;
        c.gridy = 0;
        this.add(btnGetKund, c);

        c.gridx = 1;
        c.gridy = 0;
        this.add(txtKund, c);

        c.gridx = 0;
        c.gridy = 1;
        this.add(btnGetTur, c);

        c.gridx = 1;
        c.gridy = 1;
        this.add(txtTur, c);

        c.gridx = 0;
        c.gridy = 2;
        this.add(btnGetDatum, c);

        c.gridx = 1;
        c.gridy = 2;
        this.add(txtDatum, c);

        c.gridx = 1;
        c.gridy = 3;
        this.add(btnBoka, c);

        Listener listener = new Listener();
        btnGetKund.addActionListener(listener);
        btnGetTur.addActionListener(listener);
        btnGetDatum.addActionListener(listener);

    }

    private class Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == btnGetKund) {
                if (app.getCurrentTable().equals("kunder")) {
                    String id = app.getCurrentSelection();
                    if (id != null && id.length() > 0)
                        txtKund.setText(id);
                }
            }
        }
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new AddBokningPanel(null));
        frame.pack();
        frame.setVisible(true);
    }

}
