package com.company.views;

import com.company.Main;
import com.company.hibernateClass.ProveedoresEntity;
import com.company.swingConfig.JTextFieldConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import javax.swing.*;
import java.util.List;

public class VentanaConsultaProveedores extends JFrame {

    private JPanel panel1;
    private List<ProveedoresEntity> listaProveedores;

    // Tipo 0 = por codigo, 1 = por nombre y 2 = por direccion
    public VentanaConsultaProveedores(String title, int tipo) {

        if (tipo == 0) {
            add(porCodigo());
        } else if (tipo == 1) {
            add(porNombre());
        } else if (tipo == 2) {
            add(porDireccion());
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

        // Codigo proveedor
        JLabel jlCodProv = new JLabel("Escribe el código o parte del el:");
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

        // Combo Busqueda
        JComboBox<String> combo = new JComboBox<>();
        combo.setBounds(170, 60, 200, 20);
        panel1.add(combo);

        JTextArea display = new JTextArea();
        display.setBounds(90, 100, 400, 200);
        display.setEditable(false);
        panel1.add(display);

        jbBuscar.addActionListener(e -> {

            try {

                SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                        new StandardServiceRegistryBuilder().configure().build());

                Session session = sessionFactory.openSession();

                Query q = session.createQuery("from ProveedoresEntity where codigo like ?1");
                q.setParameter(1, '%' + jtCodProv.getText() + '%');
                listaProveedores = q.list();

                combo.removeAllItems();

                if (listaProveedores.size() == 0) {
                    JOptionPane.showMessageDialog(null, "No existen proveedores con esos datos.", "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    for (ProveedoresEntity listaProv : listaProveedores) {
                        combo.addItem(listaProv.getCodigo());
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
                    msgError = "No existen proveedores";
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

        // Codigo proveedor
        JLabel jlNombreProv = new JLabel("Escribe el nombre o parte del el:");
        jlNombreProv.setBounds(30, 30, 250, 20);
        panel1.add(jlNombreProv);
        JTextField jtNombreProv = new JTextField();
        jtNombreProv.setBounds(250, 30, 100, 20);
        panel1.add(jtNombreProv);

        // Boton buscar
        JButton jbBuscar = new JButton("Buscar Proveedor");
        jbBuscar.setBounds(360, 30, 150, 20);
        panel1.add(jbBuscar);

        // Combo Busqueda
        JComboBox<String> combo = new JComboBox<>();
        combo.setBounds(170, 60, 200, 20);
        panel1.add(combo);

        JTextArea display = new JTextArea();
        display.setBounds(90, 100, 400, 200);
        display.setEditable(false);
        panel1.add(display);

        jbBuscar.addActionListener(e -> {

            try {

                SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                        new StandardServiceRegistryBuilder().configure().build());

                Session session = sessionFactory.openSession();

                Query q = session.createQuery("from ProveedoresEntity where nombre like ?1");
                q.setParameter(1, '%' + jtNombreProv.getText() + '%');
                listaProveedores = q.list();

                combo.removeAllItems();

                if (listaProveedores.size() == 0) {
                    JOptionPane.showMessageDialog(null, "No existen proveedores con esos datos.", "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    for (ProveedoresEntity listaProv : listaProveedores) {
                        combo.addItem(listaProv.getCodigo());
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
                    msgError = "No existen proveedores";
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

    private JPanel porDireccion() {

        JPanel panel1 = new JPanel();
        panel1.setLayout(null);

        // Codigo proveedor
        JLabel jlDirProv = new JLabel("Escribe la dirección o parte del ella:");
        jlDirProv.setBounds(30, 30, 250, 20);
        panel1.add(jlDirProv);
        JTextField jtDirProv = new JTextField();
        jtDirProv.setBounds(250, 30, 100, 20);
        panel1.add(jtDirProv);

        // Boton buscar
        JButton jbBuscar = new JButton("Buscar Proveedor");
        jbBuscar.setBounds(360, 30, 150, 20);
        panel1.add(jbBuscar);

        // Combo Busqueda
        JComboBox<String> combo = new JComboBox<>();
        combo.setBounds(170, 60, 200, 20);
        panel1.add(combo);

        JTextArea display = new JTextArea();
        display.setBounds(90, 100, 400, 200);
        display.setEditable(false);
        panel1.add(display);

        jbBuscar.addActionListener(e -> {

            try {

                SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                        new StandardServiceRegistryBuilder().configure().build());

                Session session = sessionFactory.openSession();

                Query q = session.createQuery("from ProveedoresEntity where direccion like ?1");
                q.setParameter(1, '%' + jtDirProv.getText() + '%');
                listaProveedores = q.list();

                combo.removeAllItems();

                if (listaProveedores.size() == 0) {
                    JOptionPane.showMessageDialog(null, "No existen proveedores con esos datos.", "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    for (ProveedoresEntity listaProv : listaProveedores) {
                        combo.addItem(listaProv.getCodigo());
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
                    msgError = "No existen proveedores";
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
            ProveedoresEntity prov = listaProveedores.get(combo.getSelectedIndex());
            String texto = "\n     CÓDIGO :     " + prov.getCodigo() + "";
            texto += "\n\n\n     NOMBRE :     " + prov.getNombre() + "";
            texto += "\n\n\n     APELLIDOS :     " + prov.getApellidos() + "";
            texto += "\n\n\n     DIRECCIÓN :     " + prov.getDireccion() + "";
            display.setText(texto);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

}
