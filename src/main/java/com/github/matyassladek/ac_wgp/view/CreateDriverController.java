package com.github.matyassladek.ac_wgp.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.matyassladek.ac_wgp.HelloApplication;
import com.github.matyassladek.ac_wgp.controller.Game;
import com.github.matyassladek.ac_wgp.model.Country;
import com.github.matyassladek.ac_wgp.model.Manufacture;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private static final String SAVE_DIRECTORY = "resources/save/";
    private final ObjectMapper objectMapper = new ObjectMapper();

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
        countryChoiceBox.setValue(String.valueOf(""));

        List<String> teamNames = Arrays.stream(Manufacture.values())
                .map(Manufacture::getNameShort)
                .collect(Collectors.toList());

        teamChoiceBox.setItems(FXCollections.observableArrayList(teamNames));

        teamChoiceBox.setValue(String.valueOf(""));
    }

    @FXML
    private void onSubmitButtonClick() {
        // Retrieve values from form fields
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String countryName = countryChoiceBox.getValue();
        String teamName = teamChoiceBox.getValue(); // This will be null if teamChoiceBox is empty

        // Convert countryName and teamName back to their respective enums
        Optional<Country> country = Arrays.stream(Country.values())
                .filter(c -> c.getName().equals(countryName))
                .findFirst();

        Optional<Manufacture> team = Arrays.stream(Manufacture.values())
                .filter(t -> t.getNameShort().equals(teamName))
                .findFirst();

        // Validate form inputs
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

        // Process the form submission
        log.info(String.format("Driver Created: %s %s from %s, Team: %s%n", firstName, lastName, country.get(), team));

        Game game = new Game(firstName, lastName, country.get(), team.get());

//        saveGameToJson(game);

        try {
            HelloApplication.showWindow("view/hello-view.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info(game.getPlayer().toString());

//        // Reset the form (optional)
//        firstNameField.clear();
//        lastNameField.clear();
//        countryChoiceBox.setValue(null);  // Reset to default
//        teamChoiceBox.setValue(null);      // Clear team selection
    }

//    private void saveGameToJson(Game game) {
//        Path saveDirPath = Paths.get(SAVE_DIRECTORY);
//        try {
//            // Ensure save directory exists
//            if (!Files.exists(saveDirPath)) {
//                Files.createDirectories(saveDirPath);
//            }
//
//            // Create the save file path
//            String saveFileName = "game_save.json";
//            File saveFile = new File(SAVE_DIRECTORY + saveFileName);
//
//            // Serialize Game object to JSON and save
//            objectMapper.writeValue(saveFile, game);
//            log.info("Game saved to " + saveFile.getAbsolutePath());
//        } catch (IOException e) {
//            log.severe("Failed to save game: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
}
