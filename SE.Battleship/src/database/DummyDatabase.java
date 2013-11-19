package database;


import interfaces.IGameContent;
import interfaces.IPlaygroundCell;

import java.util.LinkedList;
import java.util.List;

import model.playground.PlaygroundCell;

public class DummyDatabase extends AbstractDatabase {

    /**
     * Do nothing, because this is a dummy implementation
     * @param name of the game
     * @return null
     */
    @Override
    public IGameContent load(String name) {       
        getStatus().addText("Dummy database load was called.");
        return null;
    }

    /**
     * Do nothing, because this is a dummy implementation
     * @return null
     */
    @Override
    public List<String> getAll() {
        return new LinkedList<String>();
    }

    /**
     * Do nothing, because this is a dummy implementation
     * @param name of the game
     * @param GameContent to save
     */
    @Override
    public void save(String name, IGameContent content) {
        getStatus().addText("Dummy database save was called.");
    }

    /**
     * Do nothing, because this is a dummy implementation
     * @param name of the game
     */
    @Override
    public void delete(String name) {
        getStatus().addText("Dummy database delete was called.");
    }

    @Override
    protected IPlaygroundCell[][] loadPlayground1(PersistentGameContent hcontent) {
        /* dummy database does not need any data mapping, so this method can be empty */
        return new PlaygroundCell[0][0];
    }

    @Override
    protected IPlaygroundCell[][] loadPlayground2(PersistentGameContent hcontent) {
        /* dummy database does not need any data mapping, so this method can be empty */
        return new PlaygroundCell[0][0];
    }
    
    @Override
    protected PersistentPlaygroundItem createPersistentPlaygroundItem1(PersistentGameContent hContent, int row, int column, IPlaygroundCell[][] playground1Raw) {
        /* dummy database does not need any data mapping, so this method can be empty */
        return new PersistentPlaygroundItem();
    }
    
    @Override
    protected PersistentPlaygroundItem createPersistentPlaygroundItem2(PersistentGameContent hContent, int row, int column, IPlaygroundCell[][] playground2Raw) {
        /* dummy database does not need any data mapping, so this method can be empty */
        return new PersistentPlaygroundItem();
    }

}
