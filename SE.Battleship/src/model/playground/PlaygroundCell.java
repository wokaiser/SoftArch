package model.playground;

import interfaces.IPlaygroundCell;
import model.general.Constances;

public class PlaygroundCell implements IPlaygroundCell {
    private char shipId = 0;
    private char element = Constances.MATRIX_INIT;
    
    public PlaygroundCell() {
        
    }
    
    public PlaygroundCell(char stat, char id) {
        element = stat;
        shipId = id;
    }
    
    public char get() {
        return element;
    }
    
    public char getShipId() {
        return shipId;
    }

    public void set(char newElement) {
        element = newElement;
    }
    
    public void setShipId(char id) {
        if (0 != shipId) {
            throw new IllegalAccessError("Could not set a ship twice to a playground cell.");
        }
        element = id;
        shipId = id;
    }
}
