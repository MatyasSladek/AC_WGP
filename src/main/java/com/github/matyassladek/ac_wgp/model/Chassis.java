package com.github.matyassladek.ac_wgp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.eclipse.jdt.annotation.NonNullByDefault;

import java.util.List;

@NonNullByDefault
public class Chassis extends VehiclePart {

    @JsonCreator
    public Chassis() {
        super(List.of(40, 26, 16, 9, 4, 0, 0), 5);
    }
}