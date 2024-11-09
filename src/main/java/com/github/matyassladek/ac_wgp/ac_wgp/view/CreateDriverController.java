package com.github.matyassladek.ac_wgp.ac_wgp.view;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class CreateDriverController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private ChoiceBox<String> countryChoiceBox;

    @FXML
    private ChoiceBox<String> teamChoiceBox;

    @FXML
    private Button submitButton;

    @FXML
    public void initialize() {
        // Populate countryChoiceBox with a list of countries
        countryChoiceBox.setItems(FXCollections.observableArrayList(
                "USA", "Canada", "UK", "Germany", "France", "Japan", "Australia", "Italy"
                // Add more countries as needed
        ));

        // Optional: Set default selection for the ChoiceBox
        countryChoiceBox.setValue("USA");
    }

    @FXML
    private void onSubmitButtonClick() {
        // Retrieve values from form fields
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String country = countryChoiceBox.getValue();
        String team = teamChoiceBox.getValue(); // This will be null if teamChoiceBox is empty

        // Process or validate the form inputs
        if (firstName.isEmpty() || lastName.isEmpty() || country == null) {
            System.out.println("Please fill out all required fields.");
            return;
        }

        // Process the form submission
        System.out.printf("Driver Created: %s %s from %s, Team: %s%n", firstName, lastName, country, team);

        // Reset the form (optional)
        firstNameField.clear();
        lastNameField.clear();
        countryChoiceBox.setValue("USA");  // Reset to default
        teamChoiceBox.setValue(null);      // Clear team selection
    }
}
