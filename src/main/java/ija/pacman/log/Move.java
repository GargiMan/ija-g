/**
 * @file Move.java
 * @brief Class for storing moves from log and accessing them easily for replay
 * @author Marek Gergel (xgerge01)
 */
package ija.pacman.log;

import ija.pacman.game.object.MazeObject;

public class Move {
    private final MazeObject object;
    private final Move previousMove;
    private final int row;
    private final int col;
    private Move nextMove = null;

    public Move(MazeObject object, Move previous, int row, int col) {
        this.object = object;
        this.previousMove = previous;
        this.row = row;
        this.col = col;
    }

    public int[] getCoordinates() {
        return new int[] {row, col};
    }
    public void setNext(Move next) {
        this.nextMove = next;
    }

    public Move next() {
        return nextMove;
    }

    public Move previous() {
        return previousMove;
    }

    public MazeObject getObject() {
        return object;
    }
}