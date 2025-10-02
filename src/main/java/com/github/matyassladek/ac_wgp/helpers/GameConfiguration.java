package com.github.matyassladek.ac_wgp.helpers;

/**
 * Configuration class to store paths and settings for AC integration
 */
public class GameConfiguration {

    private String jsonResultsPath;
    private String acGamePath;
    private boolean autoLoadResults;
    private boolean validateTracks;

    public GameConfiguration() {
        this.autoLoadResults = true;
        this.validateTracks = true;
    }

    public GameConfiguration(String jsonResultsPath, String acGamePath) {
        this();
        this.jsonResultsPath = jsonResultsPath;
        this.acGamePath = acGamePath;
    }

    public String getJsonResultsPath() {
        return jsonResultsPath;
    }

    public void setJsonResultsPath(String jsonResultsPath) {
        this.jsonResultsPath = jsonResultsPath;
    }

    public String getAcGamePath() {
        return acGamePath;
    }

    public void setAcGamePath(String acGamePath) {
        this.acGamePath = acGamePath;
    }

    public boolean isAutoLoadResults() {
        return autoLoadResults;
    }

    public void setAutoLoadResults(boolean autoLoadResults) {
        this.autoLoadResults = autoLoadResults;
    }

    public boolean isValidateTracks() {
        return validateTracks;
    }

    public void setValidateTracks(boolean validateTracks) {
        this.validateTracks = validateTracks;
    }

    @Override
    public String toString() {
        return "GameConfiguration{" +
                "jsonResultsPath='" + jsonResultsPath + '\'' +
                ", acGamePath='" + acGamePath + '\'' +
                ", autoLoadResults=" + autoLoadResults +
                ", validateTracks=" + validateTracks +
                '}';
    }
}