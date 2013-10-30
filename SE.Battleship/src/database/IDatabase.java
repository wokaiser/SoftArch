package database;

import java.util.List;

public interface IDatabase {
	
	GameContent load(String name);
	
	List<String> getAll();
	
	boolean save(String name, GameContent content);
	
	boolean delete(String name);
	
}
