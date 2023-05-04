package ija.pacman.view;

import ija.pacman.App;
import ija.pacman.Game;
import ija.pacman.game.Direction;
import ija.pacman.game.field.Field;
import ija.pacman.game.field.WallField;
import ija.pacman.game.object.MazeObject;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class FieldView extends Pane implements MazeObject {
    private final Field model;
    private final List<NodeView> objects;
    private int changedModel = 0;

    public FieldView(Field model) {
        this.model = model;
        this.objects = new ArrayList<>();
        this.getChildren().add(new Canvas(Game.GAME_TILE_SIZE, Game.GAME_TILE_SIZE));
        this.updateView();
        model.addObject(this);
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        GraphicsContext g = ((Canvas) this.getChildren().get(0)).getGraphicsContext2D();
        this.objects.forEach(v -> v.paintNode(g));
    }

    private void updateView() {
        this.setPrefSize(Game.GAME_TILE_SIZE, Game.GAME_TILE_SIZE);
        if (this.model.canMove()) {
            this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
            this.getChildren().set(0, new Canvas(Game.GAME_TILE_SIZE, Game.GAME_TILE_SIZE));
            if (!this.model.isEmpty()) {
                MazeObject o = this.model.get();
                if (o.isPacman()) {
                    this.objects.add(new PacmanView(this, o));
                } else if (o.isGhost()) {
                    this.objects.add(new GhostView(this, o));
                } else if (o.isKey()) {
                    this.objects.add(new KeyView(this, o));
                } else if (o.isTarget()) {
                    this.objects.add(new TargetView(this, o));
                }
                GraphicsContext g = ((Canvas) this.getChildren().get(0)).getGraphicsContext2D();
                this.objects.get(this.objects.size()-1).paintNode(g);

                if (App.getStage() != null) {
                    //object info tooltip
                    Tooltip tooltip = new Tooltip();
                    this.setOnMouseEntered(e -> {
                        Tooltip.install(this, tooltip);
                        this.setBackground(new Background(new BackgroundFill(Color.color(0.1,0.1,0.1), null, null)));
                        tooltip.setText(this.model.get().getInfo());
                        tooltip.show(this, e.getScreenX(), e.getScreenY());
                        tooltip.setShowDelay(Duration.ZERO);
                    });
                    this.setOnMouseExited(e -> {
                        tooltip.hide();
                        Tooltip.uninstall(this, tooltip);
                        this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
                    });
                }
            } else {
                this.objects.clear();
                this.setOnMouseEntered(e -> this.setBackground(new Background(new BackgroundFill(Color.color(0.1,0.1,0.1), null, null))));
                this.setOnMouseExited(e -> this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null))));
            }
        } else {
            this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

            //wall connection
            double left = this.model.nextField(Direction.L) instanceof WallField ? 0 : Game.GAME_TILE_SIZE/10.0;
            double top = this.model.nextField(Direction.U) instanceof WallField ? 0 : Game.GAME_TILE_SIZE/10.0;
            double right = this.model.nextField(Direction.R) instanceof WallField ? 0 : Game.GAME_TILE_SIZE/10.0;
            double bottom = this.model.nextField(Direction.D) instanceof WallField ? 0 : Game.GAME_TILE_SIZE/10.0;
            BorderWidths borderWidths = new BorderWidths(top, right, bottom, left);

            this.setBorder(new Border(new BorderStroke(Color.DARKBLUE, BorderStrokeStyle.SOLID, new CornerRadii(5.0), borderWidths)));
        }
    }

    public int numberUpdates() {
        return this.changedModel;
    }

    public void clearChanged() {
        this.changedModel = 0;
    }

    @Override
    public Color getColor() {
        return null;
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

    @Override
    public boolean undoMove(Direction dir) {
        return false;
    }

    @Override
    public int getLives() {
        return 0;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ++this.changedModel;
        this.updateView();
    }
}
