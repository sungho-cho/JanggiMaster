package janggi.master.core;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public enum Formation {
    YANGGWIMA, GWIMASANG, GWIMAMA, WONWANGMA;

    private Table<Character, Integer, Piece> initialBoard() {
        Table<Character, Integer, Piece> board = HashBasedTable.create();

        board.put('A', 4, new Piece(PieceType.SOLDIER));
        board.put('C', 4, new Piece(PieceType.SOLDIER));
        board.put('E', 4, new Piece(PieceType.SOLDIER));
        board.put('G', 4, new Piece(PieceType.SOLDIER));
        board.put('I', 4, new Piece(PieceType.SOLDIER));

        board.put('B', 3, new Piece(PieceType.CANNON));
        board.put('H', 3, new Piece(PieceType.CANNON));

        board.put('E', 3, new Piece(PieceType.GENERAL));

        board.put('D', 1, new Piece(PieceType.GUARD));
        board.put('F', 1, new Piece(PieceType.GUARD));

        board.put('A', 1, new Piece(PieceType.CHARIOT));
        board.put('I', 1, new Piece(PieceType.CHARIOT));

        return board;
    }

    public Table<Character, Integer, Piece> getBoard() {
        Table<Character, Integer, Piece> board = initialBoard();
        switch (Formation.values()[this.ordinal()]) {
            case YANGGWIMA:
                board.put('B', 1, new Piece(PieceType.ELEPHANT));
                board.put('C', 1, new Piece(PieceType.HORSE));
                board.put('G', 1, new Piece(PieceType.HORSE));
                board.put('H', 1, new Piece(PieceType.ELEPHANT));
                break;

            case GWIMASANG:
                board.put('B', 1, new Piece(PieceType.ELEPHANT));
                board.put('C', 1, new Piece(PieceType.HORSE));
                board.put('G', 1, new Piece(PieceType.ELEPHANT));
                board.put('H', 1, new Piece(PieceType.HORSE));
                break;

            case GWIMAMA:
                board.put('B', 1, new Piece(PieceType.HORSE));
                board.put('C', 1, new Piece(PieceType.ELEPHANT));
                board.put('G', 1, new Piece(PieceType.HORSE));
                board.put('H', 1, new Piece(PieceType.ELEPHANT));
                break;

            case WONWANGMA:
                board.put('B', 1, new Piece(PieceType.HORSE));
                board.put('C', 1, new Piece(PieceType.ELEPHANT));
                board.put('G', 1, new Piece(PieceType.ELEPHANT));
                board.put('H', 1, new Piece(PieceType.HORSE));
                break;
        }
        return board;
    }
}
