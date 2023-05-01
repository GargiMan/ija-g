module ija.pacman {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;

    exports ija.pacman;
    exports ija.pacman.controls;
    opens ija.pacman to javafx.fxml;
    opens ija.pacman.controls to javafx.fxml;
}