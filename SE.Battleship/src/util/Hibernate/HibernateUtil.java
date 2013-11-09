package util.Hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {
	private static final SessionFactory sessionFactory;

	static {
	   try{
		   sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
	   }catch (Throwable ex) { 
	      System.err.println("Failed to create sessionFactory object." + ex);
	      throw new ExceptionInInitializerError(ex); 
	   }
	}
	
	private HibernateUtil() {
	}
	
	public static SessionFactory getInstance() {
		return sessionFactory;
	}
}