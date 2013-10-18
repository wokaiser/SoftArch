package playground;

import general.Constances;

/** 
 * The PlaygroundOwnView class is a subclass of the PlaygroundView class and specifies how the output
 * of a playground look like if a player look to his own playground (all ships are visible).
 * @author Dennis Parlak
 */
public class PlaygroundOwnView extends PlaygroundView {

	/**
	 * Method return the appearance of one element of the playground.
	 * @return If a ship is placed on the playground, the DEFAULT_SHIP_ID will
	 * be returned, otherwise the status of the element (hit, miss, init) 
	 */
	@Override
	protected char getElement(char element) {
		if (NO_SHIP.contains(element)) {
			return element;
		} 
		return Constances.DEFAULT_SHIP_ID;
	}
}