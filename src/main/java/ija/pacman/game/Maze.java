package ija.pacman.game;

import ija.pacman.game.field.Field;
import ija.pacman.game.field.PathField;
import ija.pacman.game.object.GhostObject;
import ija.pacman.game.object.MazeObject;

import java.util.ArrayList;
import java.util.List;

public class Maze {

    private final int numRows;
    private final int numCols;
    private final Field[][] fields;
    private final List<MazeObject> ghosts = new ArrayList<>();

    public Maze(int rows, int cols) {
        numRows = rows + 2 * MazeConfigure.BORDER_SIZE;
        numCols = cols + 2 * MazeConfigure.BORDER_SIZE;
        fields = new Field[numRows][numCols];
    }

    public Field getField(int row, int col) {
        if (row < MazeConfigure.BORDER_SIZE - 1 || col < MazeConfigure.BORDER_SIZE - 1 || row > MazeConfigure.BORDER_SIZE + numRows || col > MazeConfigure.BORDER_SIZE + numCols)
            return null;

        return fields[row][col];
    }

    public void setField(int row, int col, Field field) {
        if (field instanceof PathField) {
            field.setMaze(this);
        }
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
}
