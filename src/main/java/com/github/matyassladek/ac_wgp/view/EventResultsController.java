package com.github.matyassladek.ac_wgp.view;

import com.github.matyassladek.ac_wgp.controller.Game;
import com.github.matyassladek.ac_wgp.model.Championship;
import com.github.matyassladek.ac_wgp.model.Driver;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventResultsController extends ViewController {

    private static final Logger log = Logger.getLogger(EventResultsController.class.getName());

    @FXML private ChoiceBox<String> choiceBoxPosition1;
    @FXML private ChoiceBox<String> choiceBoxPosition2;
    @FXML private ChoiceBox<String> choiceBoxPosition3;
    @FXML private ChoiceBox<String> choiceBoxPosition4;
    @FXML private ChoiceBox<String> choiceBoxPosition5;
    @FXML private ChoiceBox<String> choiceBoxPosition6;
    @FXML private ChoiceBox<String> choiceBoxPosition7;
    @FXML private ChoiceBox<String> choiceBoxPosition8;
    @FXML private ChoiceBox<String> choiceBoxPosition9;
    @FXML private ChoiceBox<String> choiceBoxPosition10;
    @FXML private ChoiceBox<String> choiceBoxPosition11;
    @FXML private ChoiceBox<String> choiceBoxPosition12;
    @FXML private ChoiceBox<String> choiceBoxPosition13;
    @FXML private ChoiceBox<String> choiceBoxPosition14;
    @FXML private ChoiceBox<String> choiceBoxPosition15;
    @FXML private ChoiceBox<String> choiceBoxPosition16;
    @FXML private ChoiceBox<String> choiceBoxPosition17;
    @FXML private ChoiceBox<String> choiceBoxPosition18;
    @FXML private ChoiceBox<String> choiceBoxPosition19;
    @FXML private ChoiceBox<String> choiceBoxPosition20;
    @FXML private ChoiceBox<String> choiceBoxPosition21;
    @FXML private ChoiceBox<String> choiceBoxPosition22;
    @FXML private ChoiceBox<String> choiceBoxPosition23;
    @FXML private ChoiceBox<String> choiceBoxPosition24;

    @Override
    public void setGame(Game game) {
        this.game = game;
        setDrivers();
    }

    private List<ChoiceBox<String>> choiceBoxes;

    public EventResultsController() {
        super(FXMLFile.EVENT_RESULTS.getFileName() , FXMLFile.DRIVERS_STANDINGS.getFileName());
    }

    public void initialize() {
        choiceBoxes = List.of(
                choiceBoxPosition1, choiceBoxPosition2, choiceBoxPosition3, choiceBoxPosition4,
                choiceBoxPosition5, choiceBoxPosition6, choiceBoxPosition7, choiceBoxPosition8,
                choiceBoxPosition9, choiceBoxPosition10, choiceBoxPosition11, choiceBoxPosition12,
                choiceBoxPosition13, choiceBoxPosition14, choiceBoxPosition15, choiceBoxPosition16,
                choiceBoxPosition17, choiceBoxPosition18, choiceBoxPosition19, choiceBoxPosition20,
                choiceBoxPosition21, choiceBoxPosition22, choiceBoxPosition23, choiceBoxPosition24
        );
    }

    // Set drivers for the race (based on existing drivers in the championship)
    public void setDrivers() {
        List<Driver> drivers = game.getCurrentChampionship().getDrivers();
        for (int i = 0; i < choiceBoxes.size(); i++) {
            ChoiceBox<String> choiceBox = choiceBoxes.get(i);
            choiceBox.getItems().clear();
            for (Driver driver : drivers) {
                choiceBox.getItems().add(driver.getName());
            }
            if (i < drivers.size()) {
                choiceBox.setValue(drivers.get(i).getName());
            }
        }
    }

    @FXML
    private void onSubmitButtonClick() throws IOException {
        log.info("EventResultsController submit button clicked");
        if (!validateDriverSelections()) {
            showAlert();
            return;
        }
        game.setCurrentChampionship(updateChampionship(game.getCurrentChampionship()));
        if (log.isLoggable(Level.INFO)) {
            log.info(() -> game.getCurrentChampionship().getDriversStandings().toString());
            log.info(() -> game.getCurrentChampionship().getConstructorsStandings().toString());
        }
        showNextScreen();
    }

    private boolean validateDriverSelections() {
        Set<String> selectedDrivers = new HashSet<>();
        for (ChoiceBox<String> choiceBox : choiceBoxes) {
            String driver = choiceBox.getValue();
            if (driver != null && !selectedDrivers.add(driver)) {
                return false;
            }
        }
        return true;
    }

    // Helper method to show alerts
    private void showAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Duplicate driver selected!");
        alert.showAndWait();
    }

    private Championship updateChampionship(Championship currentChampionship) {
        List<Championship.DriverSlot> driversStandings = currentChampionship.getDriversStandings();
        List<Championship.TeamSlot> constructorsStandings = currentChampionship.getConstructorsStandings();
        List<String> raceResult = getSelectedValues();

        for (int i = 0; i < 10; i++) {
            updateDriverAndConstructorPoints(raceResult.get(i), i, currentChampionship, driversStandings, constructorsStandings);
        }

        driversStandings.sort(Comparator.comparingInt(Championship.DriverSlot::getPoints).reversed());
        constructorsStandings.sort(Comparator.comparingInt(Championship.TeamSlot::getPoints).reversed());

        return currentChampionship;
    }

    private void updateDriverAndConstructorPoints(String driverName, int raceIndex, Championship currentChampionship,
                                                  List<Championship.DriverSlot> driversStandings, List<Championship.TeamSlot> constructorsStandings) {
        Championship.DriverSlot driverSlot = findDriverSlot(driverName, driversStandings);
        if (driverSlot == null) return;

        driverSlot.setPoints(driverSlot.getPoints() + currentChampionship.getScoring().get(raceIndex));

        Championship.TeamSlot teamSlot = findTeamSlotForDriver(driverSlot, constructorsStandings);
        if (teamSlot != null) {
            teamSlot.setPoints(teamSlot.getPoints() + currentChampionship.getScoring().get(raceIndex));
        }
    }

    private Championship.DriverSlot findDriverSlot(String driverName, List<Championship.DriverSlot> driversStandings) {
        for (Championship.DriverSlot driverSlot : driversStandings) {
            if (driverSlot.getDriver().getName().equals(driverName)) {
                return driverSlot;
            }
        }
        return null;
    }

    private Championship.TeamSlot findTeamSlotForDriver(Championship.DriverSlot driverSlot, List<Championship.TeamSlot> constructorsStandings) {
        for (Championship.TeamSlot teamSlot : constructorsStandings) {
            if (teamSlot.getTeam().getDriver1() == driverSlot.getDriver() || teamSlot.getTeam().getDriver2() == driverSlot.getDriver()) {
                return teamSlot;
            }
        }
        return null;
    }

    private List<String> getSelectedValues() {
        return choiceBoxes.stream()
                .map(ChoiceBox::getValue)
                .toList();
    }

}
