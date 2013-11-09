package database;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.Hibernate.HibernateUtil;

public class HibernateDatabase implements IDatabase {

	@Override
	public GameContent load(String name) {
		Transaction tx = null;
		Session session = null;

		try {
			session = HibernateUtil.getInstance().getCurrentSession();
			tx = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<GameContent> gameContent = session.createCriteria(GameContent.class).list();
			for (int index = 0; index < gameContent.size(); index++) {
				if (0 == name.compareTo(gameContent.get(index).getName())) {
					return gameContent.get(index);
				}
			}
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
		} finally {
		       session.close(); 
	    }
		return null;
	}

	@Override
	public List<String> getAll() {
		Transaction tx = null;
		Session session = null;
		List<String> list = new LinkedList<String>();

		try {
			session = HibernateUtil.getInstance().getCurrentSession();
			tx = session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<GameContent> gameContent = session.createCriteria(GameContent.class).list();
			for (int index = 0; index < gameContent.size(); index++) {
				list.add(gameContent.get(index).getName());
			}
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
		} finally {
		       session.close(); 
	    }
		return list;
	}

	@Override
	public boolean save(String name, GameContent content) {
		Transaction tx = null;
		Session session = null;

		try {
			session = HibernateUtil.getInstance().getCurrentSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(HibernateMapping.map(content));
			tx.commit();
		} catch (HibernateException ex) {
			System.out.println(ex.getMessage());
			if (tx != null)
				tx.rollback();
				return false;
		}
		return true;
	}

	@Override
	public boolean delete(String name) {
		
		return false;
	}

}
