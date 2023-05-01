package ija.pacman.controls;

import ija.pacman.App;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyProperty;
import javafx.event.ActionEvent;

public class Controller {

    public void onStartGameButtonClick(ActionEvent actionEvent) {
        App.startGame(App.getSelectedMap());
    }

    public void onMapSelection(Observable observable) {
        if (observable instanceof ReadOnlyProperty<?>) {
            App.setSelectedMap((String) ((ReadOnlyProperty<?>) observable).getValue());
        }
    }
}