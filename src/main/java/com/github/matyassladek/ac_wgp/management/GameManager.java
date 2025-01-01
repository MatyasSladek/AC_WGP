package com.github.matyassladek.ac_wgp.management;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.matyassladek.ac_wgp.model.Game;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameManager {
    private static final Logger log = Logger.getLogger(GameManager.class.getName());
    private static final String RESOURCE_FILE_PATH = "save/game_save.json";
    private static final String EXTERNAL_FILE_PATH = "game_save.json";

    // Save the game state to an external JSON file
    public void saveGame(Game game) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(Paths.get(EXTERNAL_FILE_PATH).toFile(), game);
            log.info(() -> "Game saved successfully to " + EXTERNAL_FILE_PATH);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed to save game: ", e);
        }
    }

    // Load the game state from an external JSON file or fallback to resource file
    public Game loadGame() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Try loading from external file
        File externalFile = new File(EXTERNAL_FILE_PATH);
        if (externalFile.exists()) {
            try {
                log.info(() -> "Loading game from external file: " + externalFile.getPath());
                return objectMapper.readValue(externalFile, Game.class);
            } catch (IOException e) {
                log.log(Level.SEVERE, "Failed to load game from external file: ", e);
            }
        }

        // Fallback to default resource file
        try {
            log.info(() -> "Loading game from default resource: " + RESOURCE_FILE_PATH);
            return objectMapper.readValue(
                    GameManager.class.getClassLoader().getResourceAsStream(RESOURCE_FILE_PATH),
                    Game.class
            );
        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed to load game from resources: ", e);
        }

        throw new IOException("Failed to load game.");
//        // Return a new game instance as a fallback
//        log.warning("Returning a new game instance as fallback.");
//        return new Game();
    }

    // Check if the game save file exists in the external directory
    public boolean gameSaveExist() {
        return new File(EXTERNAL_FILE_PATH).exists();
    }
}