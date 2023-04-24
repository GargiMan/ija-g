package ija.pacman.controls;

import ija.pacman.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Controller {

    @FXML
    public Label gameName;

    public void onStartGameButtonClick(ActionEvent actionEvent) {
        App.startGame(App.maps[0]);
    }
}