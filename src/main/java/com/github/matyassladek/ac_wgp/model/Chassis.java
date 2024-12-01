package com.github.matyassladek.ac_wgp.model;

import org.eclipse.jdt.annotation.NonNullByDefault;

import java.util.List;

@NonNullByDefault
public class Chassis extends VehiclePart {
    public Chassis() {
        super(List.of(20, 25, 25, 20, 20, 15, 15, 10, 10, 10, 10, 5, 5, 5, 5),
                5, 50_000);
        this.performance = 200;
        this.upgradeCost = 2_000_000;
    }
}