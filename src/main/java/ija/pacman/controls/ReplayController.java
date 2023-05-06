/**
 * @file ReplayController.java
 * @brief Controller for the replay scene of the application
 * @author Marek Gergel (xgerge01)
 */
package ija.pacman.controls;

import ija.pacman.App;
import ija.pacman.game.Game;
import ija.pacman.log.GameLogger;
import ija.pacman.others.Constant;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class ReplayController {

    private GameLogger gameLogger = App.getGame().getLogger();

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
        ToggleButton buttonBackward = new ToggleButton(Constant.UI.BUTTON_BACKWARD);
        Button buttonStop = new Button(Constant.UI.BUTTON_STOP);
        ToggleButton buttonForward = new ToggleButton(Constant.UI.BUTTON_FORWARD);
        Button buttonNext = new Button(Constant.UI.BUTTON_NEXT);
        Button buttonLast = new Button(Constant.UI.BUTTON_LAST);

        // first, previous, next, last
        buttonFirst.setOnAction(event -> {
            gameLogger.first();
        });
        buttonPrevious.setOnAction(event -> {
            gameLogger.previous();
        });
        buttonNext.setOnAction(event -> {
            gameLogger.next();
        });
        buttonLast.setOnAction(event -> {
            gameLogger.last();
        });

        // forward and reverse
        ToggleGroup toggleGroup = new ToggleGroup();
        buttonBackward.setToggleGroup(toggleGroup);
        buttonForward.setToggleGroup(toggleGroup);
        buttonBackward.setOnAction(event -> {
            if (buttonBackward.isSelected()) {
                buttonForward.setText(Constant.UI.BUTTON_FORWARD);
                buttonBackward.setText(Constant.UI.BUTTON_PAUSE);
                gameLogger.backward();
            } else {
                buttonBackward.setText(Constant.UI.BUTTON_BACKWARD);
                gameLogger.pause();
            }
        });
        buttonForward.setOnAction(event -> {
            if (buttonForward.isSelected()) {
                buttonBackward.setText(Constant.UI.BUTTON_BACKWARD);
                buttonForward.setText(Constant.UI.BUTTON_PAUSE);
                gameLogger.forward();
            } else {
                buttonForward.setText(Constant.UI.BUTTON_FORWARD);
                gameLogger.pause();
            }
        });

        // stop
        buttonStop.setOnAction(event -> App.showMenu());

        hBox.getChildren().addAll(buttonFirst, buttonPrevious, buttonBackward, buttonStop, buttonForward, buttonNext, buttonLast);
        hBox.setPrefHeight(Game.GAME_TILE_SIZE);

        return hBox;
    }
}
