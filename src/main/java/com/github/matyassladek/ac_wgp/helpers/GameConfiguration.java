package com.github.matyassladek.ac_wgp.helpers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Configuration class to store paths and settings for AC integration
 */
public class GameConfiguration {

    @JsonProperty("jsonResultsPath")
    private String jsonResultsPath;

    @JsonProperty("acGamePath")
    private String acGamePath;

    @JsonProperty("autoLoadResults")
    private boolean autoLoadResults;

    @JsonProperty("validateTracks")
    private boolean validateTracks;

    public GameConfiguration() {
        this.autoLoadResults = true;
        this.validateTracks = true;
    }

    @JsonCreator
    public GameConfiguration(
            @JsonProperty("jsonResultsPath") String jsonResultsPath,
            @JsonProperty("acGamePath") String acGamePath,
            @JsonProperty("autoLoadResults") Boolean autoLoadResults,
            @JsonProperty("validateTracks") Boolean validateTracks) {
        this.jsonResultsPath = jsonResultsPath;
        this.acGamePath = acGamePath;
        this.autoLoadResults = autoLoadResults != null ? autoLoadResults : true;
        this.validateTracks = validateTracks != null ? validateTracks : true;
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