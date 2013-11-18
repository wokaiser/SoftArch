package database;


import interfaces.IGameContent;

import java.util.LinkedList;
import java.util.List;

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

}
