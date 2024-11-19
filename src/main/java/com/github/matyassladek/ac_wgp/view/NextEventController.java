package com.github.matyassladek.ac_wgp.view;

import com.github.matyassladek.ac_wgp.controller.Game;
import com.github.matyassladek.ac_wgp.model.Track;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class NextEventController extends ViewController {

    @FXML
    private Label circuitLabel;

    @FXML
    private Label lapsLabel;

    @FXML
    private Button raceButton;

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
    private void onSubmitButtonClick() throws IOException {
        showNextScreen();
    }
}
