package controller;

import interfaces.ICoordinates;
import interfaces.IDatabase;
import interfaces.IGameContent;
import interfaces.IHighscore;
import interfaces.IStatus;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import model.general.Constances;
import modules.AiModule;

/** 
 * The GameController manages the game logic. Which is switching between players/playgrounds,
 * providing methods to shoot to the enemy's playground,..
 * @author Dennis Parlak
 */
public class GameController extends LoadableGameController {
    private IHighscore highscore = null;
    /**
     * Creates a new game     
     */
    @Inject
    public GameController(IDatabase db) {
        super(db);
    }    
    /**
     * Create a new controller with a whole new playground which have new random placed ships on it.
     */
    public final void newController(int rows, int columns, String player1, String player2, int gameType) {
        Injector inject = Guice.createInjector(new AiModule().setSettings(player1), new AiModule().setSettings(player2));
        setContent(inject.getInstance(GameContent.class));
        getContent().initContent(rows, columns, player1, player2, gameType);
    }
    /**
     * Start the game now. In a single player game this method can be called directly. In a distributed
     * multi player game, the second player has to call this method.
     */
    public void startGame() {
    	IGameContent content = getContent();
        if (null == content) {
            throw new IllegalAccessError("newController was not called before.");
        }
        if (content.gameStarted()) {
            return;
        }
        content.getStatus().addText("Game started. "+content.getActivePlayer()+" please select your target.");
        content.startGame();
        notifyObservers();
        aiShoot();
    }    
    /**
     * Get back the game type
     * @return the game type which should be SINGLEPLAYER or MULTIPLAYER
     */
    public int getGameType() {
    	IGameContent content = getContent();
        return content.getGameType();
    }    
    /**
     * Check if the game is finished (all ships of one place are destroyed)
     * @return true  The game finished, the winner of the game was written to status
     * @return false The game is not finished.
     */
    public boolean gameFinished() {
    	IGameContent content = getContent();
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
    public IStatus getStatus() {    	
    	IGameContent content = getContent();
        return content.getStatus();
    }    
    /**
     * Actual player shoots to a target. After the shot, the player switch if the player
     * have not hit a target. The observers will be notified after the shot, independent
     * from the result of the shot. 
     * @return The result of the shot (see Playground class)
     */
    public int shoot(String player, ICoordinates t) {
    	IGameContent content = getContent();
        int shootStatus = Constances.SHOOT_INVALID;
        ICoordinates target = (content.aiIsActive()) ? content.getActiveAI().getCoordinates() : t;
        
        if (!playerCanShoot(player)) {
            notifyObservers();
            return Constances.SHOOT_INVALID;
        }
        
        content.setSwitchedPlayer(false);
        
        content.incMove();
        
        content.getStatus().addText(content.getActivePlayer()+" shoot to: " + target.toString() + " with his "+content.getMoves(content.getActivePlayer())+" move.");
        
        if (content.getEnemyPlayground(player).alreadyShot(target)) {
            content.getStatus().addError(content.getActivePlayer()+" already shot to this target. Try again.");    
        } else {
            shootStatus = content.getEnemyPlayground(player).shoot(target);
            content.getStatus().moveStatus(content.getEnemyPlayground(player).getStatus());
            /* set flags for AI */
            if(Constances.SHOOT_MISS == shootStatus) {
                /* no ship hit/destroyed, so switch the user */
                content.switchPlayer();
                content.getStatus().addText(content.getActivePlayer() + " it's your turn now.");
            }            
        }
        
        notifyObservers();
        /* if the new player is a AI */
        if (content.aiIsActive() && switchedPlayer()) {
            aiShoot();
        }
        return shootStatus;
    }
    
    private boolean playerCanShoot(String player) {
       IGameContent content = getContent();
       if (!content.gameStarted()) {
            content.getStatus().addError("Wait for another player to get started.");
            return false;
        }

        /* check if the game finished */
        if (gameFinished()) {
            return false;
        }
        
        /* check if the player try to shoot which has to shoot.*/
        if (0 != getActivePlayer().compareTo(player)) {
            content.getStatus().addError(content.getActivePlayer()+" can shoot to the playground now.");
            return false;
        }
        return true;
    }
    
    /**
     * aiShoot, will shoot until the game is finished or the AI did not hit a ship.
     * Note that the aiShoot method will call the shoot method, which will notify
     * the Observers after every shot.
     */
    private void aiShoot() {
    	IGameContent content = getContent();
        while (!gameFinished() && content.aiIsActive()) {
            int status = shoot(getActivePlayer(), null);
            content.getActiveAI().shotResult(status);
            if (Constances.SHOOT_MISS == status) {
                break;
            }
        }
    }
    @Override
    public String getHighscores() {
    	if (null == highscore) {
    		return "No Highscore available.";
    	}
        return highscore.toString();
    }
    
    @Inject
    public void setHighscoreService(IHighscore h) {
    	highscore = h;
    }
}
