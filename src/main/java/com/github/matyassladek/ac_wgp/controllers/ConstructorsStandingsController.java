package com.github.matyassladek.ac_wgp.controllers;

import com.github.matyassladek.ac_wgp.enums.FXMLFile;
import com.github.matyassladek.ac_wgp.model.Game;
import com.github.matyassladek.ac_wgp.model.Championship;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.logging.Logger;

public class ConstructorsStandingsController extends ViewController {

    private static final Logger log = Logger.getLogger(ConstructorsStandingsController.class.getName());

    @FXML
    private TableView<Championship.TeamSlot> standingsTable;

    @FXML
    private TableColumn<Championship.TeamSlot, Integer> positionColumn;

    @FXML
    private TableColumn<Championship.TeamSlot, String> teamColumn;

    @FXML
    private TableColumn<Championship.TeamSlot, Integer> pointsColumn;

    public ConstructorsStandingsController() {
        super(FXMLFile.NEXT_EVENT.getFileName());
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
        loadConstructorsStandings();
    }

    @FXML
    private void onSubmitButtonClick() throws IOException {
        log.info("Continue button clicked!");
        if (game.getCurrentChampionship().getCurrentRound() >= game.getCurrentChampionship().getCalendar().size()) {
            if (game.newSeason()) {
                setNextScreen(FXMLFile.PRE_SEASON.getFileName());
                log.info("New season started");
            } else {
                setNextScreen(FXMLFile.CAREER_END.getFileName());
            }
        }
        showNextScreen();
    }

    @FXML
    private void initialize() {
        positionColumn.setCellValueFactory(data -> {
            int position = standingsTable.getItems().indexOf(data.getValue()) + 1;
            return new javafx.beans.property.SimpleIntegerProperty(position).asObject();
        });
        teamColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTeam().getManufacture().getNameShort()));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
    }

    private void loadConstructorsStandings() {
        if (game != null) {
            ObservableList<Championship.TeamSlot> standings = FXCollections.observableArrayList(game.getCurrentChampionship().getConstructorsStandings());
            standingsTable.setItems(standings);
        }
    }
}
