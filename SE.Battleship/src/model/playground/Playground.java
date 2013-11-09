package model.playground;

import java.util.LinkedList;
import java.util.List;

import model.general.Constances;
import model.general.Status;

/** 
 * The Playground class provide all operation which are necessary to interact with
 * a playground (shoot to playground, place ships,..).
 * @author Dennis Parlak
 */
public class Playground extends AbstractPlayground {
	public static final int DIRECTION_UP = 0;
	public static final int DIRECTION_DOWN = 1;
	public static final int DIRECTION_LEFT = 2;
	public static final int DIRECTION_RIGHT = 3;
	private List<Ship> ships;
	private int numberOfDestroyedShips;
	private Status status;
	
	/**
	 * Create a Playground object.
	 * @param rows The number of columns of the playground
	 * @param columns The number of rows of the playground
	 */
	public Playground(int rows, int columns) {
		super(rows, columns);
		this.ships = new LinkedList<Ship>();
		this.status = new Status();
	}
	
	/**
	 * Create a Playground object.
	 * @param rows The number of columns of the playground
	 * @param columns The number of rows of the playground
	 */
	public Playground(char[][] matrix) {
		super(matrix);
		this.ships = new LinkedList<Ship>();
		this.status = new Status();
	}
	
	/**
	 * Return the status object, which log information about the program flow after
	 * calling e.g. shoot(..)  method.
	 * @return status The Status object.
	 */
	public Status getStatus() {
		return status;
	}
	
	/**
	 * Method to place a ship on given Coordinates into a given direction.
	 * @param target The Coordinates where to place the ship
	 * @param ship The Ship to place
	 * @param direction The direction in which to place the ship
	 * @return true if the ship could be placed.
	 * @return false if the ship could not be placed.
	 */
	public boolean placeShip(Coordinates target, Ship ship, int direction) {
		//get column and row, from where to start with placing the ship
		int row = target.getRow();
		int column = target.getColumn();
		
		//loop until the ship is complete placed
		for (int shipSize = ship.getLength(); shipSize > 0; shipSize--) {
			//check if the row and column is not in range
			if (!this.checkCoordinates(row, column)) {
				status.addError("Ship cannot be placed, because Coordinates are invalid.");
				return false;
			}
			
			//check if a ship is too near
			if (0 != checkForNearShips(row, column)) {
				return false;
			}	
			row += updateVerticalDirection(direction);
			column += updateHorizontalDirection(direction);
		}
		//try to add the ship and check if it failed
		if (!this.addShip(ship)) {
			return false;
		}
		//place the ship on the playground
		this.placeShipOnPlayground(target, ship, direction);
		return true;
	}
	
	private int updateVerticalDirection(int direction) {
		if (DIRECTION_UP == direction) {
			return -1;
		}
		else if (DIRECTION_DOWN == direction) {
			return +1;
		}
		/* default not moved into any direction */
		return 0;
	}
	
	private int updateHorizontalDirection(int direction) {
		if (DIRECTION_LEFT == direction) {
			return -1;
		}
		else if (DIRECTION_RIGHT == direction) {
			return +1;
		}
		/* default not moved into any direction */
		return 0;
	}
		
	/**
	 * Method to shoot on given Coordinates
	 * @param target The Coordinates which where to shoot.
	 * @return true if a ship was hit
	 * @return false if no ship was hit
	 */
	public int shoot(Coordinates target) {
		int row = target.getRow();
		int column = target.getColumn();
		if (alreadyShot(target)) {
			throw new IllegalArgumentException("Already shot to target.");
		}
		//check if something was hit
		if (Constances.MATRIX_INIT != this.get(row, column)) {
			char shipId = this.get(row, column);
			this.set(row, column, Constances.MATRIX_HIT);
			return this.updateShipsAfterHit(shipId);
		}
		status.addText("No ship was hit.");	
		this.set(row, column, Constances.MATRIX_MISS);
		return Constances.SHOOT_MISS;
	}
	
	/**
	 * Method to check if on given Coordinates where already a shot.
	 * @param target The Coordinates which to check.
	 * @return true if there was already shot to the Coordinates
	 * @return false if there was not shot to the Coordinates
	 */
	public boolean alreadyShot(Coordinates target) {
		int row = target.getRow();
		int column = target.getColumn();
		if (!checkCoordinates(row, column)) {
			throw new IllegalArgumentException("Invalid Coordinates.");
		}
		return ((Constances.MATRIX_HIT == this.get(row, column))
			  ||(Constances.MATRIX_MISS == this.get(row, column)));
	}
		
	/**
	 * Return the number of ships which has been destroyed.
	 * @return number of ships
	 */
	public int getNumberOfDestroyedShips() {
		return this.numberOfDestroyedShips;
	}
	
	/**
	 * Return the number of ships which still exist.
	 * @return number of ships
	 */
	public int getNumberOfExistingShips() {
		return this.ships.size() - this.numberOfDestroyedShips;
	}
	
