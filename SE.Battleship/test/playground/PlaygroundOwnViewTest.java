package playground;

import static org.junit.Assert.*;
import model.general.Constances;
import model.playground.PlaygroundOwnView;

import org.junit.*;

public class PlaygroundOwnViewTest {
	
	private static final int ARRAYLENGTH = 586;
	private PlaygroundOwnView view;
	
	@Before
	public void setUp() {
		view = new PlaygroundOwnView();
	}
	
	@Test
	public void testPlaygroundOwnView() {
		try {
			new PlaygroundOwnView();
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
