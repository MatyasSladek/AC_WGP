package com.github.matyassladek.ac_wgp.controllers;

import com.github.matyassladek.ac_wgp.enums.FXMLFile;
import com.github.matyassladek.ac_wgp.model.Game;
import com.github.matyassladek.ac_wgp.model.Track;
import com.github.matyassladek.ac_wgp.utils.UIHelper;
import com.github.matyassladek.ac_wgp.services.game.ChampionshipService;
import com.github.matyassladek.ac_wgp.services.results.RaceResultsLoader;
import com.github.matyassladek.ac_wgp.services.results.RaceResults;
import com.github.matyassladek.ac_wgp.services.results.DriverMatcherService;
import com.github.matyassladek.ac_wgp.services.ui.DragDropManager;
import com.github.matyassladek.ac_wgp.services.ui.DriverPoolManager;
import com.github.matyassladek.ac_wgp.services.validation.TrackValidationService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the event results screen.
 * Allows users to input race results via drag-and-drop or load from JSON.
 */
public class EventResultsController extends ViewController {

    private static final Logger log = Logger.getLogger(EventResultsController.class.getName());

    @FXML private GridPane driversList;
    @FXML private Label dropTargetPosition1;
    @FXML private Label dropTargetPosition2;
    @FXML private Label dropTargetPosition3;
    @FXML private Label dropTargetPosition4;
    @FXML private Label dropTargetPosition5;
    @FXML private Label dropTargetPosition6;
    @FXML private Label dropTargetPosition7;
    @FXML private Label dropTargetPosition8;
    @FXML private Label dropTargetPosition9;
    @FXML private Label dropTargetPosition10;
    @FXML private Label dropTargetPosition11;
    @FXML private Label dropTargetPosition12;
    @FXML private Label dropTargetPosition13;
    @FXML private Label dropTargetPosition14;
    @FXML private Label dropTargetPosition15;
    @FXML private Label dropTargetPosition16;
    @FXML private Label dropTargetPosition17;
    @FXML private Label dropTargetPosition18;
    @FXML private Label dropTargetPosition19;
    @FXML private Label dropTargetPosition20;

    private List<Label> dropTargets;
    private ChampionshipService championshipService;
    private DriverPoolManager poolManager;
    private DragDropManager dragDropManager;
    private final RaceResultsLoader resultsLoader;
    private final DriverMatcherService driverMatcher;
    private final TrackValidationService trackValidator;

    public EventResultsController() {
        this(new RaceResultsLoader(), new DriverMatcherService(), new TrackValidationService());
    }

    EventResultsController(RaceResultsLoader resultsLoader, DriverMatcherService driverMatcher,
                           TrackValidationService trackValidator) {
        super(FXMLFile.DRIVERS_STANDINGS.getFileName());
        this.resultsLoader = resultsLoader;
        this.driverMatcher = driverMatcher;
        this.trackValidator = trackValidator;
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
        this.championshipService = new ChampionshipService(game.getCurrentChampionship());

        initializeServices();
        setupUI();
        validateTracks();
    }

    @FXML
    private void onLoadFromJsonButtonClick() {
        log.info("Load from JSON button clicked");

        File jsonFile = selectJsonFile();
        if (jsonFile == null) {
            return;
        }

        try {
            loadAndApplyResults(jsonFile);
            showSuccess("Race results loaded successfully from JSON file!");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to load results from JSON", e);
            showError("Failed to load results from JSON file: " + e.getMessage());
        }
    }

    @FXML
    private void onClearResultsButtonClick() {
        log.info("Clear results button clicked");
        dragDropManager.clearAllTargets(dropTargets);
    }

    @FXML
    private void onSubmitButtonClick() throws IOException {
        log.info("Submit button clicked");

        List<String> raceResult = dragDropManager.getResults(dropTargets);

        if (raceResult == null) {
            showError("All positions must be filled!");
            return;
        }

        processRaceResults(raceResult);
        advanceToNextRound();
        showNextScreen();
    }

    private void initializeServices() {
        poolManager = new DriverPoolManager(driversList);
        dragDropManager = new DragDropManager(
                poolManager::addDriverToPool,
                poolManager::removeDriverFromPool
        );
    }

