package com.github.matyassladek.ac_wgp.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class AssettoCorsaIntegrationService {

    private static final Logger log = Logger.getLogger(AssettoCorsaIntegrationService.class.getName());

    private final String acGamePath;
    private final ObjectMapper objectMapper;

    public AssettoCorsaIntegrationService(String acGamePath) {
        this.acGamePath = acGamePath;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Get list of available tracks from the AC installation
     */
    public List<TrackInfo> getAvailableTracks() {
        List<TrackInfo> tracks = new ArrayList<>();

        try {
            Path tracksPath = Paths.get(acGamePath, "content", "tracks");
            if (!Files.exists(tracksPath)) {
                log.warning("Tracks directory not found: " + tracksPath);
                return tracks;
            }

            Files.list(tracksPath)
                    .filter(Files::isDirectory)
                    .forEach(trackDir -> {
                        TrackInfo trackInfo = loadTrackInfo(trackDir);
                        if (trackInfo != null) {
                            tracks.add(trackInfo);
                        }
                    });

        } catch (IOException e) {
            log.log(Level.SEVERE, "Error loading available tracks", e);
        }

        return tracks.stream()
                .sorted(Comparator.comparing(TrackInfo::getDisplayName))
                .collect(Collectors.toList());
    }

    private TrackInfo loadTrackInfo(Path trackDir) {
        try {
            String trackId = trackDir.getFileName().toString();

            // Try to load ui_track.json for display information
            Path uiTrackPath = trackDir.resolve("ui").resolve("ui_track.json");
            String displayName = trackId; // Default to folder name
            String country = "";
            String description = "";

            if (Files.exists(uiTrackPath)) {
                try {
                    JsonNode trackJson = objectMapper.readTree(uiTrackPath.toFile());
                    if (trackJson.has("name")) {
                        displayName = trackJson.get("name").asText();
                    }
                    if (trackJson.has("country")) {
                        country = trackJson.get("country").asText();
                    }
                    if (trackJson.has("description")) {
                        description = trackJson.get("description").asText();
                    }
                } catch (Exception e) {
                    log.log(Level.WARNING, "Error reading track UI file for " + trackId, e);
                }
            }

            // Get available configurations
            List<String> configurations = getTrackConfigurations(trackDir);

            return new TrackInfo(trackId, displayName, country, description, configurations);

        } catch (Exception e) {
            log.log(Level.WARNING, "Error loading track info for " + trackDir.getFileName(), e);
            return null;
        }
    }

    private List<String> getTrackConfigurations(Path trackDir) {
        List<String> configurations = new ArrayList<>();

        try {
            Files.list(trackDir)
                    .filter(Files::isDirectory)
                    .filter(dir -> !dir.getFileName().toString().equals("ui"))
                    .forEach(configDir -> {
                        String configName = configDir.getFileName().toString();
                        configurations.add(configName);
                    });
        } catch (IOException e) {
            log.log(Level.WARNING, "Error loading track configurations for " + trackDir.getFileName(), e);
        }

        return configurations.isEmpty() ? Arrays.asList("") : configurations;
    }

    /**
     * Read UI flags/settings from Assetto Corsa
     */
    public Map<String, String> getUIFlags() {
        Map<String, String> flags = new HashMap<>();

        try {
            // Try to read from various AC configuration files
            readVideoSettings(flags);
            readControlsSettings(flags);

        } catch (Exception e) {
            log.log(Level.WARNING, "Error reading AC UI flags", e);
        }

        return flags;
    }

    private void readVideoSettings(Map<String, String> flags) {
        try {
            Path videoSettingsPath = Paths.get(acGamePath, "system", "cfg", "video.ini");
            if (Files.exists(videoSettingsPath)) {
                Properties props = new Properties();
                props.load(Files.newInputStream(videoSettingsPath));

                // Extract relevant display settings
                flags.put("resolution", props.getProperty("WIDTH", "1920") + "x" + props.getProperty("HEIGHT", "1080"));
                flags.put("fullscreen", props.getProperty("FULLSCREEN", "0"));
                flags.put("vsync", props.getProperty("VERTICAL_SYNC", "0"));
            }
        } catch (IOException e) {
            log.log(Level.WARNING, "Could not read video settings", e);
        }
    }

    private void readControlsSettings(Map<String, String> flags) {
        try {
            Path controlsPath = Paths.get(acGamePath, "system", "cfg", "controls.ini");
            if (Files.exists(controlsPath)) {
                Properties props = new Properties();
                props.load(Files.newInputStream(controlsPath));

                // Extract control-related settings that might affect UI
                flags.put("steering_wheel", props.getProperty("STEER", ""));
                flags.put("force_feedback", props.getProperty("FF_GAIN", "100"));
            }
        } catch (IOException e) {
            log.log(Level.WARNING, "Could not read controls settings", e);
        }
    }

    /**
     * Check if a specific track exists in the AC installation
     */
    public boolean isTrackAvailable(String trackId) {
        Path trackPath = Paths.get(acGamePath, "content", "tracks", trackId);
        return Files.exists(trackPath) && Files.isDirectory(trackPath);
    }

    /**
     * Get detailed information about a specific track
     */
    public Optional<TrackInfo> getTrackInfo(String trackId) {
        Path trackPath = Paths.get(acGamePath, "content", "tracks", trackId);
        if (!Files.exists(trackPath)) {
            return Optional.empty();
        }

        TrackInfo trackInfo = loadTrackInfo(trackPath);
        return Optional.ofNullable(trackInfo);
    }

    /**
     * Data class to hold track information
     */
    public static class TrackInfo {
        private final String id;
        private final String displayName;
        private final String country;
        private final String description;
        private final List<String> configurations;

        public TrackInfo(String id, String displayName, String country, String description, List<String> configurations) {
            this.id = id;
            this.displayName = displayName;
            this.country = country;
            this.description = description;
            this.configurations = configurations;
        }

        public String getId() { return id; }
        public String getDisplayName() { return displayName; }
        public String getCountry() { return country; }
        public String getDescription() { return description; }
        public List<String> getConfigurations() { return configurations; }

        @Override
        public String toString() {
            return displayName + (country.isEmpty() ? "" : " (" + country + ")");
        }
    }
}