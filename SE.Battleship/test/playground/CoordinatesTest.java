package playground;

import org.junit.Test;
import model.playground.Coordinates;

import static org.junit.Assert.*;

public class CoordinatesTest {

	@Test
	public void testCoordinates() throws Exception {
		try {
			//test if it's possible to create Coordinate Object with too
			//small max row and column value
			new Coordinates(0, 1);
			fail("Should have raised an IllegalArgumentException");
		}
		catch (IllegalArgumentException expected) {
			
		}
		try {
			//test if it's possible to create Coordinate Object with too
			//small max row and column value
			new Coordinates(1, 0);
			fail("Should have raised an IllegalArgumentException");
		}
		catch (IllegalArgumentException expected) {
			
		}
	}
	@Test
	public void testSetRowGetRowSetColumnGetColumn() throws Exception {
		//create a coordinate object with max row and column of 5
		Coordinates c = new Coordinates(5, 5);
		//check if row and colmn is default 0
		assertTrue(0 == c.getRow());
		assertTrue(0 == c.getColumn());
		//try to set row and column over max row and column (range is from 0-4)
		assertFalse(c.setRow(5));
		assertFalse(c.setColumn(5));
		//try to set row and column below max
		assertTrue(c.setRow(4));
		assertTrue(c.setColumn(3));
		//get row and column and check if it is as expected
		assertTrue(4 == c.getRow());
		assertTrue(3 == c.getColumn());		
	}
}