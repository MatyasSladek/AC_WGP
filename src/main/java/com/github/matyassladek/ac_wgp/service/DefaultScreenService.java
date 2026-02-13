package com.github.matyassladek.ac_wgp.service;

import com.github.matyassladek.ac_wgp.exception.NotImplemented;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public class DefaultScreenService implements ScreenService {

    private final Stage stage;

    @Override
    public void showSplashscreen() throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ui/Splashscreen.fxml")));
        Scene scene = new Scene(root);
        stage.setTitle("Assetto Corsa: World Grand Prix Championship Career");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void showMainMenu() {
        throw new NotImplemented();
    }

    @Override
    public void showSettings() {
        throw new NotImplemented();
    }

    @Override
    public void showNewGame() {
        throw new NotImplemented();
    }

    @Override
    public void showSavedGames() {
        throw new NotImplemented();
    }

    @Override
    public void showSelectTeam() {
        throw new NotImplemented();
    }

    @Override
    public void showSignContract() {
        throw new NotImplemented();
    }

    @Override
    public void showCreateCalendar() {
        throw new NotImplemented();
    }

    @Override
    public void showSeasonSettings() {
        throw new NotImplemented();
    }

    @Override
    public void showSeasonPreview() {
        throw new NotImplemented();
    }

    @Override
    public void showSeasonOverview() {
        throw new NotImplemented();
    }

    @Override
    public void showStats() {
        throw new NotImplemented();
    }

    @Override
    public void showTrophies() {
        throw new NotImplemented();
    }

    @Override
    public void showPerformance() {
        throw new NotImplemented();
    }

    @Override
    public void showDevelopmentStrategy() {
        throw new NotImplemented();
    }

    @Override
    public void showCareerEnd() {
        throw new NotImplemented();
    }
}
