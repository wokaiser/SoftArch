package interfaces;

import model.general.Status;
import model.playground.PlaygroundCell;

public interface IGameContent {
    /**
     * Initialises the content
     * @param rows The number of rows of the playground
     * @param columns The number of columns of the playground
     * @param player1 The name of player1 (if it should be a computer player use AI_PLAYER_1)
     * @param player2 The name of player2 (if it should be a computer player use AI_PLAYER_2)
     * @param gameType The gameType (SINGLEPLAYER or MULITPLAYER)
     */
    void initContent(int rows, int columns, String player1, String player2, int gameType);
    /**
     * Initialises the content and set the playgrounds. This method can be useful to load an saved playground.
     * @param rows The number of rows of the playground
     * @param columns The number of columns of the playground
     * @param player1 The name of player1 (if it should be a computer player use AI_PLAYER_1)
     * @param player2 The name of player2 (if it should be a computer player use AI_PLAYER_2)
     * @param gameType The gameType (SINGLEPLAYER or MULITPLAYER)
     * @param playground1 of player1
     * @param playground2 of player2
     */
    void initContent(int rows, int columns, String player1, String player2, int gameType, PlaygroundCell[][] playground1Input, PlaygroundCell[][] playground2Input);
    /**
     * Set the flag, to indicate that the game started
     */
    void startGame();
    /**
     * get the flag to check if the game started.
     */
    boolean gameStarted();
    /**
     * Sets the name of this content
     */
    void setName(String name);
    /**
     * Gets the name of this content
     */
    String getName();
    /**
     * Get back the game type
     * @return the game type which should be SINGLEPLAYER or MULTIPLAYER
     */
    int getGameType();
    /**
     * Switch player. Will be called if the actual player did not hit a ship.
     */
    void switchPlayer();
    /**
     * check if a player is a computer
     * @return true if the player is a computer, false if not
     */
    boolean aiIsActive();
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
     * Gets the status
     */
    Status getStatus();
    /**
     * Gets the switched player
     */
    boolean getSwitchedPlayer();
    /**
     * Sets the switched player
     */
    void setSwitchedPlayer(boolean switched);
    /**
     * Gets the active AI
     */
    IAi getActiveAI();
    /**
     * Gets the enemy playground
     */
    IPlayground getEnemyPlayground(String activePlayer);
    /**
     * Gets the own playground
     */
    IPlayground getOwnPlayground(String activePlayer);
    /**
     * Gets the name of player1
     */
    String getPlayer1();
    /**
     * Gets the name of player2
     */
    String getPlayer2();
}
