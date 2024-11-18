package com.github.matyassladek.ac_wgp.view;

import com.github.matyassladek.ac_wgp.controller.Game;
import com.github.matyassladek.ac_wgp.model.Championship;
import com.github.matyassladek.ac_wgp.model.Driver;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventResultsController {

    @FXML private ChoiceBox<String> choiceBoxPosition1;
    @FXML private ChoiceBox<String> choiceBoxPosition2;
    @FXML private ChoiceBox<String> choiceBoxPosition3;
    @FXML private ChoiceBox<String> choiceBoxPosition4;
    @FXML private ChoiceBox<String> choiceBoxPosition5;
    @FXML private ChoiceBox<String> choiceBoxPosition6;
    @FXML private ChoiceBox<String> choiceBoxPosition7;
    @FXML private ChoiceBox<String> choiceBoxPosition8;
    @FXML private ChoiceBox<String> choiceBoxPosition9;
    @FXML private ChoiceBox<String> choiceBoxPosition10;
    @FXML private ChoiceBox<String> choiceBoxPosition11;
    @FXML private ChoiceBox<String> choiceBoxPosition12;
    @FXML private ChoiceBox<String> choiceBoxPosition13;
    @FXML private ChoiceBox<String> choiceBoxPosition14;
    @FXML private ChoiceBox<String> choiceBoxPosition15;
    @FXML private ChoiceBox<String> choiceBoxPosition16;
    @FXML private ChoiceBox<String> choiceBoxPosition17;
    @FXML private ChoiceBox<String> choiceBoxPosition18;
    @FXML private ChoiceBox<String> choiceBoxPosition19;
    @FXML private ChoiceBox<String> choiceBoxPosition20;
    @FXML private ChoiceBox<String> choiceBoxPosition21;
    @FXML private ChoiceBox<String> choiceBoxPosition22;
    @FXML private ChoiceBox<String> choiceBoxPosition23;
    @FXML private ChoiceBox<String> choiceBoxPosition24;

    @FXML private Button submitButton;

    private List<ChoiceBox<String>> choiceBoxes;
    private Championship championship;

    private Game game;

    public void setGame(Game game) {
        this.game = game;
    }

    public void initialize() {
        // Initialize choiceBoxes list
        choiceBoxes = List.of(
                choiceBoxPosition1, choiceBoxPosition2, choiceBoxPosition3, choiceBoxPosition4,
                choiceBoxPosition5, choiceBoxPosition6, choiceBoxPosition7, choiceBoxPosition8,
                choiceBoxPosition9, choiceBoxPosition10, choiceBoxPosition11, choiceBoxPosition12,
                choiceBoxPosition13, choiceBoxPosition14, choiceBoxPosition15, choiceBoxPosition16,
                choiceBoxPosition17, choiceBoxPosition18, choiceBoxPosition19, choiceBoxPosition20,
                choiceBoxPosition21, choiceBoxPosition22, choiceBoxPosition23, choiceBoxPosition24
        );
    }

    // Set drivers for the race (based on existing drivers in the championship)
    public void setDrivers(List<Driver> drivers) {
        for (int i = 0; i < choiceBoxes.size(); i++) {
            ChoiceBox<String> choiceBox = choiceBoxes.get(i);
            choiceBox.getItems().clear();
            // Add driver names from the championship drivers
            for (Driver driver : drivers) {
                choiceBox.getItems().add(driver.getName());
            }
            // Set default value for each position (e.g., starting order of drivers in championship)
            if (i < drivers.size()) {
                choiceBox.setValue(drivers.get(i).getName());
            }
        }
    }

    @FXML
    private void onSubmitButtonClick() {
        System.out.println("Submit button clicked!");

        // Validate that no duplicate drivers are selected
        if (!validateDriverSelections()) {
            showAlert("Error", "Duplicate driver selected!", AlertType.ERROR);
            return;
        }

        // Provide feedback upon successful submission
        showAlert("Success", "Race results submitted successfully!", AlertType.INFORMATION);
    }

    // Validates that no driver is selected more than once
    private boolean validateDriverSelections() {
        Set<String> selectedDrivers = new HashSet<>();
        for (ChoiceBox<String> choiceBox : choiceBoxes) {
            String driver = choiceBox.getValue();
            if (driver != null && !selectedDrivers.add(driver)) {
                return false; // Duplicate found
            }
        }
        return true; // No duplicates
    }

    // Helper method to show alerts
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
