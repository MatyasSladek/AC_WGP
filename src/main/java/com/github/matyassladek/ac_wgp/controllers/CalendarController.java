package com.github.matyassladek.ac_wgp.controllers;

import com.github.matyassladek.ac_wgp.enums.FXMLFile;
import com.github.matyassladek.ac_wgp.model.Game;
import com.github.matyassladek.ac_wgp.model.Track;
import com.github.matyassladek.ac_wgp.services.TrackLoader;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CalendarController extends ViewController {

    private static final Logger log = Logger.getLogger(CalendarController.class.getName());
    private static final int MIN_RACES = 8;
    private static final int MAX_RACES = 25;

    @FXML
    private ListView<Track> availableTracksList;

    @FXML
    private ListView<Track> selectedTracksList;

    @FXML
    private Button addTrackButton;

    @FXML
    private Button removeTrackButton;

    @FXML
    private Button moveUpButton;

    @FXML
    private Button moveDownButton;

    @FXML
    private Button confirmButton;

    @FXML
    private Label raceCountLabel;

    @FXML
    private Label statusLabel;

    @FXML
    private Label trackCountLabel;

    private ObservableList<Track> availableTracks;
    private ObservableList<Track> selectedTracks;
    private TrackLoader trackLoader;

    public CalendarController() {
        super(FXMLFile.NEXT_EVENT.getFileName());
        this.trackLoader = new TrackLoader();
    }

    @FXML
    public void initialize() {
        selectedTracks = FXCollections.observableArrayList();
        availableTracks = FXCollections.observableArrayList();

        availableTracksList.setItems(availableTracks);
        selectedTracksList.setItems(selectedTracks);

        // Custom cell factory to display track name and country
        availableTracksList.setCellFactory(lv -> new ListCell<Track>() {
            @Override
            protected void updateItem(Track track, boolean empty) {
                super.updateItem(track, empty);
                if (empty || track == null) {
                    setText(null);
                } else {
                    setText(track.getDisplayName() + " (" + track.getCountry() + ")");
                }
            }
        });

        selectedTracksList.setCellFactory(lv -> new ListCell<Track>() {
            @Override
            protected void updateItem(Track track, boolean empty) {
                super.updateItem(track, empty);
                if (empty || track == null) {
                    setText(null);
                } else {
                    setText((getIndex() + 1) + ". " + track.getDisplayName());
                }
            }
        });

        updateRaceCount();
        updateButtons();

        // Add listeners for selection changes
        availableTracksList.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> updateButtons()
        );
        selectedTracksList.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> updateButtons()
        );
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
        // Now that we have the game instance, load the tracks
        loadTracksFromAC();
        updateRaceCount();
        updateButtons();
    }

    private void loadTracksFromAC() {
        if (game == null || game.getAcGamePath() == null) {
            log.severe("Game or AC path not initialized");
            statusLabel.setText("Error: AC game path not found!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            List<Track> tracks = trackLoader.loadTracks(game.getAcGamePath());
            availableTracks.addAll(tracks);

            // Sort tracks by name
            availableTracks.sort((t1, t2) -> t1.getDisplayName().compareToIgnoreCase(t2.getDisplayName()));

            if (trackCountLabel != null) {
                trackCountLabel.setText("Available tracks: " + tracks.size());
            }

            log.info("Loaded " + tracks.size() + " tracks from AC installation");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to load tracks from AC", e);
            statusLabel.setText("Error loading tracks from AC!");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleAddTrack() {
        Track selected = availableTracksList.getSelectionModel().getSelectedItem();
        if (selected != null && selectedTracks.size() < MAX_RACES) {
            selectedTracks.add(selected);
            availableTracks.remove(selected);
            updateRaceCount();
            updateButtons();
        }
    }

    @FXML
    private void handleRemoveTrack() {
        Track selected = selectedTracksList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            int index = selectedTracksList.getSelectionModel().getSelectedIndex();
            selectedTracks.remove(selected);
            availableTracks.add(selected);
            availableTracks.sort((t1, t2) -> t1.getDisplayName().compareToIgnoreCase(t2.getDisplayName()));
            updateRaceCount();
            updateButtons();

            // Maintain selection
            if (!selectedTracks.isEmpty()) {
                int newIndex = Math.min(index, selectedTracks.size() - 1);
                selectedTracksList.getSelectionModel().select(newIndex);
            }
        }
    }

    @FXML
    private void handleMoveUp() {
        int index = selectedTracksList.getSelectionModel().getSelectedIndex();
        if (index > 0) {
            Track track = selectedTracks.remove(index);
            selectedTracks.add(index - 1, track);
            selectedTracksList.getSelectionModel().select(index - 1);
            selectedTracksList.refresh();
        }
    }

    @FXML
    private void handleMoveDown() {
        int index = selectedTracksList.getSelectionModel().getSelectedIndex();
        if (index >= 0 && index < selectedTracks.size() - 1) {
            Track track = selectedTracks.remove(index);
            selectedTracks.add(index + 1, track);
            selectedTracksList.getSelectionModel().select(index + 1);
            selectedTracksList.refresh();
        }
    }

    @FXML
    private void handleConfirm() {
        if (selectedTracks.size() >= MIN_RACES) {
            // Store the calendar in the game object
            List<Track> calendar = new ArrayList<>(selectedTracks);
            game.setCustomCalendar(calendar);

            // Reinitialize the championship with the new calendar
            game.setCurrentChampionship(
                    new com.github.matyassladek.ac_wgp.factory.ChampionshipFactory()
                            .createChampionship(game.getTeams(), calendar)
            );

            log.log(Level.INFO, "Calendar created with {0} races", selectedTracks.size());
            showNextScreen();
        } else {
            statusLabel.setText("Please select at least " + MIN_RACES + " races!");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleRefreshTracks() {
        // Reload tracks from AC installation
        availableTracks.clear();
        selectedTracks.clear();
        loadTracksFromAC();
        updateRaceCount();
        updateButtons();
    }

    private void updateRaceCount() {
        int count = selectedTracks.size();
        raceCountLabel.setText("Races: " + count + "/" + MAX_RACES);

        if (count >= MIN_RACES && count <= MAX_RACES) {
            raceCountLabel.setStyle("-fx-text-fill: green;");
            statusLabel.setText("Calendar ready!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } else if (count < MIN_RACES) {
            raceCountLabel.setStyle("-fx-text-fill: orange;");
            statusLabel.setText("Add " + (MIN_RACES - count) + " more race(s) to continue");
            statusLabel.setStyle("-fx-text-fill: orange;");
        }
    }

    private void updateButtons() {
        Track availableSelected = availableTracksList.getSelectionModel().getSelectedItem();
        Track selectedTrack = selectedTracksList.getSelectionModel().getSelectedItem();
        int selectedIndex = selectedTracksList.getSelectionModel().getSelectedIndex();

        addTrackButton.setDisable(availableSelected == null || selectedTracks.size() >= MAX_RACES);
        removeTrackButton.setDisable(selectedTrack == null);
        moveUpButton.setDisable(selectedTrack == null || selectedIndex <= 0);
        moveDownButton.setDisable(selectedTrack == null || selectedIndex >= selectedTracks.size() - 1);
        confirmButton.setDisable(selectedTracks.size() < MIN_RACES);
    }
}