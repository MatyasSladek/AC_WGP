package com.github.matyassladek.ac_wgp.model;

import org.eclipse.jdt.annotation.NonNullByDefault;

import java.util.List;

@NonNullByDefault
public class Engine extends VehiclePart {

    public Engine() {
        super(List.of(100, 66, 44, 28, 16, 7, 0), 1);
    }
}
