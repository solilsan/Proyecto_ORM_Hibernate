package com.company;

import com.company.hibernateClass.PiezasEntity;
import com.company.views.VentanaPrincipal;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class Main {

    public static Configuration cfg = new Configuration().configure();

    public static void main(String[] args) {

        /*
        SessionFactory sessionFactory = cfg.buildSessionFactory(
                new StandardServiceRegistryBuilder().configure().build() );

        Session session = sessionFactory.openSession();

        try{
            PiezasEntity p = session.load(PiezasEntity.class, 10);
            System.out.printf("Nombre Dep: %s%n", p.getNombre());
        }

        catch (ObjectNotFoundException o) {
            System.out.println("No existe la persona!");
        }

        finally {
            session.close();
        }
         */

        VentanaPrincipal vn = new VentanaPrincipal();
        vn.setLocationRelativeTo(null);
        vn.setVisible(true);

    }
}
