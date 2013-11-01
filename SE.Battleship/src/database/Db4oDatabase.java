package database;

import java.util.ArrayList;
import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.Db4oException;
import com.db4o.query.Query;

public class Db4oDatabase implements IDatabase {
	
	private static final String FILENAME = "Db4o_Database";
	private static final int FIRST = 0;
	
	private ObjectContainer database;
	
	/**
	 * Creats a new Db4o database and opens it
	 */
	public Db4oDatabase() {
		database = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), FILENAME);
	}

	@Override
	public GameContent load(String name) {
		Query query = database.query();
		query.constrain(GameContent.class);
		query.descend("name").constrain(String.valueOf(name)).equal();
		ObjectSet<GameContent> result = query.execute();		
		if(result.isEmpty()) {
			throw new Db4oException("No GameContent with name: " + name + "found!");
		}		
		return result.get(FIRST);
	}

	@Override
	public List<String> getAll() {		
		List<String> result = new ArrayList<String>();
		List<GameContent> tmp = database.query(GameContent.class);
		for (GameContent gameContent : tmp) {
			result.add(gameContent.getName());
		}
		return result;
	}

	@Override
	public boolean save(String name, GameContent content) {
		if(isNameAlreadyUsed(name)) {
			return false;
		}
		content.setName(name);
		database.store(content);
		return true;
	}

	@Override
	public boolean delete(String name) {
		try {
			database.delete(load(name));
		} catch (Exception exc) {
			return false;
		}
		return true;
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
