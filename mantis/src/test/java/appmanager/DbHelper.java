package appmanager;


import model.UserData;
import model.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;

import java.util.List;


public class DbHelper {

    private final ApplicationManager app;
    private SessionFactory sessionFactory;

    public DbHelper(ApplicationManager app) {
        this.app = app;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
    }

    public Users users() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<UserData> result = session.createQuery( "from UserData where id <> 1").list();
        for ( UserData user : result ) {
            //System.out.println(user);
        }
        session.getTransaction().commit();
        session.close();
        return new Users(result);
    }
}
