package com.github.matyassladek.ac_wgp.management;

import com.github.matyassladek.ac_wgp.MainApplication;
import com.github.matyassladek.ac_wgp.controller.Game;
import com.github.matyassladek.ac_wgp.view.FXMLFile;
import com.github.matyassladek.ac_wgp.view.ViewController;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.logging.Logger;

public class NavigationManager {

    private static final Logger log = Logger.getLogger(NavigationManager.class.getName());
    private final Stage primaryStage;
    private final GameManager gameManager;

    public NavigationManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.gameManager = new GameManager();
    }

    public void init() throws IOException {
        if (!gameManager.gameSaveExist()) {
            initCreateDriver();
            log.info("Game save not found. Starting a new game.");
        } else {
            initSavedGame();
            log.info("Game save found. Loading saved game.");
        }
    }

    private void initSavedGame() throws IOException {
        Game game = gameManager.loadGame();
        log.info(game::getFxmlScreen);
        loadScene(game.getFxmlScreen(), game, gameManager);
    }

    public void loadScene(String fxmlFile, Game game, GameManager gameManager) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlFile));
        Parent newRoot = loader.load();
        ViewController controller = loader.getController();
        controller.setNavigationManager(this);
        controller.setGame(game);
        controller.setGameManager(gameManager);
        sceneInit(newRoot);
    }

    public void navigateTo(String fxmlFile, Game game, GameManager gameManager) throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlFile));
        Parent newRoot = loader.load();
        ViewController controller = loader.getController();
        controller.setNavigationManager(this);
        controller.setGame(game);
        controller.setGameManager(gameManager);

        animate(newRoot);
    }

    private void initCreateDriver() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(FXMLFile.CREATE_DRIVER.getFileName()));
        Parent root = fxmlLoader.load();
        ViewController controller = fxmlLoader.getController();
        controller.setNavigationManager(this);
        controller.setGameManager(new GameManager());

        sceneInit(root);
    }

    private void sceneInit(Parent root) {
        Scene scene = new Scene(root);
        primaryStage.setTitle("Assetto Corsa: World Grand Prix Championship Career");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void animate(Parent newRoot) {
        Scene currentScene = primaryStage.getScene();
        Parent currentRoot = currentScene.getRoot();

        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), currentRoot);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> {
            newRoot.setOpacity(0.0);
            currentScene.setRoot(newRoot);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(500), newRoot);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });

        fadeOut.play();
    }
}
