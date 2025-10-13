package com.github.matyassladek.ac_wgp.services.game;

import com.github.matyassladek.ac_wgp.MainApplication;
import com.github.matyassladek.ac_wgp.model.Game;
import com.github.matyassladek.ac_wgp.enums.FXMLFile;
import com.github.matyassladek.ac_wgp.controllers.ViewController;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Service responsible for scene navigation and transitions.
 * Handles loading FXML files, initializing controllers, and screen transitions.
 */
public class NavigationManager {

    private static final Logger log = Logger.getLogger(NavigationManager.class.getName());
    private static final int FADE_DURATION_MS = 500;

    private final Stage primaryStage;
    private final GameManager gameManager;

    public NavigationManager(Stage primaryStage, GameManager gameManager) {
        this.primaryStage = primaryStage;
        this.gameManager = gameManager;
    }

    /**
     * Initializes the application by loading either a saved game or creating a new one.
     *
     * @throws IOException if scene loading fails
     */
    public void init() throws IOException {
        if (gameManager.gameSaveExists()) {
            initSavedGame();
        } else {
            initCreateDriver();
        }
    }

    /**
     * Loads a saved game and navigates to the last saved screen.
     *
     * @throws IOException if loading fails
     */
    private void initSavedGame() throws IOException {
        log.info("Game save found. Loading saved game.");
        Game game = gameManager.loadGame();
        log.info("Loading screen: " + game.getFxmlScreen());
        loadScene(game.getFxmlScreen(), game);
    }

    /**
     * Initializes the driver creation screen for a new game.
     *
     * @throws IOException if scene loading fails
     */
    private void initCreateDriver() throws IOException {
        log.info("Game save not found. Starting a new game.");
        FXMLLoader loader = createFXMLLoader(FXMLFile.CREATE_DRIVER.getFileName());
        Parent root = loader.load();

        ViewController controller = loader.getController();
        initializeController(controller, null);

        showScene(root);
    }

    /**
     * Loads a scene without animation (used for initial loading).
     *
     * @param fxmlFile The FXML file name
     * @param game The game instance (can be null for initial creation)
     * @throws IOException if loading fails
     */
    public void loadScene(String fxmlFile, Game game) throws IOException {
        FXMLLoader loader = createFXMLLoader(fxmlFile);
        Parent root = loader.load();

        ViewController controller = loader.getController();
        initializeController(controller, game);

        showScene(root);
    }

    /**
     * Navigates to a new scene with fade animation.
     *
     * @param fxmlFile The FXML file name
     * @param game The game instance
     * @throws IOException if loading fails
     */
    public void navigateTo(String fxmlFile, Game game) throws IOException {
        FXMLLoader loader = createFXMLLoader(fxmlFile);
        Parent newRoot = loader.load();

        ViewController controller = loader.getController();
        initializeController(controller, game);

        animateTransition(newRoot);
    }

    /**
     * Creates an FXML loader for the specified file.
     *
     * @param fxmlFile The FXML file name
     * @return Configured FXMLLoader
     */
    private FXMLLoader createFXMLLoader(String fxmlFile) {
        return new FXMLLoader(MainApplication.class.getResource(fxmlFile));
    }

    /**
     * Initializes a view controller with dependencies.
     *
     * @param controller The controller to initialize
     * @param game The game instance (can be null)
     */
    private void initializeController(ViewController controller, Game game) {
        controller.setNavigationManager(this);
        controller.setGameManager(gameManager);

        if (game != null) {
            controller.setGame(game);
        }
    }

    /**
     * Shows a scene without animation.
     *
     * @param root The root node of the scene
     */
    private void showScene(Parent root) {
        Scene scene = new Scene(root);
        scene.setFill(javafx.scene.paint.Color.web("#121212"));

        primaryStage.setTitle("Assetto Corsa: World Grand Prix Championship Career");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Animates the transition to a new scene with fade effect.
     *
     * @param newRoot The new root node
     */
    private void animateTransition(Parent newRoot) {
        Scene currentScene = primaryStage.getScene();
        Parent currentRoot = currentScene.getRoot();

        FadeTransition fadeOut = createFadeTransition(currentRoot, 1.0, 0.0);
        fadeOut.setOnFinished(event -> fadeInNewScene(currentScene, newRoot));

        fadeOut.play();
    }

    /**
     * Fades in the new scene after the old one has faded out.
     *
     * @param currentScene The current scene
     * @param newRoot The new root node
     */
    private void fadeInNewScene(Scene currentScene, Parent newRoot) {
        newRoot.setOpacity(0.0);
        currentScene.setFill(javafx.scene.paint.Color.web("#121212"));
        currentScene.setRoot(newRoot);

        FadeTransition fadeIn = createFadeTransition(newRoot, 0.0, 1.0);
        fadeIn.play();
    }

    /**
     * Creates a fade transition with the specified parameters.
     *
     * @param node The node to animate
     * @param fromValue Starting opacity
     * @param toValue Ending opacity
     * @return Configured FadeTransition
     */
    private FadeTransition createFadeTransition(Parent node, double fromValue, double toValue) {
        FadeTransition transition = new FadeTransition(Duration.millis(FADE_DURATION_MS), node);
        transition.setFromValue(fromValue);
        transition.setToValue(toValue);
        return transition;
    }
}