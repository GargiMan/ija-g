package ija.pacman.game.field;

import ija.pacman.game.Direction;
import ija.pacman.game.Maze;
import ija.pacman.game.object.MazeObject;

public class WallField implements Field {

    private final int row;
    private final int col;
    private Maze maze;

    public WallField(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    @Override
    public Field nextField(Direction dir) {
        return maze.getField(row + dir.deltaRow(), col + dir.deltaCol());
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public MazeObject get() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean canMove() {
        return false;
    }

    @Override
    public boolean contains(MazeObject commonMazeObject) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addObject(MazeObject object) {}

    @Override
    public void removeObject(MazeObject object) {}

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof WallField) {
            return ((WallField) obj).row == this.row && ((WallField) obj).col == this.col;
        }
        return false;
    }
}
