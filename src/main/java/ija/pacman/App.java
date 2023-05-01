package ija.pacman;

import ija.pacman.controls.Controller;
import ija.pacman.others.Constant;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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

    static Document pom;
    private static Game game;
    private static Stage stage;
    public static File[] maps;

    private static File map;

    public static Game getGame() {
        return game;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setMap(String mapFilename) {
        map = Arrays.stream(maps).filter(file -> file.getName().equals(mapFilename+".txt")).findFirst().orElseThrow();
    }

    public static File getMap() {
        return map;
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
        // load maps
        String filePath = System.getProperty("user.dir") + File.separator + "data" + File.separator + "maps";
        maps = new File(filePath).listFiles();

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(20.0));
        vBox.setSpacing(20.0);
        vBox.setAlignment(Pos.CENTER);

        // start game button
        Button button = new Button();
        vBox.getChildren().add(button);
        Controller controller = new Controller();
        button.setOnAction(controller::onStartGameButtonClick);
        button.setText(Constant.UI.BUTTON_START);

        // show list of maps
        ListView<String> listView = new ListView<>();
        vBox.getChildren().add(listView);
        listView.setId("mapList");
        listView.getItems().addAll(Arrays.stream(maps).map(File::getName).filter(s -> s.contains(".txt")).map(s -> s.replace(".txt","")).toList());
        if (game != null) {
            listView.getSelectionModel().select(map.getName()+".txt");
        } else {
            listView.getSelectionModel().selectFirst();
        }
        listView.getSelectionModel().selectedItemProperty().addListener(controller::onMapSelection);

        // needs adjustable size in case of different map sizes
        Scene scene = new Scene(vBox, 320, 240);
        vBox.requestFocus();

        App.stage.setScene(scene);
        App.stage.setTitle(pom.getElementsByTagName("name").item(0).getTextContent());
        App.stage.show();
    }

    public static void startGame(File map) {
        game = new Game(map);
        game.start();
    }
}