package ija.pacman.view;

import ija.pacman.game.object.MazeObject;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;

public class GhostView implements NodeView {
    private final MazeObject model;
    private final FieldView parent;

    public GhostView(FieldView parent, MazeObject m) {
        this.model = m;
        this.parent = parent;
    }

    public void paintNode(GraphicsContext g) {
        Bounds bounds = this.parent.getLayoutBounds();
        double w = bounds.getWidth();
        double h = bounds.getHeight();
        Math.max(h, w);
        double diameter = Math.min(h, w) - 10.0;
        double x = (w - diameter) / 2.0;
        double y = (h - diameter) / 2.0;
        g.setFill(this.model.getColor());
        g.fillOval(x, y, diameter, diameter);
    }
}
