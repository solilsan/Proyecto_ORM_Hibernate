package com.company.views;

import com.company.Main;
import com.company.hibernateClass.*;
import com.company.swingConfig.JTextFieldConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class VentanaGestionGlobalPPP extends JFrame {

    private JPanel panel1;

    JButton jbInsert = new JButton("Insertar");
    JButton jbModify = new JButton("Modificar");
    JButton jbDelete = new JButton("Eliminar");
    JButton jbListado = new JButton("Listado");

    JTextField jtCatPieza = new JTextField();

    private List<ProveedoresEntity> listaProveedores;
    private List <ProyectosEntity> listaProyetos;
    private List <PiezasEntity> listaPiezas;

    // Combo proveedor
    JComboBox<String> comboProv = new JComboBox<>();
    // Combo pieza
    JComboBox<String> comboPieza = new JComboBox<>();
    // Combo proyecto
    JComboBox<String> comboProy = new JComboBox<>();

    private int codigoGestion;

    public VentanaGestionGlobalPPP() {

        panel1.setLayout(null);
        add(panel1);
        setTitle("Gestión Global - Proveedores - Piezas - Proyectos");
        setBounds(100, 100, 600, 400);
        setResizable(false);

        // Titulo
        JLabel jlTitulo = new JLabel("Relaciones Proveedores - Piezas - Proyectos");
        jlTitulo.setBounds(150, 20, 348, 20);
        panel1.add(jlTitulo);

        // Codigo proveedor
        JLabel jlCodProv = new JLabel("Proveedor:");
        jlCodProv.setBounds(40, 60, 200, 20);
        panel1.add(jlCodProv);

        // Combo proveedor
        comboProv.setBounds(110, 60, 100, 20);
        comboProv.addItem("Elige código");
        panel1.add(comboProv);

        // Datos proveedor
        JTextArea datosProv = new JTextArea();
        datosProv.setBounds(220, 60, 340, 50);
        datosProv.setLineWrap(true);
        datosProv.setEditable(false);
        datosProv.setBorder(new LineBorder(Color.BLACK));
        panel1.add(datosProv);

        // Codigo pieza
        JLabel jlCodPieza = new JLabel("Prieza:");
        jlCodPieza.setBounds(40, 120, 200, 20);
        panel1.add(jlCodPieza);

        // Combo pieza
        comboPieza.setBounds(110, 120, 100, 20);
        comboPieza.addItem("Elige código");
        panel1.add(comboPieza);

        // Datos pieza
        JTextArea datosPieza = new JTextArea();
        datosPieza.setBounds(220, 120, 340, 50);
        datosPieza.setLineWrap(true);
        datosPieza.setEditable(false);
        datosPieza.setBorder(new LineBorder(Color.BLACK));
        panel1.add(datosPieza);

        // Codigo proyecto
        JLabel jlCodProy = new JLabel("Proyecto:");
        jlCodProy.setBounds(40, 180, 200, 20);
        panel1.add(jlCodProy);

        // Combo proyecto
        comboProy.setBounds(110, 180, 100, 20);
        comboProy.addItem("Elige código");
        panel1.add(comboProy);

        // Datos proyecto
        JTextArea datosProy = new JTextArea();
        datosProy.setBounds(220, 180, 340, 50);
        datosProy.setLineWrap(true);
        datosProy.setEditable(false);
        datosProy.setBorder(new LineBorder(Color.BLACK));
        panel1.add(datosProy);

        // Cantidad pieza
        JLabel jlCatPieza = new JLabel("Cantidad:");
        jlCatPieza.setBounds(200, 250, 200, 20);
        panel1.add(jlCatPieza);
        jtCatPieza.setBounds(260, 250, 100, 20);
        jtCatPieza.setDocument(new JTextFieldConfig(10, false));
        panel1.add(jtCatPieza);

        // Boton insertar
        jbInsert.setBounds(70, 300, 100, 40);
        jbInsert.setEnabled(false);
        panel1.add(jbInsert);

        // Boton modificar
        jbModify.setBounds(180, 300, 100, 40);
        jbModify.setEnabled(false);
        panel1.add(jbModify);

        // Boton eliminar
        jbDelete.setBounds(290, 300, 100, 40);
        jbDelete.setEnabled(false);
        panel1.add(jbDelete);

        // Boton listado
        jbListado.setBounds(400, 300, 100, 40);
        panel1.add(jbListado);

        cargarDatos();

        comboProv.addActionListener(e -> {
            comprobarDatosSeleccionados();
            if (comboProv.getSelectedIndex() != 0) {
                cargarDatosExistentes();

                ProveedoresEntity prov = listaProveedores.get(comboProv.getSelectedIndex() - 1);

                String texto = "NOMBRE : " + prov.getNombre() + "";
                texto += "\nAPELLIDOS : " + prov.getApellidos() + "";
                texto += "\nDIRECCIÓN : " + prov.getDireccion() + "";

                datosProv.setText(texto);

            } else {
                datosProv.setText("");
            }
        });

        comboProy.addActionListener(e -> {
            comprobarDatosSeleccionados();
            if (comboProy.getSelectedIndex() != 0) {
                cargarDatosExistentes();

                ProyectosEntity proy = listaProyetos.get(comboProy.getSelectedIndex() - 1);

                String texto = "NOMBRE : " + proy.getNombre() + "";
                texto += "\nCIUDAD : " + proy.getCiudad() + "";

                datosProy.setText(texto);

            } else {
                datosProy.setText("");
            }
        });

        comboPieza.addActionListener(e -> {
            comprobarDatosSeleccionados();
            if (comboPieza.getSelectedIndex() != 0) {
                cargarDatosExistentes();

                PiezasEntity pieza = listaPiezas.get(comboPieza.getSelectedIndex() - 1);

                String texto = "NOMBRE : " + pieza.getNombre() + "";
                texto += "\nPRECIO : " + pieza.getPrecio() + "";

                datosPieza.setText(texto);

            } else {
                datosPieza.setText("");
            }
        });

        jbInsert.addActionListener(e ->{

            try {

                if (!jtCatPieza.getText().isEmpty()) {

                    double nPiezas = Double.parseDouble(jtCatPieza.getText());

                    comprobarDatosSeleccionados();

                    SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                            new StandardServiceRegistryBuilder().configure().build());

                    Session session = sessionFactory.openSession();

                    Transaction tx = session.beginTransaction();

                    GestionesEntity g = new GestionesEntity();
                    g.setCodproveedor(listaProveedores.get(comboProv.getSelectedIndex() - 1).getCodigo());
                    g.setCodproyecto(listaProyetos.get(comboProy.getSelectedIndex() - 1).getCodigo());

                    session.save(g);
                    tx.commit();

                    tx = session.beginTransaction();

                    Query gestionCodigo = session.createQuery("select codigo from GestionesEntity where codproveedor = ?1 and codproyecto = ?2");
                    gestionCodigo.setParameter(1, listaProveedores.get(comboProv.getSelectedIndex() - 1).getCodigo());
                    gestionCodigo.setParameter(2, listaProyetos.get(comboProy.getSelectedIndex() - 1).getCodigo());

                    GestionespiezasEntity gp = new GestionespiezasEntity();
                    gp.setCodpieza(listaPiezas.get(comboPieza.getSelectedIndex() - 1).getCodigo());
                    gp.setCodgestion( (int) gestionCodigo.list().get(0));
                    gp.setCantidad(nPiezas);

                    session.save(gp);
                    tx.commit();

                    session.close();
                    sessionFactory.close();

                    jbInsert.setEnabled(false);
                    jbModify.setEnabled(true);
                    jbDelete.setEnabled(true);

                } else {
                    JOptionPane.showMessageDialog(null, "Falta algún dato por introducir.", "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Introduce un número en piezas.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        });

        jbModify.addActionListener(e ->{

            try {

                if (!jtCatPieza.getText().isEmpty()) {

                    double nPiezas = Double.parseDouble(jtCatPieza.getText());

                    comprobarDatosSeleccionados();
                    getCodigoGestion();

                    SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                            new StandardServiceRegistryBuilder().configure().build());

                    Session session = sessionFactory.openSession();

                    Transaction txn = session.beginTransaction();

                    String query = "UPDATE GestionespiezasEntity SET cantidad = ?1 WHERE codpieza = ?2 AND codgestion = ?3";
                    Query ejecuteQuery = session.createQuery(query);
                    ejecuteQuery.setParameter(1, nPiezas);
                    ejecuteQuery.setParameter(2, listaPiezas.get(comboPieza.getSelectedIndex() - 1).getCodigo());
                    ejecuteQuery.setParameter(3, codigoGestion);
                    ejecuteQuery.executeUpdate();

                    txn.commit();
                    session.close();
                    sessionFactory.close();

                    jbInsert.setEnabled(false);

                } else {
                    JOptionPane.showMessageDialog(null, "Falta algún dato por introducir.", "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        });

        jbDelete.addActionListener(e ->{

            try {

                comprobarDatosSeleccionados();

                String msgInputDialog = "¿Seguro que deseas eliminar esta gestión?";
                int input = JOptionPane.showConfirmDialog(null, msgInputDialog, "Eliminar", JOptionPane.YES_NO_OPTION);

                if (input == 0) {

                    SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                            new StandardServiceRegistryBuilder().configure().build());

                    Session session = sessionFactory.openSession();

                    Transaction txn = session.beginTransaction();

                    String query = "DELETE FROM GestionespiezasEntity WHERE codgestion = ?1";
                    Query ejecuteQuery = session.createQuery(query);
                    ejecuteQuery.setParameter(1, codigoGestion);
                    ejecuteQuery.executeUpdate();

                    txn.commit();

                    txn = session.beginTransaction();

                    query = "DELETE FROM GestionesEntity WHERE codigo = ?1";
                    ejecuteQuery = session.createQuery(query);
                    ejecuteQuery.setParameter(1, codigoGestion);
                    ejecuteQuery.executeUpdate();

                    txn.commit();
                    session.close();
                    sessionFactory.close();

                    cargarDatos();

                    jtCatPieza.setText("");
                    jbInsert.setEnabled(true);
                    jbModify.setEnabled(false);
                    jbDelete.setEnabled(false);

                } else {
                    jbInsert.setEnabled(false);
                }

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        });

        jbListado.addActionListener(e ->{

            try {

                SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                        new StandardServiceRegistryBuilder().configure().build());

                Session session = sessionFactory.openSession();

                Query q = session.createQuery("SELECT g.codigo, g.codproveedor, g.codproyecto, gp.codpieza, gp.cantidad FROM GestionespiezasEntity gp, GestionesEntity g WHERE gp.codgestion = g.codigo");

                List<Object> datos = q.list();

                VentanaListadoGestiones vcp = new VentanaListadoGestiones(datos);
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

            Query proyectos = session.createQuery("from ProyectosEntity ");
            listaProyetos = proyectos.list();

            Query piezas = session.createQuery("from PiezasEntity ");
            listaPiezas = piezas.list();

            session.close();
            sessionFactory.close();

            if (listaProveedores.size() > 0) {
                for (ProveedoresEntity listaProv : listaProveedores) {
                    comboProv.addItem(listaProv.getCodigo());
                }
            }

            if (listaProyetos.size() > 0) {
                for (ProyectosEntity listaProy : listaProyetos) {
                    comboProy.addItem(listaProy.getCodigo());
                }
            }

            if (listaPiezas.size() > 0) {
                for (PiezasEntity listaPieza : listaPiezas) {
                    comboPieza.addItem(listaPieza.getCodigo());
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void comprobarDatosSeleccionados() {

        if (comboPieza.getSelectedIndex() != 0 && comboProv.getSelectedIndex() != 0 && comboProy.getSelectedIndex() != 0) {
            jbInsert.setEnabled(true);
        } else {
            jtCatPieza.setText("");
            jbInsert.setEnabled(false);
            jbModify.setEnabled(false);
            jbDelete.setEnabled(false);
        }

    }

    private void cargarDatosExistentes() {

        try {

            if (comboPieza.getSelectedIndex() != 0 && comboProv.getSelectedIndex() != 0 && comboProy.getSelectedIndex() != 0) {

                SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                        new StandardServiceRegistryBuilder().configure().build());

                Session session = sessionFactory.openSession();

                Query q = session.createQuery("SELECT gp.cantidad, g.codigo FROM GestionespiezasEntity gp, GestionesEntity g WHERE gp.codgestion = g.codigo AND gp.codpieza = ?1 AND g.codproveedor = ?2 AND codproyecto = ?3");
                q.setParameter(1, listaPiezas.get(comboPieza.getSelectedIndex() - 1).getCodigo());
                q.setParameter(2, listaProveedores.get(comboProv.getSelectedIndex() - 1).getCodigo());
                q.setParameter(3, listaProyetos.get(comboProy.getSelectedIndex() - 1).getCodigo());

                Object datos[] = (Object[]) q.list().get(0);

                codigoGestion = (int) datos[1];
                jtCatPieza.setText(String.valueOf((double) datos[0]));
                jbInsert.setEnabled(false);
                jbModify.setEnabled(true);
                jbDelete.setEnabled(true);

                session.close();
                sessionFactory.close();

            }

        } catch (IndexOutOfBoundsException iobe) {
            jtCatPieza.setText("");
            jbInsert.setEnabled(true);
            jbModify.setEnabled(false);
            jbDelete.setEnabled(false);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void getCodigoGestion() {

        try {

            if (comboPieza.getSelectedIndex() != 0 && comboProv.getSelectedIndex() != 0 && comboProy.getSelectedIndex() != 0) {

                SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                        new StandardServiceRegistryBuilder().configure().build());

                Session session = sessionFactory.openSession();

                Query q = session.createQuery("SELECT g.codigo FROM GestionespiezasEntity gp, GestionesEntity g WHERE gp.codgestion = g.codigo AND gp.codpieza = ?1 AND g.codproveedor = ?2 AND codproyecto = ?3");
                q.setParameter(1, listaPiezas.get(comboPieza.getSelectedIndex() - 1).getCodigo());
                q.setParameter(2, listaProveedores.get(comboProv.getSelectedIndex() - 1).getCodigo());
                q.setParameter(3, listaProyetos.get(comboProy.getSelectedIndex() - 1).getCodigo());

                codigoGestion = (int) q.list().get(0);

                session.close();
                sessionFactory.close();

            }

        } catch (IndexOutOfBoundsException iobe) {
            jtCatPieza.setText("");
            jbInsert.setEnabled(true);
            jbModify.setEnabled(false);
            jbDelete.setEnabled(false);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

}
