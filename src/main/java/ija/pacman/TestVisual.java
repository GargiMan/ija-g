package ija.pacman;

import ija.pacman.game.Direction;
import ija.pacman.game.object.MazeObject;
import javafx.stage.Stage;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TestVisual extends App {

    public static final int DELAY = 500;

    @Override
    public void start(Stage stage) throws XmlPullParserException, IOException {
        super.start(stage);
        String str = System.getProperty("user.dir") + File.separator + "data" + File.separator + "maps" + File.separator + "mapaValidGhost.txt";
        App.startGame(new File(str));

        runTest();
    }

    public void runTest() {
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

    public static void main(String... args) {
        launch();
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
