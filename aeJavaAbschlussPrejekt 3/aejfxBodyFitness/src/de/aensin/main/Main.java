package de.aensin.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Startet die Applikation
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/main_layout.fxml"));
        primaryStage.setTitle("BodyFitness Fitness-Planer");
        primaryStage.setScene(new Scene(root, 850, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
