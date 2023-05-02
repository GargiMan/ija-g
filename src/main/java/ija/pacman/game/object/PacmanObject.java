package ija.pacman.game.object;

import ija.pacman.Game;
import ija.pacman.game.Direction;
import ija.pacman.game.field.Field;
import javafx.scene.paint.Color;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class PacmanObject implements MazeObject {

    private int lives = 1;

    private final Color color = Color.YELLOW;

    private final List<KeyObject> keys = new ArrayList<>();
    private Field field;

    public PacmanObject(Field field) {
        this.field = field;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Field getField() {
        return field;
    }

    @Override
    public boolean canMove(Direction dir) {
        return field.nextField(dir).canMove();
    }

    @Override
    public boolean move(Direction dir) {
        if (canMove(dir)) {
            field.removeObject(this);
            field = field.nextField(dir);
            field.addObject(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean isPacman() {
        return true;
    }

    public int getLives() {
        return lives;
    }

    public void hit() {
        lives--;
        if (lives <= 0) {
            Game.stop(false);
        }
    }

    public void collect(KeyObject key) {
        keys.add(key);
    }

    public List<KeyObject> showKeys() {
        return keys;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        List<MazeObject> mazeObjects = (List<MazeObject>) evt.getNewValue();

        // this object has changed
        if (mazeObjects.get(mazeObjects.size() - 1).toString().equals(this.toString())) return;

        //met with ghost
        if (mazeObjects.stream().anyMatch(MazeObject::isGhost)) {
            hit();
        }
    }

    @Override
    public String getInfo() {
        return "Pacman\nLives: " + lives + "\nKeys: " + keys.size() + "\n";
    }
}
