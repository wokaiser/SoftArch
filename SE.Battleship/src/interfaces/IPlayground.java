package interfaces;

import com.fasterxml.jackson.databind.JsonNode;

public interface IPlayground {
    /**
     * Get number of rows of the playground
     * @return The number of rows
     */
    int getRows();
    /**
     * Get number of columns of the playground
     * @return The number of columns
     */
    int getColumns();
    /**
     * Method to check if on given Coordinates where already a shot.
     * @param target The Coordinates which to check.
     * @return true if there was already shot to the Coordinates
     * @return false if there was not shot to the Coordinates
     */
    boolean alreadyShot(ICoordinates target);
    /**
     * Return the number of ships which still exist.
     * @return number of ships
     */
    int getNumberOfExistingShips();
    /**
     * Method to shoot on given Coordinates
     * @param target The Coordinates which where to shoot.
     * @return status of shot
     */
    int shoot(ICoordinates target);
    /**
     * Return the status object, which log information about the program flow after
     * calling e.g. shoot(..)  method.
     * @return status The Status object.
     */
    IStatus getStatus();
    /**
     * Get the playground with all ships visible on it.
     * @return The playground as a String
     */
    String ownStringView();
    /**
     * Get the playground from the view of an enemy, without any visible ships.
     * Ships which has been hit are visible.
     * @return The playground as a String
     */
    String enemyStringView();
    /**
     * Get the playground with all ships visible on it.
     * @return The playground as a Json
     */
    JsonNode ownJsonView();
    /**
     * Get the playground from the view of an enemy, without any visible ships.
     * Ships which has been hit are visible.
     * return The playground as a Json
     */
    JsonNode enemyJsonView();
    /**
     * Get the playground with all ships visible on it.
     * return The playground
     */
    char[][] ownMatrixView();
    /**
     * Get the playground from the view of an enemy, without any visible ships.
     * Ships which has been hit are visible.
     * return The playground
     */
    char[][] enemyMatrixView();
    /**
     * Get the own playground in the cell format
     * Ships are visible
     * return The playground
     */
    IPlaygroundCell[][] ownView();
}
