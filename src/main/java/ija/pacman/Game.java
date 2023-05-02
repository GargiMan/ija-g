package ija.pacman;

import ija.pacman.controls.GameController;
import ija.pacman.game.Maze;
import ija.pacman.game.MazeConfigure;
import ija.pacman.view.FieldView;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.util.concurrent.CountDownLatch;

public class Game {

    public static final int GAME_TILE_SIZE = 50;
    private CountDownLatch latch;
    private final File map;
    private final Maze maze;

    public Game(File map) {
        this.map = map;

        // Load maze
        try {
            maze = new MazeConfigure().load(this.map).createMaze();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Maze getMaze() {
        return maze;
    }

    public void start() {

        System.getLogger(this.toString()).log(System.Logger.Level.INFO, "Initializing user interface");
        latch = new CountDownLatch(1);

        initializeInterface();
        latch.countDown();
        //Platform.runLater(() -> {});

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }

        System.getLogger(Game.class.getName()).log(System.Logger.Level.INFO, "User interface initialized");
    }

    public static void stop(boolean finished) {
        String message = "Game"+(App.getSelectedMap() != null ? " ("+App.getSelectedMap().getName().replace(".txt", "")+")" : "") + " succefully finished: "+finished;
        System.getLogger(Game.class.getName()).log(System.Logger.Level.INFO, message);
        if (App.getStage() != null) App.showMenu();
    }

    private void initializeInterface() {
        // Add fields to the layout
        int rows = maze.numRows();
        int cols = maze.numCols();

        int height = rows * GAME_TILE_SIZE;
        int width = cols * GAME_TILE_SIZE;

        GridPane layout = new GridPane();
        //layout.setGridLinesVisible(true);
        layout.setPrefSize(height, width);

        // setup controls
        GameController gameController = new GameController();
        layout.setOnKeyReleased(gameController::keyReleased);
        layout.setOnKeyPressed(gameController::keyPressed);
        layout.setFocusTraversable(true);

        // create maze field
        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < cols; ++j) {
                layout.add(new FieldView(maze.getField(i, j)), j, i);
            }
        }

        Scene scene = new Scene(layout, height, width);

        // focus on game - needed for controls
        layout.requestFocus();

        // Set the scene
        Stage stage = App.getStage();
        stage.setScene(scene);
        stage.setTitle(stage.getTitle()+" - "+map.getName().replace(".txt", ""));
        stage.setResizable(false);
        stage.show();
    }
}
