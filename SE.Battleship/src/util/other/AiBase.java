package util.other;

import interfaces.IAi;

import java.util.LinkedList;
import java.util.List;

import model.playground.Coordinates;

public abstract class AiBase implements IAi {
    protected int rows;
    protected int columns;
    private List<Coordinates> range;
    
    /**
     * Chooses a random coordinate
     * @param the number of rows
     * @param the number of columns
     */
    protected void baseInitialize(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.range = new LinkedList<Coordinates>();
        
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                range.add(new Coordinates(r, c));
            }
        }
    }
    
    /**
     * Remove the given coordinate from the range list
     * @return true if the coordinates where in the range list and else false.
     */
    protected boolean removeCoordinates(Coordinates coord) {
        return range.remove(coord);
    }
    
    /**
     * Chooses a random coordinate
     * @return A coordinate
     */
    protected Coordinates getRandomCoordinates() {
        return range.remove((int) (Math.random() * range.size()));
    }
}
