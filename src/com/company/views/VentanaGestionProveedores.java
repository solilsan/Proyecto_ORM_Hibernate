package com.company.views;

import com.company.Main;
import com.company.hibernateClass.ProveedoresEntity;
import com.company.swingConfig.JTextFieldConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import javax.persistence.PersistenceException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.Iterator;
import java.util.List;

public class VentanaGestionProveedores extends JFrame{
    private JPanel panel1;
    private List <ProveedoresEntity> listaProveedores;
    private int regActual;

    public VentanaGestionProveedores() {

        add(panel1);

        setTitle("Gestión de Proveedores");
        setResizable(false);
        setSize(600, 400);

        // título de la ventana
        setTitle("Gestión de Proveedores");
        // operación al cerra la ventana
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // coordenadas de las esquinas del frame en el escritorio
        setBounds(100, 100, 600, 400);

        // el panel que contiene todo se crea y se pone en el frame
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        // distribución nula para poder posicionar los elementos
        // en las coordenadas que queramos
        contentPane.setLayout(null);

        // se crea el panel de pestañas
        JTabbedPane panelDePestanas = new JTabbedPane(JTabbedPane.TOP);
        // se posiciona en el panel
        panelDePestanas.setBounds(10, 11, 564, 340);
        contentPane.add(panelDePestanas);

        // éste es el primer panel
        // que se añade como pestaña al 'tabbedPane'
        JPanel panel1 = new JPanel();
        panelDePestanas.addTab("Gestión de Proveedores", null, panel1, null);
        // al panel le pongo distribución nula para
        // posicionar los elementos en las coordenadas que
        // quiera
        panel1.setLayout(null);

        // una etiqueta en el panel de la pestaña 1
        JLabel jlTitulo = new JLabel("ALTAS, BAJAS Y MODIFICACIONES");
        jlTitulo.setBounds(40, 20, 348, 20);
        panel1.add(jlTitulo);

        // Codigo proveedor
        JLabel jlCodProv = new JLabel("Código del Proveedor:");
        jlCodProv.setBounds(90, 60, 200, 20);
        panel1.add(jlCodProv);
        JTextField jtCodProv = new JTextField();
        jtCodProv.setBounds(240, 60, 100, 20);
        panel1.add(jtCodProv);
        jtCodProv.setDocument(new JTextFieldConfig(6, true));

        // Nombre proveedor
        JLabel jlNombre = new JLabel("Nombre:");
        jlNombre.setBounds(90, 100, 200, 20);
        panel1.add(jlNombre);
        JTextField jtNombre = new JTextField();
        jtNombre.setBounds(180, 100, 200, 20);
        panel1.add(jtNombre);

        // Apellidos proveedor
        JLabel jlApellidos = new JLabel("Apellidos:");
        jlApellidos.setBounds(90, 140, 200, 20);
        panel1.add(jlApellidos);
        JTextField jtApellidos = new JTextField();
        jtApellidos.setBounds(180, 140, 240, 20);
        panel1.add(jtApellidos);

        // Direccion proveedor
        JLabel jlDir = new JLabel("Dirección:");
        jlDir.setBounds(90, 180, 200, 20);
        panel1.add(jlDir);
        JTextField jtDir = new JTextField();
        jtDir.setBounds(180, 180, 280, 20);
        panel1.add(jtDir);

        // Boton limpiar
        JButton jbLimpiar = new JButton("Limpiar");
        jbLimpiar.setBounds(70, 240, 100, 40);
        panel1.add(jbLimpiar);

        // Boton insertar
        JButton jbInsert = new JButton("Insertar");
        jbInsert.setBounds(180, 240, 100, 40);
        panel1.add(jbInsert);

        // Boton modificar
        JButton jbModify = new JButton("Modificar");
        jbModify.setBounds(290, 240, 100, 40);
        jbModify.setEnabled(false);
        panel1.add(jbModify);

        // Boton eliminar
        JButton jbDelete = new JButton("Eliminar");
        jbDelete.setBounds(400, 240, 100, 40);
        jbDelete.setEnabled(false);
        panel1.add(jbDelete);

        // Ejecución del boton limpiar
        jbLimpiar.addActionListener(e -> {
            jtCodProv.setText("");
            jtNombre.setText("");
            jtApellidos.setText("");
            jtDir.setText("");

            jbModify.setEnabled(false);
            jbDelete.setEnabled(false);
        });

        // Ejecución del boton insertar
        jbInsert.addActionListener(e -> {

            try {

                if (!jtCodProv.getText().isEmpty() && !jtNombre.getText().isEmpty() && !jtApellidos.getText().isEmpty() && !jtDir.getText().isEmpty()) {

                    SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                            new StandardServiceRegistryBuilder().configure().build());

                    Session session = sessionFactory.openSession();

                    Transaction tx = session.beginTransaction();

                    ProveedoresEntity prov = new ProveedoresEntity();

                    prov.setCodigo(jtCodProv.getText());
                    prov.setNombre(jtNombre.getText());
                    prov.setApellidos(jtApellidos.getText());
                    prov.setDireccion(jtDir.getText());
                    prov.setEstado("ALTA");

                    session.save(prov);
                    tx.commit();

                    session.close();
                    sessionFactory.close();

                    jbModify.setEnabled(true);
                    jbDelete.setEnabled(true);

                } else {
                    JOptionPane.showMessageDialog(null, "Faltan datos por rellenar", "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (PersistenceException pe) {
                JOptionPane.showMessageDialog(null, "Ya existe un proveedor con ese código", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        });

        jbModify.addActionListener(e -> {

            try {

                if (!jtCodProv.getText().isEmpty() && !jtNombre.getText().isEmpty() && !jtApellidos.getText().isEmpty() && !jtDir.getText().isEmpty()) {

                    SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                            new StandardServiceRegistryBuilder().configure().build());

                    Session session = sessionFactory.openSession();

                    Transaction tx = session.beginTransaction();

                    ProveedoresEntity prov = session.load(ProveedoresEntity.class, jtCodProv.getText());

                    prov.setNombre(jtNombre.getText());
                    prov.setApellidos(jtApellidos.getText());
                    prov.setDireccion(jtDir.getText());

                    session.update(prov);
                    tx.commit();

                    session.close();
                    sessionFactory.close();

                } else {
                    JOptionPane.showMessageDialog(null, "Faltan datos por rellenar", "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (PersistenceException pe) {
                JOptionPane.showMessageDialog(null, "No existe un proveedor con ese código", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        });

        jbDelete.addActionListener(e -> {

            try {

                if (!jtCodProv.getText().isEmpty() && !jtNombre.getText().isEmpty() && !jtApellidos.getText().isEmpty() && !jtDir.getText().isEmpty()) {

                    SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                            new StandardServiceRegistryBuilder().configure().build());

                    Session session = sessionFactory.openSession();

                    Transaction tx = session.beginTransaction();

                    ProveedoresEntity prov = session.load(ProveedoresEntity.class, jtCodProv.getText());

                    int input = JOptionPane.showConfirmDialog(null, "¿Seguro que deseas eliminar este proveedor?", "Eliminar", JOptionPane.YES_NO_OPTION);

                    if (input == 0) {
                        session.delete(prov);
                        tx.commit();

                        jtCodProv.setText("");
                        jtNombre.setText("");
                        jtApellidos.setText("");
                        jtDir.setText("");

                        jbModify.setEnabled(false);
                        jbDelete.setEnabled(false);
                    }

                    session.close();
                    sessionFactory.close();

                } else {
                    JOptionPane.showMessageDialog(null, "Faltan datos por rellenar", "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (PersistenceException pe) {
                JOptionPane.showMessageDialog(null, "No existe un proveedor con ese código", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        });

        // consulta por el codigo del proveedor
        jtCodProv.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { }
            public void removeUpdate(DocumentEvent e) {
                jtNombre.setText("");
                jtApellidos.setText("");
                jtDir.setText("");

                jbModify.setEnabled(false);
                jbDelete.setEnabled(false);
            }
            public void insertUpdate(DocumentEvent e) {

                SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                        new StandardServiceRegistryBuilder().configure().build());

                Session session = sessionFactory.openSession();

                String hql = "from ProveedoresEntity p where p.codigo = ?1";

                Query cons = session.createQuery (hql);
                cons.setParameter(1, jtCodProv.getText());
                Iterator q = cons.iterate();

                if (q.hasNext()) {
                    ProveedoresEntity prov = (ProveedoresEntity) q.next();
                    jtNombre.setText(prov.getNombre());
                    jtApellidos.setText(prov.getApellidos());
                    jtDir.setText(prov.getDireccion());

                    jbModify.setEnabled(true);
                    jbDelete.setEnabled(true);

                } else {
                    jtNombre.setText("");
                    jtApellidos.setText("");
                    jtDir.setText("");

                    jbModify.setEnabled(false);
                    jbDelete.setEnabled(false);
                }

            }
        });


        // otro panel de igual forma
        JPanel panel2 = new JPanel();
        panelDePestanas.addTab("Lista de Proveedores", null, panel2, null);
        panel2.setLayout(null);

        // otra etiqueta ésta vez en el segundo panel
        JLabel lbl2 = new JLabel("LISTA DE PROVEEDORES - UTILIZA LOS BOTONES PARA IR DE UN REG A OTRO");
        lbl2.setBounds(20, 20, 450, 14);
        panel2.add(lbl2);

        // Codigo proveedor
        JLabel jlCodProvVer = new JLabel("Código del Proveedor:");
        jlCodProvVer.setBounds(90, 60, 200, 20);
        panel2.add(jlCodProvVer);
        JTextField jtCodProvVer = new JTextField();
        jtCodProvVer.setBounds(240, 60, 100, 20);
        jtCodProvVer.setEditable(false);
        panel2.add(jtCodProvVer);

        // Nombre proveedor
        JLabel jlNombreVer = new JLabel("Nombre:");
        jlNombreVer.setBounds(90, 90, 200, 20);
        panel2.add(jlNombreVer);
        JTextField jtNombreVer = new JTextField();
        jtNombreVer.setBounds(180, 90, 200, 20);
        jtNombreVer.setEditable(false);
        panel2.add(jtNombreVer);

        // Apellidos proveedor
        JLabel jlApellidosVer = new JLabel("Apellidos:");
        jlApellidosVer.setBounds(90, 120, 200, 20);
        panel2.add(jlApellidosVer);
        JTextField jtApellidosVer = new JTextField();
        jtApellidosVer.setBounds(180, 120, 240, 20);
        jtApellidosVer.setEditable(false);
        panel2.add(jtApellidosVer);

        // Direccion proveedor
        JLabel jlDirVer = new JLabel("Dirección:");
        jlDirVer.setBounds(90, 150, 200, 20);
        panel2.add(jlDirVer);
        JTextField jtDirVer = new JTextField();
        jtDirVer.setBounds(180, 150, 280, 20);
        jtDirVer.setEditable(false);
        panel2.add(jtDirVer);

        // Resgistro
        JLabel jlReg = new JLabel("REG:");
        jlReg.setBounds(80, 200, 40, 20);
        panel2.add(jlReg);

        // Resgistro actual
        JTextField jtpag1 = new JTextField();
        jtpag1.setBounds(120, 200, 40, 20);
        jtpag1.setText("0");
        jtpag1.setHorizontalAlignment(JTextField.CENTER);
        jtpag1.setEditable(false);
        panel2.add(jtpag1);

        // Barra entre el numero de registros
        JLabel jlBarra = new JLabel("/");
        jlBarra.setBounds(170, 200, 10, 20);
        panel2.add(jlBarra);

        // Resgistros totales
        JTextField jtpag2 = new JTextField();
        jtpag2.setBounds(180, 200, 40, 20);
        jtpag2.setText("0");
        jtpag2.setHorizontalAlignment(JTextField.CENTER);
        jtpag2.setEditable(false);
        panel2.add(jtpag2);

        // Boton primer registro
        JButton jbFistReg = new JButton("|<<");
        jbFistReg.setBounds(240, 195, 55, 30);
        jbFistReg.setEnabled(false);
        panel2.add(jbFistReg);

        // Boton registro anterior
        JButton jbMesReg = new JButton("<<");
        jbMesReg.setBounds(300, 195, 55, 30);
        jbMesReg.setEnabled(false);
        panel2.add(jbMesReg);

        // Boton registro siguiente
        JButton jbSigReg = new JButton(">>");
        jbSigReg.setBounds(360, 195, 55, 30);
        jbSigReg.setEnabled(false);
        panel2.add(jbSigReg);

        // Boton ultimo registro
        JButton jbLastReg = new JButton(">>|");
        jbLastReg.setBounds(420, 195, 55, 30);
        jbLastReg.setEnabled(false);
        panel2.add(jbLastReg);

        // Boton ejecutar consulta
        JButton jbEjecuteCon = new JButton("Ejecutar Consulta");
        jbEjecuteCon.setBounds(100, 250, 280, 40);
        panel2.add(jbEjecuteCon);

        // Boton baja
        JButton jbBaja = new JButton("Baja");
        jbBaja.setBounds(390, 250, 60, 40);
        jbBaja.setEnabled(false);
        panel2.add(jbBaja);

        jbEjecuteCon.addActionListener(e -> {

            try {

                jbFistReg.setEnabled(false);
                jbMesReg.setEnabled(false);

                SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                        new StandardServiceRegistryBuilder().configure().build());

                Session session = sessionFactory.openSession();

                Query q = session.createQuery("from ProveedoresEntity ");
                listaProveedores = q.list();

                if (listaProveedores.size() > 0) {

                    regActual = 0;

                    jtCodProvVer.setText(listaProveedores.get(regActual).getCodigo());
                    jtNombreVer.setText(listaProveedores.get(regActual).getNombre());
                    jtApellidosVer.setText(listaProveedores.get(regActual).getApellidos());
                    jtDirVer.setText(listaProveedores.get(regActual).getDireccion());

                    jtpag1.setText(String.valueOf(regActual + 1));
                    jtpag2.setText(String.valueOf(listaProveedores.size()));

                    if (listaProveedores.size() > 1) {
                        jbSigReg.setEnabled(true);
                        jbLastReg.setEnabled(true);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "No hay proveedores", "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                session.close();
                sessionFactory.close();

            } catch (PersistenceException pe) {
                JOptionPane.showMessageDialog(null, "No existe un proveedor con ese código", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        });

        jbSigReg.addActionListener(e -> {

            try {

                if (listaProveedores.size() > 1 && listaProveedores.size() < listaProveedores.size() + 1) {

                    regActual++;

                    jtCodProvVer.setText(listaProveedores.get(regActual).getCodigo());
                    jtNombreVer.setText(listaProveedores.get(regActual).getNombre());
                    jtApellidosVer.setText(listaProveedores.get(regActual).getApellidos());
                    jtDirVer.setText(listaProveedores.get(regActual).getDireccion());

                    jtpag1.setText(String.valueOf(regActual + 1));
                    jbFistReg.setEnabled(true);
                    jbMesReg.setEnabled(true);

                    if ((regActual + 1) == listaProveedores.size()) {
                        jbSigReg.setEnabled(false);
                        jbLastReg.setEnabled(false);
                    }

                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        });

        jbLastReg.addActionListener(e -> {

            try {

                jbSigReg.setEnabled(false);
                jbLastReg.setEnabled(false);

                jbFistReg.setEnabled(true);
                jbMesReg.setEnabled(true);

                if (listaProveedores.size() > 1 && listaProveedores.size() < listaProveedores.size() + 1) {

                    jtCodProvVer.setText(listaProveedores.get(listaProveedores.size() - 1).getCodigo());
                    jtNombreVer.setText(listaProveedores.get(listaProveedores.size() - 1).getNombre());
                    jtApellidosVer.setText(listaProveedores.get(listaProveedores.size() - 1).getApellidos());
                    jtDirVer.setText(listaProveedores.get(listaProveedores.size() - 1).getDireccion());

                    jtpag1.setText(String.valueOf(listaProveedores.size()));

                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        });

    }

}
