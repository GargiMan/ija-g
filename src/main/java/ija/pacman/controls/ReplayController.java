package ija.pacman.controls;

import ija.pacman.App;
import ija.pacman.log.Logger;
import ija.pacman.others.Constant;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class ReplayController {

    private Logger logger = App.getGame().getLogger();
    public void keyReleased(KeyEvent keyEvent) {
    }

    public Node getControls() {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10.0));
        hBox.setSpacing(20.0);
        hBox.setAlignment(Pos.CENTER);

        hBox.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        Button buttonFirst = new Button(Constant.UI.BUTTON_FIRST);
        Button buttonPrevious = new Button(Constant.UI.BUTTON_PREVIOUS);
        ToggleButton buttonReverse = new ToggleButton(Constant.UI.BUTTON_REVERSE);
        Button buttonStop = new Button(Constant.UI.BUTTON_STOP);
        ToggleButton buttonForward = new ToggleButton(Constant.UI.BUTTON_FORWARD);
        Button buttonNext = new Button(Constant.UI.BUTTON_NEXT);
        Button buttonLast = new Button(Constant.UI.BUTTON_LAST);

        // first, previous, next, last
        buttonFirst.setOnAction(event -> {
            logger.first();
        });
        buttonPrevious.setOnAction(event -> {
            logger.previous();
        });
        buttonNext.setOnAction(event -> {
            logger.next();
        });
        buttonLast.setOnAction(event -> {
            logger.last();
        });

        // forward and reverse
        ToggleGroup toggleGroup = new ToggleGroup();
        buttonReverse.setToggleGroup(toggleGroup);
        buttonForward.setToggleGroup(toggleGroup);
        buttonReverse.setOnAction(event -> {
            if (buttonReverse.isSelected()) {
                buttonForward.setText(Constant.UI.BUTTON_FORWARD);
                buttonReverse.setText(Constant.UI.BUTTON_PAUSE);
            } else {
                buttonReverse.setText(Constant.UI.BUTTON_REVERSE);
            }
            //logger.reverse();
        });
        buttonForward.setOnAction(event -> {
            if (buttonForward.isSelected()) {
                buttonReverse.setText(Constant.UI.BUTTON_REVERSE);
                buttonForward.setText(Constant.UI.BUTTON_PAUSE);
            } else {
                buttonForward.setText(Constant.UI.BUTTON_FORWARD);
            }
            //logger.forward();
        });

        // stop
        buttonStop.setOnAction(event -> App.showMenu());

        hBox.getChildren().addAll(buttonFirst, buttonPrevious, buttonReverse, buttonStop, buttonForward, buttonNext, buttonLast);

        return hBox;
    }
}
