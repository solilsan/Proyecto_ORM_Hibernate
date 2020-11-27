package com.company.views;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class VentanaGestionProveedores extends JFrame{
    private JPanel panel1;
    private JPanel contentPane;

    public VentanaGestionProveedores() {

        add(panel1);

        setTitle("Gestión de Proveedores");
        setResizable(false);
        setSize(600, 400);

        // título de la ventana
        setTitle("Pestau00F1as con Swing by jnj");
        // operación al cerra la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // coordenadas de las esquinas del frame en el escritorio
        setBounds(100, 100, 419, 234);

        // el panel que contiene todo se crea y se pone en el frame
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        // distribución nula para poder posicionar los elementos
        // en las coordenadas que queramos
        contentPane.setLayout(null);

        // se crea el panel de pestañas
        JTabbedPane panelDePestanas = new JTabbedPane(JTabbedPane.TOP);
        // se posiciona en el panel
        panelDePestanas.setBounds(10, 11, 383, 174);
        contentPane.add(panelDePestanas);

        // éste es el primer panel
        // que se añade como pestaña al 'tabbedPane'
        JPanel panel1 = new JPanel();
        panelDePestanas.addTab("Panel 1", null, panel1, null);
        // al panel le pongo distribución nula para
        // posicionar los elementos en las coordenadas que
        // quiera
        panel1.setLayout(null);

        // una etiqueta en el panel de la pestaña 1
        JLabel lbl1 = new JLabel("Primera pestau00F1a..");
        lbl1.setBounds(10, 11, 348, 14);
        panel1.add(lbl1);

        // otro panel de igual forma
        JPanel panel2 = new JPanel();
        panelDePestanas.addTab("Panel 2", null, panel2, null);
        panel2.setLayout(null);

        // otra etiqueta ésta vez en el segundo panel
        JLabel lbl2 = new JLabel("Segunda pestau00F1a..");
        lbl2.setBounds(10, 11, 290, 14);
        panel2.add(lbl2);

    }

}
