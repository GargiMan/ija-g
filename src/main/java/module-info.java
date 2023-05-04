module ija.pacman {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;

    exports ija.pacman;
    exports ija.pacman.game;
    exports ija.pacman.game.object;
    exports ija.pacman.game.field;
    exports ija.pacman.controls;
    exports ija.pacman.log;
    opens ija.pacman to javafx.fxml;
    opens ija.pacman.controls to javafx.fxml;
    opens ija.pacman.log to javafx.fxml;
}