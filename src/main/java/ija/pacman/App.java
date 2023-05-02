package ija.pacman;

import ija.pacman.controls.AppController;
import ija.pacman.others.Constant;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class App extends Application {

    private static Document pom;
    private static Game game;
    private static Stage stage;
    private static File[] maps;
    private static File selectedMap;

    private static File[] logs;

    private static File selectedLog;

    public static Game getGame() {
        return game;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setSelectedMap(String mapFilename) {
        selectedMap = Arrays.stream(maps).filter(file -> file.getName().equals(mapFilename+".txt")).findFirst().orElseThrow();
    }

    public static File getSelectedMap() {
        return selectedMap;
    }

    public static void setSelectedLog(String logFilename) {
        selectedLog = Arrays.stream(logs).filter(file -> file.getName().equals(logFilename+".log")).findFirst().orElseThrow();
    }

    public static File getSelectedLog() {
        return selectedLog;
    }

    @Override
    public void start(Stage stage) throws ParserConfigurationException, IOException, SAXException {
        App.stage = stage;
        App.pom = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File("pom.xml"));

        showMenu();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void showMenu() {
        // load files
        String filePath = System.getProperty("user.dir") + File.separator + "data";
        maps = new File(filePath + File.separator + "maps").listFiles();
        logs = new File(filePath + File.separator + "logs").listFiles();

        // tab pane and controller
        TabPane tabPane = new TabPane();
        AppController appController = new AppController();

        // tab play
        Tab tabPlay = new Tab();
        tabPlay.setText(Constant.UI.TAB_PLAY);
        tabPlay.closableProperty().setValue(false);
        tabPane.getTabs().add(tabPlay);

        // tab play content
        VBox vBoxPlay = new VBox();
        vBoxPlay.setPadding(new Insets(20.0));
        vBoxPlay.setSpacing(20.0);
        vBoxPlay.setAlignment(Pos.CENTER);
        tabPlay.setContent(vBoxPlay);

        // start game button
        Button buttonStart = new Button();
        buttonStart.setText(Constant.UI.BUTTON_START);
        buttonStart.setOnAction(appController::onStartGameButtonClick);
        vBoxPlay.getChildren().add(buttonStart);

        // show list of maps
        ListView<String> listViewPlay = new ListView<>();
        listViewPlay.getItems().addAll(Arrays.stream(maps).map(File::getName).filter(s -> s.contains(".txt")).map(s -> s.replace(".txt","")).toList());
        listViewPlay.getSelectionModel().selectedItemProperty().addListener(appController::onMapSelection);
        vBoxPlay.getChildren().add(listViewPlay);

        // tab replay
        Tab tabReplay = new Tab();
        tabReplay.setText(Constant.UI.TAB_REPLAY);
        tabReplay.closableProperty().setValue(false);
        tabPane.getTabs().add(tabReplay);

        // tab replay content
        VBox vBoxReplay = new VBox();
        vBoxReplay.setPadding(new Insets(20.0));
        vBoxReplay.setSpacing(20.0);
        vBoxReplay.setAlignment(Pos.CENTER);
        tabReplay.setContent(vBoxReplay);

        // replay game button
        Button buttonReplay = new Button();
        buttonReplay.setText(Constant.UI.BUTTON_REPLAY);
        buttonReplay.setOnAction(appController::onReplayGameButtonClick);
        vBoxReplay.getChildren().add(buttonReplay);

        // show list of logs
        ListView<String> listViewReplay = new ListView<>();
        listViewReplay.getItems().addAll(Arrays.stream(logs).map(File::getName).filter(s -> s.contains(".log")).map(s -> s.replace(".log","")).toList());
        listViewReplay.getSelectionModel().selectedItemProperty().addListener(appController::onLogSelection);
        if (listViewReplay.getItems().isEmpty()) {
            tabReplay.setDisable(true);
        }
        vBoxReplay.getChildren().add(listViewReplay);

        // needs adjustable size in case of different map sizes
        Scene scene = new Scene(tabPane, 320, 240);
        tabPane.requestFocus();

        App.stage.setScene(scene);
        App.stage.setTitle(pom.getElementsByTagName("name").item(0).getTextContent());
        App.stage.show();

        if (game != null) {
            listViewPlay.getSelectionModel().select(selectedMap.getName().replace(".txt", ""));
            //listViewReplay.getSelectionModel().select(selectedLog.getName().replace(".log", ""));
        } else {
            listViewPlay.getSelectionModel().selectFirst();
            //listViewReplay.getSelectionModel().selectFirst();
        }
    }

    public static void startGame(File map) {
        game = new Game(map);
        game.start();
    }
}