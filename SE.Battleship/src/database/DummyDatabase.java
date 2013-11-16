package database;

import interfaces.IDatabase;

import java.util.LinkedList;
import java.util.List;

public class DummyDatabase implements IDatabase{

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
     * @return always false
     */
    @Override
    public boolean save(String name, GameContent content) {
        return false;
    }

    /**
     * Do nothing, because this is a dummy implementation
     * @param name of the game
     * @return always false
     */
    @Override
    public boolean delete(String name) {
        return false;
    }

}
