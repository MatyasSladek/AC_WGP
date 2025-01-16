package com.github.matyassladek.ac_wgp.controllers;

import com.github.matyassladek.ac_wgp.enums.FXMLFile;
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
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
    @FXML private Label dropTargetPosition21;
    @FXML private Label dropTargetPosition22;

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
                dropTargetPosition17, dropTargetPosition18, dropTargetPosition19, dropTargetPosition20,
                dropTargetPosition21, dropTargetPosition22
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

                    // Add the currently occupied driver back to the pool
                    if (dropTarget.getText() != null && !dropTarget.getText().equals("Drop Here")) {
                        addDriverToPool(dropTarget.getText());
                    }

                    // Assign the new driver to the drop target
                    dropTarget.setText(driverName);

                    // Remove the driver from the pool
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

                    // Clear the drop target
                    String removedDriver = dropTarget.getText();
                    dropTarget.setText("Drop Here");

                    // Add the removed driver back to the pool
                    addDriverToPool(removedDriver);
                }
            });
        }
    }


    private void populateDriversList() {
        // Clear previous content
        driversList.getChildren().clear();
        driversList.getRowConstraints().clear();
        driversList.getColumnConstraints().clear();

        List<Driver> drivers = game.getCurrentChampionship().getDrivers();

        int maxRowsPerColumn = 12; // Maximum rows before a new column starts
        int row = 0;
        int col = 0;

        for (Driver driver : drivers) {
            // Create a label for each driver
            Label driverLabel = new Label(driver.getName());
            driverLabel.setId("driver-" + driver.getName()); // Unique ID
            driverLabel.setStyle("-fx-border-color: black; -fx-background-color: lightgray; -fx-padding: 5px;");

            // Enable drag-and-drop functionality
            driverLabel.setOnDragDetected(event -> {
                Dragboard dragboard = driverLabel.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(driverLabel.getText());
                dragboard.setContent(content);
                event.consume();
            });

            driverLabel.setOnDragDone(event -> {
                if (event.getTransferMode() == TransferMode.MOVE) {
                    driversList.getChildren().remove(driverLabel); // Remove from old position
                }
            });

            // Add the label to the grid
            driversList.add(driverLabel, col, row);

            // Increment row/column
            row++;
            if (row >= maxRowsPerColumn) {
                row = 0;
                col++;
            }
        }

        // Dynamically add row constraints for spacing
        for (int i = 0; i < maxRowsPerColumn; i++) {
            RowConstraints rowConstraint = new RowConstraints();
            rowConstraint.setPrefHeight(30); // Adjust height as needed
            driversList.getRowConstraints().add(rowConstraint);
        }

        // Dynamically add column constraints for spacing
        for (int i = 0; i <= col; i++) {
            ColumnConstraints columnConstraint = new ColumnConstraints();
            columnConstraint.setPrefWidth(150); // Adjust width as needed
            driversList.getColumnConstraints().add(columnConstraint);
        }
    }

    private void addDriverToGridPane(String driverName) {
        // Ensure the driver is not already present in the pool
        for (Node node : driversList.getChildren()) {
            if (node instanceof Label) {
                Label existingLabel = (Label) node;
                if (existingLabel.getText().equals(driverName)) {
                    return; // Driver already exists, no need to add
                }
            }
        }

        // Create a new label for the driver
        Label driverLabel = new Label(driverName);
        driverLabel.setId("driver-" + driverName); // Assign a unique ID
        driverLabel.setStyle("-fx-border-color: black; -fx-background-color: lightgray; -fx-padding: 5px;");

        // Enable drag functionality for the new label
        driverLabel.setOnDragDetected(event -> {
            ClipboardContent content = new ClipboardContent();
            content.putString(driverLabel.getText());
            driverLabel.startDragAndDrop(TransferMode.MOVE).setContent(content);
            event.consume();
        });

        driverLabel.setOnDragDone(event -> {
            if (event.getTransferMode() == TransferMode.MOVE) {
                driversList.getChildren().remove(driverLabel); // Remove from old position
            }
        });

        // Find the next available empty cell in the GridPane
        int maxRowsPerColumn = 12;
        int rows = driversList.getRowConstraints().size();
        int cols = driversList.getColumnConstraints().size();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                boolean isCellOccupied = false;

                // Check if the cell is occupied
                for (Node node : driversList.getChildren()) {
                    Integer nodeRow = GridPane.getRowIndex(node);
                    Integer nodeCol = GridPane.getColumnIndex(node);

                    if (nodeRow != null && nodeCol != null && nodeRow == row && nodeCol == col) {
                        isCellOccupied = true;
                        break;
                    }
                }

                // If an empty cell is found, place the driver label here
                if (!isCellOccupied) {
                    driversList.add(driverLabel, col, row);
                    return;
                }
            }
        }

        // Add row and column constraints dynamically if needed
        if (rows < maxRowsPerColumn) {
            RowConstraints newRowConstraint = new RowConstraints(30); // Adjust height
            driversList.getRowConstraints().add(newRowConstraint);
        } else {
            ColumnConstraints newColumnConstraint = new ColumnConstraints(150); // Adjust width
            driversList.getColumnConstraints().add(newColumnConstraint);
        }
    }


    private void addDriverToPool(String driverName) {
        addDriverToGridPane(driverName); // Use the new method
    }

    private void removeDriverFromPool(String driverName) {
        // Remove the driver label with the matching text
        driversList.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().equals(driverName));
    }

    @FXML
    private void onSubmitButtonClick() throws IOException {
        log.info("Submit button clicked");

        List<String> raceResult = new ArrayList<>();
        for (Label dropTarget : dropTargets) {
            if (dropTarget.getText().equals("Drop Here")) {
                UIHelper.showAlert("Error", "All positions must be filled!");
                return;
            }
            raceResult.add(dropTarget.getText());
        }

        championshipService.updateChampionshipPoints(raceResult);

        log.info(() -> championshipService.getChampionship().getDriversStandings().toString());
        log.info(() -> championshipService.getChampionship().getConstructorsStandings().toString());

        showNextScreen();
    }
}
