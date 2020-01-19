package janggi.master.core;

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
}
