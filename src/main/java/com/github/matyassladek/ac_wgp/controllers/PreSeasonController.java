package com.github.matyassladek.ac_wgp.controllers;

import com.github.matyassladek.ac_wgp.enums.FXMLFile;
import com.github.matyassladek.ac_wgp.model.Team;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PreSeasonController extends ViewController {

    private static final Logger log = Logger.getLogger(PreSeasonController.class.getName());

    @FXML
    private Label seasonLabel;

    @FXML
    private VBox teamsContainer;

    public PreSeasonController() {
        super(FXMLFile.CREATE_CALENDAR.getFileName());
    }

    @FXML
    public void initialize() {
        // Don't load data here - wait for game to be set
    }

    @Override
    public void setGame(com.github.matyassladek.ac_wgp.model.Game game) {
        super.setGame(game);
        // Load data immediately after game is set
        loadTeamData();
    }

    public void loadTeamData() {
        if (game == null) {
            log.log(Level.WARNING, "Game object is null");
            return;
        }

        // Set season info
        int nextSeason = game.getCurrentSeason() + 1;
        seasonLabel.setText("Season " + nextSeason + " - Team Development");

        // Find player's team by comparing first and last names
        Team playerTeam = game.getTeams().stream()
                .filter(team -> isPlayerDriver(team.getDriver1(), game.getPlayer()) ||
                        isPlayerDriver(team.getDriver2(), game.getPlayer()))
                .findFirst()
                .orElse(null);

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

        // Sort by total performance (ascending)
        upgradeInfos.sort((a, b) -> Integer.compare(
                a.getTotalPerformanceValue(),
                b.getTotalPerformanceValue()
        ));

        // Clear and populate teams container
        teamsContainer.getChildren().clear();
        for (TeamUpgradeInfo info : upgradeInfos) {
            teamsContainer.getChildren().add(createTeamRow(info));
        }
    }

    private HBox createTeamRow(TeamUpgradeInfo info) {
        HBox row = new HBox(20);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(12, 20, 12, 20));
        row.setStyle(
                "-fx-background-color: " + (info.isPlayerTeam() ? "rgba(100, 200, 100, 0.2)" : "rgba(255, 255, 255, 0.05)") + ";" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-color: " + (info.isPlayerTeam() ? "#64c864" : "rgba(255, 255, 255, 0.1)") + ";" +
                        "-fx-border-width: " + (info.isPlayerTeam() ? "2" : "1") + ";" +
                        "-fx-border-radius: 8;"
        );

        // Team name (fixed width)
        Label nameLabel = new Label(info.getTeamName() + (info.isPlayerTeam() ? " ★" : ""));
        nameLabel.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;" +
                        "-fx-min-width: 120px;"
        );

        // Car image
        ImageView carImage = new ImageView();
        carImage.setFitHeight(60);
        carImage.setFitWidth(120);
        carImage.setPreserveRatio(true);
        try {
            String imagePath = "/com/github/matyassladek/ac_wgp/enum/" + info.getTeamName() + ".png";
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            carImage.setImage(image);
        } catch (Exception e) {
            log.log(Level.WARNING, "Failed to load car image: " + info.getTeamName(), e);
            // If image fails to load, show a placeholder label
            carImage.setImage(null);
        }

        // Restrictor stat
        VBox restrictorBox = createPenaltyColumn("Restrictor", 100 - info.enginePerfValue, 100);

        // Ballast stat
        VBox ballastBox = createPenaltyColumn("Ballast", 200 - info.chassisPerfValue, 200);

        // Total Performance stat
        VBox totalBox = createStatColumn("Total Development", 294 - info.totalPerfValue, 294);

        // Factory level
        Label factoryLabel = new Label(info.getFactoryLevel());
        factoryLabel.setStyle(
                "-fx-text-fill: #aaaaaa;" +
                        "-fx-font-size: 14px;" +
                        "-fx-min-width: 80px;"
        );

        row.getChildren().addAll(nameLabel, carImage, restrictorBox, ballastBox, totalBox, factoryLabel);
        HBox.setHgrow(restrictorBox, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(ballastBox, javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(totalBox, javafx.scene.layout.Priority.ALWAYS);

        return row;
    }

    private VBox createPenaltyColumn(String label, int value, int maxValue) {
        VBox column = new VBox(5);
        column.setAlignment(Pos.CENTER_LEFT);
        column.setPrefWidth(180);

        // Label
        Label nameLabel = new Label(label);
        nameLabel.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 12px;");

        // Value and progress bar container
        HBox valueBarBox = new HBox(8);
        valueBarBox.setAlignment(Pos.CENTER_LEFT);

        Label valueLabel = new Label(String.valueOf(maxValue - value));
        valueLabel.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-min-width: 35px;"
        );

        // Progress bar
        ProgressBar bar = new ProgressBar((double) value / maxValue);
        bar.setPrefHeight(16);
        bar.setPrefWidth(100);
        bar.setStyle(
                "-fx-accent: " + getColorForValue(value, maxValue) + ";" +
                        "-fx-background-color: rgba(255, 255, 255, 0.1);"
        );

        valueBarBox.getChildren().addAll(valueLabel, bar);
        column.getChildren().addAll(nameLabel, valueBarBox);

        return column;
    }

    private VBox createStatColumn(String label, int value, int maxValue) {
        VBox column = new VBox(5);
        column.setAlignment(Pos.CENTER_LEFT);
        column.setPrefWidth(180);

        // Label
        Label nameLabel = new Label(label);
        nameLabel.setStyle("-fx-text-fill: #cccccc; -fx-font-size: 12px;");

        // Value and progress bar container
        HBox valueBarBox = new HBox(8);
        valueBarBox.setAlignment(Pos.CENTER_LEFT);

        Label valueLabel = new Label(String.valueOf(value));
        valueLabel.setStyle(
                "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-min-width: 35px;"
        );

        // Progress bar
        ProgressBar bar = new ProgressBar((double) value / maxValue);
        bar.setPrefHeight(16);
        bar.setPrefWidth(100);
        bar.setStyle(
                "-fx-accent: " + getColorForValue(value, maxValue) + ";" +
                        "-fx-background-color: rgba(255, 255, 255, 0.1);"
        );

        valueBarBox.getChildren().addAll(valueLabel, bar);
        column.getChildren().addAll(nameLabel, valueBarBox);

        return column;
    }

    private String getColorForValue(int value, int maxValue) {
        double ratio = (double) value / maxValue;
        if (ratio > 0.8) return "#4caf50"; // Green
        if (ratio > 0.6) return "#8bc34a"; // Light green
        if (ratio > 0.4) return "#ffc107"; // Yellow
        if (ratio > 0.2) return "#ff9800"; // Orange
        return "#f44336"; // Red
    }

    @FXML
    private void handleContinue() {
        log.log(Level.INFO, "Continuing to calendar creation");
        showNextScreen();
    }

    // Helper method to compare drivers by name instead of object equality
    private boolean isPlayerDriver(com.github.matyassladek.ac_wgp.model.Driver driver,
                                   com.github.matyassladek.ac_wgp.model.Driver player) {
        if (driver == null || player == null) {
            return false;
        }
        return driver.getFirstName().equals(player.getFirstName()) &&
                driver.getLastName().equals(player.getLastName()) &&
                driver.getCountry().equals(player.getCountry());
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
            return "Factory Level " + factoryLevelValue;
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