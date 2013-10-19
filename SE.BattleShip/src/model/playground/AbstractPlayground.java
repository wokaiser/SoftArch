package model.playground;

import model.general.Constances;

/** 
 * The Abstract Playground class supports a minimal implementation of a playground with the
 * basic functionality like get/set and view of playground.
 * @author Dennis Parlak
 */
public abstract class AbstractPlayground {
	public static final int MIN_ROWS = 12;
	public static final int MIN_COLUMNS = 12;
	
	private int rows;
	private int columns;
	private char[][] matrix;
	
	/**
	 * Creates a Playground.
	 */
	public AbstractPlayground(int rows, int columns) {
		this.checkRows(rows);
		this.checkColumns(columns);
		this.rows = rows;
		this.columns = columns;
		this.matrix = new char[rows][columns];
		this.initMatrix();
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
				this.matrix[row][column] = Constances.MATRIX_INIT;
			}
		}
	}
	
	/**
	 * Get the element at specified position
	 * @param row The row of the playground
	 * @param column The column of the playground
	 * @return The element on the specified position
	 */
	protected char get(int row, int column) {
		return this.matrix[row][column];
	}
	
	/**
	 * Set the element at specified position
	 * @param row The row of the playground
	 * @param column The column of the playground
	 */
	protected void set(int row, int column, char element) {
		this.matrix[row][column] = element;
	}
	
	/**
	 * Get number of rows of the playground
	 * return The number of rows
	 */
	public int getRows() {
		return this.rows;
	}
	
	/**
	 * Get number of columns of the playground
	 * return The number of columns
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
	protected boolean checkCoordinates(int row, int column) {
		if (row >= this.rows || row < 0) {
			return false;
		}
		if (column >= this.columns || column < 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Get the playground with all ships visible on it.
	 * return The playground as a String
	 */
	public String ownStringView() {		
		return new PlaygroundOwnView().toString(matrix, rows, columns);
	}
	
	/**
	 * Get the playground from the view of an enemy, without any visible ships.
	 * Ships which has been hit are visible.
	 * return The playground as a String
	 */
	public String enemyStringView() {
		return new PlaygroundEnemyView().toString(matrix, rows, columns);
	}
	
	/**
	 * Get the playground with all ships visible on it.
	 * return The playground
	 */
	public char[][] ownView() {		
		return new PlaygroundOwnView().get(matrix, rows, columns);
	}
	
	/**
	 * Get the playground from the view of an enemy, without any visible ships.
	 * Ships which has been hit are visible.
	 * return The playground
	 */
	public char[][] enemyView() {		
		return new PlaygroundEnemyView().get(matrix, rows, columns);
	}
}
