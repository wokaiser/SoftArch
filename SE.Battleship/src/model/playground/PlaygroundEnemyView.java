package model.playground;

import model.general.Constances;

/** 
 * The PlaygroundEnemyView class is a subclass of the PlaygroundView class and specifies how the output
 * of a playground look like if a player look to the enemy playground (ships are hidden).
 * @author Dennis Parlak
 */
public class PlaygroundEnemyView extends PlaygroundView {
    
    /**
     * Method return the appearance of one element of the playground.
     * @return If a ship is placed on the playground, the ship will not
     * be returned (init will be returned). If no ship is placed the 
     * status of the element (hit, miss, init) will be returned.
     */
    @Override
    protected char getElement(char element) {
        if (!NO_SHIP.contains(element)) {
            return Constances.MATRIX_INIT;
        } 
        return element;
    }
}