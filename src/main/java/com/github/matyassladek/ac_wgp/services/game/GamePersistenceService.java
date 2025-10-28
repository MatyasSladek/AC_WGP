package com.github.matyassladek.ac_wgp.services.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.matyassladek.ac_wgp.model.Championship;
import com.github.matyassladek.ac_wgp.model.Game;
import com.github.matyassladek.ac_wgp.model.Team;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service responsible for game state persistence.
 * Handles saving and loading game data from JSON files.
 */
public class GamePersistenceService {

    private static final Logger log = Logger.getLogger(GamePersistenceService.class.getName());
    private static final String RESOURCE_FILE_PATH = "save/game_save.json";
    private static final String EXTERNAL_FILE_PATH = "game_save.json";

    private final ObjectMapper objectMapper;

    public GamePersistenceService() {
        this.objectMapper = new ObjectMapper();
    }

    public GamePersistenceService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Saves the game state to an external JSON file.
     *
     * @param game The game to save
     * @throws IOException if save fails
     */
    public void saveGame(Game game) throws IOException {
        if (game == null) {
            throw new IllegalArgumentException("Game cannot be null");
        }

        // Log what we're about to save
        log.info("=== SAVING GAME ===");
        log.info("Current season (internal): " + game.getCurrentSeason());
        log.info("Display season: " + (game.getCurrentSeason() + 1));
        log.info("Player: " + game.getPlayer().getName());

        // Log a few team states
        for (int i = 0; i < Math.min(3, game.getTeams().size()); i++) {
            Team team = game.getTeams().get(i);
            log.info("Team " + team.getManufacture().getNameLong() +
                    " - Engine: " + team.getEngine().getPerformance() +
                    ", Chassis: " + team.getChassis().getPerformance());
        }

        File saveFile = Paths.get(EXTERNAL_FILE_PATH).toFile();
        objectMapper.writeValue(saveFile, game);
        log.info("Game saved successfully to " + saveFile.getAbsolutePath());
    }

    /**
     * Loads the game state from external file or fallback to resource file.
     * After loading, reconciles Team object references between Game and Championship.
     *
     * @return The loaded game with reconciled references
     * @throws IOException if both external and resource loading fail
     */
    public Game loadGame() throws IOException {
        // Try loading from external file first
        Game game = loadFromExternalFile();
        if (game != null) {
            reconcileTeamReferences(game);
            return game;
        }

        // Fallback to resource file
        game = loadFromResourceFile();
        if (game != null) {
            reconcileTeamReferences(game);
            return game;
        }

        throw new IOException("Failed to load game from both external file and resources");
    }

    /**
     * Reconciles Team object references between Game and Championship.
     * Jackson creates new Team objects when deserializing Championship.
     * This method ensures Championship uses the same Team objects as Game.
     *
     * @param game The loaded game
     */
    private void reconcileTeamReferences(Game game) {
        Championship championship = game.getCurrentChampionship();
        if (championship == null) {
            log.fine("No championship to reconcile");
            return;
        }

        // Create a map of team identifiers to actual Team objects from game
        Map<String, Team> teamMap = new HashMap<>();
        for (Team team : game.getTeams()) {
            String key = createTeamKey(team);
            teamMap.put(key, team);
        }

        // Update DriverSlots to use correct Team references
        for (Championship.DriverSlot slot : championship.getDriversStandings()) {
            String key = createTeamKey(slot.getTeam());
            Team correctTeam = teamMap.get(key);
            if (correctTeam != null && correctTeam != slot.getTeam()) {
                // Use reflection to update the final team field
                updateTeamReference(slot, correctTeam);
                log.fine("Reconciled driver slot team reference: " + key);
            }
        }

        // Update TeamSlots to use correct Team references
        for (Championship.TeamSlot slot : championship.getConstructorsStandings()) {
            String key = createTeamKey(slot.getTeam());
            Team correctTeam = teamMap.get(key);
            if (correctTeam != null && correctTeam != slot.getTeam()) {
                // Use reflection to update the final team field
                updateTeamReference(slot, correctTeam);
                log.fine("Reconciled constructor slot team reference: " + key);
            }
        }

        log.info("Team references reconciled between Game and Championship");
    }

    /**
     * Creates a unique key for a team based on its manufacture.
     *
     * @param team The team
     * @return A unique identifier for the team
     */
    private String createTeamKey(Team team) {
        return team.getManufacture().name() + "-" + team.getGarage();
    }

    /**
     * Updates the team reference in a DriverSlot.
     *
     * @param slot The driver slot
     * @param team The correct team reference
     */
    private void updateTeamReference(Championship.DriverSlot slot, Team team) {
        slot.setTeam(team);
    }

    /**
     * Updates the team reference in a TeamSlot.
     *
     * @param slot The team slot
     * @param team The correct team reference
     */
    private void updateTeamReference(Championship.TeamSlot slot, Team team) {
        slot.setTeam(team);
    }

    /**
     * Checks if a game save file exists in the external directory.
     *
     * @return true if save file exists
     */
    public boolean gameSaveExists() {
        return new File(EXTERNAL_FILE_PATH).exists();
    }

    /**
     * Loads game from external file.
     *
     * @return Game instance or null if loading fails
     */
    private Game loadFromExternalFile() {
        File externalFile = new File(EXTERNAL_FILE_PATH);

        if (!externalFile.exists()) {
            log.info("External save file does not exist: " + externalFile.getPath());
            return null;
        }

        try {
            log.info("Loading game from external file: " + externalFile.getPath());
            Game game = objectMapper.readValue(externalFile, Game.class);

            // Log what we loaded
            log.info("=== LOADED GAME ===");
            log.info("Current season (internal): " + game.getCurrentSeason());
            log.info("Display season: " + (game.getCurrentSeason() + 1));
            log.info("Player: " + game.getPlayer().getName());

            // Log a few team states
            for (int i = 0; i < Math.min(3, game.getTeams().size()); i++) {
                Team team = game.getTeams().get(i);
                log.info("Team " + team.getManufacture().getNameLong() +
                        " - Engine: " + team.getEngine().getPerformance() +
                        ", Chassis: " + team.getChassis().getPerformance());
            }

            return game;
        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed to load game from external file", e);
            return null;
        }
    }

    /**
     * Loads game from resource file.
     *
     * @return Game instance or null if loading fails
     */
    private Game loadFromResourceFile() {
        try (InputStream resourceStream = getClass().getClassLoader()
                .getResourceAsStream(RESOURCE_FILE_PATH)) {

            if (resourceStream == null) {
                log.warning("Resource file not found: " + RESOURCE_FILE_PATH);
                return null;
            }

            log.info("Loading game from default resource: " + RESOURCE_FILE_PATH);
            return objectMapper.readValue(resourceStream, Game.class);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed to load game from resources", e);
            return null;
        }
    }

    /**
     * Deletes the external save file.
     *
     * @return true if file was deleted or didn't exist
     */
    public boolean deleteSaveFile() {
        File saveFile = new File(EXTERNAL_FILE_PATH);

        if (!saveFile.exists()) {
            return true;
        }

        boolean deleted = saveFile.delete();
        if (deleted) {
            log.info("Save file deleted successfully");
        } else {
            log.warning("Failed to delete save file");
        }

        return deleted;
    }
}