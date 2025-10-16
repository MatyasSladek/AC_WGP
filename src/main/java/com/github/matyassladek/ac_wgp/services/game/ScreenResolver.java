package com.github.matyassladek.ac_wgp.services.game;

import com.github.matyassladek.ac_wgp.enums.FXMLFile;
import com.github.matyassladek.ac_wgp.model.Championship;
import com.github.matyassladek.ac_wgp.model.Game;

import java.util.logging.Logger;

/**
 * Service responsible for determining the appropriate screen to display
 * based on the current game state.
 */
public class ScreenResolver {

    private static final Logger log = Logger.getLogger(ScreenResolver.class.getName());

    /**
     * Determines the appropriate default screen based on game state.
     * This is used when loading a saved game or when no explicit screen is set.
     *
     * @param game The game instance
     * @return The appropriate FXML file path
     */
    public String resolveDefaultScreen(Game game) {
        if (game == null) {
            log.warning("Game is null, returning CREATE_DRIVER screen");
            return FXMLFile.CREATE_DRIVER.getFileName();
        }

        Championship championship = game.getCurrentChampionship();

        // No championship exists - show pre-season
        if (championship == null) {
            log.info("No championship found, resolving to PRE_SEASON screen");
            return FXMLFile.PRE_SEASON.getFileName();
        }

        // Championship exists and is complete - show pre-season for next season
        if (isChampionshipComplete(game, championship)) {
            log.info("Championship complete, resolving to PRE_SEASON screen");
            return FXMLFile.PRE_SEASON.getFileName();
        }

        // Active championship - show driver standings
        log.info("Active championship found, resolving to DRIVERS_STANDINGS screen");
        return FXMLFile.DRIVERS_STANDINGS.getFileName();
    }

    /**
     * Determines if the current championship is complete.
     *
     * @param game The game instance
     * @param championship The championship to check
     * @return true if all races are complete
     */
    private boolean isChampionshipComplete(Game game, Championship championship) {
        if (!game.hasCurrentCalendar()) {
            return true;
        }

        int totalRaces = game.getCurrentCalendar().size();
        int currentRound = championship.getCurrentRound();

        return currentRound >= totalRaces;
    }
}