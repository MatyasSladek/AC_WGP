package com.github.matyassladek.ac_wgp;

import com.github.matyassladek.ac_wgp.controller.Game;
import com.github.matyassladek.ac_wgp.view.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(FXMLFile.CREATE_DRIVER.getFileName()));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        stage.setTitle("Assetto Corsa: World Grand Prix Championship Career");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public static void showNext(String fxmlFile, Game game) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
        Parent root = loader.load();
        ViewController controller = loader.getController();
        controller.setGame(game);
        primaryStage.setScene(new Scene(root, primaryStage.getWidth(), primaryStage.getHeight()));
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}