package model.playground;

import interfaces.ICoordinates;

/** 
 * The Class Coordinates save the coordinates of a element of a playground.
 * @author Dennis Parlak
 */
public class Coordinates implements ICoordinates {
    private final int row;
    private final int column;

    /**
     * Create a Coordinates object.
     * @param row of the coordinates
     * @param column of the coordinates
     */
    public Coordinates(int row, int column) {
        this.row = row;
        this.column = column;
    }
    
    /**
     * Create a copy of a coordinates object
     * @param row of the coordinates
     * @param column of the coordinates
     */
    public Coordinates(ICoordinates coords) {
        this.row = coords.getRow();
        this.column = coords.getColumn();
    }  
    
    /**
     * Return the saved row.
     * @return the row, which is bigger as 0 and smaller as maxRow
     */
    public int getRow() {
        return this.row;
    }
    
    /**
     * Return the saved column.
     * @return the column, which is bigger as 0 and smaller as maxColumn
     */
    public int getColumn() {
        return this.column;
    }
    
    /**
     * Overwritten toString(..) method which returns the row and column as a string
     * @return row and column as String
     */
    @Override
    public String toString()  {
        StringBuilder str = new StringBuilder();
        str.append(column).append(" ");
        str.append(row);
        return str.toString();
    }
    
    /**
     * Overwritten equals(..) method which returns true if a object is at the same coordinates
     * @param a Coordinates object.
     * @return true if coordinates are the same and false if not
     */
    @Override
    public boolean equals (Object obj) {
        if (obj == null) {
            return false;        
        }
        if (!(obj instanceof Coordinates)) {
            return false;         
        }

        Coordinates coord = (Coordinates) obj;
        if (row != coord.getRow()) {
            return false;
        }
        if (column != coord.getColumn()) {
            return false;
        }
        return true;
    }
    
    /**
     * compare if two Coordinates objects are equal or not
     * @param a Coordinates object which should be compared to this.
     * @return 0 if the coordinates are equal
     * @return -1 if this.row or this.column is smaller than the row or column of the other coordinates object
     * @return 1 if column and row of this is bigger.
     */
    public int compareTo (ICoordinates coords) {
        if (equals(coords)) {
            return 0;
        }
        if ((row < coords.getRow())
         || (column < coords.getColumn())) {
            return -1;
        }
        return 1;
    }
    
    /**
     * Overwritten hashCode(..) method
     * @return The hash of the object
     */
    @Override
    public int hashCode() {
        return row * column;
    }
}
