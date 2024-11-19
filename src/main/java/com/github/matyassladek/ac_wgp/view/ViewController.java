package com.github.matyassladek.ac_wgp.view;

import com.github.matyassladek.ac_wgp.HelloApplication;
import com.github.matyassladek.ac_wgp.controller.Game;
import lombok.Setter;

import java.io.IOException;

@Setter
public abstract class ViewController {

    protected Game game;
    protected final String nextScreen;


    protected ViewController(String nextScreen) {
        this.nextScreen = nextScreen;
    }

    protected void showNextScreen() throws IOException {
        try {
            HelloApplication.showNext(nextScreen, game);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
