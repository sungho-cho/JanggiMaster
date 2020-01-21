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

        markCamp(choBoard, Camp.CHO);
        markCamp(hanBoard, Camp.HAN);

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

    private boolean validateMove(Piece piece, Grid from, Grid to) {
        // Not validated when "from" or "to" is out of bound
        if (from.getCol() < MIN_COL || from.getCol() > MAX_COL ||
                from.getRow() < MIN_ROW || from.getRow() > MAX_ROW ||
                to.getCol() < MIN_COL || to.getCol() > MAX_COL ||
                to.getRow() < MIN_ROW || to.getRow() > MAX_ROW) {
            return false;
        }

        // Not validated when no piece is on "from" grid
        if (!board.contains(from.getCol(), from.getRow())) {
            return false;
        }

        // Not validated when piecetype is wrong
        Piece piece = board.get(from.getCol(), from.getRow());
        if (piece.getPieceType() != piece.getPieceType()) {
            return false;
        }

        // Not validated when ally piece is already on "to" grid
        if (board.contains(to.getCol(), to.getRow()) && board.get(to.getCol(), to.getRow()).getCamp() == piece.getCamp()) {
            return false;
        }

        switch (piece.getPieceType()) {
            case GENERAL:
            case GUARD:
            case SOLDIER:
                break;

            case HORSE:
            case ELEPHANT:
                break;

            case CHARIOT:
            case CANNON:
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

    protected static Table<Character, Integer, Piece> markCamp(Table<Character, Integer, Piece> board, Camp camp) {
        for (char c = MIN_COL; c <= MAX_COL; c++) {
            for (int r = MIN_ROW; r <= MAX_ROW; r++) {

                if (board.contains(c, r)) {
                    board.get(c, r).setCamp(camp);
                }
            }
        }
        return board;
    }
}
