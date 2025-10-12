package com.github.matyassladek.ac_wgp.controllers;

import com.github.matyassladek.ac_wgp.enums.FXMLFile;
import com.github.matyassladek.ac_wgp.enums.Country;
import com.github.matyassladek.ac_wgp.enums.Manufacture;
import com.github.matyassladek.ac_wgp.factory.GameFactory;
import com.github.matyassladek.ac_wgp.model.DriverFormData;
import com.github.matyassladek.ac_wgp.utils.UIHelper;
import com.github.matyassladek.ac_wgp.services.validation.AcInstallationValidator;
import com.github.matyassladek.ac_wgp.services.ac.FilePathSelector;
import com.github.matyassladek.ac_wgp.services.validation.FormValidator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the driver creation screen.
 * Handles user input for creating a new game with driver information.
 */
public class CreateDriverController extends ViewController {

    private static final Logger log = Logger.getLogger(CreateDriverController.class.getName());

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private ChoiceBox<String> countryChoiceBox;
    @FXML private ChoiceBox<String> teamChoiceBox;
    @FXML private TextField jsonFilePathField;
    @FXML private Button browseJsonButton;
    @FXML private TextField acGamePathField;
    @FXML private Button browseAcPathButton;

    private final FilePathSelector filePathSelector;
    private final AcInstallationValidator acValidator;
    private final FormValidator formValidator;
    private final GameFactory gameFactory;

    private String selectedJsonPath;
    private String selectedAcGamePath;

    public CreateDriverController() {
        this(new FilePathSelector(), new AcInstallationValidator(),
                new FormValidator(), new GameFactory());
    }

    // Constructor injection for testing
    CreateDriverController(FilePathSelector filePathSelector,
                           AcInstallationValidator acValidator,
                           FormValidator formValidator,
                           GameFactory gameFactory) {
        super(FXMLFile.PRE_SEASON.getFileName());
        this.filePathSelector = filePathSelector;
        this.acValidator = acValidator;
        this.formValidator = formValidator;
        this.gameFactory = gameFactory;
    }

    @FXML
    public void initialize() {
        initializeCountryChoiceBox();
        initializeTeamChoiceBox();
        setPathFieldsReadOnly();
    }

    @FXML
    private void onBrowseJsonButtonClick() {
        filePathSelector.selectJsonFile(browseJsonButton.getScene().getWindow())
                .ifPresent(path -> {
                    selectedJsonPath = path;
                    jsonFilePathField.setText(path);
                    log.info("Selected JSON file: " + path);
                });
    }

    @FXML
    private void onBrowseAcPathButtonClick() {
        filePathSelector.selectAcDirectory(browseAcPathButton.getScene().getWindow())
                .ifPresent(directory -> {
                    if (acValidator.isValidInstallation(directory)) {
                        selectedAcGamePath = directory.getAbsolutePath();
                        acGamePathField.setText(selectedAcGamePath);
                        log.info("Selected AC game path: " + selectedAcGamePath);
                        acValidator.logAvailableTracks(directory);
                    } else {
                        showInvalidAcDirectoryError();
                    }
                });
    }

    @FXML
    private void onSubmitButtonClick() throws IOException {
        DriverFormData formData = collectFormData();

        if (!formValidator.validateDriverForm(formData, firstNameField.getScene().getWindow())) {
            return;
        }

        createAndInitializeGame(formData);
        showNextScreen();

        if (log.isLoggable(Level.INFO)) {
            log.info(game.getPlayer().toString());
        }
    }

    private void initializeCountryChoiceBox() {
        List<String> countryNames = Arrays.stream(Country.values())
                .map(Country::getName)
                .toList();
        countryChoiceBox.setItems(FXCollections.observableArrayList(countryNames));
        countryChoiceBox.setValue("");
    }

    private void initializeTeamChoiceBox() {
        List<String> teamNames = Arrays.stream(Manufacture.values())
                .map(Manufacture::getNameShort)
                .toList();
        teamChoiceBox.setItems(FXCollections.observableArrayList(teamNames));
        teamChoiceBox.setValue("");
    }

    private void setPathFieldsReadOnly() {
        jsonFilePathField.setEditable(false);
        acGamePathField.setEditable(false);
    }

    private void showInvalidAcDirectoryError() {
        UIHelper.showAlert("Invalid Directory",
                "The selected directory doesn't appear to be a valid Assetto Corsa installation.\n" +
                        "Please select the main Assetto Corsa game directory.",
                browseAcPathButton.getScene().getWindow());
    }

    private DriverFormData collectFormData() {
        return new DriverFormData(
                firstNameField.getText(),
                lastNameField.getText(),
                countryChoiceBox.getValue(),
                teamChoiceBox.getValue(),
                selectedJsonPath,
                selectedAcGamePath
        );
    }

    private void createAndInitializeGame(DriverFormData formData) {
        Country country = findCountryByName(formData.getCountryName())
                .orElseThrow(() -> new IllegalStateException("Country not found: " + formData.getCountryName()));

        Manufacture team = findTeamByName(formData.getTeamName())
                .orElseThrow(() -> new IllegalStateException("Team not found: " + formData.getTeamName()));

        game = gameFactory.createGame(
                formData.getFirstName(),
                formData.getLastName(),
                country,
                team,
                formData.getJsonPath(),
                formData.getAcGamePath()
        );

        log.info("Game created - JSON Path: " + formData.getJsonPath() + ", AC Path: " + formData.getAcGamePath());
    }

    private Optional<Country> findCountryByName(String name) {
        return Arrays.stream(Country.values())
                .filter(c -> c.getName().equals(name))
                .findFirst();
    }

    private Optional<Manufacture> findTeamByName(String name) {
        return Arrays.stream(Manufacture.values())
                .filter(t -> t.getNameShort().equals(name))
                .findFirst();
    }
}