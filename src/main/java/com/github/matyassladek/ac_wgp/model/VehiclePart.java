package com.github.matyassladek.ac_wgp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.eclipse.jdt.annotation.NonNullByDefault;

import java.util.List;
import java.util.Random;

@Getter
@NonNullByDefault
public abstract class VehiclePart {

    @JsonProperty("developmentLimits")
    protected final List<Integer> developmentLimits;

    @JsonProperty("upgradeValue")
    protected final int upgradeValue;

    @JsonProperty("performance")
    protected int performance;

    @JsonCreator
    protected VehiclePart(List<Integer> developmentLimits, int upgradeValue) {
        this.developmentLimits = developmentLimits;
        this.upgradeValue = upgradeValue;
        this.performance = developmentLimits.getFirst() * upgradeValue;
    }

    @JsonIgnore
    public void upgrade(int factoryLevel, int season, Random rand) {
        int range = performance / upgradeValue - developmentLimits.get(season) - factoryLevel;
        if (range < 1) {
            this.performance = 0;
        } else {
            this.performance = (performance / upgradeValue - factoryLevel - rand.nextInt(range)) * upgradeValue;
        }
    }
}
