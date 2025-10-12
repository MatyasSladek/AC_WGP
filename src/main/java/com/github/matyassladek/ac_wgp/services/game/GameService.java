package com.github.matyassladek.ac_wgp.services.game;

import com.github.matyassladek.ac_wgp.factory.ChampionshipFactory;
import com.github.matyassladek.ac_wgp.model.Championship;
import com.github.matyassladek.ac_wgp.model.Game;
import com.github.matyassladek.ac_wgp.model.Track;

import java.util.List;
import java.util.logging.Logger;

/**
 * Service for game-related business operations.
 * Handles operations that work with the Game model.
 */
public class GameService {

    private static final Logger log = Logger.getLogger(GameService.class.getName());

    private final ChampionshipFactory championshipFactory;

    public GameService() {
        this.championshipFactory = new ChampionshipFactory();
    }

    public GameService(ChampionshipFactory championshipFactory) {
        this.championshipFactory = championshipFactory;
    }

    /**
     * Sets the calendar for the current season.
     *
     * @param game The game instance
     * @param calendar List of tracks for the season
     */
    public void setCurrentSeasonCalendar(Game game, List<Track> calendar) {
        if (calendar == null || calendar.isEmpty()) {
            throw new IllegalArgumentException("Calendar cannot be null or empty");
        }

        game.setSeasonCalendar(game.getCurrentSeason(), calendar);
        log.info("Calendar set for season " + game.getCurrentSeason() + " with " + calendar.size() + " tracks");
    }

    /**
     * Initializes the championship for the current season.
     *
     * @param game The game instance
     * @return true if championship was initialized, false if calendar not set
     */
    public boolean initializeCurrentChampionship(Game game) {
        if (!game.hasCurrentCalendar()) {
            log.warning("Cannot initialize championship: No calendar set for current season");
            return false;
        }

        Championship championship = championshipFactory.createChampionship(
                game.getTeams(),
                game.getCurrentCalendar()
        );

        game.setCurrentChampionship(championship);
        log.info("Championship initialized for season " + game.getCurrentSeason());
        return true;
    }

    /**
     * Gets the current race track.
     *
     * @param game The game instance
     * @return Current Track or null if not available
     */
    public Track getCurrentRace(Game game) {
        Championship championship = game.getCurrentChampionship();
        if (championship == null) {
            log.warning("No active championship");
            return null;
        }

        List<Track> calendar = game.getCurrentCalendar();
        int raceIndex = championship.getCurrentRaceIndex();

        if (raceIndex < 0 || raceIndex >= calendar.size()) {
            log.warning("Invalid race index: " + raceIndex);
            return null;
        }

        return calendar.get(raceIndex);
    }

    /**
     * Checks if there are more races in the current season.
     *
     * @param game The game instance
     * @return true if more races are available
     */
    public boolean hasMoreRaces(Game game) {
        Championship championship = game.getCurrentChampionship();
        if (championship == null) {
            return false;
        }

        List<Track> calendar = game.getCurrentCalendar();
        return championship.getCurrentRaceIndex() < calendar.size() - 1;
    }
}