package interfaces;

import java.util.List;

import model.playground.GameContent;

public interface IDatabase {
	
	GameContent load(String name);
	
	List<String> getAll();
	
	boolean save(String name, GameContent content);
	
	boolean delete(String name);
	
}
