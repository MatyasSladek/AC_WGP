package com.github.matyassladek.ac_wgp;

import com.github.matyassladek.ac_wgp.controller.Game;
import com.github.matyassladek.ac_wgp.view.*;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MainApplication extends Application {

    private static Stage primaryStage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(FXMLFile.CREATE_DRIVER.getFileName()));
        Parent root = fxmlLoader.load();
        ViewController controller = fxmlLoader.getController();
        controller.setMainApplication(this);
        Scene scene = new Scene(root, 1200, 800);
        stage.setTitle("Assetto Corsa: World Grand Prix Championship Career");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setResizable(false);
        stage.show();
    }

    public void showNext(String fxmlFile, Game game) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlFile));
        Parent newRoot = loader.load();
        ViewController controller = loader.getController();
        controller.setMainApplication(this);
        controller.setGame(game);

        Scene currentScene = primaryStage.getScene();
        Parent currentRoot = currentScene.getRoot();

        // Fade out the current root
        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), currentRoot);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(event -> {
            // Prepare the new root (hidden initially)
            newRoot.setOpacity(0.0);
            currentScene.setRoot(newRoot);

            // Fade in the new root
            FadeTransition fadeIn = new FadeTransition(Duration.millis(500), newRoot);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });

        fadeOut.play();
    }
}