package com.github.matyassladek.ac_wgp.view;

import com.github.matyassladek.ac_wgp.HelloApplication;
import com.github.matyassladek.ac_wgp.controller.Game;
import com.github.matyassladek.ac_wgp.model.Driver;
import com.github.matyassladek.ac_wgp.model.Track;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class NextEventController {

    @FXML
    private Label circuitLabel;

    @FXML
    private Label lapsLabel;

    @FXML
    private Button raceButton;

    private Game game;

    public void setGame(Game game) {
        this.game = game;
        loadNextEvent();
    }

    private void loadNextEvent() {
        if (game == null || game.getCurrentChampionship() == null) return;

        int currentRound = game.getCurrentChampionship().getCurrentRound();
        Track[] calendar = game.getCurrentChampionship().getCalendar();

        if (currentRound < calendar.length) {
            Track nextTrack = calendar[currentRound];
            circuitLabel.setText(nextTrack.getName());
            lapsLabel.setText("Laps: " + nextTrack.getLaps());
        } else {
            circuitLabel.setText("Season Complete");
            lapsLabel.setText("");
            raceButton.setDisable(true);
        }
    }

    @FXML
    private void onSubmitButtonClick() {
        try {
            HelloApplication.showEventResults(game);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
