package com.github.matyassladek.ac_wgp.view;

import com.github.matyassladek.ac_wgp.model.Driver;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Button;

import java.util.List;

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

    public void initialize() {
        choiceBoxes = List.of(
                choiceBoxPosition1, choiceBoxPosition2, choiceBoxPosition3, choiceBoxPosition4,
                choiceBoxPosition5, choiceBoxPosition6, choiceBoxPosition7, choiceBoxPosition8,
                choiceBoxPosition9, choiceBoxPosition10, choiceBoxPosition11, choiceBoxPosition12,
                choiceBoxPosition13, choiceBoxPosition14, choiceBoxPosition15, choiceBoxPosition16,
                choiceBoxPosition17, choiceBoxPosition18, choiceBoxPosition19, choiceBoxPosition20,
                choiceBoxPosition21, choiceBoxPosition22, choiceBoxPosition23, choiceBoxPosition24
        );
    }

    /**
     * Set the list of drivers into the choice boxes.
     * @param drivers The list of available drivers
     */
    public void setDrivers(List<Driver> drivers) {
        // Clear existing items in the choice boxes
        for (ChoiceBox<String> choiceBox : choiceBoxes) {
            choiceBox.getItems().clear();
        }

        // Add the list of drivers to each choice box
        for (ChoiceBox<String> choiceBox : choiceBoxes) {
            for (Driver driver : drivers) {
                choiceBox.getItems().add(driver.getName());
            }
        }
    }

    /**
     * Handle the submit button click event.
     * Logic for capturing selected drivers or any other actions goes here.
     */
    @FXML
    private void onSubmitButtonClick() {
        System.out.println("Submit button clicked!");

        // Example: Capture and print the selected drivers for each position
        for (int i = 0; i < choiceBoxes.size(); i++) {
            String selectedDriver = choiceBoxes.get(i).getValue();
            System.out.println("Position " + (i + 1) + ": " + selectedDriver);
        }

        // Additional logic for processing race results can be added here
    }
}
