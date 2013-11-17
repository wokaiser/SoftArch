package controller;

import java.util.List;

public abstract class LoadableGameController extends AbstractGameController {
    protected boolean loadedGame = false;
    
    /**
     * Saves the actual game
     * @param The name of the savegame
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
     * @param the name of the game to load
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
        loadedGame = false;
    }
    /**
     * Gets all the stored GameContent names
     * @return a list with the stored games
     */
    public List<String> getStoredGames() {
        return database.getAll();
    }
    /**
     * Deletes the specific savegame
     * @param the name of the game to delete
     */
    public void deleteGame(String name) {
        database.delete(name);
    }
}
