package com.company.views;

import com.company.hibernateClass.ProveedoresEntity;
import com.company.swingConfig.JTextFieldConfig;

import javax.swing.*;

public class VentanaConsultaProveedores extends JFrame {

    private JPanel panel1;

    // Tipo 0 = por codigo, 1 = por nombre y 2 = por direccion
    public VentanaConsultaProveedores(String title, int tipo) {

        if (tipo == 0) {
            add(porCodigo());
        } else {
            add(panel1);
        }

        setTitle(title);
        setBounds(100, 100, 600, 400);
        setResizable(false);

    }

    private JPanel porCodigo() {

        JPanel panel1 = new JPanel();
        panel1.setLayout(null);

        // Codigo proveedor
        JLabel jlCodProv = new JLabel("Escribe el código o parte del código:");
        jlCodProv.setBounds(30, 30, 250, 20);
        panel1.add(jlCodProv);
        JTextField jtCodProv = new JTextField();
        jtCodProv.setBounds(250, 30, 100, 20);
        panel1.add(jtCodProv);
        jtCodProv.setDocument(new JTextFieldConfig(6, true));

        // Boton buscar
        JButton jbBuscar = new JButton("Buscar Proveedor");
        jbBuscar.setBounds(360, 30, 150, 20);
        panel1.add(jbBuscar);

        JComboBox<String> combo = new JComboBox<>();
        combo.setBounds(170, 60, 200, 20);
        ProveedoresEntity prov = new ProveedoresEntity();
        prov.setCodigo("1");
        prov.setNombre("p");
        combo.addItem(prov.getCodigo());
        panel1.add(combo);

        return panel1;

    }

}
