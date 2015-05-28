package gui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import application.models.Paketresa;
import application.models.Tur;

import com.toedter.calendar.DateUtil;
import com.toedter.calendar.IDateEvaluator;
import com.toedter.calendar.JDateChooser;

public class AddBokningPanel extends JPanel {

    private JButton btnGetKund = new JButton("Hämta kund");
    private JButton btnGetTur = new JButton("Hämta tur");
    private JButton btnGetDatum =  new JButton("Välj datum");
    private JButton btnBoka = new JButton("Boka");

    private JTextField txtKund = new JTextField();
    private JTextField txtTur = new JTextField();

    private JLabel lblDate = new JLabel("Datum:", JLabel.RIGHT);

    private JDateChooser jdcDate = new JDateChooser();
    private IDateEvaluator currentEvaluator;

    private ArrayList<Integer> turer = new ArrayList<Integer>();
    private boolean isPaketBokning = false;

    private ApplicationGUI app;

    public AddBokningPanel(ApplicationGUI app) {
        this.app = app;

        this.setPreferredSize(new Dimension(300, 500));
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        Dimension txtSize= new Dimension(100, 20);
        txtKund.setPreferredSize(txtSize);
        txtTur.setPreferredSize(txtSize);

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
        this.add(lblDate, c);

        c.gridx = 1;
        c.gridy = 2;
        this.add(jdcDate, c);

        c.gridx = 1;
        c.gridy = 3;
        this.add(btnBoka, c);

        Listener listener = new Listener();
        btnGetKund.addActionListener(listener);
        btnGetTur.addActionListener(listener);
        btnGetDatum.addActionListener(listener);
        btnBoka.addActionListener(listener);

        Date start = new Date();
        Date end = new Date();
        try {
            end = new SimpleDateFormat("yyyy-MM-dd").parse("2015-12-31");
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        jdcDate.setLocale(new Locale("sv", "SWE"));
        jdcDate.getJCalendar().setSelectableDateRange(start, end);
        jdcDate.setEnabled(false);

    }

    private class DateEvaluator implements IDateEvaluator {

        private DateUtil dateUtil = new DateUtil();
        private Calendar calendar = Calendar.getInstance();

        private int dayOfWeek;

        public DateEvaluator(int dayOfWeek) {
        	calendar.setFirstDayOfWeek(Calendar.MONDAY);
            this.dayOfWeek = dayOfWeek;
        }

        @Override
        public boolean isSpecial(Date date) {
            return false;
        }

        @Override
        public Color getSpecialForegroundColor() {
            return Color.MAGENTA;
        }

        @Override
        public Color getSpecialBackroundColor() {
            return null;
        }

        @Override
        public String getSpecialTooltip() {
            return null;
        }

        @Override
        public boolean isInvalid(Date date) {
            calendar.setTime(date);
            int day = calendar.get(Calendar.DAY_OF_WEEK);

            return (dayOfWeek != day);
        }

        @Override
        public Color getInvalidForegroundColor() {
            return null;
        }

        @Override
        public Color getInvalidBackroundColor() {
            return null;
        }

        @Override
        public String getInvalidTooltip() {
            return null;
        }
    };

    private class Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == btnGetKund) {
                if (app.getCurrentTable().equals("kunder")) {
                    String id = app.getCurrentSelection();
                    if (id != null && id.length() > 0)
                        txtKund.setText(id);
                }
            } else if (e.getSource() == btnGetTur) {
                if (app.getCurrentTable().equals("turer")) {
                    isPaketBokning = false;
                    turer.clear();

                    String id = app.getCurrentSelection();

                    if (id != null && id.length() > 0) {
                        txtTur.setText(id);

                        int turId = Integer.parseInt(id);

                        Tur tur = app.getDbc().getTur(turId);

                        jdcDate.setEnabled(true);
                        if (currentEvaluator != null) 
                        	jdcDate.getJCalendar().getDayChooser().removeDateEvaluator(currentEvaluator);
                        currentEvaluator = new DateEvaluator(tur.getAvresedag()+1);
                        jdcDate.getJCalendar().getDayChooser().addDateEvaluator(currentEvaluator);

                        turer.add(tur.getId());
                    }

                } else if (app.getCurrentTable().equals("paketresor")) {
                    isPaketBokning = true;
                    String id = app.getCurrentSelection();
                    turer.clear();

                    if (id != null && id.length() > 0) {
                        txtTur.setText(id);

                        ArrayList<Paketresa> paketresa_turer = app.getDbc().getPaketresaTurer(id);

                        Tur first = app.getDbc().getTur(paketresa_turer.get(0).getTur());

                        jdcDate.setEnabled(true);
                        if (currentEvaluator != null) 
                        	jdcDate.getJCalendar().getDayChooser().removeDateEvaluator(currentEvaluator);
                        currentEvaluator = new DateEvaluator(first.getAvresedag());
                        jdcDate.getJCalendar().getDayChooser().addDateEvaluator(currentEvaluator);

                        for (Paketresa tur : paketresa_turer) {
                            turer.add(tur.getTur());
                        }
                    }
                }

            } else if (e.getSource() == btnBoka) {

                if (isPaketBokning) {
                    app.getDbc().addPaketBokning(txtKund.getText(), jdcDate.getDate(), txtTur.getText());
                } else {
                    int turId = Integer.parseInt(txtTur.getText());

                    app.getDbc().addTurBokning(txtKund.getText(), jdcDate.getDate(), turId);
                }
            }
        }
    }

}
