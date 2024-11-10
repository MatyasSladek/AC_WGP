package com.github.matyassladek.ac_wgp;

import com.github.matyassladek.ac_wgp.model.Country;
import com.github.matyassladek.ac_wgp.model.Manufacture;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Edite code from chatgpt.com
 */
public class CreateDriverController {


    private static final Logger log = Logger.getLogger(CreateDriverController.class.getName());

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
        // Get country names from Country enum
        List<String> countryNames = Arrays.stream(Country.values())
                .map(Country::getName)
                .collect(Collectors.toList());

        // Populate countryChoiceBox with the list of country names
        countryChoiceBox.setItems(FXCollections.observableArrayList(countryNames));

        // Optional: Set default selection for the ChoiceBox
        countryChoiceBox.setValue(null);

        List<String> teamNames = Arrays.stream(Manufacture.values())
                .map(Manufacture::getNameShort)
                .collect(Collectors.toList());

        teamChoiceBox.setItems(FXCollections.observableArrayList(teamNames));

        teamChoiceBox.setValue(null);
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
            log.severe("Please fill out all required fields.");
            return;
        }

        // Process the form submission
        log.info(String.format("Driver Created: %s %s from %s, Team: %s%n", firstName, lastName, country, team));

        // Reset the form (optional)
        firstNameField.clear();
        lastNameField.clear();
        countryChoiceBox.setValue(null);  // Reset to default
        teamChoiceBox.setValue(null);      // Clear team selection
    }
}
