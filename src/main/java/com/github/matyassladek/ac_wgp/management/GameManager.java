package com.github.matyassladek.ac_wgp.management;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.matyassladek.ac_wgp.controller.Game;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class GameManager {
    private static final Logger log = Logger.getLogger(GameManager.class.getName());
    private final String SAVE_FILE_PATH = "src/main/resources/save/game_save.json";

    // Save the game state to a JSON file
    public void saveGame(Game game) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(Paths.get(SAVE_FILE_PATH).toFile(), game);
            log.info("Game saved successfully to " + SAVE_FILE_PATH);
        } catch (IOException e) {
            log.severe("Failed to save game: " + e.getMessage());
        }
    }

    // Load the game state from a JSON file
    public Game loadGame() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(SAVE_FILE_PATH), Game.class);
        } catch (IOException e) {
            log.severe("Failed to load game: " + e.getMessage());
            return null;
        }
    }
}
