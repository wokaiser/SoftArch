package database;

import interfaces.IGameContent;

import java.util.ArrayList;
import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

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
     * This method is ONLY to be used for testing purposes!
     */
    public void close() {
    	database.close();
    }

    /**
     * Load a game from the database
     * @param name of the game
     * @return a GameContent
     */
    @Override
    public IGameContent load(String name) {
        Query query = database.query();
        query.constrain(PersistentGameContent.class);
        query.descend("gameContentId").constrain(String.valueOf(name)).equal();
        PersistentGameContent hContent = loadPersistent(name);       
        if(null == hContent) {
            getStatus().addError(SAVEGAME_NOT_EXIST);
            return null;
        } else {
            IGameContent content = map(hContent);
            getStatus().addText("Successfully loaded game. "+content.getActivePlayer() + " please select your target.");
            return content;
        }
    }
    
    private PersistentGameContent loadPersistent(String name) {
        Query query = database.query();
        query.constrain(PersistentGameContent.class);
        query.descend("gameContentId").constrain(String.valueOf(name)).equal();
        ObjectSet<PersistentGameContent> result = query.execute();        
        if(result.isEmpty()) {
            return null;
        } else {
            return result.get(FIRST);
        }    
    }

    /**
     * Load the name of all games from the database and return it as a list.
     * @return A list of Strings with the names
     */
    @Override
    public List<String> getAll() {        
        List<String> result = new ArrayList<String>();
        List<PersistentGameContent> tmp = database.query(PersistentGameContent.class);
        for (PersistentGameContent gameContent : tmp) {
            result.add(gameContent.getId());
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
        if(getAll().contains(name)) {
            getStatus().addError(SAVEGAME_NAME_EXIST);
        } else {
            PersistentGameContent hContent = map(content);
            database.store(hContent);
        }
    }

    /**
     * Delete a game from the database
     * @param name of the game
     * @return true if delete was successful, otherwise false
     */
    @Override
    public void delete(String name) {
        PersistentGameContent hContent = loadPersistent(name);
        if (null != hContent) {
            database.delete(hContent);
        }
    }
}
