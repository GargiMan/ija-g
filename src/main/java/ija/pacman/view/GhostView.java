package ija.pacman.view;

import ija.pacman.game.object.MazeObject;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class GhostView implements ComponentView {
    private final MazeObject model;
    private final FieldView parent;

    public GhostView(FieldView parent, MazeObject m) {
        this.model = m;
        this.parent = parent;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        Rectangle bounds = this.parent.getBounds();
        double w = bounds.getWidth();
        double h = bounds.getHeight();
        Math.max(h, w);
        double diameter = Math.min(h, w) - 10.0;
        double x = (w - diameter) / 2.0;
        double y = (h - diameter) / 2.0;
        Ellipse2D.Double ellipse = new Ellipse2D.Double(x, y, diameter, diameter);
        g2.setColor(Color.yellow);
        g2.fill(ellipse);
    }
}
