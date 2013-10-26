package playground;

import static org.junit.Assert.*;
import model.general.Constances;
import model.playground.PlaygroundEnemyView;

import org.junit.*;

public class PlaygroundEnemyViewTest {
	
	private static final int ARRAYLENGTH = 586;
	private PlaygroundEnemyView view;
	
	@Before
	public void setUp() {
		view = new PlaygroundEnemyView();
	}
	
	@Test
	public void testPlaygroundOwnView() {
		try {
			new PlaygroundEnemyView();
		} catch (Exception exc) {
			fail("Should not have thrown exception at constructor!");
		}
	}
	
	@Test
	public void testtoString() {
		char[][] tmp = new char[Constances.DEFAULT_ROWS][Constances.DEFAULT_COLUMNS];
		String result = view.toString(tmp, Constances.DEFAULT_ROWS, Constances.DEFAULT_COLUMNS);
		assertTrue(result != null);
		assertTrue(result.length() == ARRAYLENGTH);
	}
	
	@Test
	public void testget() {
		char[][] tmp = new char[Constances.DEFAULT_ROWS][Constances.DEFAULT_COLUMNS];
		char[][] result = view.get(tmp, Constances.DEFAULT_ROWS, Constances.DEFAULT_COLUMNS);
		assertNotNull(result);
		assertTrue(tmp.length == result.length);		
	}
}
