package com.company.views;

import com.company.Main;
import com.company.hibernateClass.PiezasEntity;
import com.company.hibernateClass.ProveedoresEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class VentanaSuministrosPiezas extends JFrame {

    private JPanel panel1;

    // Combo pieza
    JComboBox<String> comboPieza = new JComboBox<>();

    JTextField jtCatProy = new JTextField();
    JTextField jtCatProv = new JTextField();
    JTextField jtCatTotal = new JTextField();

    JButton jbListado = new JButton("Ver piezas suministradas");

    private List<PiezasEntity> listaPiezas;

    public VentanaSuministrosPiezas() {

        panel1.setLayout(null);
        add(panel1);
        setTitle("Suministros por Piezas");
        setBounds(100, 100, 700, 400);
        setResizable(false);

        // Codigo pieza
        JLabel jlCodPieza = new JLabel("Pieza:");
        jlCodPieza.setBounds(40, 20, 200, 20);
        panel1.add(jlCodPieza);

        // Combo pieza
        comboPieza.setBounds(110, 20, 100, 20);
        comboPieza.addItem("Elige código");
        panel1.add(comboPieza);

        // Datos piezas
        JTextArea datosPieza = new JTextArea();
        datosPieza.setBounds(220, 20, 420, 240);
        datosPieza.setLineWrap(true);
        datosPieza.setEditable(false);
        datosPieza.setBorder(new LineBorder(Color.BLACK));
        panel1.add(datosPieza);

        // Cantidad proyectos
        JLabel jlCatProy = new JLabel("Nº Proyectos:");
        jlCatProy.setBounds(40, 80, 100, 20);
        panel1.add(jlCatProy);
        jtCatProy.setBounds(130, 80, 50, 20);
        jtCatProy.setEditable(false);
        panel1.add(jtCatProy);

        // Cantidad proveedores
        JLabel jlCatProv = new JLabel("Nº Proveedores:");
        jlCatProv.setBounds(40, 140, 100, 20);
        panel1.add(jlCatProv);
        jtCatProv.setBounds(140, 140, 50, 20);
        jtCatProv.setEditable(false);
        panel1.add(jtCatProv);

        // Cantidad total
        JLabel jlCatTotal = new JLabel("Cantidad total:");
        jlCatTotal.setBounds(40, 200, 100, 20);
        panel1.add(jlCatTotal);
        jtCatTotal.setBounds(140, 200, 70, 20);
        jtCatTotal.setEditable(false);
        panel1.add(jtCatTotal);

        // Boton listado
        jbListado.setBounds(220, 280, 200, 40);
        jbListado.setEnabled(false);
        panel1.add(jbListado);

        cargarDatos();

        comboPieza.addActionListener(e -> {
            if (comboPieza.getSelectedIndex() != 0) {

                datosPieza.removeAll();
                PiezasEntity pieza = listaPiezas.get(comboPieza.getSelectedIndex() - 1);
                String texto = "\n     CÓDIGO :     " + pieza.getCodigo() + "";
                texto += "\n\n\n     NOMBRE :     " + pieza.getNombre() + "";
                texto += "\n\n\n     PRECIO :     " + pieza.getPrecio() + "";
                texto += "\n\n\n     DESCRIPCIÓN :     ";
                datosPieza.setText(texto);

                JTextArea jtaDes = new JTextArea();
                jtaDes.setBounds(110, 160, 300, 70);
                jtaDes.setLineWrap(true);
                jtaDes.setEditable(false);
                jtaDes.setText(pieza.getDescripcion());
                jtaDes.setBorder(new LineBorder(Color.BLACK));

                datosPieza.add(jtaDes);

                SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                        new StandardServiceRegistryBuilder().configure().build());

                Session session = sessionFactory.openSession();

                Query q = session.createQuery("SELECT COUNT(DISTINCT g.codproyecto), COUNT(DISTINCT g.codproveedor), SUM(gp.cantidad) FROM GestionespiezasEntity gp, GestionesEntity g  WHERE gp.codgestion = g.codigo AND gp.codpieza = ?1");
                q.setParameter(1, pieza.getCodigo());

                Object[] datos = (Object[]) q.list().get(0);

                String cantidadProyectos = String.valueOf(datos[0]);
                String cantidadProveedores = String.valueOf(datos[1]);
                String cantidadPiezas = String.valueOf(datos[2]);

                if (!cantidadProyectos.equals("null")) {
                    jtCatProy.setText(cantidadProyectos);
                } else {
                    jtCatProy.setText("0");
                }
                if (!cantidadProveedores.equals("null")) {
                    jtCatProv.setText(cantidadProveedores);
                } else {
                    jtCatProv.setText("0");
                }
                if (!cantidadPiezas.equals("null")) {
                    jtCatTotal.setText(cantidadPiezas);
                    jbListado.setEnabled(true);
                } else {
                    jtCatTotal.setText("0");
                    jbListado.setEnabled(false);
                }

                session.close();
                sessionFactory.close();

            } else {
                datosPieza.removeAll();
                jbListado.setEnabled(false);
                datosPieza.setText("");
                jtCatProv.setText("");
                jtCatProy.setText("");
                jtCatTotal.setText("");
            }
        });

        jbListado.addActionListener(e ->{

            try {

                SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                        new StandardServiceRegistryBuilder().configure().build());

                Session session = sessionFactory.openSession();

                Query q = session.createQuery("SELECT g.codproveedor, g.codproyecto, SUM(gp.cantidad) FROM GestionespiezasEntity gp, GestionesEntity g WHERE gp.codgestion = g.codigo AND gp.codpieza = ?1 GROUP BY g.codproveedor, g.codproyecto");
                q.setParameter(1, listaPiezas.get(comboPieza.getSelectedIndex() - 1).getCodigo());

                List<Object> datos = q.list();

                VentanaListadoPiezasSuministradas vcp = new VentanaListadoPiezasSuministradas(datos, "PIEZAS");
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

            Query proveedores = session.createQuery("from PiezasEntity ");
            listaPiezas = proveedores.list();

            session.close();
            sessionFactory.close();

            if (listaPiezas.size() > 0) {
                for (PiezasEntity listaPieza : listaPiezas) {
                    comboPieza.addItem(listaPieza.getCodigo());
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

}
