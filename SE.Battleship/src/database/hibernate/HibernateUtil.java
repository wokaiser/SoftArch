package database.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public final class HibernateUtil {
    private static final SessionFactory FACTORY;

    static {
        FACTORY = new AnnotationConfiguration().configure("hibernate.cfg.xml").buildSessionFactory();
    }
    
    private HibernateUtil() {
    }
    
    public static SessionFactory getInstance() {
        return FACTORY;
    }
}