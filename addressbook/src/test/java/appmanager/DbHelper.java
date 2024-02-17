package appmanager;

import model.ContactData;
import model.Contacts;
import model.GroupData;
import model.Groups;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class DbHelper {

    private final SessionFactory sessionFactory;

    public DbHelper() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    }

    public Groups groups() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<GroupData> result = session.createQuery( "from GroupData").list();
        for ( GroupData group : result ) {
            //System.out.println(group);
        }
        session.getTransaction().commit();
        session.close();
        return new Groups(result);
    }

    public Contacts contacts() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<ContactData> result = session.createQuery( "from ContactData").list();
        for ( ContactData contact : result ) {
            //System.out.println(contact);
        }
        session.getTransaction().commit();
        session.close();
        return new Contacts(result);
    }

    public ContactData contactsInGroup(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        ContactData result = (ContactData) session.createQuery( "from ContactData where id='" + id + "'").list().get(0);
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
