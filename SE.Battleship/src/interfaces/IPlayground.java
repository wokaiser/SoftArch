package interfaces;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import model.general.Status;
import model.playground.Coordinates;
import model.playground.Ship;

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
     * Place a list of ships random on the playground
     * @param ships A list with the ships
     */  
    void placeShipsRandom(List<Ship> ships);
    /**
     * Method to check if on given Coordinates where already a shot.
     * @param target The Coordinates which to check.
     * @return true if there was already shot to the Coordinates
     * @return false if there was not shot to the Coordinates
     */
    boolean alreadyShot(Coordinates target);
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
    int shoot(Coordinates target);
    /**
     * Return the ships which are on the playground
     * @return List with ships
     */
    List<Ship> getShips();
    /**
     * Return the status object, which log information about the program flow after
     * calling e.g. shoot(..)  method.
     * @return status The Status object.
     */
    Status getStatus();
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
}
