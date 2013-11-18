package interfaces;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import model.general.Status;
import model.playground.Coordinates;

public interface IGameController {
    /**
     * Sets the range of the field which the AI needs to know
     * @param r The maximum number of row to use.
     * @param c The maximum number of column to use.
     */
    void newController(int rows, int columns, String player1, String player2, int gameType);
    /**
     * The AI Shoots
     * @return Coordinates to which the AI shoots
     */
    void startGame();
    /**
     * Call after a shot this method to tell the AI how the shot was (hit, miss, etc,..)
     * @param The status of the shot
     */
    int getGameType();
    /**
     * Get the status object. The status object logs information after a important method
     * call like shoot(..)
     * @return status A status object with logged information.
     */
    Status getStatus();
    /**
     * Actual player shoots to a target. After the shot, the player switch if the player
     * have not hit a target. The observers will be notified after the shot, independent
     * from the result of the shot. 
     * @return The result of the shot (see Playground class)
     */
    int shoot(String player, Coordinates t);
    /**
     * Saves the actual game
     * @param The name of the savegame
     */
    void saveGame(String name);
    /**
     * To check if a game was loaded. The status will be set to true if a game was loaded and
     * it will be set to false, after all observables were notified.
     * @return true if a game was loaded and false if not.
     */
    boolean loadedGame();
    
    /**
     * Loads the actual game
     * @param the name of the game to load
     */
    void loadGame(String name);
    
    /**
     * Gets all the stored GameContent names
     * @return a list with the stored games
     */
    List<String> getStoredGames();
    
    /**
     * Deletes the specific savegame
     * @param the name of the game to delete
     */
    void deleteGame(String name);
    /**
     * Get the name of the active player
     * @return name The name of the active player
     */
    String getActivePlayer();
    /**
     * Get the name of the enemy player
     * @return name The name of the enemy
     */
    String getEnemyPlayer();
    /**
     * Get the rows of the playground
     * @return rows The number of columns 
     */
    int getRows();
    /**
     * Get the columns of the playground
     * @return columns The number of columns 
     */
    int getColumns(); 
    /**
     * Get the playground of the active player as a String.
     * @return Formatted String of the playground
     */
    String getEnemyPlaygroundAsString(String activePlayer);
    /**
     * Get the playground of the enemy player as a String.
     * @return Formatted String of the playground
     */
    char[][] getEnemyPlaygroundAsMatrix(String activePlayer);  
    /**
     * Get the playground of the enemy player as a Json.
     * @return Json of the playground
     */
    JsonNode getEnemyPlaygroundAsJson(String activePlayer);
    /**
     * Get the playground of the enemy player (With no ships visible.).
     * @return 2 dimensional matrix of the playground
     */
    String getOwnPlaygroundAsString(String activePlayer); 
    /**
     * Get the playground of the active player (With all placed ships visible).
     * @return 2 dimensional matrix of the playground
     */
    char[][] getOwnPlaygroundAsMatrix(String activePlayer);
    /**
     * Get the playground of the active player (With all placed ships visible).
     * @return Json of the playground
     */
    JsonNode getOwnPlaygroundAsJson(String activePlayer);
    /**
     * Get the playground of the active player (With all placed ships visible).
     * @return 
     */
    IPlaygroundCell[][] getOwnPlayground(String activePlayer);
    /**
     * Check if a player switched after a shot
     * @return true if player switched, false if not
     */
    boolean switchedPlayer();
}
