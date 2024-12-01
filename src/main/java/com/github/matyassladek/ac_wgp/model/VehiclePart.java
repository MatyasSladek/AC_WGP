package com.github.matyassladek.ac_wgp.model;

import org.eclipse.jdt.annotation.NonNullByDefault;

import java.util.List;

@NonNullByDefault
public abstract class VehiclePart {

    protected final List<Integer> upgradeSpeed;
    protected final int upgradeValue;
    protected final int upgradeCostIncrease;
    protected int performance;
    protected int upgradeCost;

    protected VehiclePart(List<Integer> upgradeSpeed, int upgradeValue, int upgradeCostIncrease) {
        this.upgradeSpeed = upgradeSpeed;
        this.upgradeValue = upgradeValue;
        this.upgradeCostIncrease = upgradeCostIncrease;
    }

    public void upgrade() {
        this.performance -= this.upgradeValue;
        this.upgradeCost += this.upgradeCostIncrease;
    }
}
