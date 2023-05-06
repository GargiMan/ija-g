/**
 * @file GameController.java
 * @brief Controller for the game scene of the application
 * @author Marek Gergel (xgerge01)
 */
package ija.pacman.controls;

import ija.pacman.App;
import ija.pacman.log.GameLogger;
import ija.pacman.game.Direction;
import ija.pacman.game.Maze;
import ija.pacman.game.object.GhostObject;
import ija.pacman.game.object.KeyObject;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;
import java.util.Random;

public class GameController {

    private final GameLogger gameLogger = App.getGame().getLogger();
    private final Maze maze = App.getGame().getMaze();

    /**
     * Handles key released event
     * @param e key event
     */
    public void keyReleased(KeyEvent e) {
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
        boolean canMove;
        Random random = new Random();
        do {
            direction = Direction.values()[random.nextInt(4)+1];
            canMove = ghostObject.canMove(direction);
        } while (!canMove);
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
}
