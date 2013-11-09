package controller;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import database.GameContent;
import database.IDatabase;
import model.general.Constances;
import model.general.Status;
import model.playground.Coordinates;
import modules.AiModule;
import util.observer.Observable;

/** 
 * The GameController manages the game logic. Which is switching between players/playgrounds,
 * providing methods to shoot to the enemy's playground,..
 * @author Dennis Parlak
 */
public class GameController extends Observable {
	public static final String AI_PLAYER_1 = "Computer 1 Weak";
	public static final String AI_PLAYER_2 = "Computer 2 Hard";
	public static final String HUMAN_PLAYER_1 = "Player 1";
	public static final String HUMAN_PLAYER_2 = "Player 2";
		
	private boolean loadedGame = false;
	private IDatabase database = null;
	private GameContent content = null;

	/**
	 * Override the Observable, to notify the Observers and clear the status after that.
	 */
	@Override
	public void notifyObservers() {
		super.notifyObservers();
		content.getStatus().clear();	
	}	
	/**
	 * Creates a new game	 
	 */
	@Inject
	public GameController(IDatabase database) {
		this.database = database;
	}	
	/**
	 * Create a new controller with a whole new playground which have new random placed ships on it.
	 */
	public final void newController(int rows, int columns, String player1, String player2, int gameType) {
		Injector inject = Guice.createInjector(new AiModule().setSettings(player1), new AiModule().setSettings(player2));
		content = inject.getInstance(GameContent.class);
		content.initContent(rows, columns, player1, player2, gameType);
		this.checkGameType();
	}
	/**
	 * Start the game now. In a single player game this method can be called directly. In a distributed
	 * multi player game, the second player has to call this method.
	 */
	public void startGame() {
		if (null == content) {
			throw new IllegalAccessError("newController was not called before.");
		}
		if (content.gameStarted()) {
			throw new IllegalAccessError("Called startGame twice.");
		}
		content.getStatus().addText("Game started. "+content.getActivePlayer()+" please select your target.");
		content.startGame();
		notifyObservers();
		aiShoot();
	}
	
