package ija.pacman;

import ija.pacman.controls.GameController;
import ija.pacman.controls.ReplayController;
import ija.pacman.game.Maze;
import ija.pacman.game.MazeConfigure;
import ija.pacman.view.FieldView;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;

public class Game {

    public static int GAME_TILE_SIZE;
    private final File map;
    private Maze maze = null;
    private Logger logger;
    private boolean replay = false;

    public Game(File map) {
        this.map = map;
        GAME_TILE_SIZE = 50;

        // Load maze
        try {
            maze = new MazeConfigure().load(this.map).createMaze();
        } catch (Exception e) {
            System.getLogger(Game.class.getName()).log(System.Logger.Level.ERROR, "Failed to load maze from file: "+map.getAbsolutePath()+"\n"+e.getMessage());
        }
    }

    public Game(File map, boolean replay) {
        this(map);
        this.replay = replay;
    }

    public boolean isReplay() {
        return replay;
    }

    public Maze getMaze() {
        return maze;
    }

    public Logger getLogger() {
        return logger;
    }

    public void start() {
        if (maze == null) {
            return;
        }

        logger = new Logger(LocalDateTime.now().toString().replaceAll("[.].*", "").replace("T","_").replace(":","-")+".log");
        try (FileReader fr = new FileReader(map);
             BufferedReader br = new BufferedReader(fr)) {
            while (br.ready()) {
                logger.log(br.readLine()+"\n");
            }
        } catch (Exception e) {
            System.getLogger(Game.class.getName()).log(System.Logger.Level.ERROR, "Failed to log maze\n"+e.getMessage());
            return;
        }

        System.getLogger(Game.class.getName()).log(System.Logger.Level.INFO, "Initializing user interface");
        initializeInterface();
    }

    public void replay() {
        if (maze == null) {
            return;
        }

        System.getLogger(Game.class.getName()).log(System.Logger.Level.INFO, "Initializing user interface");
        initializeInterface();
    }

    public static void stop(boolean finished) {
        String message = "Game"+(App.getSelectedMap() != null ? " ("+App.getSelectedMap().getName().replace(".txt", "")+")" : "") + " succefully finished: "+finished;
        System.getLogger(Game.class.getName()).log(System.Logger.Level.INFO, message);
        if (App.getStage() != null) App.showMenu();
    }

    private void initializeInterface() {

        // Set the scene
        Stage stage = App.getStage();
        stage.setScene(replay ? createReplayScene() : createPlayScene());
        stage.setTitle(stage.getTitle()+" - "+map.getName().replace(".txt", ""));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();

        System.getLogger(Game.class.getName()).log(System.Logger.Level.INFO, "User interface initialized");
    }

    private Scene createPlayScene() {
        GridPane layout = createGrid();

        // setup controls
        GameController gameController = new GameController();
        layout.setOnKeyReleased(gameController::keyReleased);

        Scene scene = new Scene(layout, layout.getMaxWidth(), layout.getMaxHeight());

        // focus on game - needed for controls
        layout.requestFocus();

        return scene;
    }

    private Scene createReplayScene() {
        GridPane layout = createGrid();

        // setup controls
        ReplayController replayController = new ReplayController();
        layout.setOnKeyReleased(replayController::keyReleased);

        Scene scene = new Scene(layout, layout.getMaxWidth(), layout.getMaxHeight());

        // focus on game - needed for controls
        layout.requestFocus();

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
