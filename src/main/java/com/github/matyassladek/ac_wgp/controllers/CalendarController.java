package com.github.matyassladek.ac_wgp.controllers;

import com.github.matyassladek.ac_wgp.enums.FXMLFile;
import com.github.matyassladek.ac_wgp.model.Game;
import com.github.matyassladek.ac_wgp.model.Track;
import com.github.matyassladek.ac_wgp.services.ac.ChampionshipExportService;
import com.github.matyassladek.ac_wgp.services.ac.TrackLoader;
import com.github.matyassladek.ac_wgp.services.calendar.CalendarValidationService;
import com.github.matyassladek.ac_wgp.services.calendar.TrackListManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the calendar creation screen.
 * Allows users to select tracks for the season and arrange them in order.
 */
public class CalendarController extends ViewController {

    private static final Logger log = Logger.getLogger(CalendarController.class.getName());

    @FXML private ListView<Track> availableTracksList;
    @FXML private ListView<Track> selectedTracksList;
    @FXML private Button addTrackButton;
    @FXML private Button removeTrackButton;
    @FXML private Button moveUpButton;
    @FXML private Button moveDownButton;
    @FXML private Button confirmButton;
    @FXML private Label raceCountLabel;
    @FXML private Label statusLabel;
    @FXML private Label trackCountLabel;

    private ObservableList<Track> availableTracks;
    private ObservableList<Track> selectedTracks;

    private final TrackLoader trackLoader;
    private final TrackListManager trackListManager;
    private final CalendarValidationService validationService;
    private final ChampionshipExportService championshipExportService;

    public CalendarController() {
        this(new TrackLoader(), new TrackListManager(), new CalendarValidationService(), new ChampionshipExportService());
    }

    CalendarController(TrackLoader trackLoader, TrackListManager trackListManager,
                       CalendarValidationService validationService, ChampionshipExportService championshipExportService) {
        super(FXMLFile.NEXT_EVENT.getFileName());
        this.trackLoader = trackLoader;
        this.trackListManager = trackListManager;
        this.validationService = validationService;
        this.championshipExportService = championshipExportService;
    }

    @FXML
    public void initialize() {
        initializeObservableLists();
        setupCellFactories();
        setupSelectionListeners();
        updateUI();
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
        loadTracksFromAC();
        updateUI();
    }

