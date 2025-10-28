package com.github.matyassladek.ac_wgp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.eclipse.jdt.annotation.NonNullByDefault;

import java.util.List;

@NonNullByDefault
public class Engine extends VehiclePart {

    // Default constructor for creating new engines
    public Engine() {
        super(List.of(100, 66, 42, 24, 10, 0, 0), 1);
    }

    // Constructor for Jackson deserialization
    @JsonCreator
    public Engine(
            @JsonProperty("developmentLimits") List<Integer> developmentLimits,
            @JsonProperty("upgradeValue") int upgradeValue,
            @JsonProperty("performance") int performance) {
        super(developmentLimits, upgradeValue, performance);
    }
}