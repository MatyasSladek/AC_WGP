package com.github.matyassladek.ac_wgp.controllers;

import com.github.matyassladek.ac_wgp.enums.FXMLFile;
import com.github.matyassladek.ac_wgp.model.Game;
import com.github.matyassladek.ac_wgp.helpers.UIHelper;
import com.github.matyassladek.ac_wgp.helpers.ValidationHelper;
import com.github.matyassladek.ac_wgp.model.Driver;
import com.github.matyassladek.ac_wgp.services.ChampionshipService;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.logging.Logger;
import java.io.IOException;
import java.util.List;

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

    public EventResultsController() {
        super(FXMLFile.DRIVERS_STANDINGS.getFileName());
    }

    private List<ChoiceBox<String>> choiceBoxes;
    private ChampionshipService championshipService;

    @Override
    public void setGame(Game game) {
        this.game = game;
        this.championshipService = new ChampionshipService(game.getCurrentChampionship());
        setDrivers();
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

    private void setDrivers() {
        List<Driver> drivers = game.getCurrentChampionship().getDrivers();
        UIHelper.populateChoiceBoxes(choiceBoxes, drivers);
    }

    @FXML
    private void onSubmitButtonClick() throws IOException {
        log.info("Submit button clicked");

        if (!ValidationHelper.validateUniqueSelections(choiceBoxes)) {
            UIHelper.showAlert("Error", "Duplicate driver selected!");
            return;
        }

        List<String> raceResult = UIHelper.getSelectedValues(choiceBoxes);
        championshipService.updateChampionshipPoints(raceResult);

        log.info(() -> championshipService.getChampionship().getDriversStandings().toString());
        log.info(() -> championshipService.getChampionship().getConstructorsStandings().toString());

        showNextScreen();
    }
}
