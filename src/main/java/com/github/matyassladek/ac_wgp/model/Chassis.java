package com.github.matyassladek.ac_wgp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.jdt.annotation.NonNullByDefault;

import java.util.List;

@NonNullByDefault
public class Chassis extends VehiclePart {

    // Default constructor for creating new chassis
    public Chassis() {
        super(List.of(40, 26, 16, 9, 4, 0, 0), 5);
    }

    // Constructor for Jackson deserialization
    @JsonCreator
    public Chassis(
            @JsonProperty("developmentLimits") List<Integer> developmentLimits,
            @JsonProperty("upgradeValue") int upgradeValue,
            @JsonProperty("performance") int performance) {
        super(developmentLimits, upgradeValue, performance);
    }
}