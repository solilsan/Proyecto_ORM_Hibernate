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

        // Menñu proveedores
        var proveedoresMenu = new JMenu("Proveedores");
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

        // Menú piezas
        var piezasMenu = new JMenu("Piezas");
        menuBar.add(piezasMenu);

        var piezasGestionItem = new JMenuItem("Gestión de Piezas");
        piezasGestionItem.addActionListener(
                (event) -> {
                    VentanaGestionPiezas vgp = new VentanaGestionPiezas();
                    vgp.setLocationRelativeTo(null);
                    vgp.setVisible(true);
                }
        );

        var piezasConsultaMenu = new JMenu("Consulta de Piezas");
        var piezasConsultaMenuPorCodigo = new JMenuItem("Por Código");
        piezasConsultaMenuPorCodigo.addActionListener(
                (event) -> {
                    VentanaConsultaPiezas vcp = new VentanaConsultaPiezas("Consulta de Piezas por Código", 0);
                    vcp.setLocationRelativeTo(null);
                    vcp.setVisible(true);
                }
        );
        var piezasConsultaMenuPorNombre = new JMenuItem("Por Nombre");
        piezasConsultaMenuPorNombre.addActionListener(
                (event) -> {
                    VentanaConsultaPiezas vcp = new VentanaConsultaPiezas("Consulta de Piezas por Nombre", 1);
                    vcp.setLocationRelativeTo(null);
                    vcp.setVisible(true);
                }
        );
        var piezasConsultaMenuPorDescript = new JMenuItem("Por Descripción");
        piezasConsultaMenuPorDescript.addActionListener(
                (event) -> {
                    VentanaConsultaPiezas vcp = new VentanaConsultaPiezas("Consulta de Piezas por Descripción", 2);
                    vcp.setLocationRelativeTo(null);
                    vcp.setVisible(true);
                }
        );

        piezasMenu.add(piezasGestionItem);
        piezasMenu.add(piezasConsultaMenu);
        piezasConsultaMenu.add(piezasConsultaMenuPorCodigo);
        piezasConsultaMenu.add(piezasConsultaMenuPorNombre);
        piezasConsultaMenu.add(piezasConsultaMenuPorDescript);

        // Menú proyectos
        var proyectosMenu = new JMenu("Proyectos");
        menuBar.add(proyectosMenu);

        var proyectosGestionItem = new JMenuItem("Gestión de Proyectos");
        proyectosGestionItem.addActionListener(
                (event) -> {
                    VentanaGestionProyectos vgp = new VentanaGestionProyectos();
                    vgp.setLocationRelativeTo(null);
                    vgp.setVisible(true);
                }
        );

        var proyectosConsultaMenu = new JMenu("Consulta de Proyectos");
        var proyectosConsultaMenuPorCodigo = new JMenuItem("Por Código");
        proyectosConsultaMenuPorCodigo.addActionListener(
                (event) -> {
                    VentanaConsultaProyectos vcp = new VentanaConsultaProyectos("Consulta de Proyectos por Código", 0);
                    vcp.setLocationRelativeTo(null);
                    vcp.setVisible(true);
                }
        );
        var proyectosConsultaMenuPorNombre = new JMenuItem("Por Nombre");
        proyectosConsultaMenuPorNombre.addActionListener(
                (event) -> {
                    VentanaConsultaProyectos vcp = new VentanaConsultaProyectos("Consulta de Proyectos por Nombre", 1);
                    vcp.setLocationRelativeTo(null);
                    vcp.setVisible(true);
                }
        );
        var proyectosConsultaMenuPorDescript = new JMenuItem("Por Ciudad");
        proyectosConsultaMenuPorDescript.addActionListener(
                (event) -> {
                    VentanaConsultaProyectos vcp = new VentanaConsultaProyectos("Consulta de Proyectos por Ciudad", 2);
                    vcp.setLocationRelativeTo(null);
                    vcp.setVisible(true);
                }
        );

        proyectosMenu.add(proyectosGestionItem);
        proyectosMenu.add(proyectosConsultaMenu);
        proyectosConsultaMenu.add(proyectosConsultaMenuPorCodigo);
        proyectosConsultaMenu.add(proyectosConsultaMenuPorNombre);
        proyectosConsultaMenu.add(proyectosConsultaMenuPorDescript);

        // Menú global
        var globalMenu = new JMenu("Gestión Global");
        menuBar.add(globalMenu);

        var globalGestionItem = new JMenuItem("Piezas, Proveedores y Proyectos");
        globalGestionItem.addActionListener(
                (event) -> {
                    VentanaGestionGlobalPPP vgp = new VentanaGestionGlobalPPP();
                    vgp.setLocationRelativeTo(null);
                    vgp.setVisible(true);
                }
        );

        var suministrosProvGestionItem = new JMenuItem("Suministros por Proveedor");
        suministrosProvGestionItem.addActionListener(
                (event) -> {
                    VentanaSuministrosProveedores vgp = new VentanaSuministrosProveedores();
                    vgp.setLocationRelativeTo(null);
                    vgp.setVisible(true);
                }
        );

        var suministrosPiezaGestionItem = new JMenuItem("Suministros por Piezas");
        suministrosPiezaGestionItem.addActionListener(
                (event) -> {
                    VentanaSuministrosPiezas vgp = new VentanaSuministrosPiezas();
                    vgp.setLocationRelativeTo(null);
                    vgp.setVisible(true);
                }
        );

        globalMenu.add(globalGestionItem);
        globalMenu.add(suministrosProvGestionItem);
        globalMenu.add(suministrosPiezaGestionItem);

        setJMenuBar(menuBar);
    }

}
