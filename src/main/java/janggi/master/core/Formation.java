package janggi.master.core;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public enum Formation {
    YANGGWIMA, GWIMASANG, GWIMAMA, WONWANGMA;

    private Table<Character, Integer, Piece> initialBoard() {
        Table<Character, Integer, Piece> board = HashBasedTable.create();

        board.put('A', 4, Piece.SOLDIER);
        board.put('C', 4, Piece.SOLDIER);
        board.put('E', 4, Piece.SOLDIER);
        board.put('G', 4, Piece.SOLDIER);
        board.put('I', 4, Piece.SOLDIER);

        board.put('B', 3, Piece.CANNON);
        board.put('H', 3, Piece.CANNON);

        board.put('E', 3, Piece.GENERAL);

        board.put('A', 1, Piece.CHARIOT);
        board.put('I', 1, Piece.CHARIOT);

        return board;
    }

    public Table<Character, Integer, Piece> getBoard() {
        Table<Character, Integer, Piece> board = initialBoard();
        switch (Formation.values()[this.ordinal()]) {
            case YANGGWIMA:
                board.put('B', 1, Piece.ELEPHANT);
                board.put('C', 1, Piece.HORSE);
                board.put('G', 1, Piece.HORSE);
                board.put('H', 1, Piece.ELEPHANT);
                break;

            case GWIMASANG:
                board.put('B', 1, Piece.ELEPHANT);
                board.put('C', 1, Piece.HORSE);
                board.put('G', 1, Piece.ELEPHANT);
                board.put('H', 1, Piece.HORSE);
                break;

            case GWIMAMA:
                board.put('B', 1, Piece.HORSE);
                board.put('C', 1, Piece.ELEPHANT);
                board.put('G', 1, Piece.HORSE);
                board.put('H', 1, Piece.ELEPHANT);
                break;

            case WONWANGMA:
                board.put('B', 1, Piece.HORSE);
                board.put('C', 1, Piece.ELEPHANT);
                board.put('G', 1, Piece.ELEPHANT);
                board.put('H', 1, Piece.HORSE);
                break;
        }
        return board;
    }
}
