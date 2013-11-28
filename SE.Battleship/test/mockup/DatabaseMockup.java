package mockup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Status;
import interfaces.IDatabase;
import interfaces.IGameContent;
import interfaces.IStatus;

/**
 * Class only to be used for testing!
 * @author Wolfgang
 *
 */
public class DatabaseMockup implements IDatabase {
	
	private Map<String, IGameContent> database;

	public DatabaseMockup() {
		database = new HashMap<String, IGameContent>();
	}

	@Override
	public IGameContent load(String name) {
		return database.get(name); 
	}

	@Override
	public List<String> getAll() {
		List<String> result  = new ArrayList<String>();
		for (Map.Entry<String, IGameContent> entry : database.entrySet()) {
			result.add(entry.getKey());
		}
		return result;
	}

	@Override
	public void save(String name, IGameContent content) {
		database.put(name, content);
	}

	@Override
	public void delete(String name) {
		database.remove(name);
	}

	@Override
	public IStatus getStatus() {
		return new Status();
	}

}
