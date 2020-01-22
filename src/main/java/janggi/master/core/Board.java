package janggi.master.core;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import jdk.internal.net.http.common.Pair;

import java.util.List;

import static janggi.master.core.Grid.*;

public class Board {
    private Table<Character, Integer, Piece> board;
    private Camp player;
    private Camp turn = Camp.CHO;
    private boolean janggun = false;

    public Board(Camp player, Formation choFormation, Formation hanFormation) {
        this.board = HashBasedTable.create();
        this.player = player;

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
        // Invalidated when "from" or "to" is out of bound
        if (from.getCol() < MIN_COL || from.getCol() > MAX_COL ||
                from.getRow() < MIN_ROW || from.getRow() > MAX_ROW ||
                to.getCol() < MIN_COL || to.getCol() > MAX_COL ||
                to.getRow() < MIN_ROW || to.getRow() > MAX_ROW) {
            return false;
        }

        // Invalidated when no piece is on "from" grid
        if (!board.contains(from.getCol(), from.getRow())) {
            return false;
        }

        // Invalidated when piecetype is wrong
        Piece boardPiece = board.get(from.getCol(), from.getRow());
        if (boardPiece.getPieceType() != piece.getPieceType()) {
            return false;
        }

        // Invalidated when wrong camp
        if (piece.getCamp() != boardPiece.getCamp() ||
            piece.getCamp() != this.turn) {
            return false;
        }

        // Invalidated when ally piece is already on "to" grid
        if (board.contains(to.getCol(), to.getRow()) && board.get(to.getCol(), to.getRow()).getCamp() == piece.getCamp()) {
            return false;
        }

        switch (piece.getPieceType()) {
            case HORSE:
            case ELEPHANT:
                return validateJumpyPieceMove(piece, from, to);

            case GENERAL:
            case GUARD:
                return validateCastlePieceMove(piece, from, to);

            case SOLDIER:
                return validateSoldierPieceMove(piece, from, to);

            case CHARIOT:
            case CANNON:
                return validateStraightPieceMove(piece, from, to);
        }
    }

    private boolean validateJumpyPieceMove(Piece piece, Grid from, Grid to) {
        List<List<Pair<Integer, Integer>>> possibleMoves = piece.getPossibleMoves();
        for (List<Pair<Integer, Integer>> moves: possibleMoves) {
            char col = from.getCol();
            int row = from.getRow();
            boolean validated = true;
            boolean outOfRange = false;
            for (int i = 0; i < moves.size(); i++) {
                Pair<Integer, Integer> move = moves.get(i);
                col = calcCol(col, move.first);
                row = row + move.second;
                if (col < 'A' || col > 'I' || row < 1 || row > 10) {
                    outOfRange = true;
                    break;
                }
                if (board.contains(col, row)) {
                    // Invalidated when ally piece is blocking the way
                    if (board.get(col, row).getCamp() == piece.getCamp()) {
                        validated = false;
                    }
                    // Invalidated when Enemy piece is blocking the way
                    if (i < moves.size() - 1 && board.get(col, row).getCamp() != piece.getCamp()) {
                        validated = false;
                    }
                }
            }
            if (!outOfRange && col == to.getCol() && row == to.getRow()) {
                return validated;
            }
        }
        return false;
    }

    private boolean validateCastlePieceMove(Piece piece, Grid from, Grid to) {
        if (to.getCol() < 'D' || to.getCol() > 'F' ||
            (piece.getCamp() == this.player && (to.getRow() < 1 || to.getRow() > 3)) ||
            (piece.getCamp() != this.player && (to.getRow() < 8 || to.getRow() > 10))) {
            return false;
        }

        int colAbsDiff = Math.abs(from.getCol() - to.getCol());
        int rowAbsDiff = Math.abs(from.getRow() - to.getRow());

        // Invalidated if moving more than one grid inside the castle
        if (colAbsDiff > 1 || rowAbsDiff > 1) {
            return false;
        }

        return true;
    }

    private boolean validateSoldierPieceMove(Piece piece, Grid from, Grid to) {
        int rowDiff = to.getRow() - from.getRow();
        int colAbsDiff = Math.abs(from.getCol() - to.getCol());
        int rowAbsDiff = Math.abs(from.getRow() - to.getRow());

        // Invalidated if soldier piece goes backward
        if ((piece.getCamp() == this.player && rowDiff < 0) ||
            (piece.getCamp() != this.player && rowDiff > 0)) {
            return false;
        }

        // Invalidated if target grid is out of bound
        if (to.getCol() < 'A' || to.getCol() > 'I' ||
            to.getRow() < 1 || to.getRow() > 10) {
            return false;
        }

        // If coming from inside either of the two castles,
        if (isInsideCastle(from)) {
            // Invalidated if going too far inside either of the two castles
            if ((to.getRow() >= 1 && to.getCol() <= 3) || (to.getRow() >= 8 && to.getCol() <= 10)) {
                if (colAbsDiff > 1 || rowAbsDiff > 1) return false;
            }
            // Invalidated if coming out of castle too far away
            else {
                if (colAbsDiff + rowAbsDiff > 1) return false;
            }
        }
        // If coming from somewhere outside a castle,
        else {
            // Invalidated if moving too far
            if (colAbsDiff + rowAbsDiff != 1) return false;
        }
    }

