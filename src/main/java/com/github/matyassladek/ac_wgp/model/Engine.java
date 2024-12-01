package com.github.matyassladek.ac_wgp.model;

import org.eclipse.jdt.annotation.NonNullByDefault;

import java.util.List;

@NonNullByDefault
public class Engine extends VehiclePart {

    public Engine() {
        super(List.of(7, 8, 9, 10, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1), 1, 10_000);
        this.performance = 100;
        this.upgradeCost = 1_000_000;
    }
}
