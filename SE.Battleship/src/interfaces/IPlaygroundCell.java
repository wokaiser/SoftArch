package interfaces;

public interface IPlaygroundCell {
    /**
     * Get the cell status (cell hit, miss, init,..)
     * @return the status of the cell
     */
    char get();
    /**
     * Get the ship id, if a ship is placed on the cell
     * @return the ship id
     */
    char getShipId();
    /**
     * Set the cell status (cell hit, miss, init,..), and do not change the ship id.
     * @param the status to set
     */
    void set(char newElement);
    /**
     * Set a ship id
     * @param the id to set
     */
    void setShipId(char id);
}
