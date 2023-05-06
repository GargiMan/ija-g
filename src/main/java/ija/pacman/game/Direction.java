/**
 * @file Direction.java
 * @brief Enum for directions in maze (L, U, R, D, NONE)
 * @author Marek Gergel (xgerge01)
 */
package ija.pacman.game;

public enum Direction {
    NONE(0, 0),
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

    public static Direction fromPositions(int fromRow, int fromCol, int toRow, int toCol) {
        if (fromCol != toCol) {
            if (fromCol > toCol) {
                return L;
            } else {
                return R;
            }
        } else if (fromRow != toRow) {
            if (fromRow > toRow) {
                return U;
            } else {
                return D;
            }
        }
        return NONE;
    }
}
