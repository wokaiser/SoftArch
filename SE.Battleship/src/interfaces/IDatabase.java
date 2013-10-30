package interfaces;

import java.util.List;
import model.general.GameContent;

public interface IDatabase {
	
	public GameContent load(String name);
	
	public List<String> getAll();
	
	public boolean save(String name, GameContent content);
	
	public boolean delete(String name);
	
}
