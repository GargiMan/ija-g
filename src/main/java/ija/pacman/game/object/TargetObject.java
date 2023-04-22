package ija.pacman.game.object;

import ija.pacman.game.Direction;
import ija.pacman.game.field.Field;

import java.beans.PropertyChangeEvent;
import java.util.List;

public class TargetObject implements MazeObject {
    public TargetObject(Field current_field) {
    }

    @Override
    public Field getField() {
        return null;
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
    public boolean isTarget() {
        return true;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        List<MazeObject> mazeObjects = (List<MazeObject>) evt.getNewValue();

        if (mazeObjects.stream().anyMatch(MazeObject::isPacman)) {
            //TODO game finish
        }
    }
}
