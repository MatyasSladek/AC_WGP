package com.github.matyassladek.ac_wgp.model;

import lombok.Getter;
import org.eclipse.jdt.annotation.NonNullByDefault;

import java.util.List;
import java.util.Random;

@Getter
@NonNullByDefault
public abstract class VehiclePart {

    protected final List<Integer> developmentLimits;
    protected final int upgradeValue;
    protected int performance;

    protected VehiclePart(List<Integer> developmentLimits, int upgradeValue) {
        this.developmentLimits = developmentLimits;
        this.upgradeValue = upgradeValue;
        this.performance = developmentLimits.getFirst();
    }

    public void upgrade(int factoryLevel, int season, Random rand) {
        int range = performance - developmentLimits.get(season) - factoryLevel;
        this.performance = (performance - factoryLevel - rand.nextInt(range)) * upgradeValue;
    }
}
