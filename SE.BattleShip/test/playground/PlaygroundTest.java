package playground;

import general.Constances;
import junit.framework.TestCase;
import playground.Playground;

public class PlaygroundTest extends TestCase {
	
	public void testPlayground() throws Exception {
		try {
			//test if it's possible to create a too small playground
			//minimum rows and columns should be 12
			new Playground(11, 12);
			fail("Should have raised an IllegalArgumentException");
		}
		catch (IllegalArgumentException expected) {
		}
		try {
			//test if it's possible to create a too small playground
			//minimum rows and columns should be 12
			new Playground(12, 11);
			fail("Should have raised an IllegalArgumentException");
		}
		catch (IllegalArgumentException expected) {
		}
	}
	public void testPlaceShipWithRowOutOfRange() throws Exception {
		try {
			//declarate a playground
			Playground playground = new Playground(12, 12);
			//declarate a coordinate object with a too big row and column lengh
			Coordinates target = new Coordinates(13, 13);
			//declarate ship
			Ship ship = new Ship("Ship", 1, 'A');
			//set row out of range from playground
			target.setRow(12);
			//shoot
			assertFalse(playground.placeShip(target, ship, Playground.DIRECTION_RIGHT));
		}
		catch (IllegalArgumentException expected) {
			
		}
	}
	public void testPlaceShipWithColumnOutOfRange() throws Exception {
		try {
			//declarate a playground
			Playground playground = new Playground(12, 12);
			//declarate a coordinate object with a too big row and column lengh
			Coordinates target = new Coordinates(13, 13);
			//declarate ship
			Ship ship = new Ship("Ship", 1, 'A');
			//set row out of range from playground
			target.setColumn(12);
			//shoot
			assertFalse(playground.placeShip(target, ship, Playground.DIRECTION_RIGHT));
		}
		catch (IllegalArgumentException expected) {
			
		}
	}
	
	public void testPlaceShip() throws Exception {
		//declarate a playground
		Playground playground = new Playground(12, 12);
		//declarate a coordinate object with a too big row and column lengh
		Coordinates target = new Coordinates(12, 12);
		//declarate ship
		Ship shipA = new Ship("Ship A", 3, 'A');
		Ship shipB = new Ship("Ship B", 4, 'B');
		Ship shipC = new Ship("Ship C", 2, 'C');
		Ship shipD = new Ship("Ship D", 2, 'D');

		target.setColumn(0);
		target.setRow(0);
		assertTrue(playground.placeShip(target, shipA, Playground.DIRECTION_RIGHT));
		//try to place the ship again on the same Coordinates
		assertFalse(playground.placeShip(target, shipA, Playground.DIRECTION_RIGHT));
		target.setRow(2);
		target.setColumn(1);
		//try to place the ship which already exists on other Coordinates
		assertFalse(playground.placeShip(target, shipA, Playground.DIRECTION_RIGHT));
		assertTrue(playground.placeShip(target, shipB, Playground.DIRECTION_RIGHT));
		target.setRow(11);
		target.setColumn(3);
		assertTrue(playground.placeShip(target, shipC, Playground.DIRECTION_RIGHT));
		target.setColumn(0);
		target.setRow(0);
		assertTrue(Constances.SHOOT_HIT == playground.shoot(target));
		target.setColumn(1);
		assertTrue(Constances.SHOOT_HIT == playground.shoot(target));
		target.setColumn(2);
		assertTrue(Constances.SHOOT_DESTROYED == playground.shoot(target));
		//try to place the ship on a area which was already shot
		assertFalse(playground.placeShip(target, shipD, Playground.DIRECTION_RIGHT));
	}
	
