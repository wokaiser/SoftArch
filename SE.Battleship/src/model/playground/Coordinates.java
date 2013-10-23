package model.playground;

/** 
 * The Class Coordinates save the coordinates of a element of a playground.
 * @author Dennis Parlak
 */
public class Coordinates {
	private int row;
	private int column;
	private int maxRow;
	private int maxColumn;
	
	/**
	 * Create a Coordinates object.
	 * @param maxRow The maximum number of row to use (from 0 - (maxRow-1)).
	 * @param maxColumn The maximum number of column to use (from 0 - (maxColumn-1)).
	 */
	public Coordinates(int maxRow, int maxColumn) {
		if (maxRow <= 0) {
			throw new IllegalArgumentException("Paramter maxRow should be bigger than 0.");
		}
		if (maxColumn <= 0) {
			throw new IllegalArgumentException("Paramter maxColumn should be bigger than 0.");
		}
		this.maxRow = maxRow;
		this.maxColumn = maxColumn;
		this.row = 0;
		this.column = 0;
	}
	
	/**
	 * Create a Coordinates object with no default row and column (default is 0).
	 * @param maxRow The maximum number of row to use (from 0 - (maxRow-1)).
	 * @param maxColumn The maximum number of column to use (from 0 - (maxColumn-1)).
	 * @param defaultRow The default row to set
	 * @param defaultColumn The default colunmn to set
	 */
	public Coordinates(int maxRow, int maxColumn, int defaultRow, int defaultColumn) {
		this(maxRow, maxColumn);
		this.row = defaultRow;
		this.column = defaultColumn;
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
	 * Set row to a given value
	 * @param row The value to set
	 * @return true Row is bigger as 0 and smaller as maxRow
	 * @return false Row was not set, because the value was out of range.
	 */
	public boolean setRow(int row) {
		if (row >= this.maxRow || row < 0) {
			return false;
		}
		this.row = row;
		return true;
	}
	
	/**
	 * Set column to a given value
	 * @param column The value to set
	 * @return true Column is bigger as 0 and smaller as maxColumn
	 * @return false Column was not set, because the value was out of range.
	 */
	public boolean setColumn(int column) {
		if (column >= this.maxColumn || column < 0) {
			return false;
		}
		this.column = column;
		return true;
	}
	
	/**
	 * Set row and column, equal to a other Coordinate object
	 * @param other Coordinates object
	 * @return true Coordinates successfully set
	 * @return false Coordinates could not be set
	 */
	public boolean set(Coordinates other) {
		if (!this.setRow(other.getRow())) {
			return false;
		}
		return this.setColumn(other.getColumn());
	}
	
	/**
	 * Overwritten toString(..) method which returns the row and column as a string
	 * @return row and column as String
	 */
	@Override
	public String toString()  {
		StringBuilder str = new StringBuilder();
		str.append(this.column).append(" ");
		str.append(this.row);
		return str.toString();
	}
}
