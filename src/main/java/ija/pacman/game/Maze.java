package ija.pacman.game;

import ija.pacman.game.field.Field;
import ija.pacman.game.object.*;

import java.util.ArrayList;
import java.util.List;

public class Maze {

    private final int numRows;
    private final int numCols;
    private final Field[][] fields;
    private final List<MazeObject> ghosts = new ArrayList<>();
    private final List<KeyObject> keys = new ArrayList<>();
    private PacmanObject pacman = null;
    private TargetObject target = null;

    public Maze(int rows, int cols) {
        numRows = rows + 2 * MazeConfigure.BORDER_SIZE;
        numCols = cols + 2 * MazeConfigure.BORDER_SIZE;
        fields = new Field[numRows][numCols];
    }

    public Field getField(int row, int col) {
        if (row < 0 || col < 0 || row > numRows - 1 || col > numCols - 1)
            return null;

        return fields[row][col];
    }

    public void setField(int row, int col, Field field) {
        field.setMaze(this);
        fields[row][col] = field;
    }

    public int numRows() {
        return numRows;
    }

    public int numCols() {
        return numCols;
    }

    public List<MazeObject> ghosts() {
        return new ArrayList<>(ghosts);
    }

    public void addGhost(GhostObject ghost) {
        ghosts.add(ghost);
    }

    public List<KeyObject> getKeys() {
        return keys;
    }

    public void addKey(KeyObject key) {
        keys.add(key);
    }

    public PacmanObject getPacman() {
        return pacman;
    }

    public void setPacman(PacmanObject pacman) {
        this.pacman = pacman;
    }

    public TargetObject getTarget() {
        return target;
    }

    public void setTarget(TargetObject target) {
        this.target = target;
    }
}