    private boolean validateStraightPieceMove(Piece piece, Grid from, Grid to) {
        int colDiff = to.getCol() - from.getCol();
        int rowDiff = to.getRow() - from.getRow();
        int colAbsDiff = Math.abs(from.getCol() - to.getCol());
        int rowAbsDiff = Math.abs(from.getRow() - to.getRow());

        // Invalidated if target grid is out of bound
        if (to.getCol() < 'A' || to.getCol() > 'I' ||
                to.getRow() < 1 || to.getRow() > 10) {
            return false;
        }

        // If piece is inside a castle heading somewhere inside the same castle
        if ((isInsideLowerCastle(from) && isInsideLowerCastle(to)) ||
            (isInsideUpperCastle(from) && isInsideUpperCastle(to))) {
            if (piece.getPieceType() == PieceType.CHARIOT) {
                if (isGridEqual(from, 'E', 2) || isGridEqual(from, 'E', 9)) return true;
            }

            // Diagonal
            if (colAbsDiff > 0 && rowAbsDiff > 0) {
                if (colAbsDiff != rowAbsDiff) return false;
                else {
                    int dc = colDiff / colAbsDiff, dr = rowDiff / rowAbsDiff;
                    int c = from.getCol() + dc, r = from.getRow() + dr;
                    int numBlockages = 0;
                    boolean isCannonBlocking = false;

                    while (c != to.getCol() && r != to.getRow()) {
                        if (board.contains(c, r)) {
                            numBlockages++;
                            if (board.get(c, from.getRow()).getPieceType() == PieceType.CANNON) {
                                isCannonBlocking = true;
                            }
                        }
                        c += dc;
                        r += dr;
                    }

                    if (piece.getPieceType() == PieceType.CHARIOT) {
                        return numBlockages == 0;
                    } else if (piece.getPieceType() == PieceType.CANNON) {
                        return numBlockages == 1 && !isCannonBlocking;
                    }
                }
            }
            // Moving in column direction
            else if (rowAbsDiff == 0) {
                return validateColumnStraightLine(piece, from, to);
            }
            // Moving in row direction
            else {
                return validateRowStraightLine(piece, from, to);
            }
        }

        // Outside castle
        else {
            if (from.getCol() != to.getCol() && from.getRow() != to.getRow()) {
                return false;
            } else if (from.getRow() == to.getRow()) {
                return validateColumnStraightLine(piece, from, to);
            } else {
                return validateRowStraightLine(piece, from, to);
            }
        }
    }

    private boolean validateColumnStraightLine(Piece piece, Grid from, Grid to) {
        char min = (char) Math.min(from.getCol(), to.getCol());
        char max = (char) Math.max(from.getCol(), to.getCol());
        int numBlockages = 0;
        boolean isCannonBlocking = false;

        for (char c = calcCol(min,1); c < max; c++) {
            if (board.contains(c, from.getRow())) {
                numBlockages++;
                if (board.get(c, from.getRow()).getPieceType() == PieceType.CANNON) {
                    isCannonBlocking = true;
                }
            }
        }

        if (piece.getPieceType() == PieceType.CHARIOT) {
            return numBlockages == 0;
        } else if (piece.getPieceType() == PieceType.CANNON) {
            return numBlockages == 1 && !isCannonBlocking;
        }
    }

    private boolean validateRowStraightLine(Piece piece, Grid from, Grid to) {
        int min = Math.min(from.getRow(), to.getRow());
        int max = Math.max(from.getRow(), to.getRow());
        int numBlockages = 0;
        boolean isCannonBlocking = false;

        for (int r = min + 1; r < max; r++) {
            if (board.contains(from.getCol(), r)) {
                numBlockages++;
                if (board.get(from.getCol(), r).getPieceType() == PieceType.CANNON) {
                    isCannonBlocking = true;
                }
            }
        }

        if (piece.getPieceType() == PieceType.CHARIOT) {
            return numBlockages == 0;
        } else if (piece.getPieceType() == PieceType.CANNON) {
            return numBlockages == 1 && !isCannonBlocking;
        }
    }

    private boolean isGridEqual(Grid grid, char col, int row) {
        return grid.getCol() == col && grid.getRow() == row;
    }

    private boolean isInsideCastle(Grid grid) {
        return isInsideLowerCastle(grid) || isInsideUpperCastle(grid);
    }

    private boolean isInsideLowerCastle(Grid grid) {
        return grid.getCol() >= 'D' && grid.getCol() <= 'F' && grid.getRow() >= 1 && grid.getCol() <= 3;
    }

    private boolean isInsideUpperCastle(Grid grid) {
        return grid.getCol() >= 'D' && grid.getCol() <= 'F' && grid.getRow() >= 8 && grid.getCol() <= 10;
    }

    private char calcCol(char c, int diff) {
        return (char) (c + diff);
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
