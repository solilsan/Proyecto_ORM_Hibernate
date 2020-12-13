package com.company.views;

import com.company.Main;
import com.company.hibernateClass.PiezasEntity;
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
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class VentanaGestionPiezas extends JFrame {

    private JPanel panel1;
    private List<PiezasEntity> listaPiezas;
    private int regActual;

    public VentanaGestionPiezas() {

        add(panel1);

        setResizable(false);

        // título de la ventana
        setTitle("Gestión de Piezas");
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
        panelDePestanas.addTab("Gestión de Piezas", null, panel1, null);
        // al panel le pongo distribución nula para
        // posicionar los elementos en las coordenadas que
        // quiera
        panel1.setLayout(null);

        // una etiqueta en el panel de la pestaña 1
        JLabel jlTitulo = new JLabel("ALTAS, BAJAS Y MODIFICACIONES");
        jlTitulo.setBounds(40, 20, 348, 20);
        panel1.add(jlTitulo);

        // Codigo pieza
        JLabel jlCodProv = new JLabel("Código:");
        jlCodProv.setBounds(90, 60, 200, 20);
        panel1.add(jlCodProv);
        JTextField jtCodProv = new JTextField();
        jtCodProv.setBounds(180, 60, 100, 20);
        panel1.add(jtCodProv);
        jtCodProv.setDocument(new JTextFieldConfig(6, true));

        // Nombre pieza
        JLabel jlNombre = new JLabel("Nombre:");
        jlNombre.setBounds(90, 100, 200, 20);
        panel1.add(jlNombre);
        JTextField jtNombre = new JTextField();
        jtNombre.setBounds(180, 100, 200, 20);
        panel1.add(jtNombre);
        jtNombre.setDocument(new JTextFieldConfig(20, false));

        // Precio pieza
        JLabel jlPrecio = new JLabel("Precio:");
        jlPrecio.setBounds(90, 140, 200, 20);
        panel1.add(jlPrecio);
        JTextField jtPrecio = new JTextField();
        jtPrecio.setBounds(180, 140, 80, 20);
        panel1.add(jtPrecio);
        jtPrecio.setDocument(new JTextFieldConfig(10, false));

        // Descripcion pieza
        JLabel jlDir = new JLabel("Descripción:");
        jlDir.setBounds(90, 180, 200, 20);
        panel1.add(jlDir);
        JTextArea jtaDes = new JTextArea();
        jtaDes.setBounds(180, 180, 300, 70);
        jtaDes.setLineWrap(true);
        jtaDes.setBorder(new LineBorder(Color.BLACK));
        jtaDes.setDocument(new JTextFieldConfig(168, false));
        panel1.add(jtaDes);

        // Boton limpiar
        JButton jbLimpiar = new JButton("Limpiar");
        jbLimpiar.setBounds(70, 260, 100, 40);
        panel1.add(jbLimpiar);

        // Boton insertar
        JButton jbInsert = new JButton("Insertar");
        jbInsert.setBounds(180, 260, 100, 40);
        panel1.add(jbInsert);

        // Boton modificar
        JButton jbModify = new JButton("Modificar");
        jbModify.setBounds(290, 260, 100, 40);
        jbModify.setEnabled(false);
        panel1.add(jbModify);

        // Boton eliminar
        JButton jbDelete = new JButton("Eliminar");
        jbDelete.setBounds(400, 260, 100, 40);
        jbDelete.setEnabled(false);
        panel1.add(jbDelete);

        // Ejecución del boton limpiar
        jbLimpiar.addActionListener(e -> {
            jtCodProv.setText("");
            jtNombre.setText("");
            jtPrecio.setText("");
            jtaDes.setText("");

            jbModify.setEnabled(false);
            jbDelete.setEnabled(false);
        });

        // consulta por el codigo del proveedor
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

                String hql = "from PiezasEntity p where p.codigo = ?1";

                Query cons = session.createQuery (hql);
                cons.setParameter(1, jtCodProv.getText());
                Iterator q = cons.iterate();

                if (q.hasNext()) {
                    PiezasEntity pieza = (PiezasEntity) q.next();
                    jtNombre.setText(pieza.getNombre());
                    jtPrecio.setText(String.valueOf(pieza.getPrecio()));
                    jtaDes.setText(pieza.getDescripcion());

                    jbModify.setEnabled(true);
                    jbDelete.setEnabled(true);

                } else {
                    jbModify.setEnabled(false);
                    jbDelete.setEnabled(false);
                }

            }
        });

        // Ejecución del boton insertar
        jbInsert.addActionListener(e -> {

            try {

                if (!jtCodProv.getText().isEmpty() && !jtNombre.getText().isEmpty() && !jtPrecio.getText().isEmpty()) {

                    double precio = Double.parseDouble(jtPrecio.getText());

                    SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                            new StandardServiceRegistryBuilder().configure().build());

                    Session session = sessionFactory.openSession();

                    Transaction tx = session.beginTransaction();

                    PiezasEntity pieza = new PiezasEntity();

                    pieza.setCodigo(jtCodProv.getText());
                    pieza.setNombre(jtNombre.getText());
                    pieza.setPrecio(precio);
                    pieza.setDescripcion(jtaDes.getText());

                    session.save(pieza);
                    tx.commit();

                    session.close();
                    sessionFactory.close();

                    jbModify.setEnabled(true);
                    jbDelete.setEnabled(true);

                } else {
                    JOptionPane.showMessageDialog(null, "Faltan datos por rellenar", "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Introduce un número en precio por favor.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (PersistenceException pe) {
                JOptionPane.showMessageDialog(null, "Ya existe una pieza con ese código", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        });

        // Ejecución del boton modificar
        jbModify.addActionListener(e -> {

            try {

                if (!jtCodProv.getText().isEmpty() && !jtNombre.getText().isEmpty() && !jtPrecio.getText().isEmpty()) {

                    double precio = Double.parseDouble(jtPrecio.getText());

                    SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                            new StandardServiceRegistryBuilder().configure().build());

                    Session session = sessionFactory.openSession();

                    Transaction tx = session.beginTransaction();

                    PiezasEntity pieza = session.load(PiezasEntity.class, jtCodProv.getText());

                    pieza.setNombre(jtNombre.getText());
                    pieza.setPrecio(precio);
                    pieza.setDescripcion(jtaDes.getText());

                    session.update(pieza);
                    tx.commit();

                    session.close();
                    sessionFactory.close();

                } else {
                    JOptionPane.showMessageDialog(null, "Faltan datos por rellenar", "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Introduce un número en precio por favor.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (PersistenceException pe) {
                JOptionPane.showMessageDialog(null, "No existe un proveedor con ese código", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        });

        // Ejecución del boton borrar
        jbDelete.addActionListener(e -> {

            try {

                if (!jtCodProv.getText().isEmpty()) {

                    SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                            new StandardServiceRegistryBuilder().configure().build());

                    Session session = sessionFactory.openSession();

                    Transaction tx = session.beginTransaction();

                    PiezasEntity pieza = session.load(PiezasEntity.class, jtCodProv.getText());

                    String msgInputDialog = "¿Seguro que deseas eliminar esta pieza con código: " + jtCodProv.getText() + "?";
                    int input = JOptionPane.showConfirmDialog(null, msgInputDialog, "Eliminar", JOptionPane.YES_NO_OPTION);

                    if (input == 0) {
                        session.delete(pieza);
                        tx.commit();

                        jtCodProv.setText("");
                        jtNombre.setText("");
                        jtPrecio.setText("");
                        jtaDes.setText("");

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
                JOptionPane.showMessageDialog(null, "1-No existe una pieza con ese código.\n2-La pieza pertenece a una Gestión.", "Posibles Errores",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        });

        // otro panel de igual forma
        JPanel panel2 = new JPanel();
        panelDePestanas.addTab("Lista de Piezas", null, panel2, null);
        panel2.setLayout(null);

        // otra etiqueta ésta vez en el segundo panel
        JLabel lbl2 = new JLabel("LISTA DE PIEZAS - UTILIZA LOS BOTONES PARA IR DE UN REGISTRO A OTRO");
        lbl2.setBounds(20, 20, 480, 14);
        panel2.add(lbl2);

        // Codigo proveedor
        JLabel jlCodProvVer = new JLabel("Código:");
        jlCodProvVer.setBounds(90, 40, 200, 20);
        panel2.add(jlCodProvVer);
        JTextField jtCodProvVer = new JTextField();
        jtCodProvVer.setBounds(180, 40, 100, 20);
        jtCodProvVer.setEditable(false);
        panel2.add(jtCodProvVer);

        // Nombre pieza
        JLabel jlNombreVer = new JLabel("Nombre:");
        jlNombreVer.setBounds(90, 70, 200, 20);
        panel2.add(jlNombreVer);
        JTextField jtNombreVer = new JTextField();
        jtNombreVer.setBounds(180, 70, 200, 20);
        jtNombreVer.setEditable(false);
        panel2.add(jtNombreVer);

        // Precio pieza
        JLabel jlPrecioVer = new JLabel("Precio:");
        jlPrecioVer.setBounds(90, 100, 200, 20);
        panel2.add(jlPrecioVer);
        JTextField jtPrecioVer = new JTextField();
        jtPrecioVer.setBounds(180, 100, 240, 20);
        jtPrecioVer.setEditable(false);
        panel2.add(jtPrecioVer);

        // Descripcion pieza
        JLabel jlDirVer = new JLabel("Descripción:");
        jlDirVer.setBounds(90, 130, 200, 20);
        panel2.add(jlDirVer);
        JTextArea jtaDesVer = new JTextArea();
        jtaDesVer.setBounds(180, 130, 300, 70);
        jtaDesVer.setLineWrap(true);
        jtaDesVer.setBorder(new LineBorder(Color.BLACK));
        jtaDesVer.setEditable(false);
        jtaDesVer.setDocument(new JTextFieldConfig(168, false));
        panel2.add(jtaDesVer);

        // Resgistro
        JLabel jlReg = new JLabel("REG:");
        jlReg.setBounds(80, 215, 40, 20);
        panel2.add(jlReg);

        // Resgistro actual
        JTextField jtpag1 = new JTextField();
        jtpag1.setBounds(120, 215, 40, 20);
        jtpag1.setText("0");
        jtpag1.setHorizontalAlignment(JTextField.CENTER);
        jtpag1.setEditable(false);
        panel2.add(jtpag1);

        // Barra entre el numero de registros
        JLabel jlBarra = new JLabel("/");
        jlBarra.setBounds(170, 215, 10, 20);
        panel2.add(jlBarra);

        // Resgistros totales
        JTextField jtpag2 = new JTextField();
        jtpag2.setBounds(180, 215, 40, 20);
        jtpag2.setText("0");
        jtpag2.setHorizontalAlignment(JTextField.CENTER);
        jtpag2.setEditable(false);
        panel2.add(jtpag2);

        // Boton primer registro
        JButton jbFistReg = new JButton("|<<");
        jbFistReg.setBounds(240, 210, 55, 30);
        jbFistReg.setEnabled(false);
        panel2.add(jbFistReg);

        // Boton registro anterior
        JButton jbMesReg = new JButton("<<");
        jbMesReg.setBounds(300, 210, 55, 30);
        jbMesReg.setEnabled(false);
        panel2.add(jbMesReg);

        // Boton registro siguiente
        JButton jbSigReg = new JButton(">>");
        jbSigReg.setBounds(360, 210, 55, 30);
        jbSigReg.setEnabled(false);
        panel2.add(jbSigReg);

        // Boton ultimo registro
        JButton jbLastReg = new JButton(">>|");
        jbLastReg.setBounds(420, 210, 55, 30);
        jbLastReg.setEnabled(false);
        panel2.add(jbLastReg);

        // Boton ejecutar consulta
        JButton jbEjecuteCon = new JButton("Ejecutar Consulta");
        jbEjecuteCon.setBounds(125, 260, 280, 40);
        panel2.add(jbEjecuteCon);

        // Ejecución del boton consulta
        jbEjecuteCon.addActionListener(e -> {

            try {

                jbFistReg.setEnabled(false);
                jbMesReg.setEnabled(false);

                consultaListaPiezas();

                if (listaPiezas.size() > 0) {

                    regActual = 0;

                    jtCodProvVer.setText(listaPiezas.get(regActual).getCodigo());
                    jtNombreVer.setText(listaPiezas.get(regActual).getNombre());
                    jtPrecioVer.setText(String.valueOf(listaPiezas.get(regActual).getPrecio()));
                    jtaDesVer.setText(listaPiezas.get(regActual).getDescripcion());

                    jtpag1.setText(String.valueOf(regActual + 1));
                    jtpag2.setText(String.valueOf(listaPiezas.size()));

                    if (listaPiezas.size() > 1) {
                        jbSigReg.setEnabled(true);
                        jbLastReg.setEnabled(true);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "No hay piezas", "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                }

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

        // Ejecución del boton siguiente registro
        jbSigReg.addActionListener(e -> {

            try {

                if (listaPiezas.size() > 1 && listaPiezas.size() < listaPiezas.size() + 1) {

                    regActual++;

                    jtCodProvVer.setText(listaPiezas.get(regActual).getCodigo());
                    jtNombreVer.setText(listaPiezas.get(regActual).getNombre());
                    jtPrecioVer.setText(String.valueOf(listaPiezas.get(regActual).getPrecio()));
                    jtaDesVer.setText(listaPiezas.get(regActual).getDescripcion());

                    jtpag1.setText(String.valueOf(regActual + 1));
                    jbFistReg.setEnabled(true);
                    jbMesReg.setEnabled(true);

                    if ((regActual + 1) == listaPiezas.size()) {
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

                if (listaPiezas.size() > 1 && listaPiezas.size() < listaPiezas.size() + 1) {

                    regActual = listaPiezas.size() - 1;

                    jtCodProvVer.setText(listaPiezas.get(listaPiezas.size() - 1).getCodigo());
                    jtNombreVer.setText(listaPiezas.get(listaPiezas.size() - 1).getNombre());
                    jtPrecioVer.setText(String.valueOf(listaPiezas.get(listaPiezas.size() - 1).getPrecio()));
                    jtaDesVer.setText(listaPiezas.get(listaPiezas.size() - 1).getDescripcion());

                    jtpag1.setText(String.valueOf(listaPiezas.size()));

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

                    jtCodProvVer.setText(listaPiezas.get(regActual).getCodigo());
                    jtNombreVer.setText(listaPiezas.get(regActual).getNombre());
                    jtPrecioVer.setText(String.valueOf(listaPiezas.get(regActual).getPrecio()));
                    jtaDesVer.setText(listaPiezas.get(regActual).getDescripcion());

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

                    jtCodProvVer.setText(listaPiezas.get(regActual).getCodigo());
                    jtNombreVer.setText(listaPiezas.get(regActual).getNombre());
                    jtPrecioVer.setText(String.valueOf(listaPiezas.get(regActual).getPrecio()));
                    jtaDesVer.setText(listaPiezas.get(regActual).getDescripcion());

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

    private void consultaListaPiezas() {

        SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                new StandardServiceRegistryBuilder().configure().build());

        Session session = sessionFactory.openSession();

        Query q = session.createQuery("from PiezasEntity ");
        listaPiezas = q.list();

        session.close();
        sessionFactory.close();

    }

}
