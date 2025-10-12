package com.github.matyassladek.ac_wgp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

/**
 * Represents a track loaded from Assetto Corsa installation
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Track {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("tags")
    private List<String> tags;

    @JsonProperty("country")
    private String country;

    @JsonProperty("city")
    private String city;

    @JsonProperty("length")
    private String length;

    @JsonProperty("width")
    private String width;

    @JsonProperty("pitboxes")
    private String pitboxes;

    @JsonProperty("run")
    private String run;

    // Not from JSON - internal data
    private String trackFolderName;
    private String layoutFolderName;
    private Path trackPath;

    // Calculated field for race distance
    private int laps;
    private static final int RACE_DISTANCE = 170000; // Default race distance in meters

    public Track() {
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getPitboxes() {
        return pitboxes;
    }

    public void setPitboxes(String pitboxes) {
        this.pitboxes = pitboxes;
    }

    public String getRun() {
        return run;
    }

    public void setRun(String run) {
        this.run = run;
    }

    public String getTrackFolderName() {
        return trackFolderName;
    }

    public void setTrackFolderName(String trackFolderName) {
        this.trackFolderName = trackFolderName;
    }

    public String getLayoutFolderName() {
        return layoutFolderName;
    }

    public void setLayoutFolderName(String layoutFolderName) {
        this.layoutFolderName = layoutFolderName;
    }

    public Path getTrackPath() {
        return trackPath;
    }

    public void setTrackPath(Path trackPath) {
        this.trackPath = trackPath;
    }

    public int getLaps() {
        return laps;
    }

    public void setLaps(int laps) {
        this.laps = laps;
    }

    /**
     * Calculate number of laps based on track length and race distance
     * Call this after setting length
     */
    public void calculateLaps() {
        if (length != null && !length.isEmpty()) {
            try {
                // Parse length - handle formats like "5148", "5148m", "5.148m"
                String cleanLength = length.replaceAll("[^0-9.]", "");
                double lengthMeters = Double.parseDouble(cleanLength);

                // If length is in km (less than 100), convert to meters
                if (lengthMeters < 100) {
                    lengthMeters *= 1000;
                }

                this.laps = (int) (RACE_DISTANCE / lengthMeters) + 1;
            } catch (NumberFormatException e) {
                this.laps = 30; // Default fallback
            }
        } else {
            this.laps = 30; // Default fallback
        }
    }

    /**
     * Gets the display name for the track (includes layout if not default)
     */
    public String getDisplayName() {
        if (layoutFolderName != null && !layoutFolderName.isEmpty()) {
            return name + " - " + layoutFolderName;
        }
        return name;
    }

    /**
     * Gets the full identifier for the track (folder_layout)
     */
    public String getIdentifier() {
        if (layoutFolderName != null && !layoutFolderName.isEmpty()) {
            return trackFolderName + "_" + layoutFolderName;
        }
        return trackFolderName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return Objects.equals(trackFolderName, track.trackFolderName) &&
                Objects.equals(layoutFolderName, track.layoutFolderName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trackFolderName, layoutFolderName);
    }

    @Override
    public String toString() {
        return getDisplayName() + " (" + country + ")";
    }
}