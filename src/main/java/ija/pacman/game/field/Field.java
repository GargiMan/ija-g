package ija.pacman.game.field;

import ija.pacman.game.Direction;
import ija.pacman.game.Maze;
import ija.pacman.game.object.MazeObject;

public interface Field {
    int[] getCoordinates();
    void setMaze(Maze maze);
    Field nextField(Direction dir);
    boolean isEmpty();
    MazeObject get();
    boolean canMove();
    boolean contains(MazeObject object);
    void addObject(MazeObject object);
    void removeObject(MazeObject object);
    String toString();
}
