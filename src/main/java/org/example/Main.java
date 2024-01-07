// Code submission for Cloud Computing
// Leonard-Andrei Dascalete - 210025374 - Leonard.Dascalete@city.ac.uk
// Roshaunn Taylor - 210041247 - Roshaunn.Taylor@city.ac.uk

// Parts of this code have used sample code from multiple sources
// Including: StackOverflow, Google Tutorials, ChatGPT and IntelliJ Ultimate suggestions



package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.apache.log4j.BasicConfigurator;

import java.io.IOException;

/**
 * Main class to launch the JavaFX application.
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application, loading the initial FXML file and setting up the primary stage.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1280, 720);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method to launch the application.
     */
    public static void main(String[] args) {
        // Configures the basic settings for Log4j
        BasicConfigurator.configure();

        // Launches the JavaFX application
        launch(args);
    }
}
