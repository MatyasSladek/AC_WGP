package com.github.matyassladek.ac_wgp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DriverStandingsController {

    @FXML
    private TableView<DriverStandings> standingsTable;

    @FXML
    private TableColumn<DriverStandings, Integer> positionColumn;

    @FXML
    private TableColumn<DriverStandings, String> driverColumn;

    @FXML
    private TableColumn<DriverStandings, String> teamColumn;

    @FXML
    private TableColumn<DriverStandings, Integer> pointsColumn;

    private ObservableList<DriverStandings> standingsData;

    // Initialize the table with sample data
    public void initialize() {
        // Initialize columns
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        driverColumn.setCellValueFactory(new PropertyValueFactory<>("driver"));
        teamColumn.setCellValueFactory(new PropertyValueFactory<>("team"));
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

        // Sample data
        standingsData = FXCollections.observableArrayList(
                new DriverStandings(1, "Driver1", "TeamA", 100),
                new DriverStandings(2, "Driver2", "TeamB", 98),
                new DriverStandings(3, "Driver3", "TeamC", 96),
                new DriverStandings(4, "Driver4", "TeamD", 94),
                new DriverStandings(5, "Driver5", "TeamE", 92),
                new DriverStandings(6, "Driver6", "TeamF", 90),
                new DriverStandings(7, "Driver7", "TeamG", 88),
                new DriverStandings(8, "Driver8", "TeamH", 86),
                new DriverStandings(9, "Driver9", "TeamI", 84),
                new DriverStandings(10, "Driver10", "TeamJ", 82),
                new DriverStandings(11, "Driver11", "TeamK", 80),
                new DriverStandings(12, "Driver12", "TeamL", 78),
                new DriverStandings(13, "Driver13", "TeamM", 76),
                new DriverStandings(14, "Driver14", "TeamN", 74),
                new DriverStandings(15, "Driver15", "TeamO", 72),
                new DriverStandings(16, "Driver16", "TeamP", 70),
                new DriverStandings(17, "Driver17", "TeamQ", 68),
                new DriverStandings(18, "Driver18", "TeamR", 66),
                new DriverStandings(19, "Driver19", "TeamS", 64),
                new DriverStandings(20, "Driver20", "TeamT", 62),
                new DriverStandings(21, "Driver21", "TeamU", 60),
                new DriverStandings(22, "Driver22", "TeamV", 58),
                new DriverStandings(23, "Driver23", "TeamW", 56),
                new DriverStandings(24, "Driver24", "TeamX", 54)
        );

        // Set the table data
        standingsTable.setItems(standingsData);
    }

    // Handle the button click to continue
    @FXML
    private void onContinueButtonClick() {
        // Action to perform when the "Continue" button is clicked
        System.out.println("Continue button clicked!");
    }
}
