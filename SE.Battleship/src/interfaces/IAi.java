package interfaces;

public interface IAi {
    /**
     * Sets the range of the field which the AI needs to know
     * @param r The maximum number of row to use.
     * @param c The maximum number of column to use.
     */
    void initialize(int r, int c);
    /**
     * The AI Shoots
     * @return Coordinates to which the AI shoots
     */
    ICoordinates getCoordinates();
    /**
     * Call after a shot this method to tell the AI how the shot was (hit, miss, etc,..)
     * @param The status of the shot
     */
    void shotResult(int status);
}
