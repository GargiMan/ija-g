package ija.pacman;

import ija.pacman.controls.Controller;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class App extends Application {

    static Document pom;
    private static Game game;
    public static Stage stage;
    public static File[] maps;

    public static Game getGame() {
        return game;
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

        Button button = new Button();
        vBox.getChildren().add(button);
        Controller controller = new Controller();
        button.setOnAction(controller::onStartGameButtonClick);
        button.setText("Start game");

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