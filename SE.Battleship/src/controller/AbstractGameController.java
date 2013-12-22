package controller;

import interfaces.IGameContent;
import interfaces.IGameController;
import interfaces.IPlaygroundCell;

import com.fasterxml.jackson.databind.JsonNode;

import util.observer.Observable;

public abstract class AbstractGameController extends Observable implements IGameController{
    private IGameContent content = null;
    
    /**
     * Override the Observable, to notify the Observers and clear the status after that.
     */
    public void setContent(IGameContent c) {
    	content = c;
    }     
    
    /**
     * Get the game content
     * @return the game content
     */
    public IGameContent getContent() {
    	return content;
    }
    
    /**
     * Override the Observable, to notify the Observers and clear the status after that.
     */
    @Override
    public void notifyObservers() {
        super.notifyObservers();
        content.getStatus().clear();    
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
        return content.getEnemyPlayground(activePlayer).enemyMatrixView();
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
        return content.getOwnPlayground(activePlayer).ownMatrixView();
    }   
    /**
     * Get the playground of the active player (With all placed ships visible).
     * @return Json of the playground
     */
    public JsonNode getOwnPlaygroundAsJson(String activePlayer) {
        return content.getOwnPlayground(activePlayer).ownJsonView();
    }   
    /**
     * Get the playground of the active player (With all placed ships visible).
     * @return 
     */
    public IPlaygroundCell[][] getOwnPlayground(String activePlayer) {
        return content.getOwnPlayground(activePlayer).ownView();
    }
    /**
     * Check if a player switched after a shot
     * @return true if player switched, false if not
     */
    public boolean switchedPlayer() {
        return content.getSwitchedPlayer();
    }
}
