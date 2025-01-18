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
        game.getCurrentChampionship().setCurrentRound((1 + game.getCurrentChampionship().getCurrentRound()));
        log.info(() -> championshipService.getChampionship().getDriversStandings().toString());
        log.info(() -> championshipService.getChampionship().getConstructorsStandings().toString());

        showNextScreen();
    }
}