	/**
	 * Return the number of ships which ever has been placed on the playground.
	 * @return number of ships
	 */
	public int getNumberOfPlacedShips() {
		return this.ships.size();
	}	
	
	/**
	 * Helper Method to place a ship with the given Coordinates on the playground.
	 * There should be checked before calling this Method if it is possible to place
	 * the ship, because this method expect that it is possible.
	 * @param target The Coordinates where to start placing the ship.
	 * @param ship The ship which to place
	 * @param direction The direction into which to place the ship
	 */
	private void placeShipOnPlayground(Coordinates target, Ship ship, int direction) {
		//get column and row, from where to start with placing the ship
		int row = target.getRow();
		int column = target.getColumn();
		
		for (int shipSize = ship.getLength(); shipSize > 0; shipSize--) {
			//set ship id to playground
			this.set(row, column, ship.getId());
			//check if coordinates where to place the ship where completely checked
			if (0 == shipSize) {
				return;
			}
			row += updateVerticalDirection(direction);
			column += updateHorizontalDirection(direction);
		}
	}
	
	/**
	 * Helper Method to add a ship to the existing ships
	 * @param ship The ship to add.
	 * @return true if a given ship, with this id still not exist
	 * @return false if a ship already exist with the same id. The ship was not add.
	 */
	private boolean addShip(Ship ship) {
		for (Ship s: this.ships) {
			if (s.getId() == ship.getId()) {
				status.addError("Cannot add ship, because a ship with the id '"+ship.getId()+"' does already exist.");
				return false;
			}
		}
		this.ships.add(ship);
		return true;
	}
	
	/**
	 * Helper Method to updated the List with all ships after any ship was hit.
	 * @param shipId The shipId which comes from the plaground.
	 */
	private int updateShipsAfterHit(char shipId) {
		for (Ship s: this.ships) {
			if (s.getId() == shipId) {
				return updateShip(s);
			}
		}	
		throw new IllegalArgumentException("A Ship with the given id '"+shipId+"' does not exist.");
	}
	/**
	 * Helper Method to updated one ship. There will be checked if the ships was hit or destroyed.
	 * Call this method only if a ship was really hit.
	 * @param s The ship to check.
	 * @param method will return SHOOT_DESTROYED or as default SHOOT_HIT.
	 */
	private int updateShip(Ship s) {
		s.setDamage();
		if (s.isDestroyed()) {
			status.addText("Destroyed ship: "+s.getName());
			this.numberOfDestroyedShips++;
			return Constances.SHOOT_DESTROYED;
		}
		status.addText("A ship was hit.");	
		return Constances.SHOOT_HIT;
	}
	
	/**
	 * Helper Method to check if on the given row and column can be placed a ship. Round through
	 * this coordinate should be no other ship placed.
	 * @param row The row to check
	 * @param column The column to check
	 * @return true if at the coordinates can be placed a ship
	 * @return false if at the coordinates can no ship be placed
	 */
	private int checkCoord(int row, int column) {
		if (!checkCoordinates(row, column)) {
			return 0;
		}
		if (Constances.MATRIX_INIT != this.get(row, column)) {
			return 1;
		}
		return 0;
	}
	
	/**
	 * Helper Method to get the number of near ships of a given Coordinate in the playground
	 * @param row The row coordinate
	 * @param column The column coordinate
	 * @return The number of near ships (0 if no ship is near)
	 */
	private int checkForNearShips(int row, int column) {
		int checkRows[] = {0, 1, -1, 0, 0, 1, 1, -1, -1};
		int checkColumns[] = {0, 0, 0, -1, 1, -1, 1, 1, -1};
		int nearShipsCount = 0;
		
		for (int i = 0; i < checkRows.length; i++) {
			nearShipsCount += checkCoord(row + checkRows[i], column + checkColumns[i]);
		}
		return nearShipsCount;
	}
	
	/**
	 * Place a list of ships random on the playground
	 * @param ships A list with the ships
	 */
	public void placeShipsRandom(List<Ship> ships) {
		for (Ship s : ships) {
			Coordinates coords;
			int direction = 0;
			while (true) {
				coords = randomCoords();
				direction = randomDirection();
				if (this.placeShip(coords, s, direction))
				{
					break;
				}
			}
		}
	}
	
	/**
	 * Generates random Coordinates
	 * @return Random coordinates where a ship could be placed
	 */
	private Coordinates randomCoords() {
		Coordinates tmp = new Coordinates(this.getRows(), this.getColumns());
		tmp.setRow(random(0, this.getRows()));
		tmp.setColumn(random(0, this.getColumns()));
		return tmp;
	}
	
	/**
	 * Generates a random direction
	 * @return Direction of ship
	 */
	private int randomDirection() {
		return random(DIRECTION_UP, DIRECTION_RIGHT);
	}
	
	/**
	 * Generate Random numbers in a range (min, max)
	 * @param min Minimum number (inclusive)
	 * @param max Maximum number (inclusive)
	 * @return A random number
	 */
	private int random(int min, int max) {
		return (int) (Math.random() * ((max + 1) - min) + min); 
	}
}