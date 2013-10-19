package model.playground;

/** 
 * The Class Ship represents the ships.
 * @author Wolfgang Kaiser
 */
public class Ship {

	private String name;
	private int length;
	private char id;
	private int health;
	
	private static final int SUNKEN = 0;
	public static final int MIN_SHIP_SIZE = 2;
	public static final int MAX_SHIP_SIZE = 5;
	public static final int MIN_SHIP_NAME_LENGTH = 1;
	
	/**
	 * Generates a new Ship object
	 * @param name Name of ship
	 * @param length Length of ship
	 * @param id Id of ship
	 */
	public Ship(String name, int length, char id) {
		this.checkName(name);
		this.checkLength(length);
		this.name = name;
		this.length = length;
		health = length;
		this.id = id;
	}
	
	private void checkName(String name) {
		if (MIN_SHIP_NAME_LENGTH > name.length()) {
			throw new IllegalArgumentException("Ships must have a name!");
		}	
	}
	
	private void checkLength(int length) {
		if (MIN_SHIP_SIZE > length || MAX_SHIP_SIZE < length)
		{
			throw new IllegalArgumentException("Ships can only have lengths between 2 and 5!");
		}
	}
	
	/**
	 * Returns name of the ship
	 * @return Name of ship
	 */
	public String getName() {
		return name;
	}
	/**
	 * Returns length of ship
	 * @return Length of ship
	 */
	public int getLength() {
		return length;
	}
	/**
	 * Return id of ship
	 * @return Id of ship
	 */
	public char getId() {
		return id;
	}
	/**
	 * Checks if ship is destroyed
	 * @return true, if ship is destroyed; false if not
	 */
	public boolean isDestroyed() {
		return (SUNKEN == health);
	}
	/**
	 * This Method is used to Damage the ship by 1.
	 */
	public void setDamage() {
		if (SUNKEN < health) {
			health--;
		} else {
			throw new IllegalStateException("Ships cannot have a negativ health!");
		}
	}	
}
