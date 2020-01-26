package janggi.master.core;

public class Move {
    private Piece piece;
    private Grid from, to;

    public Move(Piece piece, Grid from, Grid to) {
        this.piece = piece;
        this.from = from;
        this.to = to;
    }
}
