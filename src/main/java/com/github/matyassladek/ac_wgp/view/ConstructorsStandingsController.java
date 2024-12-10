package com.github.matyassladek.ac_wgp.view;

import com.github.matyassladek.ac_wgp.controller.Game;
import com.github.matyassladek.ac_wgp.model.Championship;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class ConstructorsStandingsController extends ViewController {

    @FXML
    private TableView<Championship.TeamSlot> standingsTable;

    @FXML
    private TableColumn<Championship.TeamSlot, Integer> positionColumn;

    @FXML
    private TableColumn<Championship.TeamSlot, String> teamColumn;

    @FXML
    private TableColumn<Championship.TeamSlot, Integer> pointsColumn;

    @FXML
    private Button continueButton;

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
        System.out.println("Continue button clicked!");
        game.getCurrentChampionship().setCurrentRound((1 + game.getCurrentChampionship().getCurrentRound()));
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
            // Get the driversStandings list from the Championship object
            ObservableList<Championship.TeamSlot> standings = FXCollections.observableArrayList(game.getCurrentChampionship().getConstructorsStandings());
            standingsTable.setItems(standings);
        }
    }
}
