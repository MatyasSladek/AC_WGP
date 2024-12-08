package com.github.matyassladek.ac_wgp.view;

import com.github.matyassladek.ac_wgp.controller.Game;
import com.github.matyassladek.ac_wgp.model.Track;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

        if (currentRound < calendar.size()) {
            Track nextTrack = calendar.get(currentRound);
            circuitLabel.setText(nextTrack.getName());
            lapsLabel.setText("Laps: " + nextTrack.getLaps());
        } else {
            if (game.getCurrentSeason() < game.getAllSeasons().size() - 1) {
                game.setCurrentSeason(game.getCurrentSeason() + 1);
            } else {
                circuitLabel.setText("Retirement");
                lapsLabel.setText("You have finished all seasons. " +
                        "Time to become TV commentator, youtuber or manager of some young talent!");
                //TODO: add logic for new season
                // nextScreen = FXMLFile.RETIRE
            }
        }
    }

    @FXML
    private void onSubmitButtonClick() throws IOException {
        gameManager.saveGame(game);
        showNextScreen();
    }
}
