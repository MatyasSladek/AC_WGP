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
        log.info("Continue button clicked from Constructor Standings");

        DetermineNextScreenResult result = determineNextScreen();
        setNextScreen(result.screenFile);

        // If we advanced to next season, advanceToNextSeason() already saved
        // Otherwise, we need to save before navigating
        showNextScreen(!result.alreadySaved);
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

    private DetermineNextScreenResult determineNextScreen() {
        if (isSeasonComplete()) {
            if (hasMoreSeasons()) {
                // Advance to next season (this will save the game automatically)
                boolean advanced = gameManager.advanceToNextSeason(game);
                if (advanced) {
                    log.info("Advanced to season " + game.getCurrentSeason() +
                            " (Display: Season " + (game.getCurrentSeason() + 1) + ")");
                    // Return PRE_SEASON with alreadySaved=true because advanceToNextSeason saves
                    return new DetermineNextScreenResult(FXMLFile.PRE_SEASON.getFileName(), true);
                } else {
                    log.warning("Failed to advance to next season");
                    return new DetermineNextScreenResult(FXMLFile.CAREER_END.getFileName(), false);
                }
            } else {
                log.info("Career complete - no more seasons");
                return new DetermineNextScreenResult(FXMLFile.CAREER_END.getFileName(), false);
            }
        }

        // Season not complete, continue with next event
        return new DetermineNextScreenResult(FXMLFile.NEXT_EVENT.getFileName(), false);
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

    /**
     * Helper class to track whether game was already saved during screen determination.
     */
    private static class DetermineNextScreenResult {
        final String screenFile;
        final boolean alreadySaved;

        DetermineNextScreenResult(String screenFile, boolean alreadySaved) {
            this.screenFile = screenFile;
            this.alreadySaved = alreadySaved;
        }
    }
}