    @FXML
    private void handleAddTrack() {
        Track selected = availableTracksList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            trackListManager.addTrack(selected, selectedTracks, availableTracks);
            updateUI();
        }
    }

    @FXML
    private void handleRemoveTrack() {
        Track selected = selectedTracksList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            int index = selectedTracksList.getSelectionModel().getSelectedIndex();
            trackListManager.removeTrack(selected, selectedTracks, availableTracks);
            updateUI();
            maintainSelection(index);
        }
    }

    @FXML
    private void handleMoveUp() {
        int index = selectedTracksList.getSelectionModel().getSelectedIndex();
        if (trackListManager.moveTrackUp(index, selectedTracks)) {
            selectedTracksList.getSelectionModel().select(index - 1);
            selectedTracksList.refresh();
        }
    }

    @FXML
    private void handleMoveDown() {
        int index = selectedTracksList.getSelectionModel().getSelectedIndex();
        if (trackListManager.moveTrackDown(index, selectedTracks)) {
            selectedTracksList.getSelectionModel().select(index + 1);
            selectedTracksList.refresh();
        }
    }

    @FXML
    private void handleConfirm() throws IOException {
        List<Track> calendar = new ArrayList<>(selectedTracks);

        if (!validationService.isValidCalendar(calendar)) {
            showValidationError();
            return;
        }

        // Use GameManager to set calendar and initialize championship
        boolean success = gameManager.setCurrentSeasonCalendar(game, calendar);

        if (success) {
            log.info("Calendar created with " + calendar.size() + " races");

            // Export championship to .champ file
            try {
                championshipExportService.exportChampionship(game, calendar);
                log.info("Championship exported to AC champs directory");
            } catch (Exception e) {
                log.log(Level.SEVERE, "Failed to export championship", e);
                showError("Championship created but failed to export to AC. " +
                        "You may need to manually create the championship in AC.");
                // Don't return - still proceed with the game
            }

            showNextScreen();
        } else {
            showError("Failed to initialize championship with the selected calendar.");
        }
    }

    @FXML
    private void handleRefreshTracks() {
        availableTracks.clear();
        selectedTracks.clear();
        loadTracksFromAC();
        updateUI();
    }

    private void initializeObservableLists() {
        selectedTracks = FXCollections.observableArrayList();
        availableTracks = FXCollections.observableArrayList();
        availableTracksList.setItems(availableTracks);
        selectedTracksList.setItems(selectedTracks);
    }

    private void setupCellFactories() {
        availableTracksList.setCellFactory(lv -> new TrackCell(true));
        selectedTracksList.setCellFactory(lv -> new TrackCell(false));
    }

    private void setupSelectionListeners() {
        availableTracksList.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> updateButtons());
        selectedTracksList.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> updateButtons());
    }

    private void loadTracksFromAC() {
        if (game == null || game.getAcGamePath() == null) {
            log.severe("Game or AC path not initialized");
            showError("AC game path not found!");
            return;
        }

        try {
            List<Track> tracks = trackLoader.loadTracks(game.getAcGamePath());
            availableTracks.addAll(tracks);
            availableTracks.sort((t1, t2) ->
                    t1.getDisplayName().compareToIgnoreCase(t2.getDisplayName()));

            if (trackCountLabel != null) {
                trackCountLabel.setText("Available tracks: " + tracks.size());
            }

            log.info("Loaded " + tracks.size() + " tracks from AC installation");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to load tracks from AC", e);
            showError("Error loading tracks from AC!");
        }
    }

    private void updateUI() {
        updateRaceCount();
        updateButtons();
    }

    private void updateRaceCount() {
        int count = selectedTracks.size();
        raceCountLabel.setText("Races: " + count + "/" +
                CalendarValidationService.MAX_RACES);

        if (validationService.isValidCalendar(new ArrayList<>(selectedTracks))) {
            setStatusSuccess("Calendar ready!");
            raceCountLabel.setStyle("-fx-text-fill: green;");
        } else if (count < CalendarValidationService.MIN_RACES) {
            setStatusWarning("Add " + (CalendarValidationService.MIN_RACES - count) +
                    " more race(s) to continue");
            raceCountLabel.setStyle("-fx-text-fill: orange;");
        }
    }

    private void updateButtons() {
        Track availableSelected = availableTracksList.getSelectionModel().getSelectedItem();
        Track selectedTrack = selectedTracksList.getSelectionModel().getSelectedItem();
        int selectedIndex = selectedTracksList.getSelectionModel().getSelectedIndex();

        addTrackButton.setDisable(availableSelected == null ||
                selectedTracks.size() >= CalendarValidationService.MAX_RACES);
        removeTrackButton.setDisable(selectedTrack == null);
        moveUpButton.setDisable(selectedTrack == null || selectedIndex <= 0);
        moveDownButton.setDisable(selectedTrack == null ||
                selectedIndex >= selectedTracks.size() - 1);
        confirmButton.setDisable(!validationService.isValidCalendar(
                new ArrayList<>(selectedTracks)));
    }

    private void maintainSelection(int previousIndex) {
        if (!selectedTracks.isEmpty()) {
            int newIndex = Math.min(previousIndex, selectedTracks.size() - 1);
            selectedTracksList.getSelectionModel().select(newIndex);
        }
    }

    private void showValidationError() {
        setStatusError("Please select at least " +
                CalendarValidationService.MIN_RACES + " races!");
    }

    private void showError(String message) {
        statusLabel.setText(message);
        statusLabel.setStyle("-fx-text-fill: red;");
    }

    private void setStatusSuccess(String message) {
        statusLabel.setText(message);
        statusLabel.setStyle("-fx-text-fill: green;");
    }

    private void setStatusWarning(String message) {
        statusLabel.setText(message);
        statusLabel.setStyle("-fx-text-fill: orange;");
    }

    private void setStatusError(String message) {
        statusLabel.setText(message);
        statusLabel.setStyle("-fx-text-fill: red;");
    }

    /**
     * Custom cell for displaying tracks in list views.
     */
    private static class TrackCell extends ListCell<Track> {
        private final boolean showCountry;

        TrackCell(boolean showCountry) {
            this.showCountry = showCountry;
        }

        @Override
        protected void updateItem(Track track, boolean empty) {
            super.updateItem(track, empty);
            if (empty || track == null) {
                setText(null);
            } else {
                if (showCountry) {
                    setText(track.getDisplayName() + " (" + track.getCountry() + ")");
                } else {
                    setText((getIndex() + 1) + ". " + track.getDisplayName());
                }
            }
        }
    }
}