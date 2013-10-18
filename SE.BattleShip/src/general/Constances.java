package general;

/** 
 * The Class Constances provide often used constants.
 * @author Dennis Parlak
 */
public class Constances {
	
	protected Constances() {
        // prevents calls from subclass
        throw new UnsupportedOperationException();
    }
	
	public static final char MATRIX_MISS = '-';
	public static final char MATRIX_HIT = 'X';
	public static final char MATRIX_INIT = '0';
	public static final char DEFAULT_SHIP_ID = 'S';
	
	public static final int SHOOT_HIT = 1;
	public static final int SHOOT_MISS = 2;
	public static final int SHOOT_DESTROYED = 3;
	public static final int SHOOT_INVALID = 4;
	
	public static final int DEFAULT_ROWS = 12;
	public static final int DEFAULT_COLUMNS = 12;
}
