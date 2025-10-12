package com.github.matyassladek.ac_wgp.controllers;

import com.github.matyassladek.ac_wgp.enums.FXMLFile;
import com.github.matyassladek.ac_wgp.model.Game;
import com.github.matyassladek.ac_wgp.enums.TrackEnum;
import com.github.matyassladek.ac_wgp.model.Track;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.List;

public class NextEventController extends ViewController {

    @FXML
    private Label circuitLabel;

    @FXML
    private Label lapsLabel;

    public NextEventController() {
        super(FXMLFile.EVENT_RESULTS.getFileName());
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
        loadNextEvent();
    }

    private void loadNextEvent() {
        if (game == null) return;

        int currentRound = game.getCurrentChampionship().getCurrentRound();
        List<Track> calendar = game.getCurrentChampionship().getCalendar();

        Track nextTrack = calendar.get(currentRound);
        circuitLabel.setText(nextTrack.getName());
        lapsLabel.setText("Laps: " + nextTrack.getLaps());
    }

    @FXML
    private void onSubmitButtonClick() throws IOException {
        showNextScreen();
    }
}
