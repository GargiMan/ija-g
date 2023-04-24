module ija.pacman {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;
    requires maven.model;
    requires plexus.utils;

    opens ija.pacman to javafx.fxml;
    exports ija.pacman;
    exports ija.pacman.controls;
    opens ija.pacman.controls to javafx.fxml;
}