package com.github.matyassladek.ac_wgp.view;

import com.github.matyassladek.ac_wgp.MainApplication;
import com.github.matyassladek.ac_wgp.controller.Game;
import lombok.Setter;

import java.io.IOException;

@Setter
public abstract class ViewController {

    protected final String nextScreen;
    protected Game game;
    protected MainApplication mainApplication;

    protected ViewController(String nextScreen) {
        this.nextScreen = nextScreen;
    }

    protected void showNextScreen() throws IOException {
        try {
            mainApplication.showNext(nextScreen, game);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
