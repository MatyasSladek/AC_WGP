package com.github.matyassladek.ac_wgp.model;

/**
 * Data Transfer Object (DTO) for driver creation form data.
 * Immutable class representing the form input.
 */
public class DriverFormData {
    private final String firstName;
    private final String lastName;
    private final String countryName;
    private final String teamName;
    private final String jsonPath;
    private final String acGamePath;

    public DriverFormData(String firstName, String lastName, String countryName,
                          String teamName, String jsonPath, String acGamePath) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.countryName = countryName;
        this.teamName = teamName;
        this.jsonPath = jsonPath;
        this.acGamePath = acGamePath;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getJsonPath() {
        return jsonPath;
    }

    public String getAcGamePath() {
        return acGamePath;
    }

    public boolean hasEmptyRequiredFields() {
        return isNullOrEmpty(firstName) || isNullOrEmpty(lastName)
                || isNullOrEmpty(countryName) || isNullOrEmpty(teamName);
    }

    public boolean hasEmptyPaths() {
        return isNullOrEmpty(jsonPath) || isNullOrEmpty(acGamePath);
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}