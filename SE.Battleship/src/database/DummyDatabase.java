package database;


import java.util.LinkedList;
import java.util.List;

import controller.GameContent;

public class DummyDatabase extends AbstractDatabase {

    /**
     * Do nothing, because this is a dummy implementation
     * @param name of the game
     * @return null
     */
    @Override
    public GameContent load(String name) {
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
    public void save(String name, GameContent content) {
        
    }

    /**
     * Do nothing, because this is a dummy implementation
     * @param name of the game
     */
    @Override
    public void delete(String name) {

    }

}
