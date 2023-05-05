package ija.pacman.game.object;

import ija.pacman.game.Direction;
import ija.pacman.game.field.Field;
import javafx.scene.paint.Color;

import java.beans.PropertyChangeEvent;
import java.util.List;

public class GhostObject implements MazeObject {

    private Field field;
    private int moves = 0;

    public GhostObject(Field field) {
        this.field = field;
    }

    @Override
    public Field getField() {
        return field;
    }

    @Override
    public boolean canMove(Direction dir) {
        if (dir == Direction.NONE) return false;
        return field.nextField(dir).canMove();
    }

    @Override
    public boolean move(Direction dir) {
        if (canMove(dir)) {
            field.removeObject(this);
            field = field.nextField(dir);
            field.addObject(this);
            moves++;
            return true;
        }
        return false;
    }

    @Override
    public boolean undoMove(Direction dir) {
        if (canMove(dir)) {
            field.removeObject(this);
            field = field.nextField(dir);
            field.addObject(this);
            moves--;
            return true;
        }
        return false;
    }

    @Override
    public boolean isGhost() {
        return true;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        List<MazeObject> mazeObjects = (List<MazeObject>) evt.getNewValue();

        // this object has changed
        if (mazeObjects.get(mazeObjects.size() - 1).toString().equals(this.toString())) return;

        //met with ghost
        mazeObjects.stream().filter(MazeObject::isPacman).forEach(mazeObject -> ((PacmanObject) mazeObject).hit());
    }

    @Override
    public String getInfo() {
        return "Ghost\nMovement: random\nMoves: " + moves + "\n";
    }
}
