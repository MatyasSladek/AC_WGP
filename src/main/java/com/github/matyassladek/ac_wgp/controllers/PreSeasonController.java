package com.github.matyassladek.ac_wgp.controllers;

import com.github.matyassladek.ac_wgp.enums.FXMLFile;
import com.github.matyassladek.ac_wgp.model.Team;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PreSeasonController extends ViewController {

    private static final Logger log = Logger.getLogger(PreSeasonController.class.getName());

    @FXML
    private Label seasonLabel;

    @FXML
    private Label playerTeamLabel;

    @FXML
    private TableView<TeamUpgradeInfo> upgradesTable;

    @FXML
    private TableColumn<TeamUpgradeInfo, String> teamColumn;

    @FXML
    private TableColumn<TeamUpgradeInfo, String> engineColumn;

    @FXML
    private TableColumn<TeamUpgradeInfo, String> chassisColumn;

    @FXML
    private TableColumn<TeamUpgradeInfo, String> factoryColumn;

    @FXML
    private TableColumn<TeamUpgradeInfo, String> totalPerfColumn;

    @FXML
    private Button continueButton;

    public PreSeasonController() {
        super(FXMLFile.CREATE_CALENDAR.getFileName());
    }

    @FXML
    public void initialize() {
        // Set up table columns
        teamColumn.setCellValueFactory(new PropertyValueFactory<>("teamName"));
        engineColumn.setCellValueFactory(new PropertyValueFactory<>("enginePerformance"));
        chassisColumn.setCellValueFactory(new PropertyValueFactory<>("chassisPerformance"));
        factoryColumn.setCellValueFactory(new PropertyValueFactory<>("factoryLevel"));
        totalPerfColumn.setCellValueFactory(new PropertyValueFactory<>("totalPerformance"));

        // Load team data after initialization
        javafx.application.Platform.runLater(this::loadTeamData);
    }

    public void loadTeamData() {
        if (game == null) {
            log.log(Level.WARNING, "Game object is null");
            return;
        }

        // Set season info
        int nextSeason = game.getCurrentSeason() + 1;
        seasonLabel.setText("Season " + nextSeason + " - Team Development");

        // Find player's team
        Team playerTeam = game.getTeams().stream()
                .filter(team -> team.getDriver1().equals(game.getPlayer()) ||
                        team.getDriver2().equals(game.getPlayer()))
                .findFirst()
                .orElse(null);

        if (playerTeam != null) {
            playerTeamLabel.setText("Your Team: " + playerTeam.getManufacture().name());
        }

        // Load team upgrade data
        List<TeamUpgradeInfo> upgradeInfos = new ArrayList<>();

        for (Team team : game.getTeams()) {
            TeamUpgradeInfo info = new TeamUpgradeInfo(
                    team.getManufacture().name(),
                    team.getEngine().getPerformance(),
                    team.getChassis().getPerformance(),
                    team.getFactoryLevel(),
                    team.getEngine().getPerformance() + team.getChassis().getPerformance(),
                    team.equals(playerTeam)
            );
            upgradeInfos.add(info);
        }

        // Sort by total performance (descending)
        upgradeInfos.sort((a, b) -> Integer.compare(
                b.getTotalPerformanceValue(),
                a.getTotalPerformanceValue()
        ));

        upgradesTable.setItems(FXCollections.observableArrayList(upgradeInfos));

        // Highlight player's team row
        upgradesTable.setRowFactory(tv -> new javafx.scene.control.TableRow<TeamUpgradeInfo>() {
            @Override
            protected void updateItem(TeamUpgradeInfo item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else if (item.isPlayerTeam()) {
                    setStyle("-fx-background-color: rgba(100, 200, 100, 0.3); -fx-font-weight: bold;");
                } else {
                    setStyle("");
                }
            }
        });
    }

    @FXML
    private void handleContinue() {
        log.log(Level.INFO, "Continuing to calendar creation");
        showNextScreen();
    }

    // Inner class to hold team upgrade information
    public static class TeamUpgradeInfo {
        private final String teamName;
        private final int enginePerfValue;
        private final int chassisPerfValue;
        private final int factoryLevelValue;
        private final int totalPerfValue;
        private final boolean playerTeam;

        public TeamUpgradeInfo(String teamName, int enginePerf, int chassisPerf,
                               int factoryLevel, int totalPerf, boolean playerTeam) {
            this.teamName = teamName;
            this.enginePerfValue = enginePerf;
            this.chassisPerfValue = chassisPerf;
            this.factoryLevelValue = factoryLevel;
            this.totalPerfValue = totalPerf;
            this.playerTeam = playerTeam;
        }

        public String getTeamName() {
            return teamName;
        }

        public String getEnginePerformance() {
            return String.valueOf(enginePerfValue);
        }

        public String getChassisPerformance() {
            return String.valueOf(chassisPerfValue);
        }

        public String getFactoryLevel() {
            return "Level " + factoryLevelValue;
        }

        public String getTotalPerformance() {
            return String.valueOf(totalPerfValue);
        }

        public int getTotalPerformanceValue() {
            return totalPerfValue;
        }

        public boolean isPlayerTeam() {
            return playerTeam;
        }
    }
}