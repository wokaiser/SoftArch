package highscore;

import static org.junit.Assert.*;
import interfaces.IHighscoreEntry;

import java.util.List;

import org.junit.*;

public class DummyHighscoreTest {
	
	private DummyHighscore high;
	private String player = "test123";
	private long score = 123;
	
	@Test
	public void testHtwgHighscoreEntry() {
		try {
			high = new DummyHighscore();
		} catch (Exception exc) {
			fail("Should not throw exception at this point.");
		}
	}
	
	@Test
	public void testAdd() {
		high = new DummyHighscore();
		List<IHighscoreEntry> all = high.getAll();
		assertEquals(high.getAll(), all);
		high.add(new HighscoreEntry(player, score));
		assertNotEquals(null, all);
	}
	
	@Test
	public void testString() {
		high = new DummyHighscore();
		assertNotEquals(null, high.toString());
	}

}
