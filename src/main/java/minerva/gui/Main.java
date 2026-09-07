package minerva.gui;

import java.io.IOException;

import java.io.File;

import minerva.Minerva;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/** A GUI for Minerva using FXML. */
public class Main extends Application {
    private Minerva minerva = new Minerva("." + File.separator + "data"
            + File.separator + "minerva.txt");

    @Override
    public void start(Stage stage) {
        try {
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setMinerva(minerva);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
