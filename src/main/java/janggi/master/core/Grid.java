package janggi.master.core;

/**
 * Grid class that represents each interception on 9x10 Janggi board
 */
public class Grid {
    private char col;
    private int row;

    public Grid(char col, int row) {
        char upperCol = Character.toUpperCase(col);

        if (upperCol < 'A' || upperCol > 'I') {
            throw new IllegalArgumentException("Given column is out of range");
        }

        if (row < 1 || row > 10) {
            throw new IllegalArgumentException("Given row is out of range");
        }

        this.row = row;
        this.col = col;
    }
}