	/**
	 * Saves the actual game
	 */
	public boolean saveGame(String name) {
		content.setName(name);
		boolean retVal = database.save(name, content);
		if(!retVal) {
			content.getStatus().addError("Savegame already taken, choose another one!");
		}
		return retVal; 
	}
	/**
	 * To check if a game was loaded. The status will be set to true if a game was loaded and
	 * it will be set to false, after all observables were notified.
	 * @return true if a game was loaded and false if not.
	 */
	public boolean loadedGame() {
		return loadedGame;
	}
	/**
	 * Loads the actual game
	 */
	public void loadGame(String name) {
		content = database.load(name);
		loadedGame = true;
		/* TODO: fix this hotfix. If we do not double switchPlayer, we see an incorrect playground.
		 * switchPlayer() just change "activePlayer" and "activeAI" internally in content...*/
		content.switchPlayer();
		content.switchPlayer();
		content.getStatus().addText("Successfully loaded game. "+content.getActivePlayer() + " please select your target.");
		notifyObservers();
		aiShoot();
		loadedGame = false;
	}
	/**
	 * Gets all the stored GameContent names
	 */
	public List<String> getStoredGames() {
		return database.getAll();
	}
	/**
	 * Deletes the specific savegame
	 */
	public void deleteGame(String name) {
		database.delete(name);
	}
	/**
	 * check for correct game type
	 * @throws exception if gameType is not SINGLEPLAYER or MULTIPLAYER
	 */
	private void checkGameType() {
		if (content.getGameType() != GameContent.MULTIPLAYER && content.getGameType() != GameContent.SINGLEPLAYER) {
			throw new IllegalAccessError("Invalid game Type. Choose MULTIPLAYER or SINGLEPLAYER.");
		}
	}	
	/**
	 * Get back the game type
	 * @return the game type which should be SINGLEPLAYER or MULTIPLAYER
	 */
	public int getGameType() {
		return content.getGameType();
	}	
	/**
	 * Check if the game is finished (all ships of one place are destroyed)
	 * @return true  The game finished, the winner of the game was written to status
	 * @return false The game is not finished.
	 */
	public boolean gameFinished() {
		if (null == content.getEnemyPlayground(content.getActivePlayer())) {
			content.getStatus().addText(content.getActivePlayer() + " won.");
			return true;
		}
		if (0 == content.getEnemyPlayground(content.getActivePlayer()).getNumberOfExistingShips()) {
			content.getStatus().addText(content.getActivePlayer() + " won.");
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
		return content.getStatus();
	}	
	/**
	 * Actual player shoots to a target. After the shot, the player switch if the player
	 * have not hit a target. The observers will be notified after the shot, independent
	 * from the result of the shot. 
	 * @return The result of the shot (see Playground class)
	 */
	public int shoot(String player, Coordinates t) {
		int shootStatus = Constances.SHOOT_INVALID;
		Coordinates target = t;
		
		if (!content.gameStarted()) {
			content.getStatus().addError("Wait for another player to get started.");
			notifyObservers();
			return shootStatus;
		}

		/* check if the game finished */
		if (gameFinished()) {
			notifyObservers();
			return shootStatus;
		}
		
		/* check if the player try to shoot which has to shoot.*/
		if (0 != getActivePlayer().compareTo(player)) {
			content.getStatus().addError(content.getActivePlayer()+" can shoot to the playground now.");
			notifyObservers();
			return shootStatus;
		}
		
		content.setSwitchedPlayer(false);
		
		if (null == target || isAI(content.getActivePlayer())) {
			/* get the target from the AI */
			target = content.getActiveAI().shoot();
			content.getStatus().addText("AI shoot to: " + target.toString() + ".");
		}
		
		if (content.getEnemyPlayground(player).alreadyShot(target)) {
			content.getStatus().addError(content.getActivePlayer()+" already shot to this target. Try again.");	
		}
		else
		{
			shootStatus = content.getEnemyPlayground(player).shoot(target);
			content.getStatus().moveStatus(content.getEnemyPlayground(player).getStatus());
			/* set flags for AI */
			if(content.getActiveAI().setFlags(shootStatus))
			{
				/* no ship hit/destroyed, so switch the user */
				content.switchPlayer();
				content.getStatus().addText(content.getActivePlayer() + " please select your target.");
			}			
		}
		
		notifyObservers();
		/* if the new player is a AI */
		if (isAI(content.getActivePlayer()) && switchedPlayer()) {
			aiShoot();
		}
		return shootStatus;
	}
	
	/**
	 * aiShoot, will shoot until the game is finished or the AI did not hit a ship.
	 * Note that the aiShoot method will call the shoot method, which will notify
	 * the Observers after every shot.
	 */
	private void aiShoot() {
		while (isAI(content.getActivePlayer())) {
			if (gameFinished()) {
				break;
			}
			int coord = shoot(getActivePlayer(), null);
			if (Constances.SHOOT_HIT != coord && Constances.SHOOT_DESTROYED != coord) {
				break;
			}
		}
	}
	
	/**
	 * Get the name of the active player
	 * @return name The name of the active player
	 */
	public String getActivePlayer() {
		return content.getActivePlayer();
	}	
	/**
	 * Get the name of the enemy player
	 * @return name The name of the enemy
	 */
	public String getEnemyPlayer() {
		return content.getEnemyPlayer();
	}	
	/**
	 * Get the rows of the playground
	 * @return rows The number of columns 
	 */
	public int getRows() {
		return content.getRows();
	}	
	/**
	 * Get the columns of the playground
	 * @return columns The number of columns 
	 */
	public int getColumns() {
		return content.getColumns();
	}	
	/**
	 * Get the playground of the active player as a String.
	 * @return Formatted String of the playground
	 */
	public String getEnemyPlaygroundAsString(String activePlayer) {
		return content.getEnemyPlayground(activePlayer).enemyStringView();
	}	
	/**
	 * Get the playground of the enemy player as a String.
	 * @return Formatted String of the playground
	 */
	public char [][] getEnemyPlaygroundAsMatrix(String activePlayer) {
		return content.getEnemyPlayground(activePlayer).enemyView();
	}	
	/**
	 * Get the playground of the enemy player as a Json.
	 * @return Json of the playground
	 */
	public JsonNode getEnemyPlaygroundAsJson(String activePlayer) {
		return content.getEnemyPlayground(activePlayer).enemyJsonView();
	}	
	/**
	 * Get the playground of the enemy player (With no ships visible.).
	 * @return 2 dimensional matrix of the playground
	 */
	public String getOwnPlaygroundAsString(String activePlayer) {
		return content.getOwnPlayground(activePlayer).ownStringView();
	}	
	/**
	 * Get the playground of the active player (With all placed ships visible).
	 * @return 2 dimensional matrix of the playground
	 */
	public char [][] getOwnPlaygroundAsMatrix(String activePlayer) {
		return content.getOwnPlayground(activePlayer).ownView();
	}	
	/**
	 * Get the playground of the active player (With all placed ships visible).
	 * @return Json of the playground
	 */
	public JsonNode getOwnPlaygroundAsJson(String activePlayer) {
		return content.getOwnPlayground(activePlayer).ownJsonView();
	}
	/**
	 * check if a player is a computer
	 * @return true if the player is a computer, false if not
	 */
	private boolean isAI(String player) {
		if (player.equals(AI_PLAYER_1)) {
			return true;
		}
		if (player.equals(AI_PLAYER_2)) {
			return true;
		}
		return false;
	}	
	/**
	 * Check if a player switched after a shot
	 * @return true if player switched, false if not
	 */
	public boolean switchedPlayer() {
		return content.getSwitchedPlayer();
	}
}
