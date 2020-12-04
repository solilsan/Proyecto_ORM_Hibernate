package com.company.views;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class VentanaPrincipal extends JFrame {

    private JPanel panel1;
    private JLabel back;

    public VentanaPrincipal() {

        ImageIcon imgIcon = new ImageIcon("resources/ventanaprincipalbg.png");
        back.setIcon(imgIcon);

        add(panel1);

        createMenuBar();
        setTitle("Gestión de Proyectos");
        setResizable(false);
        setSize(1024, 720);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });

    }

    private void createMenuBar() {

        var menuBar = new JMenuBar();
        var exitIcon = new ImageIcon("src/resources/exit.png"); //Icono para el boton

        var baseDatosMenu = new JMenu("Base de Datos"); //Nombre del menu
        baseDatosMenu.setMnemonic(KeyEvent.VK_F); //Acceder al menu mediante el teclado alt+f

        var baseDatosMenuItem = new JMenuItem("Exit", exitIcon); //Nombre del boton
        baseDatosMenuItem.setMnemonic(KeyEvent.VK_E); //Acceder al menu mediante el teclado alt+f
        baseDatosMenuItem.setToolTipText("Exit application"); //Hover del boton
        baseDatosMenuItem.addActionListener((event) -> System.exit(0)); //Funcion que ejecuta el boton

        baseDatosMenu.add(baseDatosMenuItem);
        menuBar.add(baseDatosMenu);

        var proveedoresMenu = new JMenu("Base de Datos");
        menuBar.add(proveedoresMenu);

        var proveedoresGestionItem = new JMenuItem("Gestión de Proveedores");
        proveedoresGestionItem.addActionListener(
                (event) -> {
                    VentanaGestionProveedores vgp = new VentanaGestionProveedores();
                    vgp.setLocationRelativeTo(null);
                    vgp.setVisible(true);
                }
        );

        var proveedoresConsultaMenu = new JMenu("Consulta de Proveedores");
        var proveedoresConsultaMenuPorCodigo = new JMenuItem("Por Código");
        proveedoresConsultaMenuPorCodigo.addActionListener(
                (event) -> {
                    VentanaConsultaProveedores vcp = new VentanaConsultaProveedores("Consulta de Proveedores por Código", 0);
                    vcp.setLocationRelativeTo(null);
                    vcp.setVisible(true);
                }
        );
        var proveedoresConsultaMenuPorNombre = new JMenuItem("Por Nombre");
        proveedoresConsultaMenuPorNombre.addActionListener(
                (event) -> {
                    VentanaConsultaProveedores vcp = new VentanaConsultaProveedores("Consulta de Proveedores por Nombre", 1);
                    vcp.setLocationRelativeTo(null);
                    vcp.setVisible(true);
                }
        );
        var proveedoresConsultaMenuPorDireccion = new JMenuItem("Por Dirección");
        proveedoresConsultaMenuPorDireccion.addActionListener(
                (event) -> {
                    VentanaConsultaProveedores vcp = new VentanaConsultaProveedores("Consulta de Proveedores por Dirección", 2);
                    vcp.setLocationRelativeTo(null);
                    vcp.setVisible(true);
                }
        );

        proveedoresMenu.add(proveedoresGestionItem);
        proveedoresMenu.add(proveedoresConsultaMenu);
        proveedoresConsultaMenu.add(proveedoresConsultaMenuPorCodigo);
        proveedoresConsultaMenu.add(proveedoresConsultaMenuPorNombre);
        proveedoresConsultaMenu.add(proveedoresConsultaMenuPorDireccion);

        setJMenuBar(menuBar);
    }

}
