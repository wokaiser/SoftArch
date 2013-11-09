package database;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import database.hibernate.HibernateGameContent;
import database.hibernate.HibernatePlaygroundItem;
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
			HibernateGameContent hContent = this.map(content);
			session.saveOrUpdate(hContent);
			for (HibernatePlaygroundItem item : hContent.getPlayground1()) {
				session.saveOrUpdate(item);
			}
			for (HibernatePlaygroundItem item : hContent.getPlayground2()) {
				session.saveOrUpdate(item);
			}
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
	
	private HibernateGameContent map(GameContent content) {
		/* copy content information to Hibernate required format */
		HibernateGameContent hContent = new HibernateGameContent();
		hContent.setId(content.getName());
		hContent.setGameType(content.getGameType());
		hContent.setRows(content.getRows());	
		hContent.setColumns(content.getColumns());	
		hContent.setPlayer1(content.getPlayer1());
		hContent.setPlayer2(content.getPlayer2());
		
		/* copy playground information to Hibernate required format */
		List<HibernatePlaygroundItem> playground1 = new LinkedList<HibernatePlaygroundItem>();
		playground1.add(new HibernatePlaygroundItem(hContent, 1, 91, 1));
		hContent.setPlayground1(playground1);
		
		List<HibernatePlaygroundItem> playground2 = new LinkedList<HibernatePlaygroundItem>();
		playground2.add(new HibernatePlaygroundItem(hContent, 2, 12, 1));
		hContent.setPlayground2(playground2);
		
		return hContent;
	}
}
