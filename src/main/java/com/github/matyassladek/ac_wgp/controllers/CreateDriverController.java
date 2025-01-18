package com.github.matyassladek.ac_wgp.controllers;

import com.github.matyassladek.ac_wgp.enums.FXMLFile;
import com.github.matyassladek.ac_wgp.model.Game;
import com.github.matyassladek.ac_wgp.enums.Country;
import com.github.matyassladek.ac_wgp.enums.Manufacture;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateDriverController extends ViewController {

    private static final Logger log = Logger.getLogger(CreateDriverController.class.getName());

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private ChoiceBox<String> countryChoiceBox;

    @FXML
    private ChoiceBox<String> teamChoiceBox;

    public CreateDriverController() {
        super(FXMLFile.NEXT_EVENT.getFileName());
    }

    @FXML
    public void initialize() {
        List<String> countryNames = Arrays.stream(Country.values())
                .map(Country::getName)
                .toList();

        countryChoiceBox.setItems(FXCollections.observableArrayList(countryNames));
        countryChoiceBox.setValue("");

        List<String> teamNames = Arrays.stream(Manufacture.values())
                .map(Manufacture::getNameShort)
                .toList();

        teamChoiceBox.setItems(FXCollections.observableArrayList(teamNames));
        teamChoiceBox.setValue("");
    }

    @FXML
    private void onSubmitButtonClick() throws IOException {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String countryName = countryChoiceBox.getValue();
        String teamName = teamChoiceBox.getValue();

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

        game = new Game(firstName, lastName, country.get(), team.get());

        showNextScreen();

        if (log.isLoggable(Level.INFO)) {
            log.info(game.getPlayer().toString());
        }
    }
}
