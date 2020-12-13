package com.company.views;

import com.company.Main;
import com.company.hibernateClass.PiezasEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import javax.swing.*;
import java.util.List;

public class VentanaEstadisticas extends JFrame {

    private JPanel panel1;

    // Piezas mas suministradas
    JTextField jtPiezaSumiCodigo = new JTextField();
    JTextField jtPiezaSumiMax = new JTextField();

    // Piezas mas suministradas en proyecto
    JTextField jtPiezaSumiProy = new JTextField();
    JTextField jtPiezaSumiMaxProy = new JTextField();

    // Proveedor suministrado a mas proyectos
    JTextField jtProvSumiCodigo = new JTextField();
    JTextField jtProvSumiMax = new JTextField();

    // Proveedor suministrado mas piezas
    JTextField jtProvSumiCodigoP = new JTextField();
    JTextField jtProvSumiMaxP = new JTextField();

    // Proyecto con mas piezas
    JTextField jtProyMaxProy = new JTextField();
    JTextField jtProyMax = new JTextField();

    public VentanaEstadisticas() {

        panel1.setLayout(null);
        add(panel1);
        setTitle("Estadísticas");
        setBounds(100, 100, 600, 400);
        setResizable(false);

        // una etiqueta en el panel de la pestaña 1
        JLabel jlTitulo = new JLabel("RESUMEN ESTADÍSTICAS - PIEZAS. PROYECTOS Y PROVEEDORES");
        jlTitulo.setBounds(100, 20, 400, 20);
        panel1.add(jlTitulo);

        // Boton Nº de piezas y cantidad de piezas suministradas en proyectos
        JButton jbListado1 = new JButton("Nº de piezas y cantidad de piezas suministradas en proyectos");
        jbListado1.setBounds(90, 50, 400, 30);
        panel1.add(jbListado1);

        // Piezas mas suministradas
        JLabel jlPiezaSumiMax = new JLabel("Pieza de la que se ha suministrado mas cantidad:");
        jlPiezaSumiMax.setBounds(80, 90, 300, 20);
        panel1.add(jlPiezaSumiMax);
        jtPiezaSumiCodigo.setBounds(380, 90, 50, 20);
        jtPiezaSumiCodigo.setEditable(false);
        panel1.add(jtPiezaSumiCodigo);
        jtPiezaSumiMax.setBounds(440, 90, 70, 20);
        jtPiezaSumiMax.setEditable(false);
        panel1.add(jtPiezaSumiMax);

        // Piezas mas suministradas en proyecto
        JLabel jlPiezaSumiMaxProy = new JLabel("Pieza que se a suministrado a mas proyectos:");
        jlPiezaSumiMaxProy.setBounds(80, 120, 300, 20);
        panel1.add(jlPiezaSumiMaxProy);
        jtPiezaSumiProy.setBounds(380, 120, 50, 20);
        jtPiezaSumiProy.setEditable(false);
        panel1.add(jtPiezaSumiProy);
        jtPiezaSumiMaxProy.setBounds(440, 120, 50, 20);
        jtPiezaSumiMaxProy.setEditable(false);
        panel1.add(jtPiezaSumiMaxProy);

        // Boton Nº de piezas y cantidad de piezas suministradas por proveedor
        JButton jbListado2 = new JButton("Nº de piezas y cantidad de piezas suministradas por proveedor");
        jbListado2.setBounds(90, 180, 400, 30);
        panel1.add(jbListado2);

        // Proveedor suministrado a mas proyectos
        JLabel jlProvSumiMax = new JLabel("Proveedor que ha suministrado a mas proyectos:");
        jlProvSumiMax.setBounds(80, 220, 300, 20);
        panel1.add(jlProvSumiMax);
        jtProvSumiCodigo.setBounds(380, 220, 50, 20);
        jtProvSumiCodigo.setEditable(false);
        panel1.add(jtProvSumiCodigo);
        jtProvSumiMax.setBounds(440, 220, 70, 20);
        jtProvSumiMax.setEditable(false);
        panel1.add(jtProvSumiMax);

        // Proveedor suministrado mas piezas
        JLabel jlProvSumiMaxP = new JLabel("Proveedor que ha suministrado mas piezas:");
        jlProvSumiMaxP.setBounds(80, 250, 300, 20);
        panel1.add(jlProvSumiMaxP);
        jtProvSumiCodigoP.setBounds(380, 250, 50, 20);
        jtProvSumiCodigoP.setEditable(false);
        panel1.add(jtProvSumiCodigoP);
        jtProvSumiMaxP.setBounds(440, 250, 70, 20);
        jtProvSumiMaxP.setEditable(false);
        panel1.add(jtProvSumiMaxP);

        // Proyecto con mas piezas
        JLabel jlProyMax = new JLabel("Proyecto con mas piezas:");
        jlProyMax.setBounds(80, 280, 300, 20);
        panel1.add(jlProyMax);
        jtProyMaxProy.setBounds(380, 280, 50, 20);
        jtProyMaxProy.setEditable(false);
        panel1.add(jtProyMaxProy);
        jtProyMax.setBounds(440, 280, 70, 20);
        jtProyMax.setEditable(false);
        panel1.add(jtProyMax);

        cargarDatos();

        jbListado1.addActionListener(e ->{

            try {

                SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                        new StandardServiceRegistryBuilder().configure().build());

                Session session = sessionFactory.openSession();

                Query q = session.createQuery("SELECT g.codproyecto, p.nombre, p.ciudad, SUM(gp.cantidad) AS suma FROM GestionespiezasEntity gp, GestionesEntity g, ProyectosEntity p WHERE gp.codgestion = g.codigo AND g.codproyecto = p.codigo GROUP BY g.codproyecto");

                List<Object> datos = q.list();

                VentanaListadoPiezasSuministradas vcp = new VentanaListadoPiezasSuministradas(datos, "PROYECTOS");
                vcp.setLocationRelativeTo(null);
                vcp.setVisible(true);

                session.close();
                sessionFactory.close();

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        });

        jbListado2.addActionListener(e ->{

            try {

                SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                        new StandardServiceRegistryBuilder().configure().build());

                Session session = sessionFactory.openSession();

                Query q = session.createQuery("SELECT g.codproveedor, p.nombre, p.apellidos, SUM(gp.cantidad) AS suma FROM GestionespiezasEntity gp, GestionesEntity g, ProveedoresEntity p WHERE gp.codgestion = g.codigo AND g.codproveedor = p.codigo GROUP BY g.codproveedor");

                List<Object> datos = q.list();

                VentanaListadoPiezasSuministradas vcp = new VentanaListadoPiezasSuministradas(datos, "PROVEEDORESP");
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

            Query q = session.createQuery("SELECT gp.codpieza, SUM(gp.cantidad) AS suma FROM GestionespiezasEntity gp, GestionesEntity g WHERE gp.codgestion = g.codigo GROUP BY gp.codpieza");
            List datos = q.list();

            String piezaSumiCodigo = "";
            double piezaSumiMax = 0;
            for (Object o : datos) {
                Object[] dato = (Object[]) o;
                if ( (Double) dato[1] > piezaSumiMax) {
                    piezaSumiCodigo = (String) dato[0];
                    piezaSumiMax = (Double) dato[1];
                }
            }

            jtPiezaSumiCodigo.setText(piezaSumiCodigo);
            jtPiezaSumiMax.setText(String.valueOf(piezaSumiMax));

            q = session.createQuery("SELECT gp.codpieza, COUNT(DISTINCT g.codproyecto) FROM GestionespiezasEntity gp, GestionesEntity g WHERE gp.codgestion = g.codigo GROUP BY gp.codpieza");
            datos = q.list();

            String piezaSumiProy = "";
            Long piezaSumiMaxProy = 0L;
            for (Object o : datos) {
                Object[] dato = (Object[]) o;
                if ( (Long) dato[1] > piezaSumiMaxProy) {
                    piezaSumiProy = (String) dato[0];
                    piezaSumiMaxProy = (Long) dato[1];
                }
            }

            jtPiezaSumiProy.setText(piezaSumiProy);
            jtPiezaSumiMaxProy.setText(String.valueOf(piezaSumiMaxProy));

            q = session.createQuery("SELECT g.codproveedor, COUNT(DISTINCT g.codproyecto) FROM GestionespiezasEntity gp, GestionesEntity g WHERE gp.codgestion = g.codigo GROUP BY g.codproveedor");
            datos = q.list();

            String provSumiProy = "";
            Long provSumiMaxProy = 0L;
            for (Object o : datos) {
                Object[] dato = (Object[]) o;
                if ( (Long) dato[1] > provSumiMaxProy) {
                    provSumiProy = (String) dato[0];
                    provSumiMaxProy = (Long) dato[1];
                }
            }

            jtProvSumiCodigo.setText(provSumiProy);
            jtProvSumiMax.setText(String.valueOf(provSumiMaxProy));

            q = session.createQuery("SELECT g.codproveedor, SUM(gp.cantidad) AS suma FROM GestionespiezasEntity gp, GestionesEntity g WHERE gp.codgestion = g.codigo GROUP BY g.codproveedor");
            datos = q.list();

            String provSumiProyP = "";
            double provSumiMaxProyP = 0;
            for (Object o : datos) {
                Object[] dato = (Object[]) o;
                if ( (Double) dato[1] > provSumiMaxProyP) {
                    provSumiProyP = (String) dato[0];
                    provSumiMaxProyP = (Double) dato[1];
                }
            }

            jtProvSumiCodigoP.setText(provSumiProyP);
            jtProvSumiMaxP.setText(String.valueOf(provSumiMaxProyP));

            q = session.createQuery("SELECT g.codproyecto, SUM(gp.cantidad) AS suma FROM GestionespiezasEntity gp, GestionesEntity g WHERE gp.codgestion = g.codigo GROUP BY g.codproyecto");
            datos = q.list();

            String proyMaxProy = "";
            double proyMax = 0;
            for (Object o : datos) {
                Object[] dato = (Object[]) o;
                if ( (Double) dato[1] > proyMax) {
                    proyMaxProy = (String) dato[0];
                    proyMax = (Double) dato[1];
                }
            }

            jtProyMaxProy.setText(proyMaxProy);
            jtProyMax.setText(String.valueOf(proyMax));

            session.close();
            sessionFactory.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

}
