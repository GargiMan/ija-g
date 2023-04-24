package ija.pacman;

import ija.pacman.controls.Controller;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class App extends Application {

    static Model model;
    private static Game game;
    public static Stage stage;
    public static File[] maps;

    public static Game getGame() {
        return game;
    }

    @Override
    public void start(Stage stage) throws IOException, XmlPullParserException {
        App.stage = stage;

        // Create a MavenXpp3Reader object to read the pom.xml file
        MavenXpp3Reader reader = new MavenXpp3Reader();
        App.model = reader.read(new FileReader("pom.xml"));


        showMenu();
    }

    public static void main(String[] args) {
        launch();
    }
    public static void showMenu() {
        // load maps
        String str = System.getProperty("user.dir") + File.separator + "data" + File.separator + "maps";
        maps = new File(str).listFiles();

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
        App.stage.setTitle(model.getName());
        App.stage.show();
    }

    public static void startGame(File map) {
        game = new Game(map);
        game.start();
    }
}