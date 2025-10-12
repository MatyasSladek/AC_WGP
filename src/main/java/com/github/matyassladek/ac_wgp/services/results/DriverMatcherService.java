package com.github.matyassladek.ac_wgp.services.results;

import com.github.matyassladek.ac_wgp.model.Driver;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Service for matching player names from AC results with game drivers.
 * Handles fuzzy matching and name variations.
 */
public class DriverMatcherService {

    private static final Logger log = Logger.getLogger(DriverMatcherService.class.getName());
    private static final int MIN_NAME_LENGTH = 2;

    /**
     * Matches race results player names to game drivers.
     *
     * @param raceResults The race results from AC
     * @param drivers List of drivers in the championship
     * @return List of matched driver names in finishing order
     */
    public List<String> matchDrivers(RaceResults raceResults, List<Driver> drivers) {
        Set<String> availableDrivers = drivers.stream()
                .map(Driver::getName)
                .collect(Collectors.toSet());

        List<String> matchedDrivers = new ArrayList<>();

        for (String playerName : raceResults.getFinishingOrder()) {
            String matchedDriver = findMatchingDriver(playerName, availableDrivers);

            if (matchedDriver != null) {
                matchedDrivers.add(matchedDriver);
                availableDrivers.remove(matchedDriver);
                log.info("Matched player '" + playerName + "' to driver '" + matchedDriver + "'");
            } else {
                log.warning("No matching driver found for player: " + playerName);
                matchedDrivers.add(playerName); // Keep original name as fallback
            }
        }

        return matchedDrivers;
    }

    /**
     * Finds a matching driver name from available drivers.
     * Tries multiple matching strategies.
     *
     * @param playerName The player name from AC
     * @param availableDrivers Set of available driver names
     * @return Matched driver name or null if no match found
     */
    private String findMatchingDriver(String playerName, Set<String> availableDrivers) {
        // Strategy 1: Exact match (case-insensitive)
        String exactMatch = findExactMatch(playerName, availableDrivers);
        if (exactMatch != null) {
            return exactMatch;
        }

        // Strategy 2: Partial match (contains)
        String partialMatch = findPartialMatch(playerName, availableDrivers);
        if (partialMatch != null) {
            return partialMatch;
        }

        // Strategy 3: Last name match
        return findLastNameMatch(playerName, availableDrivers);
    }

    /**
     * Finds exact match (case-insensitive).
     */
    private String findExactMatch(String playerName, Set<String> availableDrivers) {
        for (String driver : availableDrivers) {
            if (driver.equalsIgnoreCase(playerName)) {
                return driver;
            }
        }
        return null;
    }

    /**
     * Finds partial match (contains).
     */
    private String findPartialMatch(String playerName, Set<String> availableDrivers) {
        String playerLower = playerName.toLowerCase();

        for (String driver : availableDrivers) {
            String driverLower = driver.toLowerCase();

            if (driverLower.contains(playerLower) || playerLower.contains(driverLower)) {
                return driver;
            }
        }

        return null;
    }

    /**
     * Finds match by comparing individual name parts (e.g., last names).
     */
    private String findLastNameMatch(String playerName, Set<String> availableDrivers) {
        String[] playerParts = playerName.split("\\s+");

        for (String driver : availableDrivers) {
            String[] driverParts = driver.split("\\s+");

            for (String playerPart : playerParts) {
                if (playerPart.length() <= MIN_NAME_LENGTH) {
                    continue;
                }

                for (String driverPart : driverParts) {
                    if (playerPart.equalsIgnoreCase(driverPart)) {
                        return driver;
                    }
                }
            }
        }

        return null;
    }
}