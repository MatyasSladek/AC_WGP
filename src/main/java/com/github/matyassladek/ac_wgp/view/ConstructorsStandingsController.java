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

public class ConstructorsStandingsController extends ViewController {

    // FXML references
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

    // This method is called when the "CONTINUE" button is clicked
    @FXML
    private void onSubmitButtonClick() {
        // Example action on button click
        System.out.println("Continue button clicked!");
        // You can perform other actions here like navigating to another screen or saving data
    }

    // This method is called when the controller is initialized
    @FXML
    private void initialize() {

        // Set up column bindings
        positionColumn.setCellValueFactory(data -> {
            int position = standingsTable.getItems().indexOf(data.getValue()) + 1;
            return new javafx.beans.property.SimpleIntegerProperty(position).asObject();
        });
        teamColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTeam().getManufacture().getNameShort()));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

//        // Initialize the table columns with the appropriate property value factories
//        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
//        teamColumn.setCellValueFactory(new PropertyValueFactory<>("team"));
//        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
//
//        // Populate the table with 12 teams (example data)
//        standingsTable.getItems().addAll(
//                new ConstructorStandings(1, "Team A", 300),
//                new ConstructorStandings(2, "Team B", 290),
//                new ConstructorStandings(3, "Team C", 280),
//                new ConstructorStandings(4, "Team D", 270),
//                new ConstructorStandings(5, "Team E", 260),
//                new ConstructorStandings(6, "Team F", 250),
//                new ConstructorStandings(7, "Team G", 240),
//                new ConstructorStandings(8, "Team H", 230),
//                new ConstructorStandings(9, "Team I", 220),
//                new ConstructorStandings(10, "Team J", 210),
//                new ConstructorStandings(11, "Team K", 200),
//                new ConstructorStandings(12, "Team L", 190)
//        );
    }

    private void loadConstructorsStandings() {
        if (game != null) {
            // Get the driversStandings list from the Championship object
            ObservableList<Championship.TeamSlot> standings = FXCollections.observableArrayList(game.getCurrentChampionship().getConstructorsStandings());
            standingsTable.setItems(standings);
        }
    }
}
