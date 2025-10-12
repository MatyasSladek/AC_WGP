package com.github.matyassladek.ac_wgp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.eclipse.jdt.annotation.NonNullByDefault;

import java.util.List;

@NonNullByDefault
public class Engine extends VehiclePart {

    @JsonCreator
    public Engine() {
        super(List.of(100, 66, 42, 24, 10, 0, 0), 1);
    }
}
