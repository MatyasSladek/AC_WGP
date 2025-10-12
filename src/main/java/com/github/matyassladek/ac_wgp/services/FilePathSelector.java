package com.github.matyassladek.ac_wgp.services;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.Optional;

/**
 * Service for selecting files and directories through UI dialogs.
 * Encapsulates file/directory selection logic.
 */
public class FilePathSelector {

    private static final String DEFAULT_AC_PATH_WINDOWS =
            "C:\\Program Files (x86)\\Steam\\steamapps\\common\\assettocorsa";

    /**
     * Opens a file chooser dialog for selecting JSON files.
     *
     * @param owner The window that owns the dialog
     * @return Optional containing the selected file path, or empty if cancelled
     */
    public Optional<String> selectJsonFile(Window owner) {
        FileChooser fileChooser = createJsonFileChooser();
        File selectedFile = fileChooser.showOpenDialog(owner);
        return Optional.ofNullable(selectedFile)
                .map(File::getAbsolutePath);
    }

    /**
     * Opens a directory chooser dialog for selecting the Assetto Corsa installation.
     *
     * @param owner The window that owns the dialog
     * @return Optional containing the selected directory, or empty if cancelled
     */
    public Optional<File> selectAcDirectory(Window owner) {
        DirectoryChooser directoryChooser = createAcDirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(owner);
        return Optional.ofNullable(selectedDirectory);
    }

    private FileChooser createJsonFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select JSON Results File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json")
        );

        File userHome = new File(System.getProperty("user.home"));
        if (userHome.exists()) {
            fileChooser.setInitialDirectory(userHome);
        }

        return fileChooser;
    }

    private DirectoryChooser createAcDirectoryChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Assetto Corsa Game Directory");

        File initialDir = determineInitialAcDirectory();
        if (initialDir.exists()) {
            directoryChooser.setInitialDirectory(initialDir);
        }

        return directoryChooser;
    }

    private File determineInitialAcDirectory() {
        if (isWindows()) {
            File windowsAcPath = new File(DEFAULT_AC_PATH_WINDOWS);
            if (windowsAcPath.exists()) {
                return windowsAcPath;
            }
        }

        return new File(System.getProperty("user.home"));
    }

    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }
}