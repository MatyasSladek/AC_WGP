package com.github.matyassladek.ac_wgp.controllers;

import com.github.matyassladek.ac_wgp.enums.FXMLFile;
import com.github.matyassladek.ac_wgp.model.Game;
import com.github.matyassladek.ac_wgp.model.Championship.DriverSlot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.logging.Logger;

public class DriverStandingsController extends ViewController {

    private static final Logger log = Logger.getLogger(DriverStandingsController.class.getName());

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

    @Override
    public void setGame(Game game) {
        this.game = game;
        loadDriverStandings();
    }

    public DriverStandingsController() {
        super(FXMLFile.CONSTRUCTORS_STANDINGS.getFileName());
    }

    public void initialize() {
        positionColumn.setCellValueFactory(data -> {
            int position = standingsTable.getItems().indexOf(data.getValue()) + 1;
            return new javafx.beans.property.SimpleIntegerProperty(position).asObject();
        });
        driverColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDriver().getName()));
        teamColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTeam().getManufacture().getNameShort()));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
    }

    private void loadDriverStandings() {
        if (game != null) {
            ObservableList<DriverSlot> standings = FXCollections.observableArrayList(game.getCurrentChampionship().getDriversStandings());
            standingsTable.setItems(standings);
        }
    }

    @FXML
    private void onContinueButtonClick() throws IOException {
        log.info("Continue button clicked!");
        showNextScreen();
    }
}
