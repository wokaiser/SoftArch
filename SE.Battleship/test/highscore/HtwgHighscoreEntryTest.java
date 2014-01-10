package highscore;

import static org.junit.Assert.*;

import org.junit.*;

public class HtwgHighscoreEntryTest {
	
	private HighscoreEntry entry;
	private String player = "test123";
	private long score = 123;
	
	@Test
	public void testHtwgHighscoreEntry() {
		try {
			entry = new HighscoreEntry(player, score);
		} catch (Exception exc) {
			fail("Should not throw exception at this point.");
		}
	}
	
	@Test
	public void testGetPlayer() {
		entry = new HighscoreEntry(player, score);
		assertEquals(player, entry.getPlayer());
	}
	
	@Test
	public void testGetScore() {
		entry = new HighscoreEntry(player, score);
		assertEquals(score, entry.getScore());
	}

}
