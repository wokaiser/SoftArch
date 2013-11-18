package database;

import interfaces.IGameContent;

import java.util.LinkedList;
import java.util.List;

import model.playground.PlaygroundCell;
import modules.AiModule;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.google.inject.Guice;
import com.google.inject.Injector;

import controller.GameContent;
import database.hibernate.HibernateGameContent;
import database.hibernate.HibernatePlaygroundItem;
import database.hibernate.HibernateUtil;

public class HibernateDatabase extends AbstractDatabase {

    @Override
    public IGameContent load(String name) {
         Session session = HibernateUtil.getInstance().getCurrentSession();

        @SuppressWarnings("unchecked")
        List<HibernateGameContent> gameContent = session.createCriteria(HibernateGameContent.class).list();
        for (int index = 0; index < gameContent.size(); index++) {
            if (0 == name.compareTo(gameContent.get(index).getId())) {
                IGameContent content = loadGame(gameContent.get(index));
                getStatus().addText("Successfully loaded game. "+content.getActivePlayer() + " please select your target.");
                return content;
            }
        }
        getStatus().addError(SAVEGAME_NOT_EXIST);
        return null;
    }
    
    private GameContent loadGame(HibernateGameContent savedGame) {
        PlaygroundCell[][] matrixPlayground1 = loadPlayground(1, savedGame.getRows(), savedGame.getColumns(), savedGame.getId());        
        if (null == matrixPlayground1) {
            return null;
        }
        
        PlaygroundCell[][] matrixPlayground2 = loadPlayground(2, savedGame.getRows(), savedGame.getColumns(), savedGame.getId());
        if (null == matrixPlayground2) {
            return null;
        }
        
        Injector inject = Guice.createInjector(new AiModule().setSettings(savedGame.getPlayer1()), new AiModule().setSettings(savedGame.getPlayer2()));
        GameContent content = inject.getInstance(GameContent.class);
        content.initContent(savedGame.getRows(), savedGame.getColumns(), savedGame.getPlayer1(), savedGame.getPlayer2(), savedGame.getGameType(), matrixPlayground1, matrixPlayground2);
        content.startGame();
        
        return content;
    }
    
    private PlaygroundCell[][] loadPlayground(int playground, int rows, int columns, String gameContentid) {
        Session session = HibernateUtil.getInstance().getCurrentSession();

        PlaygroundCell[][] matrix = new PlaygroundCell[rows][columns]; 
        
        @SuppressWarnings("unchecked")
        List<HibernatePlaygroundItem> playgroundItem = session.createCriteria(HibernatePlaygroundItem.class).list();
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
    public List<String> getAll() {
        Session session = null;
        List<String> list = new LinkedList<String>();

        session = HibernateUtil.getInstance().getCurrentSession();
        session.beginTransaction();
        @SuppressWarnings("unchecked")
        List<HibernateGameContent> gameContent = session.createCriteria(HibernateGameContent.class).list();
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
            
            HibernateGameContent hContent = (HibernateGameContent) session.get(HibernateGameContent.class, name);
            
            for(HibernatePlaygroundItem item : hContent.getPlayground1()) {
                session.delete(item);
            }
            for(HibernatePlaygroundItem item : hContent.getPlayground2()) {
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
    
    private HibernateGameContent map(IGameContent content) {
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
        List<HibernatePlaygroundItem> playground2 = new LinkedList<HibernatePlaygroundItem>();
        
        PlaygroundCell[][] playground1Raw = content.getOwnPlayground(content.getPlayer1()).ownView();
        PlaygroundCell[][] playground2Raw = content.getOwnPlayground(content.getPlayer2()).ownView();
        for (int row = 0; row < content.getRows(); row++) {
            for (int column = 0; column < content.getColumns(); column++){
                playground1.add(new HibernatePlaygroundItem(hContent, 1, row, column, playground1Raw[row][column].get(), playground1Raw[row][column].getShipId()));
                playground2.add(new HibernatePlaygroundItem(hContent, 2, row, column, playground2Raw[row][column].get(), playground2Raw[row][column].getShipId()));
            }
        }
        hContent.setPlayground1(playground1);
        hContent.setPlayground2(playground2);
        
        return hContent;
    }
}
