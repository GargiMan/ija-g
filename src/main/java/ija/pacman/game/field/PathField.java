package ija.pacman.game.field;

import ija.pacman.game.Direction;
import ija.pacman.game.Maze;
import ija.pacman.game.object.GhostObject;
import ija.pacman.game.object.KeyObject;
import ija.pacman.game.object.MazeObject;
import ija.pacman.game.object.PacmanObject;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class PathField implements Field {

    private final int row;
    private final int col;
    private Maze maze;
    private final List<MazeObject> mazeObjects = new ArrayList<>();

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public PathField(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    @Override
    public Field nextField(Direction dir) {
        return maze.getField(row + dir.deltaRow(), col + dir.deltaCol());
    }

    @Override
    public boolean isEmpty() {
        return mazeObjects.isEmpty();
    }

    public boolean hasGhost() {
        return mazeObjects.stream().anyMatch(mazeObject -> mazeObject instanceof GhostObject);
    }

    public boolean hasKey() {
        return mazeObjects.stream().anyMatch(mazeObject -> mazeObject instanceof KeyObject);
    }

    @Override
    public MazeObject get() {
        return isEmpty() ? null : mazeObjects.stream().filter(mazeObject -> mazeObject instanceof PacmanObject).findFirst()
                .orElse(mazeObjects.stream().filter(mazeObject -> mazeObject instanceof KeyObject).findFirst().orElse(mazeObjects.get(0)));
    }

    @Override
    public boolean canMove() {
        return true;
    }

    @Override
    public boolean contains(MazeObject object) {
        return mazeObjects.contains(object);
    }

    @Override
    public void addObject(MazeObject object) {
        List<MazeObject> mazeObjectsOld = new ArrayList<>(mazeObjects);
        mazeObjects.add(object);
        propertyChangeSupport.addPropertyChangeListener(this.toString(), object);
        propertyChangeSupport.firePropertyChange(this.toString(), mazeObjectsOld, mazeObjects);
    }

    @Override
    public void removeObject(MazeObject object) {
        List<MazeObject> mazeObjectsOld = new ArrayList<>(mazeObjects);
        mazeObjects.remove(object);
        propertyChangeSupport.removePropertyChangeListener(this.toString(), object);
        propertyChangeSupport.firePropertyChange(this.toString(), mazeObjectsOld, mazeObjects);
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof PathField) {
            return ((PathField) object).row == this.row && ((PathField) object).col == this.col;
        }
        return false;
    }
}