	public void testShootWithRowOutOfRange() throws Exception {
		try {
			//declarate a playground
			Playground playground = new Playground(12, 12);
			//declarate a coordinate object with a too big row and column lengh
			Coordinates target = new Coordinates(13, 13);
			//set row out of range from playground
			target.setRow(12);
			//shoot
			playground.shoot(target);
			fail("Should have raised an IllegalArgumentException");
		}
		catch (IllegalArgumentException expected) {
			
		}
	}
	public void testShootWithColumnOutOfRange() throws Exception {
		try {
			//declarate a playground
			Playground playground = new Playground(12, 12);
			//declarate a coordinate object with a too big row and column lengh
			Coordinates target = new Coordinates(13, 13);
			//set row out of range from playground
			target.setColumn(12);
			//shoot
			playground.shoot(target);
			fail("Should have raised an IllegalArgumentException");
		}
		catch (IllegalArgumentException expected) {
			
		}
	}
	
	public void testToString() throws Exception {
		String matrix = "";
		Playground playground = new Playground(12, 12);
		Coordinates target = new Coordinates(12, 12);
		for (int row = 0; row < 12; row++) {
			for (int column = 0; column < 12; column++){
				matrix += "0 ";
			}
			matrix += "\n";
		}
		//place a ship on the playground and check if the ship is visible
		//(ships should be not visible)
		Ship shipA = new Ship("Ship in testAlreadyShot", 3, 'A');
		//place a ship
		assertTrue(playground.placeShip(target, shipA, Playground.DIRECTION_DOWN));
		//shoot to the hip
		assertTrue(Constances.SHOOT_HIT == playground.shoot(target));
		
		System.out.println(playground.enemyView());
		System.out.println(playground.ownView());
		
	}
	
	public void testAlreadyShot() throws Exception {
		//declarate a playground
		Playground playground = new Playground(12, 12);
		//declarate a coordinate object with a too big row and column lengh
		Coordinates target = new Coordinates(12, 12);
		//declarate ship
		Ship shipA = new Ship("Ship in testAlreadyShot", 2, 'A');
		//place a ship
		assertTrue(playground.placeShip(target, shipA, Playground.DIRECTION_DOWN));
		//check if already shot to ship and to area where no ship is placed
		assertFalse(playground.alreadyShot(target));
		target.setColumn(2);
		assertFalse(playground.alreadyShot(target));
		//shoot to area, where no ship is placed
		assertFalse(Constances.SHOOT_HIT == playground.shoot(target));
		assertTrue(playground.alreadyShot(target));
		//shoot to area where ship is placed
		target.setColumn(0);
		assertTrue(Constances.SHOOT_HIT == playground.shoot(target));
		assertTrue(playground.alreadyShot(target));		
	}
	
	public void testGetNumberOfExistingShipsGetNumberOfPlacedShipsGetNumberOfDestroyedShips() throws Exception {
		//declarate a playground
		Playground playground = new Playground(12, 12);
		//declarate a coordinate object with a too big row and column lengh
		Coordinates target = new Coordinates(12, 12);
		//declarate ship
		Ship shipA = new Ship("Ship in testGetNumberOfExistingShipsGetNumberOfPlacedShipsGetNumberOfDestroyedShips", 2, 'A');
		//check if number of existing, destroyed and placed ships is 0
		assertTrue(playground.getNumberOfExistingShips() == 0);
		assertTrue(playground.getNumberOfPlacedShips() == 0);
		assertTrue(playground.getNumberOfDestroyedShips() == 0);
		
		//place a ship
		assertTrue(playground.placeShip(target, shipA, Playground.DIRECTION_DOWN));
		
		assertTrue(playground.getNumberOfExistingShips() == 1);
		assertTrue(playground.getNumberOfPlacedShips() == 1);
		assertTrue(playground.getNumberOfDestroyedShips() == 0);	
		
		//shoot the ship. Because the ship had length 1, the ship will be destroyed
		assertTrue(Constances.SHOOT_HIT == playground.shoot(target));
		assertTrue(target.setRow(1));
		assertTrue(Constances.SHOOT_DESTROYED == playground.shoot(target));
		assertTrue(target.setRow(2));
		assertTrue(Constances.SHOOT_MISS == playground.shoot(target));
		
		assertTrue(playground.getNumberOfExistingShips() == 0);
		assertTrue(playground.getNumberOfPlacedShips() == 1);
		assertTrue(playground.getNumberOfDestroyedShips() == 1);	
	}
}