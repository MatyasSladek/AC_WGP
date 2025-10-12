package com.github.matyassladek.ac_wgp.services.ac;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.matyassladek.ac_wgp.model.Track;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Service class for loading tracks from Assetto Corsa installation
 */
public class TrackLoader {

    private static final Logger log = Logger.getLogger(TrackLoader.class.getName());
    private static final String UI_TRACK_JSON = "ui_track.json";
    private final ObjectMapper objectMapper;

    public TrackLoader() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Loads all available tracks from the Assetto Corsa installation
     *
     * @param acGamePath Path to the Assetto Corsa game directory
     * @return List of Track objects
     */
    public List<Track> loadTracks(String acGamePath) {
        List<Track> tracks = new ArrayList<>();

        Path tracksPath = Paths.get(acGamePath, "content", "tracks");

        if (!Files.exists(tracksPath) || !Files.isDirectory(tracksPath)) {
            log.warning("Tracks directory not found: " + tracksPath);
            return tracks;
        }

        try (Stream<Path> trackFolders = Files.list(tracksPath)) {
            trackFolders.filter(Files::isDirectory)
                    .forEach(trackFolder -> {
                        tracks.addAll(loadTrackLayouts(trackFolder));
                    });
        } catch (IOException e) {
            log.log(Level.SEVERE, "Error loading tracks from: " + tracksPath, e);
        }

        log.info("Loaded " + tracks.size() + " tracks from Assetto Corsa installation");
        return tracks;
    }

    /**
     * Loads all layouts for a specific track folder
     *
     * @param trackFolder Path to the track folder
     * @return List of Track objects (one per layout)
     */
    private List<Track> loadTrackLayouts(Path trackFolder) {
        List<Track> layouts = new ArrayList<>();
        String trackFolderName = trackFolder.getFileName().toString();

        Path uiFolder = trackFolder.resolve("ui");

        // Check for single layout (ui_track.json directly in ui folder)
        Path singleLayoutJson = uiFolder.resolve(UI_TRACK_JSON);
        if (Files.exists(singleLayoutJson)) {
            Track track = loadTrackFromJson(singleLayoutJson);
            if (track != null) {
                track.setTrackFolderName(trackFolderName);
                track.setLayoutFolderName(null); // No layout subfolder
                track.setTrackPath(trackFolder);
                layouts.add(track);
                log.fine("Loaded track: " + track.getDisplayName());
            }
            return layouts;
        }

        // Check for multiple layouts (subfolders in ui folder)
        if (Files.exists(uiFolder) && Files.isDirectory(uiFolder)) {
            try (Stream<Path> layoutFolders = Files.list(uiFolder)) {
                layoutFolders.filter(Files::isDirectory)
                        .forEach(layoutFolder -> {
                            Path layoutJson = layoutFolder.resolve(UI_TRACK_JSON);
                            if (Files.exists(layoutJson)) {
                                Track track = loadTrackFromJson(layoutJson);
                                if (track != null) {
                                    track.setTrackFolderName(trackFolderName);
                                    track.setLayoutFolderName(layoutFolder.getFileName().toString());
                                    track.setTrackPath(trackFolder);
                                    layouts.add(track);
                                    log.fine("Loaded track layout: " + track.getDisplayName());
                                }
                            }
                        });
            } catch (IOException e) {
                log.log(Level.WARNING, "Error loading layouts for track: " + trackFolderName, e);
            }
        }

        return layouts;
    }

    /**
     * Loads a Track object from a ui_track.json file
     *
     * @param jsonPath Path to the ui_track.json file
     * @return Track object or null if parsing fails
     */
    private Track loadTrackFromJson(Path jsonPath) {
        try {
            File jsonFile = jsonPath.toFile();
            Track track = objectMapper.readValue(jsonFile, Track.class);
            track.calculateLaps(); // Calculate laps based on track length
            return track;
        } catch (IOException e) {
            log.log(Level.WARNING, "Failed to parse track JSON: " + jsonPath, e);
            return null;
        }
    }

    /**
     * Validates a specific track exists in the AC installation
     *
     * @param acGamePath Path to the Assetto Corsa game directory
     * @param trackFolderName Track folder name
     * @param layoutFolderName Layout folder name (can be null)
     * @return true if track exists
     */
    public boolean validateTrack(String acGamePath, String trackFolderName, String layoutFolderName) {
        Path trackPath = Paths.get(acGamePath, "content", "tracks", trackFolderName);

        if (!Files.exists(trackPath)) {
            return false;
        }

        Path uiFolder = trackPath.resolve("ui");

        if (layoutFolderName == null || layoutFolderName.isEmpty()) {
            // Single layout track
            return Files.exists(uiFolder.resolve(UI_TRACK_JSON));
        } else {
            // Multi-layout track
            Path layoutPath = uiFolder.resolve(layoutFolderName);
            return Files.exists(layoutPath.resolve(UI_TRACK_JSON));
        }
    }
}