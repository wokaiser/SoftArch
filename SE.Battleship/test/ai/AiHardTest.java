package ai;

import interfaces.IAi;
import interfaces.ICoordinates;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ai.AiHard;
import model.general.Constances;
import model.playground.Coordinates;
import static org.junit.Assert.*;

public class AiHardTest {
	
    private IAi ai;
    private final int ROWS = 12;
    private final int COLUMNS = 12;
    
    @Before
    public void setup() {
        ai = new AiHard();
        ai.initialize(ROWS, COLUMNS);
    }
    
    @After
    public void after() {

    }
    

	@Test
	public void testShoot() throws Exception {
	    ICoordinates next;
	    ICoordinates coord = ai.getCoordinates();
	    ai.shotResult(Constances.SHOOT_HIT);
	    Coordinates expected1 = new Coordinates(coord.getRow(), coord.getColumn() - 1);
	    Coordinates expected2 = new Coordinates(coord.getRow() - 1, coord.getColumn());
	    Coordinates expected3 = new Coordinates(coord.getRow(), coord.getColumn() + 1);
	    Coordinates expected4 = new Coordinates(coord.getRow() + 1, coord.getColumn());
	    
	    if (coord.getColumn() > 0) {
	        next = ai.getCoordinates();
	        assertTrue(next.equals(expected1));
	        ai.shotResult(Constances.SHOOT_MISS);
	    }
	    if (coord.getRow() > 0) {
            next = ai.getCoordinates();
            assertTrue(next.equals(expected2));
            ai.shotResult(Constances.SHOOT_MISS);
        }
        if (coord.getColumn() < COLUMNS-1) {
            next = ai.getCoordinates();
            assertTrue(next.equals(expected3));
            ai.shotResult(Constances.SHOOT_MISS);
        }
        if (coord.getColumn() < ROWS-1) {
            next = ai.getCoordinates();
            assertTrue(next.equals(expected4));
            ai.shotResult(Constances.SHOOT_MISS);
        }
	}
}
