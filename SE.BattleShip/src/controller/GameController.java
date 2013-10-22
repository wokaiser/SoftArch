package controller;

import java.util.LinkedList;
import java.util.List;

import model.general.Constances;
import model.general.Status;
import model.playground.Coordinates;
import model.playground.Playground;
import model.playground.Ship;
import util.observer.Observable;

/** 
 * The GameController manages the game logic. Which is switching between players/playgrounds,
 * providing methods to shoot to the enemy's playground,..
 * @author Dennis Parlak
 */
public class GameController extends Observable {
	public static final String AI_PLAYER_1 = "Computer 1";
	public static final String AI_PLAYER_2 = "Computer 2";

	/* game types */
	public static final int MULTIPLAYER = 1;
	public static final int SINGLEPLAYER = 2;
	
	/* playground of player 1 and 2 */
	private Playground playground1;
	private Playground playground2;
	/* name of player 1 and 2 */
	private String player1;
	private String player2;
	/* AI for player 1 and 2 */
	private AI player1AI;
	private AI player2AI;
	/* the active playground switch after each turn and determine which player turn it is */
	private Playground enemyPlayground;
	private String enemyPlayer;
	private Playground ownPlayground;
	private AI activeAI;
	private String activePlayer;
	/* the status object log information about important method calls like shoot(..) */
	private Status status;
	private boolean switchedPlayer;
	private int gameType;

	/**
	 * Creates a new game
	 * @param rows The number of rows of the playground
	 * @param columns The number of columns of the playground
	 * @param The name of player1 (if it should be a computer player use AI_PLAYER_1)
	 * @param The name of player2 (if it should be a computer player use AI_PLAYER_2)
	 * @param The gameType (SINGLEPLAYER or MULITPLAYER)
	 */
	public GameController(int rows, int columns, String player1, String player2, int gameType) {
		newController(rows, columns, player1, player2, gameType);
	}
	
	/**
	 * Create a new controller with a whole new playground which have new random placed ships on it.
	 * @param rows The number of rows of the playground
	 * @param columns The number of columns of the playground
	 * @param The name of player1 (if it should be a computer player use AI_PLAYER_1)
	 * @param The name of player2 (if it should be a computer player use AI_PLAYER_2)
	 * @param The gameType (SINGLEPLAYER or MULITPLAYER)
	 */
	public final void newController(int rows, int columns, String player1, String player2, int gameType) {
		/* the ships of player 1 and 2*/
		List<Ship> fleet1 = createShips();
		List<Ship> fleet2 = createShips();
		this.status = new Status();
		this.playground1 = new Playground(rows, columns);
		this.playground2 = new Playground(rows, columns);
		this.player1 = player1;
		this.player2 = player2;
		this.player1AI = new AI(rows, columns);
		this.player2AI = new AI(rows, columns);
		this.playground1.placeShipsRandom(fleet1);
		this.playground2.placeShipsRandom(fleet2);
		this.enemyPlayground = this.playground2;
		this.enemyPlayer = this.player2;
		this.ownPlayground = this.playground1;
		this.activePlayer = this.player1;
		this.activeAI = this.player2AI;
		this.status.clear();
		this.status.addText("Welcome to Battleships");
		this.playground1.getStatus().clear();
		this.playground2.getStatus().clear();
		this.switchedPlayer = false;
		this.gameType = gameType;
		this.checkGameType();
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
		return this.gameType;
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
	 * Check if the game is finished (all ships of one place are destroyed)
	 * @return true  The game finished, the winner of the game was written to status
	 * @return false The game is not finished.
	 */
	public boolean gameFinished() {
		if (0 == enemyPlayground.getNumberOfExistingShips()) {
			status.addText(activePlayer + " won.");
			return true;
		}
		return false;
	}
	
	/**
	 * Get the status object. The status object logs information after a important method
	 * call like shoot(..)
	 * @return status A status object with logged information.
	 */
	public Status getStatus() {
		return status;
	}
	
	/**
	 * Actual player shoots to a target. After the shot, the player switch if the player
	 * have not hit a target. The observers will be notified after the shot, independent
	 * from the result of the shot. 
	 * @return The result of the shot (see Playground class)
	 */
	public int shoot(Coordinates t) {
		int shootStatus = Constances.SHOOT_INVALID;
		Coordinates target = t;
		this.switchedPlayer = false;
		
		if (null == target || isAI()) {
			/* get the target from the AI */
			target = activeAI.shoot();
			status.addText("AI shoot to: " + target.toString() + ".");
		}
		
		if (enemyPlayground.alreadyShot(target)) {
			status.addError("Already shot to this target. Try again.");	
		}
		else
		{
			shootStatus = enemyPlayground.shoot(target);
			status.moveStatus(enemyPlayground.getStatus());
			/* set flags for AI */
			if(activeAI.setFlags(shootStatus))
			{
				/* no ship hit/destroyed, so switch the user */
				switchPlayer();
				status.addText(this.activePlayer + " please select your target.");
			}			
		}
		notifyObservers();
		return shootStatus;
	}
	
	/**
	 * Get the name of the active player
	 * @return name The name of the active player
	 */
	public String getActivePlayer() {
		return activePlayer;
	}
	
	/**
	 * Get the name of the enemy player
	 * @return name The name of the enemy
	 */
	public String getEnemyPlayer() {
		return enemyPlayer;
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
	 * Get the playground of the active player as a String.
	 * @return Formatted String of the playground
	 */
	public String getEnemyPlaygroundAsString() {
		return enemyPlayground.enemyStringView();
	}
	
	/**
	 * Get the playground of the enemy player as a String.
	 * @return Formatted String of the playground
	 */
	public char [][] getEnemyPlaygroundAsMatrix() {
		return enemyPlayground.enemyView();
	}
	
	/**
	 * Get the playground of the enemy player (With no ships visible.).
	 * @return 2 dimensional matrix of the playground
	 */
	public String getOwnPlaygroundAsString() {
		return ownPlayground.ownStringView();
	}
	
	/**
	 * Get the playground of the active player (With all placed ships visible).
	 * @return 2 dimensional matrix of the playground
	 */
	public char [][] getOwnPlaygroundAsMatrix() {
		return ownPlayground.ownView();
	}
	
	/**
	 * check if the actual active player is a computer
	 * @return true if the actual player is a computer, false if not
	 */
	public boolean isAI() {
		if (activePlayer.equals(AI_PLAYER_1)
		  ||activePlayer.equals(AI_PLAYER_2)) {
			return true;
		}
		return false;
	}
	
	/**
	 * check if the enemy is a computer
	 * @return true if the enemy is a computer, false if not
	 */
	public boolean isEnemyAI() {
		if (enemyPlayer.equals(AI_PLAYER_1)
		  ||enemyPlayer.equals(AI_PLAYER_2)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Switch player. Will be called if the actual player did not hit a ship.
	 */
	public void switchPlayer() {
		this.switchedPlayer = true;
		if (playground1.equals(enemyPlayground)) {
			enemyPlayground = playground2;
			enemyPlayer = player2;
			ownPlayground = playground1;
			activePlayer = player1;
			activeAI = player1AI;
		}
		else
		{
			enemyPlayground = playground1;
			enemyPlayer = player1;
			ownPlayground = playground2;
			activePlayer = player2;
			activeAI = player2AI;
		}
	}

	/**
	 * Check if a player switched after a shot
	 * @return true if player switched, false if not
	 */
	public boolean switchedPlayer() {
		return this.switchedPlayer;
	}
}