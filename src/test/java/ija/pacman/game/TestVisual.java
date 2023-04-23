package ija.pacman.game;

import ija.pacman.game.object.MazeObject;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestVisual {

    public static final int DELAY = 500;

    public static void main(String... args) throws IOException {

        Maze maze = new MazeConfigure().load("mapaValidGhost.txt").createMaze();

        MazePresenter presenter = new MazePresenter(maze);
        presenter.open();

        sleep(DELAY);

        MazeObject obj = maze.ghosts().get(0);

        obj.move(Direction.L);
        sleep(DELAY);
        obj.move(Direction.L);
        sleep(DELAY);
        obj.move(Direction.D);
        sleep(DELAY);
        obj.move(Direction.D);
        sleep(DELAY);
        obj.move(Direction.D);
        sleep(DELAY);
        obj.move(Direction.D);
        sleep(DELAY);
        obj.move(Direction.R);
        sleep(DELAY);
        obj.move(Direction.L);
        sleep(DELAY);
        obj.move(Direction.U);
    }

    /**
     * Uspani vlakna na zadany pocet ms.
     * @param ms Pocet ms pro uspani vlakna.
     */
    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestVisual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
