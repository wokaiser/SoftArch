package controller;

import interfaces.IDatabase;

import java.util.List;

public abstract class LoadableGameController extends AbstractGameController {
    private IDatabase database = null;
    private boolean loadedGame = false;
    
    public LoadableGameController(IDatabase db) {
        database = db;
    }
    
    /**
     * Saves the actual game
     * @param The name of the savegame
     */
    public void saveGame(String name) {
        if (GameContent.MULTIPLAYER == content.getGameType()) {
            content.getStatus().addError("Multiplayer games cannot be saved!");
            return;
    }
        content.setName(name);
        database.save(name, content);
        content.getStatus().moveStatus(database.getStatus());
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
