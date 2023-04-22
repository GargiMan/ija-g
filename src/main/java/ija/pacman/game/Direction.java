package ija.pacman.game;

public enum Direction {
    L(0, -1),
    U(-1, 0),
    R(0, 1),
    D(1, 0);

    private final int row;
    private final int col;

    Direction(int deltaRow, int deltaCol) {
        this.row = deltaRow;
        this.col = deltaCol;
    }

    public int deltaRow() {
        return this.row;
    }

    public int deltaCol() {
        return this.col;
    }
}
