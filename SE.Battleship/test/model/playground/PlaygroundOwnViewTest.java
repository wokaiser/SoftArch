package model.playground;

import static org.junit.Assert.*;
import interfaces.IPlaygroundCell;
import model.general.Constances;
import model.playground.PlaygroundOwnView;

import org.junit.*;

import com.fasterxml.jackson.databind.JsonNode;

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
	public void testtoJson() {
		IPlaygroundCell[][] tmp = new PlaygroundCell[Constances.DEFAULT_ROWS][Constances.DEFAULT_COLUMNS];
        for (int row = 0; row < Constances.DEFAULT_ROWS; row++) {
            for (int column = 0; column < Constances.DEFAULT_COLUMNS; column++) {
                tmp[row][column] = new PlaygroundCell();
            }
        }
		JsonNode result = view.toJson(tmp, Constances.DEFAULT_ROWS, Constances.DEFAULT_COLUMNS);
		assertTrue(result != null);
	}
	
	@Test
	public void testtoString() {
		IPlaygroundCell[][] tmp = new PlaygroundCell[Constances.DEFAULT_ROWS][Constances.DEFAULT_COLUMNS];
        for (int row = 0; row < Constances.DEFAULT_ROWS; row++) {
            for (int column = 0; column < Constances.DEFAULT_COLUMNS; column++) {
                tmp[row][column] = new PlaygroundCell();
            }
        }
		String result = view.toString(tmp, Constances.DEFAULT_ROWS, Constances.DEFAULT_COLUMNS);
		assertTrue(result != null);
		assertTrue(result.length() == ARRAYLENGTH);
	}
	
	@Test
	public void testget() {
	    PlaygroundCell[][] tmp = new PlaygroundCell[Constances.DEFAULT_ROWS][Constances.DEFAULT_COLUMNS];
	    for (int row = 0; row < Constances.DEFAULT_ROWS; row++) {
	        for (int column = 0; column < Constances.DEFAULT_COLUMNS; column++) {
	            tmp[row][column] = new PlaygroundCell();
	        }
	    }
		char[][] result = view.get(tmp, Constances.DEFAULT_ROWS, Constances.DEFAULT_COLUMNS);
		assertNotNull(result);
		assertTrue(tmp.length == result.length);		
	}
}
