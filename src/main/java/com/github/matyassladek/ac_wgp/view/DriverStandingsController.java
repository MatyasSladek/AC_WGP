package com.github.matyassladek.ac_wgp.view;

import com.github.matyassladek.ac_wgp.controller.Game;
import com.github.matyassladek.ac_wgp.model.Championship.DriverSlot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DriverStandingsController {

    @FXML
    private TableView<DriverSlot> standingsTable;

    @FXML
    private TableColumn<DriverSlot, Integer> positionColumn;

    @FXML
    private TableColumn<DriverSlot, String> driverColumn;

    @FXML
    private TableColumn<DriverSlot, String> teamColumn;

    @FXML
    private TableColumn<DriverSlot, Integer> pointsColumn;

    private Game game;

    public void setGame(Game game) {
        this.game = game;
        loadDriverStandings();
    }

    public void initialize() {
        // Set up column bindings
        positionColumn.setCellValueFactory(data -> {
            int position = standingsTable.getItems().indexOf(data.getValue()) + 1;
            return new javafx.beans.property.SimpleIntegerProperty(position).asObject();
        });
        driverColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDriver().getName()));
        teamColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTeam().getManufacture().getNameShort()));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
    }

    private void loadDriverStandings() {
        if (game != null && game.getCurrentChampionship() != null) {
            // Get the driversStandings list from the Championship object
            ObservableList<DriverSlot> standings = FXCollections.observableArrayList(game.getCurrentChampionship().getDriversStandings());
            standingsTable.setItems(standings);
        }
    }

    @FXML
    private void onContinueButtonClick() {
        System.out.println("Continue button clicked!");
        // Action to perform when the "Continue" button is clicked
        // Example: Navigate to another scene or perform game logic
    }
}
