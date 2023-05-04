package ija.pacman.game.field;

import ija.pacman.game.Direction;
import ija.pacman.game.Maze;
import ija.pacman.game.object.MazeObject;

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

    @Override
    public int[] getCoordinates() {
        return new int[]{row, col};
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
        return mazeObjects.stream().noneMatch(mazeObject -> mazeObject.isPacman() || mazeObject.isGhost() || mazeObject.isKey() || mazeObject.isTarget());
    }

    @Override
    public MazeObject get() {
        return isEmpty() ? null :
                mazeObjects.stream().filter(MazeObject::isPacman).findFirst()
                .orElse(mazeObjects.stream().filter(MazeObject::isGhost).findFirst()
                        .orElse(mazeObjects.stream().filter(MazeObject::isKey).findFirst()
                                .orElse(mazeObjects.stream().filter(MazeObject::isTarget).findFirst()
                                        .orElse(null))));
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
        if (object.isPacman() || object.isGhost() || object.isKey()) {
            propertyChangeSupport.firePropertyChange(this.toString(), mazeObjectsOld, mazeObjects);
        }
    }

    @Override
    public void removeObject(MazeObject object) {
        List<MazeObject> mazeObjectsOld = new ArrayList<>(mazeObjects);
        mazeObjects.remove(object);
        propertyChangeSupport.removePropertyChangeListener(this.toString(), object);
        if (object.isPacman() || object.isGhost()) {
            propertyChangeSupport.firePropertyChange(this.toString(), mazeObjectsOld, mazeObjects);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof PathField pathField) {
            return pathField.row == this.row && pathField.col == this.col;
        }
        return false;
    }
}

