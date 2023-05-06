/**
 * Main module of the application.
 * @author Marek Gergel (xgerge01)
 */
module ija.pacman {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires java.logging;

    exports ija.pacman;
    exports ija.pacman.controls;
    exports ija.pacman.game;
    exports ija.pacman.game.field;
    exports ija.pacman.game.object;
    exports ija.pacman.log;
    exports ija.pacman.others;
    exports ija.pacman.view;
}