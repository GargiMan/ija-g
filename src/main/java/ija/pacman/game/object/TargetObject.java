/**
 * @file TargetObject.java
 * @brief Target object implementation of MazeObject interface
 * @author Marek Gergel (xgerge01)
 */
package ija.pacman.game.object;

import ija.pacman.App;
import ija.pacman.game.Direction;
import ija.pacman.game.field.Field;

import java.beans.PropertyChangeEvent;
import java.util.HashSet;
import java.util.List;

public class TargetObject implements MazeObject {

    private final Field field;
    private final List<KeyObject> requiredKeys;

    /**
     * Constructor of TargetObject
     * @param field field of object
     * @param requiredKeys list of required keys
     */
    public TargetObject(Field field, List<KeyObject> requiredKeys) {
        this.field = field;
        this.requiredKeys = requiredKeys;
    }

    @Override
    public Field field() {
        return field;
    }

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
    public boolean isTarget() {
        return true;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        List<MazeObject> mazeObjects = (List<MazeObject>) evt.getNewValue();

        PacmanObject pacman = (PacmanObject) mazeObjects.stream().filter(MazeObject::isPacman).findFirst().orElse(null);
        if (pacman != null && new HashSet<>(pacman.showKeys()).containsAll(requiredKeys)) {
            if (!App.getGame().isReplay()) {
                App.getGame().stop(true);
            }
        }
    }

    @Override
    public String getInfo() {
        return "Target\nRequired keys: " + requiredKeys.size() + "\n";
    }
}
