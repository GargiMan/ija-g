package ija.pacman.view;

import ija.pacman.game.object.MazeObject;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;

public class KeyView implements NodeView {
    private MazeObject model;
    private FieldView parent;

    public KeyView(FieldView parent, MazeObject m) {
        this.model = m;
        this.parent = parent;
    }

    public void paintNode(GraphicsContext g) {
        Bounds bounds = this.parent.getLayoutBounds();
        File file = new File(System.getProperty("user.dir") + File.separator + "data" + File.separator + "icons" + File.separator + "key.png");
        g.drawImage(new Image(file.toURI().toString()), 0, 0, bounds.getWidth(), bounds.getHeight());
    }
}