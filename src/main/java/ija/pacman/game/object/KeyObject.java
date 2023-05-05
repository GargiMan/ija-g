package ija.pacman.game.object;

import ija.pacman.game.Direction;
import ija.pacman.game.field.Field;

import java.beans.PropertyChangeEvent;
import java.util.List;

public class KeyObject implements MazeObject {
    private final Field field;

    public KeyObject(Field field) {
        this.field = field;
    }

    @Override
    public Field getField() {
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
    public boolean isKey() {
        return true;
    }

    public void collect() {
        field.removeObject(this);
    }

    public void place() {
        field.addObject(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        List<MazeObject> mazeObjects = (List<MazeObject>) evt.getNewValue();

        //collect key with pacman on field
        PacmanObject pacman = (PacmanObject) mazeObjects.stream().filter(MazeObject::isPacman).findFirst().orElse(null);
        if (pacman != null) {
            pacman.collectKey(this);
            this.collect();
        }
    }

    @Override
    public String getInfo() {
        return "Key\n";
    }
}
