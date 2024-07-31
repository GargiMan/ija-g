/**
 * @file ReplayController.java
 * @brief Controller for the replay scene of the application
 * @author Marek Gergel (xgerge01)
 */
package ija.pacman.controls;

import ija.pacman.App;
import ija.pacman.game.Direction;
import ija.pacman.game.Game;
import ija.pacman.game.object.PacmanObject;
import ija.pacman.log.GameLogger;
import ija.pacman.log.Move;
import ija.pacman.others.Constant;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Objects;
import java.util.Timer;

public class ReplayController {

    private static final double REPLAY_SPEED = 4.0;
    private final GameLogger gameLogger = App.getGame().getLogger();

    private static class LocalTimer {
        private static Timer timer = null;
        private static Runnable cleanup = null;

        public static void cancel() {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            if (cleanup != null) {
                Runnable tmp = cleanup;
                cleanup = null;
                tmp.run();
            }
        }

        public static void scheduleAtFixedRate(java.util.TimerTask task, Runnable cleanup, long period) {
            if (timer != null) cancel();
            timer = new Timer();
            timer.scheduleAtFixedRate(task, 0, period);
            LocalTimer.cleanup = cleanup;
        }
    }

    /**
     * Returns the controls for replaying the game
     * @return box with controls
     */
    public Node getControls() {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10.0));
        hBox.setSpacing(20.0);
        hBox.setAlignment(Pos.CENTER);

        hBox.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        Button buttonFirst = new Button(Constant.UI.BUTTON_FIRST);
        Button buttonPrevious = new Button(Constant.UI.BUTTON_PREVIOUS);
        Button buttonBackward = new Button(Constant.UI.BUTTON_BACKWARD);
        Button buttonStop = new Button(Constant.UI.BUTTON_STOP);
        Button buttonForward = new Button(Constant.UI.BUTTON_FORWARD);
        Button buttonNext = new Button(Constant.UI.BUTTON_NEXT);
        Button buttonLast = new Button(Constant.UI.BUTTON_LAST);

        // first, previous, next, last
        buttonFirst.setOnAction(event -> first());
        buttonPrevious.setOnAction(event -> previous());
        buttonNext.setOnAction(event -> {LocalTimer.cancel(); next();});
        buttonLast.setOnAction(event -> {LocalTimer.cancel(); last();});

        // forward and reverse
        buttonBackward.setOnAction(event -> {
            if (Objects.equals(buttonBackward.getText(), Constant.UI.BUTTON_BACKWARD)) {
                buttonBackward.setText(Constant.UI.BUTTON_PAUSE);
                backward();
            } else {
                buttonBackward.setText(Constant.UI.BUTTON_BACKWARD);
                pause();
            }
        });
        buttonForward.setOnAction(event -> {
            if (Objects.equals(buttonForward.getText(), Constant.UI.BUTTON_FORWARD)) {
                buttonForward.setText(Constant.UI.BUTTON_PAUSE);
                forward();
            } else {
                buttonForward.setText(Constant.UI.BUTTON_FORWARD);
                pause();
            }
        });

        // stop
        buttonStop.setOnAction(event -> App.showMenu());

        hBox.getChildren().addAll(buttonFirst, buttonPrevious, buttonBackward, buttonStop, buttonForward, buttonNext, buttonLast);
        hBox.setPrefHeight(Game.GAME_TILE_SIZE);

        return hBox;
    }

    public void first() {
        LocalTimer.scheduleAtFixedRate(new java.util.TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (!gameLogger.getMovesIterator().hasPrevious()) {
                        pause();
                        return;
                    }
                    previous();
                });
            }
        }, null, (long)(1/ REPLAY_SPEED * 100));
    }

    public void previous() {
        if (!gameLogger.getMovesIterator().hasPrevious()) return;

        for (Move move : gameLogger.getMovesIterator().current()) {
            if (move.getObject().isKey()) continue;

            move.getObject().undoMove(Direction.fromPositions(move.getRow(), move.getCol(), move.previous().getRow(), move.previous().getCol()));

            //heal pacman if he died
            if (move.getObject() instanceof PacmanObject pacman && pacman.getLives() <= 0) pacman.heal();
        }

        //return key if it was picked up
        List<Move> previousMoves = gameLogger.getMovesIterator().previous();
        if (previousMoves.stream().anyMatch(m -> m.next() == null)) {
            App.getGame().getMaze().getPacman().returnKey().place();
        }
    }

    public void backward() {
        LocalTimer.scheduleAtFixedRate(new java.util.TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (!gameLogger.getMovesIterator().hasPrevious()) {
                        pause();
                        return;
                    }
                    previous();
                });
            }
        }, () -> {
            ((Pane)App.getStage().getScene().getRoot().getChildrenUnmodifiable().get(1)).getChildrenUnmodifiable()
                    .stream().filter(n -> n instanceof ButtonBase nbutton && nbutton.getText().equals(Constant.UI.BUTTON_PAUSE)).findFirst().ifPresent(n -> ((ButtonBase)n).setText(Constant.UI.BUTTON_BACKWARD));
        }, (long)(1/ REPLAY_SPEED * 1000));
    }

    public void pause() {
        LocalTimer.cancel();
    }

    public void forward() {
        LocalTimer.scheduleAtFixedRate(new java.util.TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (!gameLogger.getMovesIterator().hasNext()) {
                        pause();
                        return;
                    }
                    next();
                });
            }
        }, () -> {
            ((Pane)App.getStage().getScene().getRoot().getChildrenUnmodifiable().get(1)).getChildrenUnmodifiable()
                    .stream().filter(n -> n instanceof ButtonBase nbutton && nbutton.getText().equals(Constant.UI.BUTTON_PAUSE)).findFirst().ifPresent(n -> ((ButtonBase)n).setText(Constant.UI.BUTTON_FORWARD));
        }, (long)(1/ REPLAY_SPEED * 1000));
    }

    public void next() {
        if (!gameLogger.getMovesIterator().hasNext()) return;

        for (Move move : gameLogger.getMovesIterator().current()) {
            if (move.getObject().isKey()) continue;

            move.getObject().move(Direction.fromPositions(move.getRow(), move.getCol(), move.next().getRow(), move.next().getCol()));
        }

        gameLogger.getMovesIterator().next();
    }

    public void last() {
        LocalTimer.scheduleAtFixedRate(new java.util.TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (!gameLogger.getMovesIterator().hasNext()) {
                        pause();
                        return;
                    }
                    next();
                });
            }
        }, null, (long)(1/ REPLAY_SPEED * 100));
    }
}
