package ija.pacman.view;

import ija.pacman.game.Direction;
import ija.pacman.game.object.MazeObject;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.io.File;
import javafx.util.Duration;

public class PacmanView implements NodeView {
    private MazeObject model;
    private FieldView parent;
    int animationFrame = 0; // start with the first frame
    private Direction direction;

    public PacmanView(FieldView parent, MazeObject m) {
        this.model = m;
        this.parent = parent;
    }

    public void setDirection(Direction l) {
        this.direction = l;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void paintNode(GraphicsContext g) {
        Bounds bounds = this.parent.getLayoutBounds();
        double w = bounds.getWidth();
        double h = bounds.getHeight();
        Math.max(h, w);
        double diameter = Math.min(h, w) - 10.0;
        double x = (w - diameter) / 2.0;
        double y = (h - diameter) / 2.0;
        Image pacmanRight1 = new Image(new File("ija-g/data/sprites/pacmanRight1.png").toURI().toString());
        Image pacmanRight2 = new Image(new File("ija-g/data/sprites/pacmanRight2.png").toURI().toString());
        Image pacmanLeft1 = new Image(new File("ija-g/data/sprites/pacmanLeft1.png").toURI().toString());
        Image pacmanLeft2 = new Image(new File("ija-g/data/sprites/pacmanLeft2.png").toURI().toString());
        Image pacmanUp1 = new Image(new File("ija-g/data/sprites/pacmanUp1.png").toURI().toString());
        Image pacmanUp2 = new Image(new File("ija-g/data/sprites/pacmanUp2.png").toURI().toString());
        Image pacmanDown1 = new Image(new File("ija-g/data/sprites/pacmanDown1.png").toURI().toString());
        Image pacmanDown2= new Image(new File("ija-g/data/sprites/pacmanDown2.png").toURI().toString());


        Image[] pacmanRightFrames = { pacmanRight1, pacmanRight2 }; // array of frames for Pacman moving right
        Image[] pacmanLeftFrames = { pacmanLeft1, pacmanLeft2 };
        Image[] pacmanUpFrames = { pacmanUp1, pacmanUp2 };
        Image[] pacmanDownFrames = { pacmanDown1, pacmanDown2 };

        // Add arrays for other directions as well
        Direction direction = this.getDirection();
        Timeline animation = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    // Change Pacman's animation every second
                    switch (direction) {
                        case R:
                            g.drawImage(pacmanRightFrames[animationFrame], x, y, diameter, diameter);
                            break;
                        case L:
                            g.drawImage(pacmanLeftFrames[animationFrame], x, y, diameter, diameter);
                            break;
                        case U:
                            g.drawImage(pacmanUpFrames[animationFrame], x, y, diameter, diameter);
                            break;
                        case D:
                            g.drawImage(pacmanDownFrames[animationFrame], x, y, diameter, diameter);
                            break;
                    }
                    animationFrame = (animationFrame + 1) % pacmanRightFrames.length; // advance to the next frame
                })
        );
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

        //g.setFill(Color.BLACK);
        //g.setFont(new Font("Serif", 10));
        //g.fillText(Integer.toString(this.model.getLives()), (x + diameter) / 2, (y + diameter + 10.0) / 2 + 5);
    }


}
