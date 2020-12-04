package com.company;

import com.company.views.VentanaPrincipal;
import org.hibernate.cfg.Configuration;

import javax.swing.*;

public class Main {

    public static Configuration cfg = new Configuration().configure();

    public static void main(String[] args) {

        try {

            VentanaPrincipal vn = new VentanaPrincipal();
            vn.setLocationRelativeTo(null);
            vn.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }
}
