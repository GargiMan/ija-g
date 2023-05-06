/**
 * @file MazeObject.java
 * @brief Interface for all objects in maze.
 * @author Marek Gergel (xgerge01)
 */
package ija.pacman.game.object;

import ija.pacman.game.Direction;
import ija.pacman.game.field.Field;

import java.beans.PropertyChangeListener;

public interface MazeObject extends PropertyChangeListener {
    /**
     * Get field where object is.
     * @return field
     */
    Field field();
    /**
     * Check if object can move to direction.
     * @param dir direction
     * @return true if object can move to direction
     */
    boolean canMove(Direction dir);

    /**
     * Move object to direction.
     * @param dir direction
     * @return true if move was successful
     */
    boolean move(Direction dir);

    /**
     * Undo move for logger
     * @param dir direction
     * @return true if move was successful
     */
    boolean undoMove(Direction dir);

    /**
     * Object is Pacman.
     * @return true if this object is Pacman
     */
    default boolean isPacman() {
        return false;
    }

    /**
     * Object is Ghost.
     * @return true if this object is Ghost
     */
    default boolean isGhost() {
        return false;
    }

    /**
     * Object is Key.
     * @return true if this object is Key
     */
    default boolean isKey() {
        return false;
    }

    /**
     * Object is Target.
     * @return true if this object is Target
     */
    default boolean isTarget() {
        return false;
    }

    /**
     * Get live count of this object.
     * @return live count of this object
     */
    default int getLives() {
        return 0;
    }

    /**
     * Get info status about this object.
     * @return info status
     */
    String getInfo();
}
