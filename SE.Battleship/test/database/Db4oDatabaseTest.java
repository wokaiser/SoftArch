package database;

import static org.junit.Assert.*;
import interfaces.IGameContent;

import java.util.List;

import org.junit.*;
import org.junit.runners.MethodSorters;

import ai.AiEasy;
import controller.GameContent;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Db4oDatabaseTest {
	
	private Db4oDatabase database;
	private String name = "test123";
	private String name2 = "test1234";
	private String name3 = "test12345";
		
	@Test
	public void testDb4oDatabase() {
		try {
			database = new Db4oDatabase();
		} catch (Exception exc) {
			fail("Should not fail at this point.");
		}
	}
	
	@Test
	public void testGetStatus() {
		database = new Db4oDatabase();
		assertNotNull(database.getStatus());
	}
	
	@Test
	public void test1save() {
		IGameContent content = generateContent(name);
		database = new Db4oDatabase();
		try {
			database.save(name, content);
		} catch (Exception exc) {
			fail("Should not fail at this point. ");
		}
		assertTrue(database.getStatus().getError().isEmpty());		
	}
	
	@Test
	public void test2load() {
		database = new Db4oDatabase();
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
		database = new Db4oDatabase();
		IGameContent content2 = generateContent(name2);
		IGameContent content3 = generateContent(name3);
		database.save(name2, content2);
		database.save(name3, content3);
		
		List<String> result = database.getAll();
		assertNotEquals(0, result.size());		
	}
	
	@Test
	public void test4delete() {
		database = new Db4oDatabase();
		try {
			database.delete(name);
			database.delete(name2);
			database.delete(name3);
		} catch (Exception exc) {
			fail("Should not fail at this point.");
		}
		assertTrue(database.getStatus().getError().isEmpty());
	}
	
	@After
	public void teadDown() {
		database.close();
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
