package ija.pacman.game.object;

import ija.pacman.App;
import ija.pacman.game.Direction;
import ija.pacman.game.field.Field;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class PacmanObject implements MazeObject {

    private int lives = 1;
    private final List<KeyObject> keys = new ArrayList<>();
    private Field field;
    private int moves = 0;

    public PacmanObject(Field field) {
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
    public boolean isPacman() {
        return true;
    }

    public int getLives() {
        return lives;
    }

    public void hit() {
        lives--;
        if (lives <= 0) {
            if (App.getGame() != null && !App.getGame().isReplay()) {
                App.getGame().stop(false);
            }
        }
    }

    public void collectKey(KeyObject key) {
        keys.add(key);
    }

    public KeyObject returnKey() {
        return keys.remove(keys.size() - 1);
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
        return "Pacman\nLives: " + lives + "\nCollected keys: " + keys.size() + "\nMoves: " + moves + "\n";
    }
}
