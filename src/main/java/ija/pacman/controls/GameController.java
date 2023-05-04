package ija.pacman.controls;

import ija.pacman.App;
import ija.pacman.log.Logger;
import ija.pacman.game.Direction;
import ija.pacman.game.Maze;
import ija.pacman.game.object.GhostObject;
import ija.pacman.game.object.KeyObject;
import javafx.scene.input.KeyEvent;

import java.util.HashSet;

public class GameController {

    private final Logger logger = App.getGame().getLogger();
    private final Maze maze = App.getGame().getMaze();

    public void keyReleased(KeyEvent e) {
        switch (e.getCode()) {
            case LEFT -> maze.getPacman().move(Direction.L);
            case UP -> maze.getPacman().move(Direction.U);
            case RIGHT -> maze.getPacman().move(Direction.R);
            case DOWN -> maze.getPacman().move(Direction.D);
            default -> {
                return;
            }
        }
        logPositions();
    }

    public void logPositions() {
        int[] position = maze.getPacman().getField().getCoordinates();
        logger.log("S-"+position[0]+":"+position[1]+"\t");
        for (GhostObject ghost : maze.getGhosts()) {
            position = ghost.getField().getCoordinates();
            logger.log("G-" + position[0] + ":" + position[1] + "\t");
        }
        HashSet<KeyObject> set = new HashSet<>(maze.getKeys());
        maze.getPacman().showKeys().forEach(set::remove);
        for (KeyObject key : set) {
            position = key.getField().getCoordinates();
            logger.log("K-" + position[0] + ":" + position[1] + "\t");
        }
        logger.log("\n");
    }
}
