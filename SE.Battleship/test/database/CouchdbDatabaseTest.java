package database;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;
import org.junit.runners.MethodSorters;

import interfaces.IGameContent;

import ai.AiEasy;

import controller.GameContent;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CouchdbDatabaseTest {
	
	private CouchdbDatabase database;
	private String name = "test123";
	private String name2 = "test1234";
	private String name3 = "test12345";
		
	@Test
	public void testCouchdbDatabase() {
		try {
			database = new CouchdbDatabase();
		} catch (Exception exc) {
			fail("Should not fail at this point.");
		}
	}
	
	@Test
	public void testGetStatus() {
		database = new CouchdbDatabase();
		assertNotNull(database.getStatus());
	}
	
	@Test
	public void test1save() {
		IGameContent content = generateContent(name);
		database = new CouchdbDatabase();
		try {
			database.save(name, content);
		} catch (Exception exc) {
			fail("Should not fail at this point. ");
		}
		assertTrue(database.getStatus().getError().isEmpty());		
	}
	
	@Test
	public void test2load() {
		database = new CouchdbDatabase();
		IGameContent content = null;
		try {
			content = database.load(name);
		} catch (Exception exc) {
			fail("Should not fail at this point.");
		}
		assertNotNull(content);
		assertFalse(database.getStatus().getText().isEmpty());
	}
	
	@Test
	public void test3getAll() {
		database = new CouchdbDatabase();
		IGameContent content2 = generateContent(name2);
		IGameContent content3 = generateContent(name3);
		database.save(name2, content2);
		database.save(name3, content3);
		
		List<String> result = database.getAll();
		assertNotEquals(0, result.size());		
	}
	
	@Test
	public void test4delete() {
		database = new CouchdbDatabase();
		try {
			database.delete(name);
			database.delete(name2);
			database.delete(name3);
		} catch (Exception exc) {
			fail("Should not fail at this point.");
		}
		assertTrue(database.getStatus().getError().isEmpty());
	}

	/**
	 * Generates a GameContent object for testing purposes
	 * @param name Name of the gamecontent
	 * @return The generated GameContent Object with ONLY default values
	 */
	private IGameContent generateContent(String name) {
		IGameContent content = new GameContent(new AiEasy(), new AiEasy());
		content.initContent(12, 12, "Player1", "Player2", 1);
		content.setName(name);
		return content;
	}
}
