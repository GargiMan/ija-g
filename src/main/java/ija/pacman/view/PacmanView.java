/**
 * @file PacmanView.java
 * @brief Class for painting pacman in UI
 * @author Marek Gergel (xgerge01)
 */
package ija.pacman.view;

import ija.pacman.game.object.MazeObject;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;

public class PacmanView implements NodeView {
    private MazeObject model;
    private FieldView parent;

    public PacmanView(FieldView parent, MazeObject m) {
        this.model = m;
        this.parent = parent;
    }

    public void paintNode(GraphicsContext g) {
        Bounds bounds = this.parent.getLayoutBounds();
        File file = new File(System.getProperty("user.dir") + File.separator + "lib" + File.separator + "icons" + File.separator + "pacman.png");
        g.drawImage(new Image(file.toURI().toString()), 0, 0, bounds.getWidth(), bounds.getHeight());
    }
}
