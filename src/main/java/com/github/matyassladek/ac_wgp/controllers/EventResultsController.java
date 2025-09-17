package com.github.matyassladek.ac_wgp.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.matyassladek.ac_wgp.enums.FXMLFile;
import com.github.matyassladek.ac_wgp.enums.Track;
import com.github.matyassladek.ac_wgp.model.Game;
import com.github.matyassladek.ac_wgp.helpers.UIHelper;
import com.github.matyassladek.ac_wgp.model.Driver;
import com.github.matyassladek.ac_wgp.services.ChampionshipService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

// TODO: refactor this awful spaghetti code
public class EventResultsController extends ViewController {

    private static final Logger log = Logger.getLogger(EventResultsController.class.getName());

    @FXML
    private GridPane driversList;

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

    public EventResultsController() {
        super(FXMLFile.DRIVERS_STANDINGS.getFileName());
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
        this.championshipService = new ChampionshipService(game.getCurrentChampionship());
        populateDriversList();
        initializeDropTargets();
    }

    private void initializeDropTargets() {
        dropTargets = List.of(
                dropTargetPosition1, dropTargetPosition2, dropTargetPosition3, dropTargetPosition4,
                dropTargetPosition5, dropTargetPosition6, dropTargetPosition7, dropTargetPosition8,
                dropTargetPosition9, dropTargetPosition10, dropTargetPosition11, dropTargetPosition12,
                dropTargetPosition13, dropTargetPosition14, dropTargetPosition15, dropTargetPosition16,
                dropTargetPosition17, dropTargetPosition18, dropTargetPosition19, dropTargetPosition20
        );

        for (Label dropTarget : dropTargets) {
            dropTarget.setOnDragOver(event -> {
                if (event.getGestureSource() != dropTarget && event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            });

            dropTarget.setOnDragDropped(event -> {
                if (event.getDragboard().hasString()) {
                    String driverName = event.getDragboard().getString();

                    if (dropTarget.getText() != null && !dropTarget.getText().equals("Drop Here")) {
                        addDriverToPool(dropTarget.getText());
                    }

                    dropTarget.setText(driverName);
                    removeDriverFromPool(driverName);
                    event.setDropCompleted(true);
                } else {
                    event.setDropCompleted(false);
                }
                event.consume();
            });

            dropTarget.setOnDragEntered(event -> dropTarget.setStyle("-fx-border-color: green; -fx-background-color: #e0ffe0;"));
            dropTarget.setOnDragExited(event -> dropTarget.setStyle("-fx-border-color: gray; -fx-background-color: #f0f0f0;"));

            dropTarget.setOnDragDetected(event -> {
                if (!dropTarget.getText().equals("Drop Here")) {
                    ClipboardContent content = new ClipboardContent();
                    content.putString(dropTarget.getText());
                    dropTarget.startDragAndDrop(TransferMode.MOVE).setContent(content);

                    String removedDriver = dropTarget.getText();
                    dropTarget.setText("Drop Here");

                    addDriverToPool(removedDriver);
                }
            });
        }
    }

    private void populateDriversList() {
        driversList.getChildren().clear();
        driversList.getRowConstraints().clear();
        driversList.getColumnConstraints().clear();

        List<Driver> drivers = game.getCurrentChampionship().getDrivers();

        int maxRowsPerColumn = 11;
        int row = 0;
        int col = 0;

        for (Driver driver : drivers) {
            Label driverLabel = new Label(driver.getName());
            driverLabel.setId("driver-" + driver.getName());
            driverLabel.setStyle("-fx-border-color: black; -fx-background-color: lightgray; -fx-padding: 5px;");

            driverLabel.setOnDragDetected(event -> {
                Dragboard dragboard = driverLabel.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(driverLabel.getText());
                dragboard.setContent(content);
                event.consume();
            });

            driverLabel.setOnDragDone(event -> {
                if (event.getTransferMode() == TransferMode.MOVE) {
                    driversList.getChildren().remove(driverLabel);
                }
            });

            driversList.add(driverLabel, col, row);

            row++;
            if (row >= maxRowsPerColumn) {
                row = 0;
                col++;
            }
        }

        for (int i = 0; i < maxRowsPerColumn; i++) {
            RowConstraints rowConstraint = new RowConstraints();
            rowConstraint.setPrefHeight(30);
            driversList.getRowConstraints().add(rowConstraint);
        }

        for (int i = 0; i <= col; i++) {
            ColumnConstraints columnConstraint = new ColumnConstraints();
            columnConstraint.setPrefWidth(150);
            driversList.getColumnConstraints().add(columnConstraint);
        }
    }

    private void addDriverToGridPane(String driverName) {
        for (Node node : driversList.getChildren()) {
            if (node instanceof Label) {
                Label existingLabel = (Label) node;
                if (existingLabel.getText().equals(driverName)) {
                    return;
                }
            }
        }

        Label driverLabel = new Label(driverName);
        driverLabel.setId("driver-" + driverName);
        driverLabel.setStyle("-fx-border-color: black; -fx-background-color: lightgray; -fx-padding: 5px;");

        driverLabel.setOnDragDetected(event -> {
            ClipboardContent content = new ClipboardContent();
            content.putString(driverLabel.getText());
            driverLabel.startDragAndDrop(TransferMode.MOVE).setContent(content);
            event.consume();
        });

        driverLabel.setOnDragDone(event -> {
            if (event.getTransferMode() == TransferMode.MOVE) {
                driversList.getChildren().remove(driverLabel);
            }
        });

        int maxRowsPerColumn = 11;
        int rows = driversList.getRowConstraints().size();
        int cols = driversList.getColumnConstraints().size();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                boolean isCellOccupied = false;

                for (Node node : driversList.getChildren()) {
                    Integer nodeRow = GridPane.getRowIndex(node);
                    Integer nodeCol = GridPane.getColumnIndex(node);

                    if (nodeRow != null && nodeCol != null && nodeRow == row && nodeCol == col) {
                        isCellOccupied = true;
                        break;
                    }
                }
                if (!isCellOccupied) {
                    driversList.add(driverLabel, col, row);
                    return;
                }
            }
        }
        if (rows < maxRowsPerColumn) {
            RowConstraints newRowConstraint = new RowConstraints(30);
            driversList.getRowConstraints().add(newRowConstraint);
        } else {
            ColumnConstraints newColumnConstraint = new ColumnConstraints(200);
            driversList.getColumnConstraints().add(newColumnConstraint);
        }
    }

    private void addDriverToPool(String driverName) {
        addDriverToGridPane(driverName);
    }

    private void removeDriverFromPool(String driverName) {
        driversList.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().equals(driverName));
    }

    @FXML
    private void onLoadFromJsonButtonClick() {
        log.info("Load from JSON button clicked");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select race_out.json file");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json")
        );

        File selectedFile = fileChooser.showOpenDialog(dropTargetPosition1.getScene().getWindow());

        if (selectedFile != null) {
            try {
                loadResultsFromJson(selectedFile);
                UIHelper.showAlert("Success", "Race results loaded successfully from JSON file!",
                        dropTargetPosition1.getScene().getWindow());
            } catch (Exception e) {
                log.log(Level.SEVERE, "Failed to load results from JSON", e);
                UIHelper.showAlert("Error", "Failed to load results from JSON file: " + e.getMessage(),
                        dropTargetPosition1.getScene().getWindow());
            }
        }
    }

    private void loadResultsFromJson(File jsonFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonFile);

        // Get the players array
        JsonNode playersNode = rootNode.get("players");
        if (playersNode == null || !playersNode.isArray()) {
            throw new IOException("Invalid JSON structure: missing or invalid players array");
        }

        // Get the race result array from the sessions
        JsonNode sessionsNode = rootNode.get("sessions");
        if (sessionsNode == null || !sessionsNode.isArray() || sessionsNode.size() == 0) {
            throw new IOException("Invalid JSON structure: missing or empty sessions array");
        }

        JsonNode sessionNode = sessionsNode.get(0); // Get first session
        JsonNode raceResultNode = sessionNode.get("raceResult");
        if (raceResultNode == null || !raceResultNode.isArray()) {
            throw new IOException("Invalid JSON structure: missing or invalid raceResult array");
        }

        // Step 1: Load all player names from the players array (index 0 = player 1, index 1 = player 2, etc.)
        List<String> playerNames = new ArrayList<>();
        for (int i = 0; i < playersNode.size(); i++) {
            JsonNode playerNode = playersNode.get(i);
            String playerName = playerNode.get("name").asText();
            playerNames.add(playerName);
            log.info("Loaded player " + (i + 1) + ": " + playerName);
        }

        // Step 2: Sort players according to raceResult array (raceResult contains player indices in finish order)
        List<String> sortedPlayerNames = new ArrayList<>();
        for (int i = 0; i < raceResultNode.size() && i < dropTargets.size(); i++) {
            int playerIndex = raceResultNode.get(i).asInt(); // This is the player index (0-based in raceResult)

            if (playerIndex >= 0 && playerIndex < playerNames.size()) {
                String playerName = playerNames.get(playerIndex);
                sortedPlayerNames.add(playerName);
                log.info("Position " + (i + 1) + ": Player " + (playerIndex + 1) + " (" + playerName + ")");
            } else {
                log.warning("Invalid player index in raceResult: " + playerIndex);
            }
        }

        // Clear current results and reset all drivers to pool
        clearCurrentResults();

        // Get available drivers from championship
        Set<String> availableDrivers = game.getCurrentChampionship().getDrivers()
                .stream()
                .map(Driver::getName)
                .collect(Collectors.toSet());

        // Step 3: Match sorted players with available drivers from the championship
        List<String> finalResults = new ArrayList<>();
        for (String playerName : sortedPlayerNames) {
            String matchedDriver = findMatchingDriver(playerName, availableDrivers);
            if (matchedDriver != null) {
                finalResults.add(matchedDriver);
                availableDrivers.remove(matchedDriver); // Remove to avoid duplicates
                log.info("Matched player '" + playerName + "' to driver '" + matchedDriver + "'");
            } else {
                log.warning("No matching driver found for player: " + playerName);
                finalResults.add(playerName); // Use original name if no match found
            }
        }

        // Apply the results to drop targets
        for (int i = 0; i < finalResults.size() && i < dropTargets.size(); i++) {
            String driverName = finalResults.get(i);
            dropTargets.get(i).setText(driverName);
            removeDriverFromPool(driverName);
        }

        log.info("Successfully loaded " + finalResults.size() + " race results from JSON file");
    }

    private String findMatchingDriver(String playerName, Set<String> availableDrivers) {
        // First try exact match (case-insensitive)
        for (String driver : availableDrivers) {
            if (driver.equalsIgnoreCase(playerName)) {
                return driver;
            }
        }

        // Then try partial match - check if player name contains driver name or vice versa
        for (String driver : availableDrivers) {
            if (driver.toLowerCase().contains(playerName.toLowerCase()) ||
                    playerName.toLowerCase().contains(driver.toLowerCase())) {
                return driver;
            }
        }

        // Try matching by last name
        String[] playerParts = playerName.split("\\s+");
        String[] driverParts;
        for (String driver : availableDrivers) {
            driverParts = driver.split("\\s+");
            // Check if any part of the player name matches any part of driver name
            for (String playerPart : playerParts) {
                for (String driverPart : driverParts) {
                    if (playerPart.equalsIgnoreCase(driverPart) && playerPart.length() > 2) {
                        return driver;
                    }
                }
            }
        }

        return null; // No match found
    }

    private void clearCurrentResults() {
        // Reset all drop targets and return drivers to pool
        for (Label dropTarget : dropTargets) {
            if (!dropTarget.getText().equals("Drop Here")) {
                addDriverToPool(dropTarget.getText());
                dropTarget.setText("Drop Here");
            }
        }
    }

    @FXML
    private void onClearResultsButtonClick() {
        log.info("Clear results button clicked");
        clearCurrentResults();
    }

    @FXML
    private void onSubmitButtonClick() throws IOException {
        log.info("Submit button clicked");

        List<String> raceResult = new ArrayList<>();
        for (Label dropTarget : dropTargets) {
            if (dropTarget.getText().equals("Drop Here")) {
                UIHelper.showAlert("Error", "All positions must be filled!", dropTarget.getScene().getWindow());
                return;
            }
            raceResult.add(dropTarget.getText());
        }

        championshipService.updateChampionshipPoints(raceResult);

        int currentRound = game.getCurrentChampionship().getCurrentRound();
        List<Track> calendar = game.getCurrentChampionship().getCalendar();

        game.getCurrentChampionship().setCurrentRound(currentRound + 1);
        if (currentRound >= calendar.size()) {
            if (game.getCurrentSeason() < game.getAllSeasons().size() - 1) {
                game.setCurrentSeason(game.getCurrentSeason() + 1);
            }
        }

        log.info(() -> championshipService.getChampionship().getDriversStandings().toString());
        log.info(() -> championshipService.getChampionship().getConstructorsStandings().toString());

        showNextScreen();
    }
}