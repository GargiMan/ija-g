package ija.pacman;

import ija.pacman.controls.GameController;
import ija.pacman.game.Maze;
import ija.pacman.game.MazeConfigure;
import ija.pacman.view.FieldView;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

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

        // Initialize the user interface here
        System.out.println("Initializing user interface...");
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

        // The user interface is now initialized
        System.out.println("User interface initialized");
    }

    public static void stop(boolean finished) {
        System.out.println("Game finished: "+finished);
        if (App.stage != null) App.showMenu();
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
        App.stage.setScene(scene);
        App.stage.setTitle(App.stage.getTitle()+" - "+map.getName());
        App.stage.setResizable(false);
        App.stage.show();
    }
}