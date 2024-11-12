package com.github.matyassladek.ac_wgp.view;

import com.github.matyassladek.ac_wgp.HelloApplication;
import com.github.matyassladek.ac_wgp.controller.Game;
import com.github.matyassladek.ac_wgp.model.Country;
import com.github.matyassladek.ac_wgp.model.Manufacture;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
        countryChoiceBox.setValue("");

        List<String> teamNames = Arrays.stream(Manufacture.values())
                .map(Manufacture::getNameShort)
                .collect(Collectors.toList());

        teamChoiceBox.setItems(FXCollections.observableArrayList(teamNames));

        teamChoiceBox.setValue("");
    }

    @FXML
    private void onSubmitButtonClick() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String countryName = countryChoiceBox.getValue();
        String teamName = teamChoiceBox.getValue(); // This will be null if teamChoiceBox is empty

        Optional<Country> country = Arrays.stream(Country.values())
                .filter(c -> c.getName().equals(countryName))
                .findFirst();

        Optional<Manufacture> team = Arrays.stream(Manufacture.values())
                .filter(t -> t.getNameShort().equals(teamName))
                .findFirst();

        if (firstName.isEmpty() || lastName.isEmpty() || countryName.isEmpty() || teamName.isEmpty()) {
            log.severe("Please fill out all required fields.");
            return;
        }

        if(country.isEmpty()) {
            log.severe("Country not found.");
            return;
        }

        if(team.isEmpty()) {
            log.severe("Team not found.");
            return;
        }

        Game game = new Game(firstName, lastName, country.get(), team.get());

        try {
            HelloApplication.showWindow("view/hello-view.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info(game.getPlayer().toString());
    }
}
