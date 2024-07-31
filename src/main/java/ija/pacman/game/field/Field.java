/**
 * @file Field.java
 * @brief Interface for field
 * @author Marek Gergel (xgerge01)
 */
package ija.pacman.game.field;

import ija.pacman.game.Direction;
import ija.pacman.game.Maze;
import ija.pacman.game.object.MazeObject;

/**
 * Interface for field
 */
public interface Field {
    /**
     * Returns row of field
     * @return row of field
     */
    int getRow();
    /**
     * Returns column of field
     * @return column of field
     */
    int getCol();
    /**
     * Sets maze for field
     * @param maze maze to set
     */
    void setMaze(Maze maze);
    /**
     * Get next field in given direction
     * @param dir direction
     * @return next field in given direction
     */
    Field nextField(Direction dir);
    /**
     * Check if field contains any game object
     * @return
     */
    boolean isEmpty();
    /**
     * Get top game object from field
     * @return top game object from field
     */
    MazeObject get();
    /**
     * Check if object can move to this field
     * @return true if object can move to this field
     */
    boolean canMove();
    /**
     * Check if field contains given object
     * @param object object to check
     * @return true if field contains given object
     */
    boolean contains(MazeObject object);
    /**
     * Add object to field
     * @param object object to add
     */
    void addObject(MazeObject object);
    /**
     * Remove object from field
     * @param object object to remove
     */
    void removeObject(MazeObject object);
}
