package com.company.views;

import com.company.Main;
import com.company.UpperCaseDocFilter;
import com.company.hibernateClass.ProveedoresEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.TransientPropertyValueException;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.exception.ConstraintViolationException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;

public class VentanaGestionProveedores extends JFrame{
    private JPanel panel1;

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

        DocumentFilter filter = new UpperCaseDocFilter();

        // Codigo proveedor
        JLabel jlCodProv = new JLabel("Código del Proveedor:");
        jlCodProv.setBounds(90, 60, 200, 20);
        panel1.add(jlCodProv);
        JTextField jtCodProv = new JTextField();
        jtCodProv.setBounds(240, 60, 100, 20);
        panel1.add(jtCodProv);
        ((AbstractDocument) jtCodProv.getDocument()).setDocumentFilter(filter);

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
        panel1.add(jbModify);

        // Boton eliminar
        JButton jbDelete = new JButton("Eliminar");
        jbDelete.setBounds(400, 240, 100, 40);
        panel1.add(jbDelete);

        // Ejecución del boton limpiar
        jbLimpiar.addActionListener(e -> {
            jtCodProv.setText("");
            jtNombre.setText("");
            jtApellidos.setText("");
            jtDir.setText("");
        });

        // Ejecución del boton insertar
        jbInsert.addActionListener(e -> {

            try {

                SessionFactory sessionFactory = Main.cfg.buildSessionFactory(
                        new StandardServiceRegistryBuilder().configure().build() );

                Session session = sessionFactory.openSession();

                Transaction tx = session.beginTransaction();

                ProveedoresEntity prov = new ProveedoresEntity();

                prov.setNombre(jtNombre.getText());
                prov.setApellidos(jtApellidos.getText());
                prov.setDireccion(jtDir.getText());

                session.save(prov);
                tx.commit();
                
                session.close();
                sessionFactory.close();

            } catch (ConstraintViolationException cv) {
                System.out.println("PROVEEDOR DUPLICADO");
                System.out.printf("MENSAJE:%s%n", cv.getMessage());
                System.out.printf("COD ERROR:%d%n", cv.getErrorCode());
                System.out.printf("ERROR SQL:%s%n", cv.getSQLException().getMessage());
            } catch (TransientPropertyValueException tp){
                System.out.println("EL PROVEEDOR NO EXISTE");
                System.out.printf("MENSAJE:%s%n", tp.getMessage());
                System.out.printf("Propiedad:%s%n", tp.getPropertyName());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
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

    }

}
