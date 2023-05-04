package ija.pacman.controls;

import ija.pacman.App;
import ija.pacman.game.Direction;
import ija.pacman.game.object.MazeObject;
import ija.pacman.game.object.PacmanObject;
import javafx.scene.input.KeyEvent;
import java.util.List;

public class GameController {

    private final PacmanObject pacman = App.getGame().getMaze().getPacman();
    List<MazeObject> ghosts = App.getGame().getMaze().ghosts();

    public void moveGhost(MazeObject ghost) {
        Direction direction;
        boolean canMove = false;
        while (!canMove) {
            int random = (int) (Math.random() * 4);
            switch (random) {
                case 0:
                    direction = Direction.L;
                    break;
                case 1:
                    direction = Direction.U;
                    break;
                case 2:
                    direction = Direction.R;
                    break;
                case 3:
                    direction = Direction.D;
                    break;
                default:
                    return;
            }
            canMove = ghost.canMove(direction);
            if (canMove) {
                ghost.move(direction);
            }
        }
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {

        switch (e.getCode()) {
            case LEFT:
                pacman.move(Direction.L);
                for (MazeObject ghost : ghosts) {
                    moveGhost(ghost);
                }
                break;
            case UP:
                pacman.move(Direction.U);
                for (MazeObject ghost : ghosts) {
                    moveGhost(ghost);
                }
                break;
            case RIGHT:
                pacman.move(Direction.R);
                for (MazeObject ghost : ghosts) {
                    moveGhost(ghost);
                }
                break;
            case DOWN:
                pacman.move(Direction.D);
                for (MazeObject ghost : ghosts) {
                    moveGhost(ghost);
                }
                break;
            default:
                System.out.println("unknown key");
                break;
        }
    }

}