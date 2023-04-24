package ija.pacman.game;

import ija.pacman.App;
import ija.pacman.game.object.MazeObject;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestVisual extends App {

    public static final int DELAY = 500;

    public static void main(String... args) {

        String filePath = System.getProperty("user.dir") + File.separator + "data" + File.separator + "maps" + File.separator + "mapaValidGhost.txt";

        App.startGame(new File(filePath));

        sleep(DELAY);

        MazeObject obj = App.getGame().getMaze().ghosts().get(0);

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
