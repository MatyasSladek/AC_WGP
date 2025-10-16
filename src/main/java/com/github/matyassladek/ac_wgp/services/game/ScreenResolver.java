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
     * This is used when loading a saved game to show the most contextually relevant screen.
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

        // No championship and no calendar = need to set up first season
        if (championship == null && !game.hasCurrentCalendar()) {
            log.info("No championship or calendar found, resolving to PRE_SEASON screen");
            return FXMLFile.PRE_SEASON.getFileName();
        }

        // No championship but calendar exists = between seasons (calendar already set for next season)
        if (championship == null && game.hasCurrentCalendar()) {
            log.info("Calendar exists but no championship, resolving to PRE_SEASON screen (next season ready)");
            return FXMLFile.PRE_SEASON.getFileName();
        }

        // Championship exists and is complete = show constructor standings for season end
        if (isChampionshipComplete(game, championship)) {
            log.info("Championship complete, resolving to CONSTRUCTORS_STANDINGS screen");
            return FXMLFile.CONSTRUCTORS_STANDINGS.getFileName();
        }

        // Active championship in progress = show driver standings
        log.info("Active championship in progress, resolving to DRIVERS_STANDINGS screen");
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