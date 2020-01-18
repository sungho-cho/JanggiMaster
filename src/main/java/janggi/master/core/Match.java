package janggi.master.core;

import java.util.Date;
import java.util.List;

public class Match {
    private List<Move> moves;
    private Formation choFormation, hanFormation;
    private Camp winner;
    private Date date;

    public Match(List<Move> moves, Formation choFormation, Formation hanFormation, Camp winner, Date date) {
        this.moves = moves;
        this.choFormation = choFormation;
        this.hanFormation = hanFormation;
        this.winner = winner;
        this.date = date;
    }
}
