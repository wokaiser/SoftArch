package model.playground;

import java.util.LinkedList;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.general.Constances;

/** 
 * The PlaygroundView class can output a playground formatted. The appearance of one
 * element from the playground should be specified in subclasses, by overwriting the
 * getElement(..) method.
 * @author Dennis Parlak
 */
public abstract class PlaygroundView {
    private static final int FREE = 3;
    
    protected static final List<Character> NO_SHIP = new LinkedList<Character>();

    /**
     * Creates a new PlaygroundView Object.
     */
    public PlaygroundView() {
        NO_SHIP.add(Constances.MATRIX_MISS);
        NO_SHIP.add(Constances.MATRIX_INIT);
        NO_SHIP.add(Constances.MATRIX_HIT);
    }
    
    /**
     * Return a playground as a String. By Overwriting the getElement(..) method in subclasses,
     * the appearance of one element should be defined. 
     * @matrix The Playground as a 2d character array
     * @rows The number of rows of the matrix
     * @columns The number of columns of the matrix
     * @return The String appearance of the playground.
     */
    public String toString(char[][] matrix, int rows, int columns) {
        int maxRowFreespaces = numberOfDigits(rows);
        int maxColumnFreespaces = numberOfDigits(columns);
        String columnFreespacesAsString = getString(maxColumnFreespaces, ' ');
        StringBuilder builder = new StringBuilder();

        builder.append(getString(maxRowFreespaces + FREE, ' '));
        for (int column = 0; column < columns; column++) {
            builder.append(column).append(getString(maxColumnFreespaces-numberOfDigits(column)+1, ' '));
        }
        
        builder.append('\n').append(getString(maxRowFreespaces+1+columns*(maxColumnFreespaces+1), '-')).append('\n');
        
        for (int row = 0; row < rows; row++) {
            builder.append(row).append(getString(maxRowFreespaces-numberOfDigits(row)+1, ' ')).append("| ");

            for (int column = 0; column < columns; column++){
                builder.append(this.getElement(matrix[row][column])).append(columnFreespacesAsString);    
            }
            builder.append('\n');
        }
        return builder.toString();
    }
    
    /**
     * Return a playground as a Json Node. By Overwriting the getElement(..) method in subclasses,
     * the appearance of one element should be defined. 
     * @matrix The Playground as a 2d character array
     * @rows The number of rows of the matrix
     * @columns The number of columns of the matrix
     * @return The Json appearance of the playground.
     */
    public JsonNode toJson(char[][] matrix, int rows, int columns) {
        StringBuilder builder = new StringBuilder("[");
        
        for (int row = 0; row < rows; row++) {
            builder.append("[");
            for (int column = 0; column < columns; column++){
                builder.append("{\"x\" : ");
                builder.append(row);
                builder.append(",\"y\" : ");
                builder.append(column);
                builder.append(",\"state\" : \"");
                builder.append(this.getElement(matrix[row][column]));
                builder.append("\"}");
                if (column < columns - 1) {
                    builder.append(",");
                }
            }
            builder.append("]");
            if (row < rows - 1) {
                builder.append(",");
            }
        }
        builder.append("]");
        
        try {
            return new ObjectMapper().readTree(builder.toString());
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Return a playground as a matrix. By Overwriting the getElement(..) method in subclasses,
     * the appearance of one element should be defined. 
     * @matrix The Playground as a 2d character array
     * @rows The number of rows of the matrix
     * @columns The number of columns of the matrix
     * @return The Playground as a 2d character array
     */
    public char[][] get(char[][] matrix, int rows, int columns) {
        char[][] returnMatrix = new char[rows][columns]; 
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++){
                returnMatrix[row][column] = this.getElement(matrix[row][column]);
            }
        }
        return returnMatrix;
    }
    
    /**
     * The abstract getElement(..) method should be overwritten by subclasses to set
     * the appearance of a element from the playground according to the subclass usage.
     * @param the actual element of the playground
     * @return the 
     */
    protected abstract char getElement(char element);
    
    /**
     * Helper function to get the number of digits of a integer
     * @param n Integer from which to get the number of digits
     * @return The number of digits
     */
    private int numberOfDigits(int n) {
        String s=String.valueOf(n);
        return s.length();
    }
    
    /**
     * Helper function to get a string with n number of chars connected
     * @param n Number of chars to connect
     * @param c The character to connect n times
     * @return The string with n * c
     */
    private String getString(int n, char c) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            builder.append(c);
        }
        return builder.toString();
    }
}
