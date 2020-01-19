package janggi.master.core;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import static janggi.master.core.Grid.*;

public class Board {
    private Table<Character, Integer, Piece> board;

    public Board(Camp player, Formation choFormation, Formation hanFormation) {
        board = HashBasedTable.create();
        Table<Character, Integer, Piece> choBoard = choFormation.getBoard();
        Table<Character, Integer, Piece> hanBoard = hanFormation.getBoard();

        switch (player) {
            case CHO:
                board.putAll(choBoard);
                board.putAll(rotate(hanBoard));
                break;
            case HAN:
                board.putAll(rotate(choBoard));
                board.putAll(hanBoard);
                break;
        }

    }

    protected static Table<Character, Integer, Piece> rotate(Table<Character, Integer, Piece> board) {
        Table<Character, Integer, Piece> newBoard = HashBasedTable.create();

        for (char c = MIN_COL; c <= MAX_COL; c++) {
            for (int r = MIN_ROW; r <= MAX_ROW; r++) {

                if (board.contains(c, r)) {
                    char cReversed = (char) ('A' + 8 - (c - 'A'));
                    int rReversed = 11 - r;

                    newBoard.put(cReversed, rReversed, board.get(c, r));
                }
            }
        }

        return newBoard;
    }
}
