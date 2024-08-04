/**
 * @file KeyObject.java
 * @brief Key object implementation of MazeObject interface
 * @author Marek Gergel (xgerge01)
 */
package ija.pacman.game.object;

import ija.pacman.App;
import ija.pacman.game.Direction;
import ija.pacman.game.field.Field;

import java.beans.PropertyChangeEvent;
import java.util.List;

/**
 * Key object
 * @param field of object
 */
public record KeyObject(Field field) implements MazeObject {

    @Override
    public boolean canMove(Direction dir) {
        return false;
    }

    @Override
    public boolean move(Direction dir) {
        return false;
    }

    @Override
    public boolean undoMove(Direction dir) {
        return false;
    }

    @Override
    public boolean isKey() {
        return true;
    }

    /**
     * Collect key from field
     */
    public void collect() {
        field.removeObject(this);
    }

    /**
     * Place key on field
     */
    public void place() {
        field.addObject(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        List<MazeObject> mazeObjects = convertToMazeObjects(evt.getNewValue());

        //collect key with pacman on field
        PacmanObject pacman = (PacmanObject) mazeObjects.stream().filter(MazeObject::isPacman).findFirst().orElse(null);
        if (pacman != null) {
            pacman.collectKey(this);
            this.collect();
        }
    }

    @Override
    public String getInfo() {
        return "Key " + (App.getGame().getMaze().getKeys().indexOf(this) + 1);
    }
}
