package com.github.matyassladek.ac_wgp.controllers;

import com.github.matyassladek.ac_wgp.enums.FXMLFile;
import com.github.matyassladek.ac_wgp.model.Game;
import com.github.matyassladek.ac_wgp.enums.Country;
import com.github.matyassladek.ac_wgp.enums.Manufacture;
import com.github.matyassladek.ac_wgp.helpers.UIHelper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @FXML
    private TextField jsonFilePathField;

    @FXML
    private Button browseJsonButton;

    @FXML
    private TextField acGamePathField;

    @FXML
    private Button browseAcPathButton;

    private String selectedJsonPath;
    private String selectedAcGamePath;

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

        // Initialize path fields
        jsonFilePathField.setEditable(false);
        acGamePathField.setEditable(false);
    }

    @FXML
    private void onBrowseJsonButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select JSON Results File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json")
        );

        // Set initial directory if possible
        String userHome = System.getProperty("user.home");
        File initialDir = new File(userHome);
        if (initialDir.exists()) {
            fileChooser.setInitialDirectory(initialDir);
        }

        File selectedFile = fileChooser.showOpenDialog(browseJsonButton.getScene().getWindow());

        if (selectedFile != null) {
            selectedJsonPath = selectedFile.getAbsolutePath();
            jsonFilePathField.setText(selectedJsonPath);
            log.info("Selected JSON file: " + selectedJsonPath);
        }
    }

    @FXML
    private void onBrowseAcPathButtonClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Assetto Corsa Game Directory");

        // Try to set a reasonable initial directory
        String programFiles = System.getProperty("os.name").toLowerCase().contains("windows")
                ? "C:\\Program Files (x86)\\Steam\\steamapps\\common\\assettocorsa"
                : System.getProperty("user.home");

        File initialDir = new File(programFiles);
        if (!initialDir.exists()) {
            initialDir = new File(System.getProperty("user.home"));
        }

        if (initialDir.exists()) {
            directoryChooser.setInitialDirectory(initialDir);
        }

        File selectedDirectory = directoryChooser.showDialog(browseAcPathButton.getScene().getWindow());

        if (selectedDirectory != null) {
            // Validate that this looks like an Assetto Corsa installation
            if (validateAcInstallation(selectedDirectory)) {
                selectedAcGamePath = selectedDirectory.getAbsolutePath();
                acGamePathField.setText(selectedAcGamePath);
                log.info("Selected AC game path: " + selectedAcGamePath);

                // Load available tracks
                loadAvailableTracks(selectedDirectory);
            } else {
                UIHelper.showAlert("Invalid Directory",
                        "The selected directory doesn't appear to be a valid Assetto Corsa installation.\n" +
                                "Please select the main Assetto Corsa game directory.",
                        browseAcPathButton.getScene().getWindow());
            }
        }
    }

    private boolean validateAcInstallation(File directory) {
        // Check for key Assetto Corsa files/folders
        File contentFolder = new File(directory, "content");
        File carsFolder = new File(contentFolder, "cars");
        File tracksFolder = new File(contentFolder, "tracks");
        File acExe = new File(directory, "acs.exe");
        File acExeAlt = new File(directory, "AssettoCorsa.exe");

        boolean hasContentStructure = contentFolder.exists() && carsFolder.exists() && tracksFolder.exists();
        boolean hasExecutable = acExe.exists() || acExeAlt.exists();

        log.info("AC validation - Content structure: " + hasContentStructure + ", Executable: " + hasExecutable);
        return hasContentStructure && hasExecutable;
    }

    private void loadAvailableTracks(File acDirectory) {
        try {
            Path tracksPath = Paths.get(acDirectory.getAbsolutePath(), "content", "tracks");
            if (Files.exists(tracksPath) && Files.isDirectory(tracksPath)) {
                List<String> trackFolders = Files.list(tracksPath)
                        .filter(Files::isDirectory)
                        .map(path -> path.getFileName().toString())
                        .sorted()
                        .toList();

                log.info("Found " + trackFolders.size() + " tracks in AC installation:");
                trackFolders.forEach(track -> log.info("  - " + track));

                // Store track information in game object or preferences
                // This could be used later for track selection validation
            }
        } catch (IOException e) {
            log.log(Level.WARNING, "Failed to load available tracks", e);
        }
    }

    @FXML
    private void onSubmitButtonClick() throws IOException {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String countryName = countryChoiceBox.getValue();
        String teamName = teamChoiceBox.getValue();

        // Validate required fields
        if (firstName.isEmpty() || lastName.isEmpty() || countryName.isEmpty() || teamName.isEmpty()) {
            UIHelper.showAlert("Missing Information", "Please fill out all required fields.",
                    firstNameField.getScene().getWindow());
            return;
        }

        if (selectedJsonPath == null || selectedJsonPath.isEmpty()) {
            UIHelper.showAlert("Missing JSON Path", "Please select a JSON results file path.",
                    jsonFilePathField.getScene().getWindow());
            return;
        }

        if (selectedAcGamePath == null || selectedAcGamePath.isEmpty()) {
            UIHelper.showAlert("Missing AC Path", "Please select the Assetto Corsa game directory.",
                    acGamePathField.getScene().getWindow());
            return;
        }

        // Validate JSON file exists
        if (!Files.exists(Paths.get(selectedJsonPath))) {
            UIHelper.showAlert("Invalid JSON Path", "The selected JSON file does not exist.",
                    jsonFilePathField.getScene().getWindow());
            return;
        }

        // Validate AC directory still exists and is valid
        File acDir = new File(selectedAcGamePath);
        if (!acDir.exists() || !validateAcInstallation(acDir)) {
            UIHelper.showAlert("Invalid AC Path", "The selected Assetto Corsa directory is not valid.",
                    acGamePathField.getScene().getWindow());
            return;
        }

        Optional<Country> country = Arrays.stream(Country.values())
                .filter(c -> c.getName().equals(countryName))
                .findFirst();

        Optional<Manufacture> team = Arrays.stream(Manufacture.values())
                .filter(t -> t.getNameShort().equals(teamName))
                .findFirst();

        if (country.isEmpty()) {
            log.severe("Country not found.");
            return;
        }

        if (team.isEmpty()) {
            log.severe("Team not found.");
            return;
        }

        // Create game with paths
        game = new Game(firstName, lastName, country.get(), team.get());

        // Store the paths in the game object (you'll need to add these fields to Game class)
        // game.setJsonResultsPath(selectedJsonPath);
        // game.setAcGamePath(selectedAcGamePath);

        // For now, log the paths until Game class is updated
        log.info("JSON Results Path: " + selectedJsonPath);
        log.info("AC Game Path: " + selectedAcGamePath);

        showNextScreen();

        if (log.isLoggable(Level.INFO)) {
            log.info(game.getPlayer().toString());
        }
    }
}