package controller;

import java.util.LinkedList;
import java.util.List;

import com.google.inject.Inject;

import interfaces.IAi;
import model.general.Status;
import model.playground.Playground;
import model.playground.Ship;

public class GameContent {

	/* game types */
	public static final int MULTIPLAYER = 1;
	public static final int SINGLEPLAYER = 2;
	
	// Identifier of the savegame
	private String name;
	
	/* playground of player 1 and 2 */
	private Playground playground1;
	private Playground playground2;
	/* name of player 1 and 2 */
	private String player1;
	private String player2;
	/* AI for player 1 and 2 */
	private IAi player1AI;
	private IAi player2AI;
	/* the active playground switch after each turn and determine which player turn it is */
	private Playground enemyPlayground;
	private String enemyPlayer;
	private Playground ownPlayground;
	private IAi activeAI;
	private String activePlayer;
	/* the status object log information about important method calls like shoot(..) */
	private Status status;
	private boolean switchedPlayer;
	private int gameType;
	
	@Inject
	public GameContent(IAi player1, IAi player2) {
		player1AI = player1;
		player2AI = player2;
	}
	/**
	 * Initialises the content
	 * @param rows The number of rows of the playground
	 * @param columns The number of columns of the playground
	 * @param player1 The name of player1 (if it should be a computer player use AI_PLAYER_1)
	 * @param player2 The name of player2 (if it should be a computer player use AI_PLAYER_2)
	 * @param gameType The gameType (SINGLEPLAYER or MULITPLAYER)
	 */
	public void initContent(int rows, int columns, String player1, String player2, int gameType) {
		/* the ships of player 1 and 2*/
		List<Ship> fleet1 = createShips();
		List<Ship> fleet2 = createShips();
		status = new Status();
		playground1 = new Playground(rows, columns);
		playground2 = new Playground(rows, columns);
		this.player1 = player1;
		this.player2 = player2;
		player1AI.initialize(rows, columns);
		player2AI.initialize(rows, columns);
		playground1.placeShipsRandom(fleet1);
		playground2.placeShipsRandom(fleet2);
		enemyPlayground = this.playground2;
		enemyPlayer = this.player2;
		ownPlayground = this.playground1;
		activePlayer = this.player1;
		activeAI = this.player2AI;
		status.clear();
		status.addText("Welcome to Battleships");
		playground1.getStatus().clear();
		playground2.getStatus().clear();
		switchedPlayer = false;
		this.gameType = gameType;
		checkGameType();
	}
	/**
	 * Sets the name of this content
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Gets the name of this content
	 */
	public String getName() {
		return name;
	}	
	/**
	 * Returns a fleet of ships
	 * @return A ship list with ships
	 */
	private List<Ship> createShips() {
		List<Ship> tmp = new LinkedList<Ship>();
		tmp.add(new Ship("Battleship", Ship.LENGTHBATTLESHIP, 'A'));
		tmp.add(new Ship("1st Cruiser", Ship.LENGTHCRUISER, 'B'));
		tmp.add(new Ship("2nd Cruiser", Ship.LENGTHCRUISER, 'C'));
		tmp.add(new Ship("1st Destroyer", Ship.LENGTHDESTROYER , 'D'));
		tmp.add(new Ship("2nd Destroyer", Ship.LENGTHDESTROYER, 'E'));
		tmp.add(new Ship("1st Submarine", Ship.LENGTHSUBMARINE, 'F'));
		tmp.add(new Ship("2nd Submarine", Ship.LENGTHSUBMARINE, 'G'));
		tmp.add(new  Ship("3rd Submarine", Ship.LENGTHSUBMARINE, 'H'));
		return tmp;
	}	
	/**
	 * check for correct game type
	 * @throws exception if gameType is not SINGLEPLAYER or MULTIPLAYER
	 */
	private void checkGameType() {
		if (this.gameType != MULTIPLAYER && this.gameType != SINGLEPLAYER) {
			throw new IllegalAccessError("Invalid game Type. Choose MULTIPLAYER or SINGLEPLAYER.");
		}
	}	
	/**
	 * Get back the game type
	 * @return the game type which should be SINGLEPLAYER or MULTIPLAYER
	 */
	public int getGameType() {
		return gameType;
	}
	/**
	 * Gets the AI of player 1
	 */
	public IAi getPlayer1AI() {
		return player1AI;
	}
	/**
	 * Gets the AI of player 2
	 */
	public IAi getPlayer2AI() {
		return player2AI;
	}
	/**
	 * Get the name of the active player
	 * @return name The name of the active player
	 */
	public String getActivePlayer() {
		return activePlayer;
	}
	/**
	 * Sets the active player
	 */
	public void setActivePlayer(String player) {
		activePlayer = player;
	}	
	/**
	 * Get the name of the enemy player
	 * @return name The name of the enemy
	 */
	public String getEnemyPlayer() {
		return enemyPlayer;
	}
	/**
	 * Sets the enemy player
	 */
	public void setEnemyPlayer(String player) {
		enemyPlayer = player;
	}
	/**
	 * Gets player 1
	 */
	public String getPlayer1() {
		return player1;
	}
	/**
	 * Gets player 2
	 */
	public String getPlayer2() {
		return player2;
	}	
	/**
	 * Get the rows of the playground
	 * @return rows The number of columns 
	 */
	public int getRows() {
		return playground1.getRows();
	}
	
	/**
	 * Get the columns of the playground
	 * @return columns The number of columns 
	 */
	public int getColumns() {
		return playground1.getColumns();
	}
	/**
	 * Gets the status
	 */
	public Status getStatus() {
		return status;
	}
	/**
	 * Gets the switched player
	 */
	public boolean getSwitchedPlayer() {
		return switchedPlayer;
	}
	/**
	 * Sets the switched player
	 */
	public void setSwitchedPlayer(boolean switched) {
		switchedPlayer = switched;
	}
	/**
	 * Gets the active AI
	 */
	public IAi getActiveAI() {
		return activeAI;
	}
	/**
	 * Sets the active AI
	 */
	public void setActiveAI(IAi ai) {
		activeAI = ai;
	}
	/**
	 * Gets the enemy playground
	 */
	public Playground getEnemyPlayground() {
		return enemyPlayground;
	}
	/**
	 * Sets the enemy playground
	 */
	public void setEnemyPlayground(Playground playground) {
		enemyPlayground = playground;
	}
	/**
	 * Gets the own playground
	 */
	public Playground getOwnPlayground() {
		return ownPlayground;
	}
	/**
	 * Sets the own playground
	 */
	public void setOwnPlayground(Playground playground) {
		ownPlayground = playground;
	}
	/**
	 * Gets the playground 2
	 */
	public Playground getPlayground1() {
		return playground1;
	}
	/**
	 * Gets the playground 2
	 */
	public Playground getPlayground2() {
		return playground2;
	}
}