    private void setupUI() {
        initializeDropTargets();
        poolManager.populateDriversList(game.getCurrentChampionship().getDrivers());
    }

    private void initializeDropTargets() {
        dropTargets = List.of(
                dropTargetPosition1, dropTargetPosition2, dropTargetPosition3, dropTargetPosition4,
                dropTargetPosition5, dropTargetPosition6, dropTargetPosition7, dropTargetPosition8,
                dropTargetPosition9, dropTargetPosition10, dropTargetPosition11, dropTargetPosition12,
                dropTargetPosition13, dropTargetPosition14, dropTargetPosition15, dropTargetPosition16,
                dropTargetPosition17, dropTargetPosition18, dropTargetPosition19, dropTargetPosition20
        );

        dragDropManager.initializeDropTargets(dropTargets);
    }

    private File selectJsonFile() {
        String jsonPath = game.getJsonResultsPath();

        if (jsonPath != null && !jsonPath.isEmpty()) {
            File jsonFile = new File(jsonPath);
            if (jsonFile.exists()) {
                return jsonFile;
            } else {
                showError("The configured JSON file was not found: " + jsonPath +
                        "\nPlease check that the race results have been generated.");
                return null;
            }
        }

        // Fallback to file chooser
        return showFileChooser();
    }

    private File showFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select race_out.json file");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json")
        );

        return fileChooser.showOpenDialog(dropTargetPosition1.getScene().getWindow());
    }

    private void loadAndApplyResults(File jsonFile) throws IOException {
        // Load results from JSON
        RaceResults raceResults = resultsLoader.loadFromJson(jsonFile);

        // Match player names to drivers
        List<String> matchedDrivers = driverMatcher.matchDrivers(
                raceResults,
                game.getCurrentChampionship().getDrivers()
        );

        // Clear current results
        dragDropManager.clearAllTargets(dropTargets);

        // Apply matched results
        dragDropManager.setResults(dropTargets, matchedDrivers);

        log.info("Successfully loaded " + matchedDrivers.size() + " race results from JSON file");
    }

    private void processRaceResults(List<String> raceResult) {
        championshipService.updateChampionshipPoints(raceResult);

        log.info("Championship updated with race results");
        log.fine(() -> championshipService.getChampionship().getDriversStandings().toString());
        log.fine(() -> championshipService.getChampionship().getConstructorsStandings().toString());
    }

    private void advanceToNextRound() {
        int currentRound = game.getCurrentChampionship().getCurrentRound();
        game.getCurrentChampionship().setCurrentRound(currentRound + 1);

        log.info("Advanced to round " + (currentRound + 1));
    }

    private void validateTracks() {
        if (!game.isValidateTracks()) {
            log.info("Track validation is disabled");
            return;
        }

        if (game.getAcGamePath() == null || game.getAcGamePath().isEmpty()) {
            log.warning("AC game path not set - skipping track validation");
            return;
        }

        try {
            List<Track> calendar = game.getCurrentChampionship().getCalendar();
            List<String> missingTracks = trackValidator.validateTracks(
                    calendar,
                    game.getAcGamePath()
            );

            if (!missingTracks.isEmpty()) {
                showMissingTracksWarning(missingTracks);
            } else {
                log.info("All championship tracks validated successfully");
            }
        } catch (Exception e) {
            log.log(Level.WARNING, "Error validating available tracks", e);
        }
    }

    private void showMissingTracksWarning(List<String> missingTracks) {
        log.warning("Some championship tracks are not available in AC installation: " +
                String.join(", ", missingTracks));

        UIHelper.showAlert("Missing Tracks",
                "The following tracks from the championship calendar are not available in your AC installation:\n\n" +
                        String.join("\n", missingTracks) +
                        "\n\nYou may need to install these tracks or the results loading may not work correctly.",
                dropTargetPosition1.getScene().getWindow());
    }

    private void showSuccess(String message) {
        UIHelper.showAlert("Success", message,
                dropTargetPosition1.getScene().getWindow());
    }

    private void showError(String message) {
        UIHelper.showAlert("Error", message,
                dropTargetPosition1.getScene().getWindow());
    }
}