package database;

import interfaces.IGameContent;
import interfaces.IPlaygroundCell;

import java.util.LinkedList;
import java.util.List;

import model.playground.PlaygroundCell;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import database.PersistentGameContent;
import database.hibernate.HibernateUtil;

public class HibernateDatabase extends AbstractDatabase {

    @Override
    public IGameContent load(String name) {
         Session session = HibernateUtil.getInstance().getCurrentSession();

        @SuppressWarnings("unchecked")
        List<PersistentGameContent> gameContent = session.createCriteria(PersistentGameContent.class).list();
        for (int index = 0; index < gameContent.size(); index++) {
            if (0 == name.compareTo(gameContent.get(index).getId())) {
                IGameContent content = map(gameContent.get(index));
                getStatus().addText("Successfully loaded game. "+content.getActivePlayer() + " please select your target.");
                return content;
            }
        }
        getStatus().addError(SAVEGAME_NOT_EXIST);
        return null;
    }
    
    @Override
    public List<String> getAll() {
        Session session = null;
        List<String> list = new LinkedList<String>();

        session = HibernateUtil.getInstance().getCurrentSession();
        session.beginTransaction();
        @SuppressWarnings("unchecked")
        List<PersistentGameContent> gameContent = session.createCriteria(PersistentGameContent.class).list();
        for (int index = 0; index < gameContent.size(); index++) {
            list.add(gameContent.get(index).getId());
        }

        return list;
    }

    @Override
    public void save(String name, IGameContent content) {
        Transaction tx = null;
        Session session = null;
        
        if (getAll().contains(name)) {
            getStatus().addError(SAVEGAME_NAME_EXIST);
            return;
        }

        try {
            session = HibernateUtil.getInstance().getCurrentSession();
            tx = session.beginTransaction();
            PersistentGameContent hContent = this.map(content);
            session.saveOrUpdate(hContent);
            for (PersistentPlaygroundItem item : hContent.getPlayground1()) {
                session.saveOrUpdate(item);
            }
            for (PersistentPlaygroundItem item : hContent.getPlayground2()) {
                session.saveOrUpdate(item);
            }
            tx.commit();
        } catch (HibernateException ex) {
            getStatus().addError(ex.getMessage());
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    @Override
    public void delete(String name) {
        Transaction tx = null;
        Session session = null;

        try {
            session = HibernateUtil.getInstance().getCurrentSession();
            tx = session.beginTransaction();
            
            PersistentGameContent hContent = (PersistentGameContent) session.get(PersistentGameContent.class, name);
            
            for(PersistentPlaygroundItem item : hContent.getPlayground1()) {
                session.delete(item);
            }
            for(PersistentPlaygroundItem item : hContent.getPlayground2()) {
                session.delete(item);
            }
            session.delete(hContent);

            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            getStatus().addError(ex.getMessage());
        }
    }
    
    private IPlaygroundCell[][] loadPlayground(int playground, int rows, int columns, String gameContentid) {
        Session session = HibernateUtil.getInstance().getCurrentSession();

        IPlaygroundCell[][] matrix = new PlaygroundCell[rows][columns]; 
        
        @SuppressWarnings("unchecked")
        List<PersistentPlaygroundItem> playgroundItem = session.createCriteria(PersistentPlaygroundItem.class).list();
        for (int index = 0; index < playgroundItem.size(); index++) {
            if (0 == gameContentid.compareTo(playgroundItem.get(index).getGameContent().getId())) {
                if (playgroundItem.get(index).getPlayground() != playground) {
                    continue;
                }
                
                /* invalid element in database, because out of range */
                if (rows <= playgroundItem.get(index).getRowcell()) {
                    return matrix;
                }
                if (columns <= playgroundItem.get(index).getColumncell()) {
                    return matrix;
                }
                
                matrix[playgroundItem.get(index).getRowcell()][playgroundItem.get(index).getColumncell()] = new PlaygroundCell(playgroundItem.get(index).getStatus(), playgroundItem.get(index).getShipId());
            }
        }

        return matrix;
    }

    @Override
    protected IPlaygroundCell[][] loadPlayground1(PersistentGameContent hcontent) {
        return loadPlayground(1, hcontent.getRows(), hcontent.getColumns(), hcontent.getId());
    }

    @Override
    protected IPlaygroundCell[][] loadPlayground2(PersistentGameContent hcontent) {
        return loadPlayground(2, hcontent.getRows(), hcontent.getColumns(), hcontent.getId());
    }
    
    @Override
    protected PersistentPlaygroundItem createPersistentPlaygroundItem1(PersistentGameContent hContent, int row, int column, IPlaygroundCell[][] playground1Raw) {
        return new PersistentPlaygroundItem(hContent, 1, row, column, playground1Raw[row][column].get(), playground1Raw[row][column].getShipId());
    }
    
    @Override
    protected PersistentPlaygroundItem createPersistentPlaygroundItem2(PersistentGameContent hContent, int row, int column, IPlaygroundCell[][] playground2Raw) {
        return new PersistentPlaygroundItem(hContent, 2, row, column, playground2Raw[row][column].get(), playground2Raw[row][column].getShipId());
    }
}
