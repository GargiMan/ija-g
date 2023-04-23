package ija.pacman.view;

import ija.pacman.game.Direction;
import ija.pacman.game.field.Field;
import ija.pacman.game.object.MazeObject;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class FieldView extends JPanel implements MazeObject {
    private final Field model;
    private final List<ComponentView> objects;
    private int changedModel = 0;

    public FieldView(Field model) {
        this.model = model;
        this.objects = new ArrayList();
        this.privUpdate();
        model.addObject(this);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.objects.forEach((v) -> {
            v.paintComponent(g);
        });
    }

    private void privUpdate() {
        if (this.model.canMove()) {
            this.setBackground(Color.white);
            if (!this.model.isEmpty()) {
                MazeObject o = this.model.get();
                ComponentView v = o.isPacman() ? new PacmanView(this, this.model.get()) : new GhostView(this, this.model.get());
                this.objects.add(v);
            } else {
                this.objects.clear();
            }
        } else {
            this.setBackground(Color.lightGray);
        }

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public int numberUpdates() {
        return this.changedModel;
    }

    public void clearChanged() {
        this.changedModel = 0;
    }

    public Field getField() {
        return this.model;
    }

    public boolean canMove(Direction dir) {
        return false;
    }

    public boolean move(Direction dir) {
        return false;
    }

    public int getLives() {
        return 0;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ++this.changedModel;
        this.privUpdate();
    }
}
