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

        // Combo Busqueda
        JComboBox<String> combo = new JComboBox<>();
        combo.setBounds(170, 60, 200, 20);
        ProveedoresEntity prov = new ProveedoresEntity();
        panel1.add(combo);

        jbBuscar.addActionListener(e -> {

            try {

                SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                        new StandardServiceRegistryBuilder().configure().build());

                Session session = sessionFactory.openSession();

                Query q = session.createQuery("from ProveedoresEntity where codigo like ?1");
                q.setParameter(1, '%' + jtCodProv.getText() + '%');
                List<ProveedoresEntity> listaProveedores = q.list();

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

        return panel1;

    }

}
