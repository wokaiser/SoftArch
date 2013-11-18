package database;

import interfaces.IGameContent;

import java.util.ArrayList;
import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

import controller.GameContent;

public class Db4oDatabase extends AbstractDatabase {
    private static final String FILENAME = "Db4o_Database";
    private static final int FIRST = 0;
    
    private ObjectContainer database;
    
    /**
     * Creats a new Db4o database and opens it
     */
    public Db4oDatabase() {
        database = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), FILENAME);
    }

    /**
     * Load a game from the database
     * @param name of the game
     * @return a GameContent
     */
    @Override
    public IGameContent load(String name) {
        Query query = database.query();
        query.constrain(GameContent.class);
        query.descend("name").constrain(String.valueOf(name)).equal();
        ObjectSet<GameContent> result = query.execute();        
        if(result.isEmpty()) {
            getStatus().addError(SAVEGAME_NOT_EXIST);
            return null;
        } else {
            GameContent content = result.get(FIRST);
            getStatus().addText("Successfully loaded game. "+content.getActivePlayer() + " please select your target.");
            return content;
        }
    }

    /**
     * Load the name of all games from the database and return it as a list.
     * @return A list of Strings with the names
     */
    @Override
    public List<String> getAll() {        
        List<String> result = new ArrayList<String>();
        List<GameContent> tmp = database.query(GameContent.class);
        for (GameContent gameContent : tmp) {
            result.add(gameContent.getName());
        }
        return result;
    }

    /**
     * Save a game to the database
     * @param name of the game
     * @param the GameContent to save
     * @return true if save was successful, otherwise false
     */
    @Override
    public void save(String name, IGameContent content) {
        if(isNameAlreadyUsed(name)) {
            getStatus().addError(SAVEGAME_NAME_EXIST);
        } else {
            content.setName(name);
            database.store(content);
        }
    }

    /**
     * Delete a game from the database
     * @param name of the game
     * @return true if delete was successful, otherwise false
     */
    @Override
    public void delete(String name) {
        IGameContent content = load(name);
        if (null != content) {
            database.delete(content);
        }
    }    
    /**
     * Determines if name is already used
     * @param name The name to be checked
     * @return true if name is already in use
     */
    private boolean isNameAlreadyUsed(String name) {
        List<String> names = getAll();        
        return names.contains(name);
    }
}
