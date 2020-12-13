package com.company.views;

import com.company.Main;
import com.company.hibernateClass.ProveedoresEntity;
import com.company.hibernateClass.ProyectosEntity;
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

public class VentanaGestionProyectos extends JFrame {

    private JPanel panel1;
    private List<ProyectosEntity> listaProyectos;
    private int regActual;

    public VentanaGestionProyectos() {

        add(panel1);

        setResizable(false);

        // título de la ventana
        setTitle("Gestión de Proyectos");
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
        panelDePestanas.addTab("Gestión de Proyectos", null, panel1, null);
        // al panel le pongo distribución nula para
        // posicionar los elementos en las coordenadas que
        // quiera
        panel1.setLayout(null);

        // una etiqueta en el panel de la pestaña 1
        JLabel jlTitulo = new JLabel("ALTAS, BAJAS Y MODIFICACIONES");
        jlTitulo.setBounds(40, 20, 348, 20);
        panel1.add(jlTitulo);

        // Codigo proyecto
        JLabel jlCodProv = new JLabel("Código:");
        jlCodProv.setBounds(90, 80, 200, 20);
        panel1.add(jlCodProv);
        JTextField jtCodProv = new JTextField();
        jtCodProv.setBounds(180, 80, 100, 20);
        panel1.add(jtCodProv);
        jtCodProv.setDocument(new JTextFieldConfig(6, true));

        // Nombre proyecto
        JLabel jlNombre = new JLabel("Nombre:");
        jlNombre.setBounds(90, 130, 200, 20);
        panel1.add(jlNombre);
        JTextField jtNombre = new JTextField();
        jtNombre.setBounds(180, 130, 200, 20);
        panel1.add(jtNombre);
        jtNombre.setDocument(new JTextFieldConfig(20, false));

        // Ciudad proyecto
        JLabel jlCiudad = new JLabel("Ciudad:");
        jlCiudad.setBounds(90, 180, 200, 20);
        panel1.add(jlCiudad);
        JTextField jtCiudad = new JTextField();
        jtCiudad.setBounds(180, 180, 200, 20);
        panel1.add(jtCiudad);
        jtCiudad.setDocument(new JTextFieldConfig(30, false));

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
            jtCiudad.setText("");

            jbModify.setEnabled(false);
            jbDelete.setEnabled(false);
        });

        // Ejecución del boton insertar
        jbInsert.addActionListener(e -> {

            try {

                if (!jtCodProv.getText().isEmpty() && !jtNombre.getText().isEmpty() && !jtCiudad.getText().isEmpty()) {

                    SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                            new StandardServiceRegistryBuilder().configure().build());

                    Session session = sessionFactory.openSession();

                    Transaction tx = session.beginTransaction();

                    ProyectosEntity prov = new ProyectosEntity();

                    prov.setCodigo(jtCodProv.getText());
                    prov.setNombre(jtNombre.getText());
                    prov.setCiudad(jtCiudad.getText());

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
                JOptionPane.showMessageDialog(null, "Ya existe un proyecto con ese código", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        });

        // Ejecución del boton modificar
        jbModify.addActionListener(e -> {

            try {

                if (!jtCodProv.getText().isEmpty() && !jtNombre.getText().isEmpty() && !jtCiudad.getText().isEmpty()) {

                    SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                            new StandardServiceRegistryBuilder().configure().build());

                    Session session = sessionFactory.openSession();

                    Transaction tx = session.beginTransaction();

                    ProyectosEntity prov = session.load(ProyectosEntity.class, jtCodProv.getText());

                    prov.setNombre(jtNombre.getText());
                    prov.setCiudad(jtCiudad.getText());

                    session.update(prov);
                    tx.commit();

                    session.close();
                    sessionFactory.close();

                } else {
                    JOptionPane.showMessageDialog(null, "Faltan datos por rellenar", "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (PersistenceException pe) {
                JOptionPane.showMessageDialog(null, "No existe un proyecto con ese código", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        });

        // Ejecución del boton borrar
        jbDelete.addActionListener(e -> {

            try {

                if (!jtCodProv.getText().isEmpty() && !jtNombre.getText().isEmpty() && !jtCiudad.getText().isEmpty()) {

                    SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                            new StandardServiceRegistryBuilder().configure().build());

                    Session session = sessionFactory.openSession();

                    Transaction tx = session.beginTransaction();

                    ProyectosEntity prov = session.load(ProyectosEntity.class, jtCodProv.getText());

                    String msgInputDialog = "¿Seguro que deseas eliminar el proyecto con código: " + jtCodProv.getText() + "?";
                    int input = JOptionPane.showConfirmDialog(null, msgInputDialog, "Eliminar", JOptionPane.YES_NO_OPTION);

                    if (input == 0) {
                        session.delete(prov);
                        tx.commit();

                        jtCodProv.setText("");
                        jtNombre.setText("");
                        jtCiudad.setText("");

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
                JOptionPane.showMessageDialog(null, "1-No existe un proyecto con ese código.\n2-El proyecto pertenece a una Gestión.", "Posibles Errores",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        });

        // consulta por el codigo del proyecto
        jtCodProv.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { }
            public void removeUpdate(DocumentEvent e) {
                jbModify.setEnabled(false);
                jbDelete.setEnabled(false);
            }
            public void insertUpdate(DocumentEvent e) {

                SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                        new StandardServiceRegistryBuilder().configure().build());

                Session session = sessionFactory.openSession();

                String hql = "from ProyectosEntity p where p.codigo = ?1";

                Query cons = session.createQuery (hql);
                cons.setParameter(1, jtCodProv.getText());
                Iterator q = cons.iterate();

                if (q.hasNext()) {
                    ProyectosEntity prov = (ProyectosEntity) q.next();
                    jtNombre.setText(prov.getNombre());
                    jtCiudad.setText(prov.getCiudad());

                    jbModify.setEnabled(true);
                    jbDelete.setEnabled(true);

                } else {
                    jbModify.setEnabled(false);
                    jbDelete.setEnabled(false);
                }

            }
        });

        // otro panel de igual forma
        JPanel panel2 = new JPanel();
        panelDePestanas.addTab("Lista de Proyectos", null, panel2, null);
        panel2.setLayout(null);

        // otra etiqueta ésta vez en el segundo panel
        JLabel lbl2 = new JLabel("LISTA DE PROYECTOS - UTILIZA LOS BOTONES PARA IR DE UN REGISTRO A OTRO");
        lbl2.setBounds(20, 20, 480, 14);
        panel2.add(lbl2);

        // Codigo proveedor
        JLabel jlCodProvVer = new JLabel("Código:");
        jlCodProvVer.setBounds(90, 60, 200, 20);
        panel2.add(jlCodProvVer);
        JTextField jtCodProvVer = new JTextField();
        jtCodProvVer.setBounds(180, 60, 100, 20);
        jtCodProvVer.setEditable(false);
        panel2.add(jtCodProvVer);

        // Nombre proveedor
        JLabel jlNombreVer = new JLabel("Nombre:");
        jlNombreVer.setBounds(90, 110, 200, 20);
        panel2.add(jlNombreVer);
        JTextField jtNombreVer = new JTextField();
        jtNombreVer.setBounds(180, 110, 200, 20);
        jtNombreVer.setEditable(false);
        panel2.add(jtNombreVer);

        // Apellidos proveedor
        JLabel jlApellidosVer = new JLabel("Ciudad:");
        jlApellidosVer.setBounds(90, 160, 200, 20);
        panel2.add(jlApellidosVer);
        JTextField jtApellidosVer = new JTextField();
        jtApellidosVer.setBounds(180, 160, 240, 20);
        jtApellidosVer.setEditable(false);
        panel2.add(jtApellidosVer);

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
        jbEjecuteCon.setBounds(130, 250, 280, 40);
        panel2.add(jbEjecuteCon);

        // Ejecución del boton consulta
        jbEjecuteCon.addActionListener(e -> {

            try {

                jbFistReg.setEnabled(false);
                jbMesReg.setEnabled(false);

                consultaListaProveedores();

                if (listaProyectos.size() > 0) {

                    regActual = 0;

                    jtCodProvVer.setText(listaProyectos.get(regActual).getCodigo());
                    jtNombreVer.setText(listaProyectos.get(regActual).getNombre());
                    jtApellidosVer.setText(listaProyectos.get(regActual).getCiudad());

                    jtpag1.setText(String.valueOf(regActual + 1));
                    jtpag2.setText(String.valueOf(listaProyectos.size()));

                    if (listaProyectos.size() > 1) {
                        jbSigReg.setEnabled(true);
                        jbLastReg.setEnabled(true);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "No hay proyectos", "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (PersistenceException pe) {
                String msgError;
                if (pe.getCause().toString().equalsIgnoreCase("org.hibernate.exception.JDBCConnectionException: Error calling Driver#connect")) {
                    msgError = "Error con la conexión";
                }
                else {
                    msgError = "No existen proyectos";
                }
                JOptionPane.showMessageDialog(null, msgError, "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        });

        // Ejecución del boton siguiente registro
        jbSigReg.addActionListener(e -> {

            try {

                if (listaProyectos.size() > 1 && listaProyectos.size() < listaProyectos.size() + 1) {

                    regActual++;

                    jtCodProvVer.setText(listaProyectos.get(regActual).getCodigo());
                    jtNombreVer.setText(listaProyectos.get(regActual).getNombre());
                    jtApellidosVer.setText(listaProyectos.get(regActual).getCiudad());

                    jtpag1.setText(String.valueOf(regActual + 1));
                    jbFistReg.setEnabled(true);
                    jbMesReg.setEnabled(true);

                    if ((regActual + 1) == listaProyectos.size()) {
                        jbSigReg.setEnabled(false);
                        jbLastReg.setEnabled(false);
                    }

                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        });

        // Ejecución del boton ultimo registro
        jbLastReg.addActionListener(e -> {

            try {

                jbSigReg.setEnabled(false);
                jbLastReg.setEnabled(false);

                jbFistReg.setEnabled(true);
                jbMesReg.setEnabled(true);

                if (listaProyectos.size() > 1 && listaProyectos.size() < listaProyectos.size() + 1) {

                    regActual = listaProyectos.size() - 1;

                    jtCodProvVer.setText(listaProyectos.get(listaProyectos.size() - 1).getCodigo());
                    jtNombreVer.setText(listaProyectos.get(listaProyectos.size() - 1).getNombre());
                    jtApellidosVer.setText(listaProyectos.get(listaProyectos.size() - 1).getCiudad());

                    jtpag1.setText(String.valueOf(listaProyectos.size()));

                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        });

        // Ejecución del boton registro anterior
        jbMesReg.addActionListener(e -> {

            try {

                if (regActual > 0) {

                    regActual--;

                    jtCodProvVer.setText(listaProyectos.get(regActual).getCodigo());
                    jtNombreVer.setText(listaProyectos.get(regActual).getNombre());
                    jtApellidosVer.setText(listaProyectos.get(regActual).getCiudad());

                    jtpag1.setText(String.valueOf(regActual + 1));

                    jbSigReg.setEnabled(true);
                    jbLastReg.setEnabled(true);

                    if (regActual == 0) {
                        jbMesReg.setEnabled(false);
                        jbFistReg.setEnabled(false);
                    }

                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        });

        // Ejecución del boton primer registro
        jbFistReg.addActionListener(e -> {

            try {

                if (regActual >= 0) {

                    regActual = 0;

                    jtCodProvVer.setText(listaProyectos.get(regActual).getCodigo());
                    jtNombreVer.setText(listaProyectos.get(regActual).getNombre());
                    jtApellidosVer.setText(listaProyectos.get(regActual).getCiudad());

                    jtpag1.setText(String.valueOf(regActual + 1));

                    jbSigReg.setEnabled(true);
                    jbLastReg.setEnabled(true);

                    if (regActual == 0) {
                        jbMesReg.setEnabled(false);
                        jbFistReg.setEnabled(false);
                    }

                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        });

    }

    private void consultaListaProveedores() {

        SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                new StandardServiceRegistryBuilder().configure().build());

        Session session = sessionFactory.openSession();

        Query q = session.createQuery("from ProyectosEntity ");
        listaProyectos = q.list();

        session.close();
        sessionFactory.close();

    }

}
