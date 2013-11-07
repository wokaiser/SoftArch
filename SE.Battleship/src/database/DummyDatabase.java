package database;

import java.util.List;

public class DummyDatabase implements IDatabase{

	@Override
	public GameContent load(String name) {
		return null;
	}

	@Override
	public List<String> getAll() {
		return null;
	}

	@Override
	public boolean save(String name, GameContent content) {
		return false;
	}

	@Override
	public boolean delete(String name) {
		return false;
	}

}
