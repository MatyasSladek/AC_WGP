package com.github.matyassladek.ac_wgp.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service for validating Assetto Corsa game installations.
 * Checks for required files and directories.
 */
public class AcInstallationValidator {

    private static final Logger log = Logger.getLogger(AcInstallationValidator.class.getName());

    private static final String CONTENT_FOLDER = "content";
    private static final String CARS_FOLDER = "cars";
    private static final String TRACKS_FOLDER = "tracks";
    private static final String AC_EXECUTABLE = "acs.exe";
    private static final String AC_EXECUTABLE_ALT = "AssettoCorsa.exe";

    /**
     * Validates whether the given directory is a valid Assetto Corsa installation.
     *
     * @param directory The directory to validate
     * @return true if the directory contains a valid AC installation
     */
    public boolean isValidInstallation(File directory) {
        if (directory == null || !directory.exists() || !directory.isDirectory()) {
            return false;
        }

        boolean hasContentStructure = hasValidContentStructure(directory);
        boolean hasExecutable = hasValidExecutable(directory);

        log.info(String.format("AC validation for '%s' - Content structure: %b, Executable: %b",
                directory.getName(), hasContentStructure, hasExecutable));

        return hasContentStructure && hasExecutable;
    }

    /**
     * Logs all available tracks found in the AC installation.
     *
     * @param acDirectory The AC installation directory
     */
    public void logAvailableTracks(File acDirectory) {
        try {
            List<String> tracks = getAvailableTracks(acDirectory);
            log.info("Found " + tracks.size() + " tracks in AC installation:");
            tracks.forEach(track -> log.info("  - " + track));
        } catch (IOException e) {
            log.log(Level.WARNING, "Failed to load available tracks", e);
        }
    }

    /**
     * Gets a list of available track folder names.
     *
     * @param acDirectory The AC installation directory
     * @return List of track folder names
     * @throws IOException if unable to read the tracks directory
     */
    public List<String> getAvailableTracks(File acDirectory) throws IOException {
        Path tracksPath = Paths.get(acDirectory.getAbsolutePath(), CONTENT_FOLDER, TRACKS_FOLDER);

        if (!Files.exists(tracksPath) || !Files.isDirectory(tracksPath)) {
            return List.of();
        }

        return Files.list(tracksPath)
                .filter(Files::isDirectory)
                .map(path -> path.getFileName().toString())
                .sorted()
                .toList();
    }

    private boolean hasValidContentStructure(File directory) {
        File contentFolder = new File(directory, CONTENT_FOLDER);
        File carsFolder = new File(contentFolder, CARS_FOLDER);
        File tracksFolder = new File(contentFolder, TRACKS_FOLDER);

        return contentFolder.exists()
                && contentFolder.isDirectory()
                && carsFolder.exists()
                && carsFolder.isDirectory()
                && tracksFolder.exists()
                && tracksFolder.isDirectory();
    }

    private boolean hasValidExecutable(File directory) {
        File acExe = new File(directory, AC_EXECUTABLE);
        File acExeAlt = new File(directory, AC_EXECUTABLE_ALT);

        return (acExe.exists() && acExe.isFile())
                || (acExeAlt.exists() && acExeAlt.isFile());
    }
}