package framework.quickStart.dataAccessObjects.hibernate;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.ejb.HibernateEntityManagerFactory;


import com.google.appengine.api.utils.SystemProperty;

public class SessionFactory {
    private static final org.hibernate.SessionFactory sessionFactory;
    
    static {
        try {
         	
            Map<String, String> properties = new HashMap();
            if (SystemProperty.environment.value() ==
                  SystemProperty.Environment.Value.Production) {
              properties.put("javax.persistence.jdbc.driver",
                  "com.mysql.jdbc.GoogleDriver");
              properties.put("javax.persistence.jdbc.url",
                  System.getProperty("cloudsql.url"));
              System.out.println(System.getProperty("cloudsql.url"));
            } else {
              properties.put("javax.persistence.jdbc.driver",
                  "com.mysql.jdbc.Driver");
              properties.put("javax.persistence.jdbc.url",
                  System.getProperty("cloudsql.url.dev"));
              System.out.println(System.getProperty("cloudsql.url.dev"));
            }
        	
            EntityManagerFactory emf = Persistence.createEntityManagerFactory(
                    "makkal", properties);
            
            System.out.println("Trying to create session factory");
            sessionFactory = ((HibernateEntityManagerFactory)emf).getSessionFactory();
        	
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
        	System.out.println("Initial SessionFactory creation failed." + ex);
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    private SessionFactory()
    {
    }
 
    public static org.hibernate.SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
