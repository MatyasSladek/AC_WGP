package com.github.matyassladek.ac_wgp;

import com.github.matyassladek.ac_wgp.management.NavigationManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        NavigationManager navigationManager;
        navigationManager = new NavigationManager(stage);
        navigationManager.init();
    }

    public static void main(String[] args) {
        launch();
    }
}