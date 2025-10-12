package com.github.matyassladek.ac_wgp.services.ac;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service for integrating with Assetto Corsa installation
 */
public class AssettoCorsaIntegrationService {

    private static final Logger log = Logger.getLogger(AssettoCorsaIntegrationService.class.getName());

    private final String acGamePath;
    private final Path tracksPath;

    public AssettoCorsaIntegrationService(String acGamePath) {
        this.acGamePath = acGamePath;
        this.tracksPath = Paths.get(acGamePath, "content", "tracks");
    }

    /**
     * Check if a track is available in the AC installation
     *
     * @param trackFolderId Track folder name (e.g., "ks_nurburgring")
     * @param layoutFolderId Layout folder name (e.g., "gp_a") or null for single-layout tracks
     * @return true if track exists
     */
    public boolean isTrackAvailable(String trackFolderId, String layoutFolderId) {
        Path trackPath = tracksPath.resolve(trackFolderId);

        if (!Files.exists(trackPath)) {
            log.warning("Track folder not found: " + trackFolderId);
            return false;
        }

        Path uiPath = trackPath.resolve("ui");

        if (layoutFolderId == null || layoutFolderId.isEmpty()) {
            // Single layout track - check for ui_track.json directly in ui folder
            Path uiTrackJson = uiPath.resolve("ui_track.json");
            return Files.exists(uiTrackJson);
        } else {
            // Multi-layout track - check for ui_track.json in layout subfolder
            Path layoutPath = uiPath.resolve(layoutFolderId);
            Path uiTrackJson = layoutPath.resolve("ui_track.json");
            return Files.exists(uiTrackJson);
        }
    }

    /**
     * Check if a track folder exists (without checking layout)
     *
     * @param trackFolderId Track folder name
     * @return true if track folder exists
     */
    public boolean isTrackFolderAvailable(String trackFolderId) {
        Path trackPath = tracksPath.resolve(trackFolderId);
        return Files.exists(trackPath) && Files.isDirectory(trackPath);
    }

    /**
     * Get UI flags from Assetto Corsa configuration
     * This could read from AC's configuration files if needed
     *
     * @return Map of UI configuration flags
     */
    public Map<String, String> getUIFlags() {
        Map<String, String> flags = new HashMap<>();

        // Try to read from AC's video configuration
        Path videoConfigPath = Paths.get(acGamePath, "system", "cfg", "video.ini");

        if (Files.exists(videoConfigPath)) {
            try {
                Files.lines(videoConfigPath).forEach(line -> {
                    if (line.contains("=")) {
                        String[] parts = line.split("=", 2);
                        if (parts.length == 2) {
                            flags.put(parts[0].trim().toLowerCase(), parts[1].trim());
                        }
                    }
                });
            } catch (IOException e) {
                log.log(Level.WARNING, "Could not read video configuration", e);
            }
        }

        return flags;
    }

    /**
     * Get the path to the AC results directory
     *
     * @return Path to results directory
     */
    public Path getResultsPath() {
        return Paths.get(acGamePath, "results");
    }

    /**
     * Check if AC installation is valid
     *
     * @return true if valid AC installation
     */
    public boolean validateInstallation() {
        File acDir = new File(acGamePath);
        if (!acDir.exists()) {
            return false;
        }

        // Check for essential folders
        Path contentPath = Paths.get(acGamePath, "content");
        Path carsPath = contentPath.resolve("cars");

        // Check for executable
        Path acExe = Paths.get(acGamePath, "acs.exe");
        Path acExeAlt = Paths.get(acGamePath, "AssettoCorsa.exe");

        return Files.exists(contentPath) &&
                Files.exists(tracksPath) &&
                Files.exists(carsPath) &&
                (Files.exists(acExe) || Files.exists(acExeAlt));
    }

    /**
     * Get the number of available tracks in the installation
     *
     * @return Track count
     */
    public int getAvailableTrackCount() {
        if (!Files.exists(tracksPath)) {
            return 0;
        }

        try {
            return (int) Files.list(tracksPath)
                    .filter(Files::isDirectory)
                    .count();
        } catch (IOException e) {
            log.log(Level.WARNING, "Error counting tracks", e);
            return 0;
        }
    }
}