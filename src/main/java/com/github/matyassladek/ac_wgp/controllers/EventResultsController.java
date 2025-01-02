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
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
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
    @FXML private Label dropTargetPosition23;
    @FXML private Label dropTargetPosition24;

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

    private void populateDriversList() {
        List<Driver> drivers = game.getCurrentChampionship().getDrivers();

        int row = 0;
        int col = 0;

        for (Driver driver : drivers) {
            Label driverLabel = new Label(driver.getName());
            driverLabel.setStyle("-fx-border-color: black; -fx-background-color: lightgray; -fx-padding: 5px;");
            driverLabel.setOnDragDetected(event -> {
                ClipboardContent content = new ClipboardContent();
                content.putString(driverLabel.getText());
                driverLabel.startDragAndDrop(TransferMode.MOVE).setContent(content);
                event.consume();
            });

            // Add the label to the GridPane at the current column and row
            driversList.add(driverLabel, col, row);

            // Update column and row to maintain two columns
            if (++row == 12) { // After 12 drivers, switch to the next column
                row = 0;
                col++;
            }
        }
    }

    private void initializeDropTargets() {
        dropTargets = List.of(
                dropTargetPosition1, dropTargetPosition2, dropTargetPosition3, dropTargetPosition4,
                dropTargetPosition5, dropTargetPosition6, dropTargetPosition7, dropTargetPosition8,
                dropTargetPosition9, dropTargetPosition10, dropTargetPosition11, dropTargetPosition12,
                dropTargetPosition13, dropTargetPosition14, dropTargetPosition15, dropTargetPosition16,
                dropTargetPosition17, dropTargetPosition18, dropTargetPosition19, dropTargetPosition20,
                dropTargetPosition21, dropTargetPosition22, dropTargetPosition23, dropTargetPosition24
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

                    // Remove the driver from the pool
                    removeDriverFromPool(driverName);

                    // Assign the new driver to the drop target
                    dropTarget.setText(driverName);
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

    private void addDriverToPool(String driverName) {
        Label driverLabel = new Label(driverName);
        driverLabel.setStyle("-fx-border-color: black; -fx-background-color: lightgray; -fx-padding: 5px;");
        driverLabel.setOnDragDetected(event -> {
            ClipboardContent content = new ClipboardContent();
            content.putString(driverLabel.getText());
            driverLabel.startDragAndDrop(TransferMode.MOVE).setContent(content);
            event.consume();
        });

        driversList.getChildren().add(driverLabel);
    }

    private void removeDriverFromPool(String driverName) {
        for (Node node : driversList.getChildren()) {
            if (node instanceof Label && ((Label) node).getText().equals(driverName)) {
                driversList.getChildren().remove(node);
                break;
            }
        }
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
