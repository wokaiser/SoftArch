package database;

import java.util.LinkedList;
import java.util.List;

import model.playground.PlaygroundCell;
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
    
    /**
     * Constructor which create a new status object.
     */
    protected AbstractDatabase() {
        status = new Status();
    }
    
    /**
     * Get the status object, which will be used for information/error logging
     * @return The status object
     */
    public Status getStatus() {
        return status;
    }
    
    /**
     * Method to map a PersistentGameContent to a IGameContent.
     * @param game content as PersistentGameContent
     * @return game content as IGameContent
     */
    protected IGameContent map(PersistentGameContent hcontent) {
        if (hcontent == null) {
            return null;
        }
        IPlaygroundCell[][] matrixPlayground1 = loadPlayground1(hcontent);
        IPlaygroundCell[][] matrixPlayground2 = loadPlayground2(hcontent);
        Injector inject = Guice.createInjector(new AiModule().setSettings(hcontent.getPlayer1()), new AiModule().setSettings(hcontent.getPlayer2()));
        GameContent content = inject.getInstance(GameContent.class);
        content.initContent(hcontent.getRows(), hcontent.getColumns(), hcontent.getPlayer1(), hcontent.getPlayer2(), hcontent.getMoves1(), hcontent.getMoves2(), hcontent.getGameType(), matrixPlayground1, matrixPlayground2);
        content.startGame();
        return content;
    }
    
    /**
     * Method to map a IGameContent to a PersistentGameContent.
     * @param game content as IGameContent
     * @return game content as PersistentGameContent
     */
    protected PersistentGameContent map(IGameContent content) {
        /* copy content information to Couchdb required format */
        PersistentGameContent hContent = new PersistentGameContent();
        hContent.setId(content.getName());
        hContent.setGameType(content.getGameType());
        hContent.setRows(content.getRows());    
        hContent.setColumns(content.getColumns());    
        hContent.setPlayer1(content.getPlayer1());
        hContent.setPlayer2(content.getPlayer2());
        hContent.setMoves1(content.getMoves(content.getPlayer1()));
        hContent.setMoves2(content.getMoves(content.getPlayer2()));
        
        /* copy playground information to Hibernate required format */
        List<PersistentPlaygroundItem> playground1 = new LinkedList<PersistentPlaygroundItem>();
        List<PersistentPlaygroundItem> playground2 = new LinkedList<PersistentPlaygroundItem>();
        
        IPlaygroundCell[][] playground1Raw = content.getOwnPlayground(content.getPlayer1()).ownView();
        IPlaygroundCell[][] playground2Raw = content.getOwnPlayground(content.getPlayer2()).ownView();
        for (int row = 0; row < content.getRows(); row++) {
            for (int column = 0; column < content.getColumns(); column++){
                playground1.add(createPersistentPlaygroundItem1(hContent, row, column, playground1Raw));
                playground2.add(createPersistentPlaygroundItem2(hContent, row, column, playground2Raw));
            }
        }
        hContent.setPlayground1(playground1);
        hContent.setPlayground2(playground2);
        
        return hContent;
    }
    
    private IPlaygroundCell[][] loadPlayground(List<PersistentPlaygroundItem> playground, int rows, int columns) {
        IPlaygroundCell[][] matrix = new PlaygroundCell[rows][columns]; 

        for (int index = 0; index < playground.size(); index++) {
            matrix[playground.get(index).getRowcell()][playground.get(index).getColumncell()] = new PlaygroundCell(playground.get(index).getStatus(), playground.get(index).getShipId());
        }
        return matrix;
    }
    
    /**
     * Method to extract playground 1 from a PersistentGameContent. Note that for different database implementations it
     * could be required to Overwrite this method, to be conform with the storage of the data by the database
     * @param The PersistentGameContent
     * @return playground as a IPlaygroundCell matrix
     */
    protected IPlaygroundCell[][] loadPlayground1(PersistentGameContent hcontent) {
        return loadPlayground(hcontent.getPlayground1(), hcontent.getRows(), hcontent.getColumns());
    }

    /**
     * Method to extract playground 2 from a PersistentGameContent. Note that for different database implementations it
     * could be required to Overwrite this method, to be conform with the storage of the data by the database
     * @param The PersistentGameContent
     * @return playground as a IPlaygroundCell matrix
     */
    protected IPlaygroundCell[][] loadPlayground2(PersistentGameContent hcontent) {
        return loadPlayground(hcontent.getPlayground2(), hcontent.getRows(), hcontent.getColumns());
    }
    
    /**
     * Method to store playground 1 from a IGameContent to a PersistenGameContent. Note that for different database implementations it
     * could be required to Overwrite this method, to be conform with the storage of the data by the database
     * @return PersistentPlaygroundItem object
     */
    protected PersistentPlaygroundItem createPersistentPlaygroundItem1(PersistentGameContent hContent, int row, int column, IPlaygroundCell[][] playground1Raw) {
        return new PersistentPlaygroundItem(null, 1, row, column, playground1Raw[row][column].get(), playground1Raw[row][column].getShipId());
    }
    
    /**
     * Method to store playground 2 from a IGameContent to a PersistenGameContent. Note that for different database implementations it
     * could be required to Overwrite this method, to be conform with the storage of the data by the database
     * @return PersistentPlaygroundItem object
     */
    protected PersistentPlaygroundItem createPersistentPlaygroundItem2(PersistentGameContent hContent, int row, int column, IPlaygroundCell[][] playground2Raw) {
        return new PersistentPlaygroundItem(null, 2, row, column, playground2Raw[row][column].get(), playground2Raw[row][column].getShipId());
    }
}
