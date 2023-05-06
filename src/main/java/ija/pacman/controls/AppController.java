/**
 * @file AppController.java
 * @brief Controller for the main menu of the application
 * @author Marek Gergel (xgerge01)
 */
package ija.pacman.controls;

import ija.pacman.App;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyProperty;
import javafx.event.ActionEvent;

public class AppController {

    /**
     * Starts the game with the selected map
     * @param actionEvent ignored
     */
    public void onStartGameButtonClick(ActionEvent actionEvent) {
        App.startGame(App.getSelectedMap());
    }

    /**
     * Set the selected map to start the game with
     * @param observable changed value
     */
    public void onMapSelection(Observable observable) {
        if (observable instanceof ReadOnlyProperty<?>) {
            App.setSelectedMap((String) ((ReadOnlyProperty<?>) observable).getValue());
        }
    }

    /**
     * Replay the selected log
     * @param actionEvent ignored
     */
    public void onReplayGameButtonClick(ActionEvent actionEvent) {
        App.replayGame(App.getSelectedLog());
    }

    /**
     * Set the selected log to replay
     * @param observable changed value
     */
    public void onLogSelection(Observable observable) {
        if (observable instanceof ReadOnlyProperty<?>) {
            App.setSelectedLog((String) ((ReadOnlyProperty<?>) observable).getValue());
        }
    }
}