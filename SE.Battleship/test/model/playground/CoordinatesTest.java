package model.playground;

import interfaces.ICoordinates;

import org.junit.Test;

import model.playground.Coordinates;
import static org.junit.Assert.*;

public class CoordinatesTest {
	@Test
	public void testCoordinates() throws Exception {
	    ICoordinates c = new Coordinates(5, 5);
	    new Coordinates(c);
	}
    @Test
    public void testGetter() throws Exception {
        ICoordinates c = new Coordinates(5, 7);
        ICoordinates d = new Coordinates(c);
        assertTrue(c.getRow() == 5);
        assertTrue(d.getRow() == 5);
        assertTrue(c.getColumn() == 7);
        assertTrue(d.getColumn() == 7);
    }
   @Test
   public void testToString() throws Exception {    
        //create a coordinate object
        String c = new Coordinates(1, 2).toString();
        //check if row and column is as expected
        assertTrue(0 == c.compareTo("2 1"));
   }
   @Test    
   public void testEquals() throws Exception {
       //create a coordinate object
       ICoordinates c = new Coordinates(5, 5);
       //check if row and column is as expected
       assertTrue(c.equals(new Coordinates(5, 5)));
       assertFalse(c.equals(new Coordinates(4, 5)));
       assertFalse(c.equals(new Coordinates(5, 4)));
       assertFalse(c.equals(1));
       assertFalse(c.equals(null));
   }
   @Test    
   public void testCompareTo() throws Exception {
       //create a coordinate object
       ICoordinates a = new Coordinates(5, 5);
       ICoordinates b = new Coordinates(5, 5);
       ICoordinates c = new Coordinates(4, 5);
       ICoordinates d = new Coordinates(5, 4);
       ICoordinates e = new Coordinates(4, 4);
       //check if row and column is as expected
       assertTrue(0 == a.compareTo(b));
       assertTrue(1 == a.compareTo(c));
       assertTrue(1 == a.compareTo(d));
       assertTrue(1 == a.compareTo(e));
       assertTrue(-1 == e.compareTo(a));
   }
}
