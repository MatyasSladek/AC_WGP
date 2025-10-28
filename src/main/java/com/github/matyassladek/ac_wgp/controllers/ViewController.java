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

    /**
     * Shows the next screen and saves the game state.
     * This is the standard navigation method.
     */
    protected void showNextScreen() {
        showNextScreen(true);
    }

    /**
     * Shows the next screen with optional save.
     * Use skipSave=true only when the game was already saved (e.g., by advanceToNextSeason).
     *
     * @param saveBeforeNavigation If true, saves game before navigating
     */
    protected void showNextScreen(boolean saveBeforeNavigation) {
        try {
            if (saveBeforeNavigation) {
                log.fine("Saving game before navigation to: " + nextScreen);
                gameManager.saveGame(game);
            } else {
                log.fine("Skipping save (already saved), navigating to: " + nextScreen);
            }

            navigationManager.navigateTo(nextScreen, game);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed to show next screen: ", e);
        }
    }

    /**
     * Convenience method for explicitly saving without navigation.
     * Useful when you want to ensure state is persisted at a specific point.
     */
    protected void saveGame() {
        try {
            log.fine("Explicitly saving game state");
            gameManager.saveGame(game);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to save game: ", e);
        }
    }
}