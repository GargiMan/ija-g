package ija.pacman.game.object;

import ija.pacman.Game;
import ija.pacman.game.Direction;
import ija.pacman.game.field.Field;

import java.beans.PropertyChangeEvent;
import java.util.List;

public class TargetObject implements MazeObject {

    private Field field;
    private List<KeyObject> requiredKeys;

    public TargetObject(Field field, List<KeyObject> requiredKeys) {
        this.field = field;
        this.requiredKeys = requiredKeys;
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
    public boolean isTarget() {
        return true;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        List<MazeObject> mazeObjects = (List<MazeObject>) evt.getNewValue();

        PacmanObject pacman = (PacmanObject) mazeObjects.stream().filter(MazeObject::isPacman).findFirst().orElse(null);
        if (pacman != null) {
            if (pacman.showKeys().equals(requiredKeys)) {
                Game.stop(true);
            }
        }
    }
}
