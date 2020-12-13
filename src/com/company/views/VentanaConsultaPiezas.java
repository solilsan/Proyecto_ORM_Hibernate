package com.company.views;

import com.company.Main;
import com.company.hibernateClass.PiezasEntity;
import com.company.swingConfig.JTextFieldConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class VentanaConsultaPiezas extends JFrame {

    private JPanel panel1;
    private List<PiezasEntity> listaPiezas;

    public VentanaConsultaPiezas(String title, int tipo) {

        if (tipo == 0) {
            add(porCodigo());
        } else if (tipo == 1) {
            add(porNombre());
        } else if (tipo == 2) {
            add(porDescripcion());
        }else {
            add(panel1);
        }

        setTitle(title);
        setBounds(100, 100, 600, 400);
        setResizable(false);

    }

    private JPanel porCodigo() {

        JPanel panel1 = new JPanel();
        panel1.setLayout(null);

        // Codigo pieza
        JLabel jlCodPieza = new JLabel("Escribe el código o parte del el:");
        jlCodPieza.setBounds(30, 30, 250, 20);
        panel1.add(jlCodPieza);
        JTextField jtCodPieza = new JTextField();
        jtCodPieza.setBounds(250, 30, 100, 20);
        panel1.add(jtCodPieza);
        jtCodPieza.setDocument(new JTextFieldConfig(6, true));

        // Boton buscar
        JButton jbBuscar = new JButton("Buscar Piezas");
        jbBuscar.setBounds(360, 30, 150, 20);
        panel1.add(jbBuscar);

        // Combo Busqueda
        JComboBox<String> combo = new JComboBox<>();
        combo.setBounds(170, 60, 200, 20);
        panel1.add(combo);

        JTextArea display = new JTextArea();
        display.setBounds(80, 100, 420, 240);
        display.setLineWrap(true);
        display.setEditable(false);
        panel1.add(display);

        jbBuscar.addActionListener(e -> {

            try {

                SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                        new StandardServiceRegistryBuilder().configure().build());

                Session session = sessionFactory.openSession();

                Query q = session.createQuery("from PiezasEntity where codigo like ?1");
                q.setParameter(1, '%' + jtCodPieza.getText() + '%');
                listaPiezas = q.list();

                combo.removeAllItems();

                if (listaPiezas.size() == 0) {
                    JOptionPane.showMessageDialog(null, "No existen piezas con esos datos.", "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    for (PiezasEntity listaPieza : listaPiezas) {
                        combo.addItem(listaPieza.getCodigo());
                    }
                }

                session.close();
                sessionFactory.close();

            } catch (PersistenceException pe) {
                String msgError;
                if (pe.getCause().toString().equalsIgnoreCase("org.hibernate.exception.JDBCConnectionException: Error calling Driver#connect")) {
                    msgError = "Error con la conexión";
                }
                else {
                    msgError = "No existen piezas";
                }
                JOptionPane.showMessageDialog(null, msgError, "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        });

        combo.addActionListener(e -> cargarDatosCombo(display, combo));

        return panel1;

    }

    private JPanel porNombre() {

        JPanel panel1 = new JPanel();
        panel1.setLayout(null);

        // Nombre pieza
        JLabel jlNombrePieza = new JLabel("Escribe el nombre o parte del el:");
        jlNombrePieza.setBounds(30, 30, 250, 20);
        panel1.add(jlNombrePieza);
        JTextField jtNombrePieza = new JTextField();
        jtNombrePieza.setBounds(250, 30, 100, 20);
        panel1.add(jtNombrePieza);

        // Boton buscar
        JButton jbBuscar = new JButton("Buscar Piezas");
        jbBuscar.setBounds(360, 30, 150, 20);
        panel1.add(jbBuscar);

        // Combo Busqueda
        JComboBox<String> combo = new JComboBox<>();
        combo.setBounds(170, 60, 200, 20);
        panel1.add(combo);

        JTextArea display = new JTextArea();
        display.setBounds(80, 100, 420, 240);
        display.setLineWrap(true);
        display.setEditable(false);
        panel1.add(display);

        jbBuscar.addActionListener(e -> {

            try {

                SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                        new StandardServiceRegistryBuilder().configure().build());

                Session session = sessionFactory.openSession();

                Query q = session.createQuery("from PiezasEntity where nombre like ?1");
                q.setParameter(1, '%' + jtNombrePieza.getText() + '%');
                listaPiezas = q.list();

                combo.removeAllItems();

                if (listaPiezas.size() == 0) {
                    JOptionPane.showMessageDialog(null, "No existen piezas con esos datos.", "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    for (PiezasEntity listaPieza : listaPiezas) {
                        combo.addItem(listaPieza.getCodigo());
                    }
                }

                session.close();
                sessionFactory.close();

            } catch (PersistenceException pe) {
                String msgError;
                if (pe.getCause().toString().equalsIgnoreCase("org.hibernate.exception.JDBCConnectionException: Error calling Driver#connect")) {
                    msgError = "Error con la conexión";
                }
                else {
                    msgError = "No existen piezas";
                }
                JOptionPane.showMessageDialog(null, msgError, "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        });

        combo.addActionListener(e -> cargarDatosCombo(display, combo));

        return panel1;

    }

    private JPanel porDescripcion() {

        JPanel panel1 = new JPanel();
        panel1.setLayout(null);

        // Codigo proveedor
        JLabel jlDirProv = new JLabel("Escribe la descripción o parte del ella:");
        jlDirProv.setBounds(30, 30, 250, 20);
        panel1.add(jlDirProv);
        JTextField jtDirProv = new JTextField();
        jtDirProv.setBounds(250, 30, 100, 20);
        panel1.add(jtDirProv);

        // Boton buscar
        JButton jbBuscar = new JButton("Buscar Piezas");
        jbBuscar.setBounds(360, 30, 150, 20);
        panel1.add(jbBuscar);

        // Combo Busqueda
        JComboBox<String> combo = new JComboBox<>();
        combo.setBounds(170, 60, 200, 20);
        panel1.add(combo);

        JTextArea display = new JTextArea();
        display.setBounds(80, 100, 420, 240);
        display.setLineWrap(true);
        display.setEditable(false);
        panel1.add(display);

        jbBuscar.addActionListener(e -> {

            try {

                SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                        new StandardServiceRegistryBuilder().configure().build());

                Session session = sessionFactory.openSession();

                Query q = session.createQuery("from PiezasEntity where descripcion like ?1");
                q.setParameter(1, '%' + jtDirProv.getText() + '%');
                listaPiezas = q.list();

                combo.removeAllItems();

                if (listaPiezas.size() == 0) {
                    JOptionPane.showMessageDialog(null, "No existen piezas con esos datos.", "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    for (PiezasEntity listaPieza : listaPiezas) {
                        combo.addItem(listaPieza.getCodigo());
                    }
                }

                session.close();
                sessionFactory.close();

            } catch (PersistenceException pe) {
                String msgError;
                if (pe.getCause().toString().equalsIgnoreCase("org.hibernate.exception.JDBCConnectionException: Error calling Driver#connect")) {
                    msgError = "Error con la conexión";
                }
                else {
                    msgError = "No existen piezas";
                }
                JOptionPane.showMessageDialog(null, msgError, "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        });

        combo.addActionListener(e -> cargarDatosCombo(display, combo));

        return panel1;

    }

    private void cargarDatosCombo(JTextArea display, JComboBox<String> combo) {

        try {

            display.setText("");
            display.removeAll();
            PiezasEntity pieza = listaPiezas.get(combo.getSelectedIndex());
            String texto = "\n     CÓDIGO :     " + pieza.getCodigo() + "";
            texto += "\n\n\n     NOMBRE :     " + pieza.getNombre() + "";
            texto += "\n\n\n     PRECIO :     " + pieza.getPrecio() + "";
            texto += "\n\n\n     DESCRIPCIÓN :     ";
            display.setText(texto);

            JTextArea jtaDes = new JTextArea();
            jtaDes.setBounds(110, 160, 300, 70);
            jtaDes.setLineWrap(true);
            jtaDes.setEditable(false);
            jtaDes.setText(pieza.getDescripcion());
            jtaDes.setBorder(new LineBorder(Color.BLACK));

            display.add(jtaDes);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

}
