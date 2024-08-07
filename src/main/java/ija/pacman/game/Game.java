/**
 * @file Game.java
 * @brief Game class that handles game logic and interface initialization and switching between game and replay mode
 * @author Marek Gergel (xgerge01)
 */
package ija.pacman.game;

import ija.pacman.App;
import ija.pacman.controls.GameController;
import ija.pacman.controls.ReplayController;
import ija.pacman.log.GameLogger;
import ija.pacman.view.FieldView;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;

public class Game {

    public static int GAME_TILE_SIZE;
    private final File file;
    private Maze maze = null;
    private GameLogger gameLogger;
    private boolean replay = false;
    private boolean finished = false;

    public Game(File file) {
        this.file = file;
        GAME_TILE_SIZE = 50;

        // Load maze
        try {
            maze = new MazeConfigure().load(this.file).createMaze();
        } catch (Exception e) {
            System.getLogger(Game.class.getName()).log(System.Logger.Level.ERROR, "Failed to load maze from file: "+ file.getAbsolutePath()+"\n"+e.getMessage());
        }
    }

    public Maze getMaze() {
        return maze;
    }

    public GameLogger getLogger() {
        return gameLogger;
    }

    public void start() {
        if (maze == null) {
            return;
        }

        gameLogger = new GameLogger(LocalDateTime.now().toString().replaceAll("[.][^.]*$", "").replace("T","_").replace(":","-")+".log");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while (br.ready()) {
                gameLogger.log(br.readLine()+"\n");
            }
        } catch (Exception e) {
            System.getLogger(Game.class.getName()).log(System.Logger.Level.ERROR, "Failed to log maze\n"+e.getMessage());
            return;
        }
        gameLogger.log("~GAME~\n");

        initializeInterface(false);
    }

    public void replay() {
        if (maze == null) {
            return;
        }

        replay = true;

        gameLogger = new GameLogger(file);
        if (gameLogger.loadGame()) {
            initializeInterface(true);
        }
    }

    public boolean isReplay() {
        return replay;
    }

    public boolean isFinished() {
        return finished;
    }

    public void stop(boolean win) {
        this.finished = true;
        String message = "Game " + (App.getSelectedMap() != null ? "(" + App.getSelectedMap().getName().replaceAll("[.][^.]*$", "") + ")" : "") + " finished: "+(win?"WIN":"LOSE");
        System.getLogger(Game.class.getName()).log(System.Logger.Level.INFO, message);
        if (App.getStage() != null) App.showMenu();
    }

    private void initializeInterface(boolean replay) {

        // Set the scene
        Stage stage = App.getStage();
        stage.setTitle(stage.getTitle()+" - "+ file.getName().replaceAll("[.][^.]*$", ""));
        stage.setScene(replay ? createReplayScene() : createPlayScene());
        stage.show();
        stage.setResizable(true);
        stage.centerOnScreen();
    }

    private Scene createPlayScene() {
        GridPane layout = createGrid();

        // setup controls
        GameController gameController = new GameController();
        layout.setOnKeyReleased(gameController::movePacman);
        layout.setOnMouseClicked(gameController::movePacman);

        // log start position
        gameController.logPositions();

        Scene scene = new Scene(layout, layout.getMaxWidth(), layout.getMaxHeight());

        // focus on game - needed for controls
        layout.requestFocus();

        return scene;
    }

    private Scene createReplayScene() {

        // create game layout
        GridPane layout = createGrid();

        // add layout to vbox
        VBox vbox = new VBox();
        vbox.getChildren().add(layout);
        vbox.setFocusTraversable(true);

        // add replay controls
        ReplayController replayController = new ReplayController();
        HBox hbox = (HBox) replayController.getControls();
        vbox.getChildren().add(hbox);

        vbox.setMaxSize(layout.getMaxWidth(), layout.getMaxHeight()+hbox.getPrefHeight());
        Scene scene = new Scene(vbox, vbox.getMaxWidth(), vbox.getMaxHeight());

        // focus on box - needed for controls
        vbox.requestFocus();

        return scene;
    }

    private GridPane createGrid() {
        // Add fields to the layout
        int rows = maze.numRows();
        int cols = maze.numCols();

        // adjust tile size to fit screen
        while (rows * GAME_TILE_SIZE > Screen.getPrimary().getVisualBounds().getHeight() || cols * GAME_TILE_SIZE > Screen.getPrimary().getVisualBounds().getWidth()) {
            GAME_TILE_SIZE = GAME_TILE_SIZE - 5;
        }

        int height = rows * GAME_TILE_SIZE;
        int width = cols * GAME_TILE_SIZE;

        GridPane layout = new GridPane();
        layout.setMaxSize(width, height);

        layout.setFocusTraversable(true);

        // create maze field
        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < cols; ++j) {
                layout.add(new FieldView(maze.getField(i, j)), j, i);
            }
        }

        return layout;
    }
}
