package com.github.matyassladek.ac_wgp.services.game;

import com.github.matyassladek.ac_wgp.model.Game;
import com.github.matyassladek.ac_wgp.model.Track;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Facade for game management operations.
 * Coordinates between persistence, game logic, and season progression.
 * Provides a unified interface for controllers to interact with game services.
 */
public class GameManager {

    private static final Logger log = Logger.getLogger(GameManager.class.getName());

    private final GamePersistenceService persistenceService;
    private final GameService gameService;
    private final SeasonProgressionService seasonProgressionService;
    private final ScreenResolver screenResolver;

    public GameManager() {
        this(new GamePersistenceService(), new GameService(),
                new SeasonProgressionService(), new ScreenResolver());
    }

    public GameManager(GamePersistenceService persistenceService,
                       GameService gameService,
                       SeasonProgressionService seasonProgressionService,
                       ScreenResolver screenResolver) {
        this.persistenceService = persistenceService;
        this.gameService = gameService;
        this.seasonProgressionService = seasonProgressionService;
        this.screenResolver = screenResolver;
    }

    /**
     * Saves the game state to persistent storage.
     * If no screen is explicitly set, resolves the appropriate default screen.
     *
     * @param game The game to save
     */
    public void saveGame(Game game) {
        try {
            // Resolve default screen if not set
            if (game.getFxmlScreen() == null || game.getFxmlScreen().isEmpty()) {
                String defaultScreen = screenResolver.resolveDefaultScreen(game);
                game.setFxmlScreen(defaultScreen);
                log.info("Resolved default screen: " + defaultScreen);
            }

            persistenceService.saveGame(game);
            log.info("Game saved successfully");
        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed to save game", e);
            throw new RuntimeException("Failed to save game", e);
        }
    }

    /**
     * Loads the game state from persistent storage.
     * Always resolves the appropriate screen based on current game state.
     *
     * @return The loaded game
     * @throws IOException if game cannot be loaded
     */
    public Game loadGame() throws IOException {
        log.info("Loading game...");
        Game game = persistenceService.loadGame();

        // Always resolve screen based on current game state
        // This ensures we show the correct screen even if the save is inconsistent
        String resolvedScreen = screenResolver.resolveDefaultScreen(game);
        game.setFxmlScreen(resolvedScreen);
        log.info("Resolved screen for loaded game: " + resolvedScreen);

        log.info("Game loaded successfully");
        return game;
    }

    /**
     * Checks if a saved game exists.
     *
     * @return true if a save file exists
     */
    public boolean gameSaveExists() {
        return persistenceService.gameSaveExists();
    }

    /**
     * Deletes the current save file.
     *
     * @return true if file was deleted or didn't exist
     */
    public boolean deleteSaveFile() {
        return persistenceService.deleteSaveFile();
    }

    /**
     * Sets the calendar for the current season and initializes the championship.
     *
     * @param game The game instance
     * @param calendar List of tracks for the season
     * @return true if successfully set and initialized
     */
    public boolean setCurrentSeasonCalendar(Game game, List<Track> calendar) {
        try {
            gameService.setCurrentSeasonCalendar(game, calendar);
            boolean initialized = gameService.initializeCurrentChampionship(game);

            if (initialized) {
                log.info("Calendar set and championship initialized for season " + game.getCurrentSeason());
            }

            return initialized;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to set calendar and initialize championship", e);
            return false;
        }
    }

    /**
     * Initializes the championship for the current season.
     * Must be called after a calendar has been set.
     *
     * @param game The game instance
     * @return true if successfully initialized
     */
    public boolean initializeChampionship(Game game) {
        boolean initialized = gameService.initializeCurrentChampionship(game);

        if (initialized) {
            log.info("Championship initialized for season " + game.getCurrentSeason());
        } else {
            log.warning("Failed to initialize championship - calendar may not be set");
        }

        return initialized;
    }

    /**
     * Advances the game to the next season.
     * Processes end-of-season standings, upgrades teams, and prepares for new season.
     * The championship is cleared - caller must set calendar and initialize new championship.
     * Automatically saves the game after successful advancement.
     *
     * @param game The game instance
     * @return true if successfully advanced to next season, false if no more seasons
     */
    public boolean advanceToNextSeason(Game game) {
        log.info("Attempting to advance to next season...");

        boolean advanced = seasonProgressionService.advanceToNextSeason(game);

        if (advanced) {
            log.info("Advanced to season " + game.getCurrentSeason());
            // Championship already cleared in seasonProgressionService
            // Screen will be resolved to PRE_SEASON automatically during save
            saveGame(game);
        } else {
            log.info("Cannot advance - no more seasons available");
        }

        return advanced;
    }

    /**
     * Gets the current race track.
     *
     * @param game The game instance
     * @return Current Track or null if not available
     */
    public Track getCurrentRace(Game game) {
        Track track = gameService.getCurrentRace(game);

        if (track != null) {
            log.fine("Current race: " + track.getName());
        } else {
            log.warning("No current race available");
        }

        return track;
    }

    /**
     * Checks if there are more races in the current season.
     *
     * @param game The game instance
     * @return true if more races are available
     */
    public boolean hasMoreRaces(Game game) {
        return gameService.hasMoreRaces(game);
    }

    /**
     * Checks if the current season has a calendar set.
     *
     * @param game The game instance
     * @return true if calendar exists
     */
    public boolean hasCurrentCalendar(Game game) {
        return game.hasCurrentCalendar();
    }

    /**
     * Checks if there are more seasons available.
     *
     * @param game The game instance
     * @return true if more seasons exist
     */
    public boolean hasMoreSeasons(Game game) {
        return game.hasMoreSeasons();
    }

    /**
     * Gets the total number of seasons in the game.
     *
     * @param game The game instance
     * @return number of seasons
     */
    public int getTotalSeasons(Game game) {
        return game.getTotalSeasons();
    }

    /**
     * Gets the current season number.
     *
     * @param game The game instance
     * @return current season index
     */
    public int getCurrentSeason(Game game) {
        return game.getCurrentSeason();
    }

    /**
     * Gets the calendar for the current season.
     *
     * @param game The game instance
     * @return List of tracks for the current season
     */
    public List<Track> getCurrentCalendar(Game game) {
        return game.getCurrentCalendar();
    }
}