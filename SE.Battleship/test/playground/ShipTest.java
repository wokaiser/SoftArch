package playground;

import junit.framework.TestCase;

public class ShipTest extends TestCase {
	// Test if it is possible to create a Ship with false arguments
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
	public void testgetName() throws Exception {
		Ship ship = new Ship("Test", 3, 's');
		assertTrue("Test" == ship.getName());
	}
	// Test if it returns correct length
	public void testgetLengtht() throws Exception {
		Ship ship = new Ship("Test", 3, 's');
		assertTrue(3 == ship.getLength());
	}
	// Test if it returns correct Id
	public void testgetId() throws Exception {
		Ship ship = new Ship("Test", 3, 's');
		assertTrue('s' == ship.getId());
	}
	// Test if ship can be destroyed
	public void testisDestroyed() throws Exception {
		Ship ship = new Ship("Test", 2, 's');
		assertFalse(ship.isDestroyed());
		ship.setDamage();
		ship.setDamage();
		assertTrue(ship.isDestroyed());
	}
	// Test if ship could be damaged
	public void testsetDamage() throws Exception {
		Ship ship = new Ship("Test", 2, 's');
		ship.setDamage();
		assertFalse(ship.isDestroyed());
		ship.setDamage();
		assertTrue(ship.isDestroyed());		
	}
}
