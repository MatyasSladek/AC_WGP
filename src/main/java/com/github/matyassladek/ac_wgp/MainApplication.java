package com.github.matyassladek.ac_wgp;

import com.github.matyassladek.ac_wgp.services.game.GameManager;
import com.github.matyassladek.ac_wgp.services.game.NavigationManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        GameManager gameManager = new GameManager();
        new NavigationManager(stage, gameManager).init();
    }

    public static void main(String[] args) {
        launch();
    }
}