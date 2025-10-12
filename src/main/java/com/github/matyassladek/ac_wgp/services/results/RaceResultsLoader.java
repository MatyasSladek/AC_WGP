package com.github.matyassladek.ac_wgp.services.results;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service for loading race results from Assetto Corsa JSON files.
 * Parses the race_out.json format and extracts player names and finishing order.
 */
public class RaceResultsLoader {

    private static final Logger log = Logger.getLogger(RaceResultsLoader.class.getName());
    private final ObjectMapper objectMapper;

    public RaceResultsLoader() {
        this.objectMapper = new ObjectMapper();
    }

    public RaceResultsLoader(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Loads race results from a JSON file.
     *
     * @param jsonFile The JSON file to load
     * @return RaceResults containing player names in finishing order
     * @throws IOException if file cannot be read or parsed
     */
    public RaceResults loadFromJson(File jsonFile) throws IOException {
        JsonNode rootNode = objectMapper.readTree(jsonFile);

        List<String> playerNames = extractPlayerNames(rootNode);
        List<Integer> raceResults = extractRaceResults(rootNode);

        return createSortedResults(playerNames, raceResults);
    }

    /**
     * Extracts player names from the JSON.
     *
     * @param rootNode The root JSON node
     * @return List of player names
     * @throws IOException if structure is invalid
     */
    private List<String> extractPlayerNames(JsonNode rootNode) throws IOException {
        JsonNode playersNode = rootNode.get("players");

        if (playersNode == null || !playersNode.isArray()) {
            throw new IOException("Invalid JSON structure: missing or invalid players array");
        }

        List<String> playerNames = new ArrayList<>();
        for (int i = 0; i < playersNode.size(); i++) {
            JsonNode playerNode = playersNode.get(i);
            String playerName = playerNode.get("name").asText();
            playerNames.add(playerName);
            log.fine("Loaded player " + (i + 1) + ": " + playerName);
        }

        return playerNames;
    }

    /**
     * Extracts race results (finishing order) from the JSON.
     *
     * @param rootNode The root JSON node
     * @return List of player indices in finishing order
     * @throws IOException if structure is invalid
     */
    private List<Integer> extractRaceResults(JsonNode rootNode) throws IOException {
        JsonNode sessionsNode = rootNode.get("sessions");

        if (sessionsNode == null || !sessionsNode.isArray() || sessionsNode.size() == 0) {
            throw new IOException("Invalid JSON structure: missing or empty sessions array");
        }

        JsonNode sessionNode = sessionsNode.get(0);
        JsonNode raceResultNode = sessionNode.get("raceResult");

        if (raceResultNode == null || !raceResultNode.isArray()) {
            throw new IOException("Invalid JSON structure: missing or invalid raceResult array");
        }

        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < raceResultNode.size(); i++) {
            results.add(raceResultNode.get(i).asInt());
        }

        return results;
    }

    /**
     * Creates sorted results by matching race results indices with player names.
     *
     * @param playerNames List of all player names
     * @param raceResults List of player indices in finishing order
     * @return RaceResults with sorted player names
     */
    private RaceResults createSortedResults(List<String> playerNames, List<Integer> raceResults) {
        List<String> sortedNames = new ArrayList<>();

        for (int i = 0; i < raceResults.size(); i++) {
            int playerIndex = raceResults.get(i);

            if (playerIndex >= 0 && playerIndex < playerNames.size()) {
                String playerName = playerNames.get(playerIndex);
                sortedNames.add(playerName);
                log.fine("Position " + (i + 1) + ": Player " + (playerIndex + 1) +
                        " (" + playerName + ")");
            } else {
                log.warning("Invalid player index in raceResult: " + playerIndex);
            }
        }

        return new RaceResults(sortedNames);
    }
}