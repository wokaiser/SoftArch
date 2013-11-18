package interfaces;

public interface ICoordinates {
    /**
     * Return the saved row.
     * @return the row, which is bigger as 0 and smaller as maxRow
     */
    int getRow();
    /**
     * Return the saved column.
     * @return the column, which is bigger as 0 and smaller as maxColumn
     */
    int getColumn();
    /**
     * Overwritten toString(..) method which returns the row and column as a string
     * @return row and column as String
     */
    String toString();
    /**
     * Overwritten equals(..) method which returns true if a object is at the same coordinates
     * @param a Coordinates object.
     * @return true if coordinates are the same and false if not
     */
    boolean equals(Object obj);
    /**
     * compare if two Coordinates objects are equal or not
     * @param a Coordinates object which should be compared to this.
     * @return 0 if the coordinates are equal
     * @return -1 if this.row or this.column is smaller than the row or column of the other coordinates object
     * @return 1 if column and row of this is bigger.
     */
    int compareTo(ICoordinates coords);
    /**
     * Overwritten hashCode(..) method
     * @return The hash of the object
     */
    int hashCode();
}
