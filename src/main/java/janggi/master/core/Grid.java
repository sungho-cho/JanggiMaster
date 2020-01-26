package janggi.master.core;

/**
 * Grid class that represents each interception on 9x10 Janggi board
 */
public class Grid {
    private final char col;
    private final int row;
    public static final char MIN_COL = 'A';
    public static final char MAX_COL = 'I';
    public static final int MIN_ROW = 1;
    public static final int MAX_ROW = 10;

    public char getCol() { return this.col; }
    public int getRow() { return this.row; }

    public Grid(char col, int row) {
        char upperCol = Character.toUpperCase(col);

        if (upperCol < MIN_COL || upperCol > MAX_COL) {
            throw new IllegalArgumentException("Given column is out of range");
        }

        if (row < MIN_ROW || row > MAX_ROW) {
            throw new IllegalArgumentException("Given row is out of range");
        }

        this.row = row;
        this.col = col;
    }
}