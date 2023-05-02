package ija.pacman.controls;

import ija.pacman.App;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyProperty;
import javafx.event.ActionEvent;

public class AppController {

    public void onStartGameButtonClick(ActionEvent actionEvent) {
        App.startGame(App.getSelectedMap());
    }

    public void onMapSelection(Observable observable) {
        if (observable instanceof ReadOnlyProperty<?>) {
            App.setSelectedMap((String) ((ReadOnlyProperty<?>) observable).getValue());
        }
    }

    public void onReplayGameButtonClick(ActionEvent actionEvent) {
    }

    public void onLogSelection(Observable observable) {
        if (observable instanceof ReadOnlyProperty<?>) {

        }
    }
}