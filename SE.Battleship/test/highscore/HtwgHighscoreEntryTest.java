package highscore;

import static org.junit.Assert.*;

import org.junit.*;

public class HtwgHighscoreEntryTest {
	
	private HtwgHighscoreEntry entry;
	private String player = "test123";
	private long score = 123;
	
	@Test
	public void testHtwgHighscoreEntry() {
		try {
			entry = new HtwgHighscoreEntry(player, score);
		} catch (Exception exc) {
			fail("Should not throw exception at this point.");
		}
	}
	
	@Test
	public void testGetPlayer() {
		entry = new HtwgHighscoreEntry(player, score);
		assertEquals(player, entry.getPlayer());
	}
	
	@Test
	public void testGetScore() {
		entry = new HtwgHighscoreEntry(player, score);
		assertEquals(score, entry.getScore());
	}

}
