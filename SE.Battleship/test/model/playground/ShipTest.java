package model.playground;

import org.junit.Test;
import model.playground.Ship;

import static org.junit.Assert.*;

public class ShipTest {
	
	// Test if it is possible to create a Ship with false arguments
	@Test
	public void testShip() throws Exception {
		try {
			new Ship("Test", 1, 's');
			fail("Should have raised an IllegalArgumentException");
		} catch(IllegalArgumentException expected) {
			
		}
		try {
			new Ship("", 2, 's');
			fail("Should have raised an IllegalArgumentException");
		} catch(IllegalArgumentException expected) {
			
		}
	}
	// Test if it returns correct name
	@Test
	public void testgetName() throws Exception {
		Ship ship = new Ship("Test", 3, 's');
		assertTrue("Test" == ship.getName());
	}
	// Test if it returns correct length
	@Test
	public void testgetLengtht() throws Exception {
		Ship ship = new Ship("Test", 3, 's');
		assertTrue(3 == ship.getLength());
	}
	// Test if it returns correct Id
	@Test
	public void testgetId() throws Exception {
		Ship ship = new Ship("Test", 3, 's');
		assertTrue('s' == ship.getId());
	}
	// Test if ship can be destroyed
	@Test
	public void testisDestroyed() throws Exception {
		Ship ship = new Ship("Test", 2, 's');
		assertFalse(ship.isDestroyed());
		ship.setDamage();
		ship.setDamage();
		assertTrue(ship.isDestroyed());
		try {
		    //try to damage ship again which should not be possible because ship is already destroyed.
		    ship.setDamage();
		    fail("Should have raised an IllegalArgumentException");
		} catch (IllegalStateException expected) {

		}
	}
	// Test if ship could be damaged
	@Test
	public void testsetDamage() throws Exception {
		Ship ship = new Ship("Test", 2, 's');
		ship.setDamage();
		assertFalse(ship.isDestroyed());
		ship.setDamage();
		assertTrue(ship.isDestroyed());		
	}
	// Test if ship could be damaged
    @Test
    public void testEquals() throws Exception {
        Ship ship1 = new Ship("Test", 2, 'a');
        Ship ship2 = new Ship("Test", 2, 'b');
        Ship ship3 = new Ship("Test3", 4, 'b');
        assertFalse(ship1.equals(null));
        assertFalse(ship1.equals(ship2));
        assertTrue(ship2.equals(ship3));
    }
}
