package com.github.matyassladek.ac_wgp.view;

import com.github.matyassladek.ac_wgp.controller.Game;
import com.github.matyassladek.ac_wgp.model.Track;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class NextEventController {

    // FXML references
    @FXML
    private Label circuitLabel;

    @FXML
    private Label lapsLabel;

    @FXML
    private Button raceButton;

    private Game game;

    // Method to set the Game instance
    public void setGame(Game game) {
        this.game = game;
        loadNextEvent();
    }

    // Load the details of the next event
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

    // Called when the "RACE" button is clicked
    @FXML
    private void onSubmitButtonClick() {
        System.out.println("Race button clicked!");
        // Placeholder: Trigger the race start or transition to race view
        // You can add additional logic here to handle the "RACE" action
    }
}
