package com.github.matyassladek.ac_wgp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {

    private static Stage primaryStage;

    private final String[] screenName = new String[]{
            "create-driver.fxml",           //0
            "next-event.fxml",              //1
            "event-results.fxml",           //2
            "drivers-standings.fxml",       //3
            "constructors-standings.fxml"   //4
    };

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/" + screenName[0]));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        stage.setTitle("Assetto Corsa: World Grand Prix Championship Career");
        stage.setScene(scene);
        stage.show();
    }

    public static void showWindow(String fxmlFile) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource(fxmlFile)));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}