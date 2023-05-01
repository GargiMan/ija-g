package ija.pacman.controls;

import ija.pacman.App;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class Controller {

    @FXML
    private Label gameName;

    @FXML
    private ListView<String> mapList;

    public void onStartGameButtonClick(ActionEvent actionEvent) {
        App.startGame(App.getMap());
    }

    public void onMapSelection(Observable observable) {
        if (observable instanceof ReadOnlyProperty<?>) {
            App.setMap((String) ((ReadOnlyProperty<?>) observable).getValue());
        }
    }
}