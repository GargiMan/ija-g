module ija.pacman {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires maven.model;
    requires plexus.utils;

    opens ija.pacman to javafx.fxml;
    exports ija.pacman;
}