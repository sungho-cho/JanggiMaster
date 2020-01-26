package janggi.master.core;

import jdk.internal.net.http.common.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Piece {
    private Camp camp;
    private final PieceType pieceType;

    Piece(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public PieceType getPieceType() { return pieceType; }

    public Camp getCamp() { return camp; }

    public void setCamp(Camp camp) {
        this.camp = camp;
    }

    public List<List<Pair<Integer, Integer>>> getPossibleMoves() {
        List<List<Pair<Integer, Integer>>> possibleMoves = new ArrayList<>();
        switch (this.pieceType) {
//            case GENERAL:
//            case GUARD:
//                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>(-1, -1)))); // Diagonal
//                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>(-1,  0))));
//                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>(-1,  1)))); // Diagonal
//                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>( 0, -1))));
//                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>( 0,  0))));
//                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>( 0,  1))));
//                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>( 1, -1)))); // Diagonal
//                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>( 1,  0))));
//                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>( 1,  1)))); // Diagonal
//                break;

//            case SOLDIER:
//                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>(-1,  0))));
//                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>(-1,  1)))); // Diagonal
//                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>( 0,  0))));
//                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>( 0,  1))));
//                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>( 1,  0))));
//                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>( 1,  1)))); // Diagonal
//                break;

            case HORSE:
                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>( 0,  1), new Pair<>(-1,  1))));
                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>( 0,  1), new Pair<>( 1,  1))));
                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>( 1,  0), new Pair<>( 1,  1))));
                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>( 1,  0), new Pair<>( 1, -1))));
                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>( 0, -1), new Pair<>(-1, -1))));
                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>( 0, -1), new Pair<>( 1, -1))));
                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>(-1,  0), new Pair<>(-1,  1))));
                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>(-1,  0), new Pair<>(-1, -1))));
                break;

            case ELEPHANT:
                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>( 0,  1), new Pair<>(-1,  1), new Pair<>(-1,  1))));
                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>( 0,  1), new Pair<>( 1,  1), new Pair<>( 1,  1))));
                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>( 1,  0), new Pair<>( 1,  1), new Pair<>( 1,  1))));
                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>( 1,  0), new Pair<>( 1, -1), new Pair<>( 1, -1))));
                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>( 0, -1), new Pair<>(-1, -1), new Pair<>(-1, -1))));
                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>( 0, -1), new Pair<>( 1, -1), new Pair<>( 1, -1))));
                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>(-1,  0), new Pair<>(-1,  1), new Pair<>(-1,  1))));
                possibleMoves.add(new ArrayList<>(Arrays.asList(new Pair<>(-1,  0), new Pair<>(-1, -1), new Pair<>(-1, -1))));
                break;

            default:
                break;
        }
        return possibleMoves;
    }
}
