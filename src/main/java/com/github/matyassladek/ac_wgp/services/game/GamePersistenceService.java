package com.github.matyassladek.ac_wgp.services.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.matyassladek.ac_wgp.model.Game;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
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

        File saveFile = Paths.get(EXTERNAL_FILE_PATH).toFile();
        objectMapper.writeValue(saveFile, game);
        log.info("Game saved successfully to " + saveFile.getAbsolutePath());
    }

    /**
     * Loads the game state from external file or fallback to resource file.
     *
     * @return The loaded game
     * @throws IOException if both external and resource loading fail
     */
    public Game loadGame() throws IOException {
        // Try loading from external file first
        Game game = loadFromExternalFile();
        if (game != null) {
            return game;
        }

        // Fallback to resource file
        game = loadFromResourceFile();
        if (game != null) {
            return game;
        }

        throw new IOException("Failed to load game from both external file and resources");
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
            return objectMapper.readValue(externalFile, Game.class);
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