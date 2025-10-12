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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Controller for displaying constructor standings.
 * Shows team rankings and points in a table view.
 */
public class ConstructorsStandingsController extends ViewController {

    private static final Logger log = Logger.getLogger(ConstructorsStandingsController.class.getName());

    @FXML private TableView<Championship.TeamSlot> standingsTable;
    @FXML private TableColumn<Championship.TeamSlot, Integer> positionColumn;
    @FXML private TableColumn<Championship.TeamSlot, String> teamColumn;
    @FXML private TableColumn<Championship.TeamSlot, Integer> pointsColumn;

    public ConstructorsStandingsController() {
        super(FXMLFile.NEXT_EVENT.getFileName());
    }

    @FXML
    private void initialize() {
        setupTableColumns();
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
        loadConstructorsStandings();
    }

    @FXML
    private void onSubmitButtonClick() throws IOException {
        log.info("Continue button clicked");

        String nextScreen = determineNextScreen();
        setNextScreen(nextScreen);
        showNextScreen();
    }

    private void setupTableColumns() {
        positionColumn.setCellValueFactory(data -> {
            int position = standingsTable.getItems().indexOf(data.getValue()) + 1;
            return new SimpleIntegerProperty(position).asObject();
        });

        teamColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getTeam()
                        .getManufacture().getNameShort()));

        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
    }

    private void loadConstructorsStandings() {
        if (game != null && game.getCurrentChampionship() != null) {
            ObservableList<Championship.TeamSlot> standings =
                    FXCollections.observableArrayList(
                            game.getCurrentChampionship().getConstructorsStandings());
            standingsTable.setItems(standings);
        }
    }

    private String determineNextScreen() {
        if (isSeasonComplete()) {
            if (hasMoreSeasons()) {
                // Advance to next season using GameManager
                boolean advanced = gameManager.advanceToNextSeason(game);
                if (advanced) {
                    log.info("Advanced to season " + game.getCurrentSeason());
                    return FXMLFile.PRE_SEASON.getFileName();
                } else {
                    log.warning("Failed to advance to next season");
                    return FXMLFile.CAREER_END.getFileName();
                }
            } else {
                log.info("Career complete - no more seasons");
                return FXMLFile.CAREER_END.getFileName();
            }
        }

        return FXMLFile.NEXT_EVENT.getFileName();
    }

    private boolean isSeasonComplete() {
        Championship championship = game.getCurrentChampionship();
        if (championship == null) {
            return true;
        }

        return championship.getCurrentRound() >= championship.getCalendar().size();
    }

    private boolean hasMoreSeasons() {
        return gameManager.hasMoreSeasons(game);
    }
}