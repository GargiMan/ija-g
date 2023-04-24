package ija.pacman.controls;

import ija.pacman.App;
import ija.pacman.game.Direction;
import ija.pacman.game.object.PacmanObject;
import javafx.scene.input.KeyEvent;

public class GameController {

    private final PacmanObject pacman = App.getGame().getMaze().getPacman();

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getCode()) {
            case LEFT -> pacman.move(Direction.L);
            case UP -> pacman.move(Direction.U);
            case RIGHT -> pacman.move(Direction.R);
            case DOWN -> pacman.move(Direction.D);
            default -> {
                System.out.println("unknown key");
            }
        }
    }
}
