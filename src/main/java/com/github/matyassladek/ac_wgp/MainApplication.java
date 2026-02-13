package com.github.matyassladek.ac_wgp;

import com.github.matyassladek.ac_wgp.app.Session;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Session session = new Session(stage);
        session.start();
    }

    public static void main(String[] args) {
        launch();
    }
}