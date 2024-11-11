package com.github.matyassladek.ac_wgp;

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

    // This method is called when the "RACE" button is clicked
    @FXML
    private void onSubmitButtonClick() {
        System.out.println("Race button clicked!");
        // You can add additional logic here to handle the "RACE" action
    }
}
