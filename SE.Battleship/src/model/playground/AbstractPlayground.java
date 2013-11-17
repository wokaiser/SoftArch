package model.playground;

import interfaces.IPlayground;

import com.fasterxml.jackson.databind.JsonNode;

/** 
 * The Abstract Playground class supports a minimal implementation of a playground with the
 * basic functionality like get/set and view of playground.
 * @author Dennis Parlak
 */
public abstract class AbstractPlayground implements IPlayground {
    public static final int MIN_ROWS = 12;
    public static final int MIN_COLUMNS = 12;
    
    private int rows;
    private int columns;
    private PlaygroundCell[][] matrix;
    
    public static final PlaygroundCell[][] copyPlayground(PlaygroundCell[][] matrixInput) {
        int rows = matrixInput.length;
        int columns = matrixInput[0].length;
        PlaygroundCell[][] matrix = new PlaygroundCell[rows][columns];
        
        for (int r = 0; r < matrixInput.length; r++) {
            if (columns != matrixInput[r].length) {
                throw new IllegalArgumentException("Matrix has different column length.");
            }
            System.arraycopy(matrixInput[r], 0, matrix[r], 0, columns);
        }
        return matrix;
    }
    
    /**
     * Creates a Playground.
     */
    public AbstractPlayground(int rows, int columns) {
        this.checkRows(rows);
        this.checkColumns(columns);
        this.rows = rows;
        this.columns = columns;
        this.matrix = new PlaygroundCell[rows][columns];
        this.initMatrix();
    }
    
    /**
     * Creates a Playground with an given matrix.
     */
    public AbstractPlayground(PlaygroundCell[][] matrixInput) {
        if (matrixInput == null) {
            throw new IllegalArgumentException("Null matrix for playground");
        }
        rows = matrixInput.length;
        checkRows(rows);
        columns = matrixInput[0].length;
        checkColumns(columns);
        matrix = new PlaygroundCell[rows][columns];
        matrix = copyPlayground(matrixInput);
    }
    
    /**
     * Check if rows parameter is in range.
     * @throws IllegalArgumentException if the rows parameter is out of range
     */
    private void checkRows(int rows) {
        if (rows < MIN_ROWS) {
            throw new IllegalArgumentException("rows parameter is below minum");
        }    
    }
    
    /**
     * Check if columns parameter is in range.
     * @throws IllegalArgumentException if the columns parameter is out of range
     */
    private void checkColumns(int columns) {
        if (columns < MIN_COLUMNS) {
            throw new IllegalArgumentException("columns parameter is below minum");
        }    
    }
    
    /**
     * Initialize the matrix of the playground
     */
    private void initMatrix() {
        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++){
                this.matrix[row][column] = new PlaygroundCell();
            }
        }
    }
    
    /**
     * Get the element at specified position
     * @param The coordinates from which to get the element
     * @return The element on the specified position
     */
    protected char get(Coordinates coords) {
        checkCoordinates(coords);
        return this.matrix[coords.getRow()][coords.getColumn()].get();
    }
    
    /**
     * Set the element at specified position
     * @param The coordinates at which to set the element
     * @param The element to set.
     */
    protected void set(Coordinates coords, char element) {
        checkCoordinates(coords);
        this.matrix[coords.getRow()][coords.getColumn()].set(element);
    }
    
    /**
     * Set the ship to the specified position
     * @param The coordinates at which to set the element
     * @param The ship id to set.
     */
    protected void setShip(Coordinates coords, char id) {
        checkCoordinates(coords);
        this.matrix[coords.getRow()][coords.getColumn()].setShipId(id);
    }
    
    /**
     * Get the ship at specified position
     * @param The coordinates from which to get the ship
     * @return The ship on the specified position
     */
    protected char getShip(Coordinates coords) {
        checkCoordinates(coords);
        return this.matrix[coords.getRow()][coords.getColumn()].getShipId();
    }
    
    /**
     * Get number of rows of the playground
     * @return The number of rows
     */
    public int getRows() {
        return this.rows;
    }
    
    /**
     * Get number of columns of the playground
     * @return The number of columns
     */
    public int getColumns() {
        return this.columns;
    }
    
    /**
     * Helper method to check if the given row and column is in range of the playground
     * @param row The row to check
     * @param column The column to check
     * @return true if the coordinates are in range and false if not
     */
    protected boolean checkCoordinates(Coordinates target) {
        if (target.getRow() >= this.rows || target.getRow() < 0) {
            return false;
        }
        if (target.getColumn() >= this.columns || target.getColumn() < 0) {
            return false;
        }
        return true;
    }
    
    /**
     * Get the playground with all ships visible on it.
     * @return The playground as a String
     */
    public String ownStringView() {        
        return new PlaygroundOwnView().toString(matrix, rows, columns);
    }
    
    /**
     * Get the playground from the view of an enemy, without any visible ships.
     * Ships which has been hit are visible.
     * @return The playground as a String
     */
    public String enemyStringView() {
        return new PlaygroundEnemyView().toString(matrix, rows, columns);
    }
    
    /**
     * Get the playground with all ships visible on it.
     * @return The playground as a Json
     */
    public JsonNode ownJsonView() {        
        return new PlaygroundOwnView().toJson(matrix, rows, columns);
    }
    
    /**
     * Get the playground from the view of an enemy, without any visible ships.
     * Ships which has been hit are visible.
     * @return The playground as a Json
     */
    public JsonNode enemyJsonView() {
        return new PlaygroundEnemyView().toJson(matrix, rows, columns);
    }
    
    /**
     * Get the playground with all ships visible on it.
     * @return The playground
     */
    public char[][] ownMatrixView() {        
        return new PlaygroundOwnView().get(matrix, rows, columns);
    }
    
    /**
     * Get the playground from the view of an enemy, without any visible ships.
     * Ships which has been hit are visible.
     * @return The playground
     */
    public char[][] enemyMatrixView() {        
        return new PlaygroundEnemyView().get(matrix, rows, columns);
    }
    
    /**
     * Get the playground with all ships visible on it.
     * @return The playground
     */
    public PlaygroundCell[][] ownView() {        
        return matrix;
    }
}
