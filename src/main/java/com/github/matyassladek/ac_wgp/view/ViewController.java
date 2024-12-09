package com.github.matyassladek.ac_wgp.view;

import com.github.matyassladek.ac_wgp.controller.Game;
import com.github.matyassladek.ac_wgp.management.GameManager;
import com.github.matyassladek.ac_wgp.management.NavigationManager;
import lombok.Setter;

import java.io.IOException;

@Setter
public abstract class ViewController {

    protected final String currentScreen;
    protected final String nextScreen;
    protected Game game;
    protected GameManager gameManager;
    protected NavigationManager navigationManager;

    protected ViewController(String currentScreen, String nextScreen) {
        this.currentScreen = currentScreen;
        this.nextScreen = nextScreen;
    }

    protected void showNextScreen() throws IOException {
        try {
            game.setFxmlScreen(currentScreen);
            gameManager.saveGame(game);
            navigationManager.navigateTo(nextScreen, game, gameManager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
