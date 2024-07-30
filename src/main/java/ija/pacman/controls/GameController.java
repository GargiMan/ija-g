/**
 * @file GameController.java
 * @brief Controller for the game scene of the application
 * @author Marek Gergel (xgerge01)
 */
package ija.pacman.controls;

import ija.pacman.App;
import ija.pacman.game.Direction;
import ija.pacman.game.Maze;
import ija.pacman.game.field.Field;
import ija.pacman.game.field.PathField;
import ija.pacman.game.object.GhostObject;
import ija.pacman.game.object.KeyObject;
import ija.pacman.log.GameLogger;
import ija.pacman.view.FieldView;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.*;

public class GameController {

    private final GameLogger gameLogger = App.getGame().getLogger();
    private final Maze maze = App.getGame().getMaze();

    /**
     * Handles key released event
     * @param e key event
     */
    public void movePacman(KeyEvent e) {
        switch (e.getCode()) {
            case LEFT -> {
                if (!maze.getPacman().move(Direction.L)) return;
            }
            case UP -> {
                if (!maze.getPacman().move(Direction.U)) return;
            }
            case RIGHT -> {
                if (!maze.getPacman().move(Direction.R)) return;
            }
            case DOWN -> {
                if (!maze.getPacman().move(Direction.D)) return;
            }
            default -> {
                return;
            }
        }
        if (!App.getGame().isFinished()) {
            maze.getGhosts().forEach(this::moveGhost);
        }
        logPositions();
    }

    /**
     * Moves ghost in random direction
     * @param ghostObject ghost to move
     */
    private void moveGhost(GhostObject ghostObject) {
        if (!ghostObject.canMove(Direction.D) && !ghostObject.canMove(Direction.U) && !ghostObject.canMove(Direction.L) && !ghostObject.canMove(Direction.R)) {
            return;
        }
        Direction direction;
        Random random = new Random();
        do {
            direction = Direction.values()[random.nextInt(4)+1];
        } while (!ghostObject.canMove(direction));
        ghostObject.move(direction);
    }

    /**
     * Logs current positions of all objects
     */
    public void logPositions() {
        int[] position = maze.getPacman().field().getCoordinates();
        gameLogger.log("S-"+position[0]+":"+position[1]+"\t");
        for (GhostObject ghost : maze.getGhosts()) {
            position = ghost.field().getCoordinates();
            gameLogger.log("G-" + position[0] + ":" + position[1] + "\t");
        }
        HashSet<KeyObject> set = new HashSet<>(maze.getKeys());
        maze.getPacman().showKeys().forEach(set::remove);
        for (KeyObject key : set) {
            position = key.field().getCoordinates();
            gameLogger.log("K-" + position[0] + ":" + position[1] + "\t");
        }
        gameLogger.log("\n");
    }

    public void movePacmanToField(MouseEvent mouseEvent) {
        if (mouseEvent.getTarget().getClass() != Canvas.class) return;
        PathField fromField = (PathField) maze.getPacman().field();
        if (!(((FieldView) (((Canvas) mouseEvent.getTarget()).getParent())).field() instanceof PathField toField)) return;

        AStarAlgorithm astar = new AStarAlgorithm(maze);
        List<Cell> path = astar.findOptimalPath(fromField.getCoordinates()[0], fromField.getCoordinates()[1], toField.getCoordinates()[0], toField.getCoordinates()[1]);

        if (path != null) {
            for (int i = 0; i < path.size() - 1; i++) {
                Direction direction = Direction.fromPositions(path.get(i).row, path.get(i).col, path.get(i+1).row, path.get(i+1).col);
                switch (direction) {
                    case L -> movePacman(new KeyEvent(null, null, null, null, null, KeyCode.LEFT, false, false, false, false));
                    case U -> movePacman(new KeyEvent(null, null, null, null, null, KeyCode.UP, false, false, false, false));
                    case R -> movePacman(new KeyEvent(null, null, null, null, null, KeyCode.RIGHT, false, false, false, false));
                    case D -> movePacman(new KeyEvent(null, null, null, null, null, KeyCode.DOWN, false, false, false, false));
                }
            }
        }
    }

    class Cell {
        int row;
        int col;
        int f;
        int g;
        int h;
        Cell parent;

        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
            this.f = 0;
            this.g = 0;
            this.h = 0;
            this.parent = null;
        }
    }

    private class AStarAlgorithm {
        private Maze maze; // Grid representation of the game field

        public AStarAlgorithm(Maze maze) {
            this.maze = maze;
        }

        // Heuristic function: Manhattan distance
        private int calculateHeuristic(int row, int col, int targetRow, int targetCol) {
            return Math.abs(row - targetRow) + Math.abs(col - targetCol);
        }

        // Check if a cell is valid (within grid bounds and not a wall)
        private boolean isValidCell(int row, int col) {
            Field field = maze.getField(row, col);
            return field != null && field.canMove();
        }

        // Check if the given cell is the target cell
        private boolean isTargetCell(int row, int col, int targetRow, int targetCol) {
            return row == targetRow && col == targetCol;
        }

        // Get the optimal path from the start cell to the target cell
        public List<Cell> findOptimalPath(int startRow, int startCol, int targetRow, int targetCol) {
            // Initialize the open and closed lists
            PriorityQueue<Cell> openList = new PriorityQueue<>(Comparator.comparingInt(c -> c.f));
            boolean[][] closedList = new boolean[maze.numRows()][maze.numCols()];

            // Create the start and target cells
            Cell startCell = new Cell(startRow, startCol);
            Cell targetCell = new Cell(targetRow, targetCol);

            // Add the start cell to the open list
            openList.offer(startCell);

            while (!openList.isEmpty()) {
                // Remove the cell with the lowest f value from the open list
                Cell currentCell = openList.poll();

                // Mark the current cell as visited
                closedList[currentCell.row][currentCell.col] = true;

                // Check if the current cell is the target cell
                if (isTargetCell(currentCell.row, currentCell.col, targetRow, targetCol)) {
                    // Reconstruct the path
                    List<Cell> path = new ArrayList<>();
                    Cell cell = currentCell;
                    while (cell != null) {
                        path.add(cell);
                        cell = cell.parent;
                    }
                    Collections.reverse(path);
                    return path;
                }

                // Generate the neighboring cells
                int[] dx = {-1, 1, 0, 0}; // row offsets
                int[] dy = {0, 0, -1, 1}; // column offsets
                for (int i = 0; i < 4; i++) {
                    int newRow = currentCell.row + dx[i];
                    int newCol = currentCell.col + dy[i];

                    // Check if the neighboring cell is valid and not visited
                    if (isValidCell(newRow, newCol) && !closedList[newRow][newCol]) {
                        Cell neighbor = new Cell(newRow, newCol);
                        neighbor.parent = currentCell;

                        // Calculate the g, h, and f values for the neighboring cell
                        neighbor.g = currentCell.g + 1;
                        neighbor.h = calculateHeuristic(newRow, newCol, targetRow, targetCol);
                        neighbor.f = neighbor.g + neighbor.h;

                        // Add the neighboring cell to the open list
                        openList.offer(neighbor);
                    }
                }
            }

            // No path found
            return null;
        }
    }


}
