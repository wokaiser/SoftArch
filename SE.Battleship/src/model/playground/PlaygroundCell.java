package model.playground;

import model.general.Constances;

public class PlaygroundCell {
    private char shipId = 0;
    private char element = Constances.MATRIX_INIT;
    
    public PlaygroundCell(char stat) {
        element = stat;
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
    
    public void setShip(char id) {
        if (0 != shipId) {
            throw new IllegalAccessError("Could not set a ship twice to a playground cell.");
        }
        element = id;
        shipId = id;
    }
}
