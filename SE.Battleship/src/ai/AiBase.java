package ai;

import interfaces.IAi;

import java.util.LinkedList;
import java.util.List;

import model.playground.Coordinates;

public abstract class AiBase implements IAi {
    private List<Coordinates> range = new LinkedList<Coordinates>();
    
    /**
     * Chooses a random coordinate
     * @param the number of rows
     * @param the number of columns
     */
    protected void baseInitialize(int rows, int columns) {    
        range.clear();
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
        rangeCheck();
        return range.remove(coord);
    }
    
    /**
     * Chooses a random coordinate
     * @return A coordinate
     */
    protected Coordinates getRandomCoordinates() {
        rangeCheck();
        return range.remove((int) (Math.random() * range.size()));
    }
    
    private void rangeCheck() {
        if (range.isEmpty()) {
            throw new IllegalStateException("No more coordinates are available for the AI.");
        }
    }
}
