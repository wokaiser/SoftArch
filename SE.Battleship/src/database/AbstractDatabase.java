package database;

import java.util.LinkedList;
import java.util.List;

import modules.AiModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

import controller.GameContent;
import util.Status;
import interfaces.IDatabase;
import interfaces.IGameContent;
import interfaces.IPlaygroundCell;

public abstract class AbstractDatabase implements IDatabase {
    protected static final String SAVEGAME_NAME_EXIST = "Savegame already taken, choose another one!";
    protected static final String SAVEGAME_NOT_EXIST = "Savegame does no longer exist.";
    private Status status;
    
    protected AbstractDatabase() {
        status = new Status();
    }
    
    @Override
    public Status getStatus() {
        return status;
    }
    
    protected IGameContent map(PersistentGameContent hcontent) {
        if (hcontent == null) {
            return null;
        }
        IPlaygroundCell[][] matrixPlayground1 = loadPlayground1(hcontent);
        IPlaygroundCell[][] matrixPlayground2 = loadPlayground2(hcontent);
        Injector inject = Guice.createInjector(new AiModule().setSettings(hcontent.getPlayer1()), new AiModule().setSettings(hcontent.getPlayer2()));
        GameContent content = inject.getInstance(GameContent.class);
        content.initContent(hcontent.getRows(), hcontent.getColumns(), hcontent.getPlayer1(), hcontent.getPlayer2(), hcontent.getGameType(), matrixPlayground1, matrixPlayground2);
        content.startGame();
        return content;
    }
    
    abstract IPlaygroundCell[][] loadPlayground1(PersistentGameContent hcontent);
    abstract IPlaygroundCell[][] loadPlayground2(PersistentGameContent hcontent);
    
    protected PersistentGameContent map(IGameContent content) {
        /* copy content information to Couchdb required format */
        PersistentGameContent hContent = new PersistentGameContent();
        hContent.setId(content.getName());
        hContent.setGameType(content.getGameType());
        hContent.setRows(content.getRows());    
        hContent.setColumns(content.getColumns());    
        hContent.setPlayer1(content.getPlayer1());
        hContent.setPlayer2(content.getPlayer2());
        
        /* copy playground information to Hibernate required format */
        List<PersistentPlaygroundItem> playground1 = new LinkedList<PersistentPlaygroundItem>();
        List<PersistentPlaygroundItem> playground2 = new LinkedList<PersistentPlaygroundItem>();
        
        IPlaygroundCell[][] playground1Raw = content.getOwnPlayground(content.getPlayer1()).ownView();
        IPlaygroundCell[][] playground2Raw = content.getOwnPlayground(content.getPlayer2()).ownView();
        for (int row = 0; row < content.getRows(); row++) {
            for (int column = 0; column < content.getColumns(); column++){
                playground1.add(new PersistentPlaygroundItem(hContent, 1, row, column, playground1Raw[row][column].get(), playground1Raw[row][column].getShipId()));
                playground2.add(new PersistentPlaygroundItem(hContent, 2, row, column, playground2Raw[row][column].get(), playground2Raw[row][column].getShipId()));
            }
        }
        hContent.setPlayground1(playground1);
        hContent.setPlayground2(playground2);
        
        return hContent;
    }
}
