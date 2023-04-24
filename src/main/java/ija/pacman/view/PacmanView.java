package ija.pacman.view;

import ija.pacman.game.object.MazeObject;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PacmanView implements NodeView {
    private MazeObject model;
    private FieldView parent;

    public PacmanView(FieldView parent, MazeObject m) {
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
        g.setFill(Color.YELLOW);
        g.fillOval(x, y, diameter, diameter);
        //g.setFill(Color.BLACK);
        //g.setFont(new Font("Serif", 10));
        //g.fillText(Integer.toString(this.model.getLives()), (x + diameter) / 2, (y + diameter + 10.0) / 2 + 5);
    }
}
