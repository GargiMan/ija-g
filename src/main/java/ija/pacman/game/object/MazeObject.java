package ija.pacman.game.object;

import ija.pacman.game.Direction;
import ija.pacman.game.field.Field;

import java.beans.PropertyChangeListener;

public interface MazeObject extends PropertyChangeListener {

    Field getField();

    boolean canMove(Direction dir);
    boolean move(Direction dir);
    default boolean isPacman() {
        return false;
    }
    default boolean isGhost() {
        return false;
    }
    default boolean isKey() {
        return false;
    }
    default boolean isTarget() {
        return false;
    }
    default int getLives() {
        return 0;
    }
}
