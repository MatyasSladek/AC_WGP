package com.github.matyassladek.ac_wgp.controllers;

import com.github.matyassladek.ac_wgp.model.Game;
import com.github.matyassladek.ac_wgp.services.game.GameManager;
import com.github.matyassladek.ac_wgp.services.game.NavigationManager;
import lombok.Setter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Setter
public abstract class ViewController {

    private static final Logger log = Logger.getLogger(ViewController.class.getName());
    protected String nextScreen;
    protected Game game;
    protected GameManager gameManager;
    protected NavigationManager navigationManager;

    protected ViewController(String nextScreen) {
        this.nextScreen = nextScreen;
    }

    protected void showNextScreen() {
        try {
            gameManager.saveGame(game);
            navigationManager.navigateTo(nextScreen, game);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed to show next screen: ", e);
        }
    }
}
