package com.company.views;

import com.company.Main;
import com.company.hibernateClass.ProveedoresEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class VentanaSuministrosProveedores extends JFrame {

    private JPanel panel1;

    private List<ProveedoresEntity> listaProveedores;

    JTextField jtCatPieza = new JTextField();
    JTextField jtCatProy = new JTextField();

    JButton jbListado = new JButton("Ver piezas suministradas");

    // Combo proveedor
    JComboBox<String> comboProv = new JComboBox<>();

    public VentanaSuministrosProveedores() {

        panel1.setLayout(null);
        add(panel1);
        setTitle("Suministros por el Proveedor");
        setBounds(100, 100, 600, 280);
        setResizable(false);

        // Codigo proveedor
        JLabel jlCodProv = new JLabel("Proveedor:");
        jlCodProv.setBounds(40, 20, 200, 20);
        panel1.add(jlCodProv);

        // Combo proveedor
        comboProv.setBounds(110, 20, 100, 20);
        comboProv.addItem("Elige código");
        panel1.add(comboProv);

        // Datos proveedor
        JTextArea datosProv = new JTextArea();
        datosProv.setBounds(220, 20, 340, 120);
        datosProv.setLineWrap(true);
        datosProv.setEditable(false);
        datosProv.setBorder(new LineBorder(Color.BLACK));
        panel1.add(datosProv);

        // Cantidad pieza
        JLabel jlCatPieza = new JLabel("Piezas Suministradas:");
        jlCatPieza.setBounds(40, 160, 200, 20);
        panel1.add(jlCatPieza);
        jtCatPieza.setBounds(180, 160, 100, 20);
        jtCatPieza.setEditable(false);
        panel1.add(jtCatPieza);

        // Cantidad proyectos
        JLabel jlCatProy = new JLabel("Proyectos:");
        jlCatProy.setBounds(40, 200, 200, 20);
        panel1.add(jlCatProy);
        jtCatProy.setBounds(180, 200, 50, 20);
        jtCatProy.setEditable(false);
        panel1.add(jtCatProy);

        // Boton listado
        jbListado.setBounds(320, 170, 200, 40);
        jbListado.setEnabled(false);
        panel1.add(jbListado);

        cargarDatos();

        comboProv.addActionListener(e -> {
            if (comboProv.getSelectedIndex() != 0) {

                ProveedoresEntity prov = listaProveedores.get(comboProv.getSelectedIndex() - 1);

                String texto = "\n  NOMBRE : " + prov.getNombre() + "";
                texto += "\n\n  APELLIDOS : " + prov.getApellidos() + "";
                texto += "\n\n  DIRECCIÓN : " + prov.getDireccion() + "\n";

                datosProv.setText(texto);

                SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                        new StandardServiceRegistryBuilder().configure().build());

                Session session = sessionFactory.openSession();

                Query q = session.createQuery("SELECT COUNT(DISTINCT g.codproyecto), SUM(gp.cantidad) FROM GestionespiezasEntity gp, GestionesEntity g WHERE gp.codgestion = g.codigo AND g.codproveedor = ?1");
                q.setParameter(1, prov.getCodigo());

                Object[] datos = (Object[]) q.list().get(0);

                String cantidadProyectos = String.valueOf(datos[0]);
                String cantidadPiezas = String.valueOf(datos[1]);

                if (!cantidadProyectos.equals("null")) {
                    jtCatProy.setText(cantidadProyectos);
                } else {
                    jtCatProy.setText("0");
                }
                if (!cantidadPiezas.equals("null")) {
                    jtCatPieza.setText(cantidadPiezas);
                    jbListado.setEnabled(true);
                } else {
                    jtCatPieza.setText("0");
                    jbListado.setEnabled(false);
                }

                session.close();
                sessionFactory.close();

            } else {
                jbListado.setEnabled(false);
                datosProv.setText("");
                jtCatProy.setText("");
                jtCatPieza.setText("");
            }
        });

        jbListado.addActionListener(e ->{

            try {

                SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                        new StandardServiceRegistryBuilder().configure().build());

                Session session = sessionFactory.openSession();

                Query q = session.createQuery("SELECT gp.codpieza, g.codproyecto, SUM(gp.cantidad) FROM GestionespiezasEntity gp, GestionesEntity g WHERE gp.codgestion = g.codigo AND g.codproveedor = ?1 GROUP BY gp.codpieza, g.codproyecto");
                q.setParameter(1, listaProveedores.get(comboProv.getSelectedIndex() - 1).getCodigo());

                List<Object> datos = q.list();

                VentanaListadoPiezasSuministradas vcp = new VentanaListadoPiezasSuministradas(datos, "PROVEEDORES");
                vcp.setLocationRelativeTo(null);
                vcp.setVisible(true);

                session.close();
                sessionFactory.close();

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        });

    }

    private void cargarDatos() {

        try {

            SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                    new StandardServiceRegistryBuilder().configure().build());

            Session session = sessionFactory.openSession();

            Query proveedores = session.createQuery("from ProveedoresEntity ");
            listaProveedores = proveedores.list();

            session.close();
            sessionFactory.close();

            if (listaProveedores.size() > 0) {
                for (ProveedoresEntity listaProv : listaProveedores) {
                    comboProv.addItem(listaProv.getCodigo());
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

}
