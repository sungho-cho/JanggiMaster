package janggi.master.core;

public enum Camp {
    CHO, HAN;

    public Camp opponent() {
        return Camp.values()[1 - this.ordinal()];
    }
}
