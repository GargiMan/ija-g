package ija.pacman.game.object;

import ija.pacman.game.Direction;
import ija.pacman.game.field.Field;

import java.beans.PropertyChangeEvent;

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
    public boolean isKey() {
        return true;
    }

    public void collect() {
        //TODO
        field.removeObject(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
