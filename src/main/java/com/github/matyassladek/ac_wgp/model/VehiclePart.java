package com.github.matyassladek.ac_wgp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.eclipse.jdt.annotation.NonNullByDefault;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Getter
@NonNullByDefault
public abstract class VehiclePart {

    private static final Logger log = Logger.getLogger(VehiclePart.class.getName());

    @JsonProperty("developmentLimits")
    protected final List<Integer> developmentLimits;

    @JsonProperty("upgradeValue")
    protected final int upgradeValue;

    @JsonProperty("performance")
    protected int performance;

    // Constructor for creating NEW parts (used by subclasses for initial creation)
    protected VehiclePart(List<Integer> developmentLimits, int upgradeValue) {
        this.developmentLimits = developmentLimits;
        this.upgradeValue = upgradeValue;
        this.performance = developmentLimits.getFirst() * upgradeValue;
    }

    // Constructor for Jackson deserialization - preserves loaded performance value
    @JsonCreator
    protected VehiclePart(
            @JsonProperty("developmentLimits") List<Integer> developmentLimits,
            @JsonProperty("upgradeValue") int upgradeValue,
            @JsonProperty("performance") int performance) {
        this.developmentLimits = developmentLimits;
        this.upgradeValue = upgradeValue;
        this.performance = performance;  // Use the loaded value, don't recalculate!
    }

    /**
     * Upgrades the vehicle part by reducing restrictions (ballast/restrictor).
     * Lower values = less restriction = better performance.
     *
     * @param factoryLevel The team's factory level (higher = better development)
     * @param season The season index (0-based)
     * @param rand Random instance for variation
     */
    @JsonIgnore
    public void upgrade(int factoryLevel, int season, Random rand) {
        // Validate season index
        if (season < 0 || season >= developmentLimits.size()) {
            log.warning("Invalid season " + season + " for upgrade (max: " + (developmentLimits.size() - 1) + ")");
            return;
        }

        int oldPerformance = performance;
        int baseValue = performance / upgradeValue;
        int seasonLimit = developmentLimits.get(season);

        log.info(String.format("UPGRADE CALLED: current=%d, baseValue=%d, seasonLimit=%d, factoryLevel=%d, season=%d",
                performance, baseValue, seasonLimit, factoryLevel, season));

        // Calculate the range of possible development
        // range = how much we can potentially improve (reduce restriction)
        int range = baseValue - seasonLimit - factoryLevel;

        log.info(String.format("  Calculated range: %d - %d - %d = %d", baseValue, seasonLimit, factoryLevel, range));

        if (range < 1) {
            // No room for improvement - already at or below the limit for this season
            log.info("  Range < 1, setting performance to seasonLimit * upgradeValue = " + (seasonLimit * upgradeValue));
            this.performance = seasonLimit * upgradeValue;
        } else {
            // Random development within the range
            int randomReduction = rand.nextInt(range);
            int newBaseValue = baseValue - factoryLevel - randomReduction;
            this.performance = newBaseValue * upgradeValue;

            log.info(String.format("  Random reduction: %d, newBaseValue: %d - %d - %d = %d, newPerformance: %d",
                    randomReduction, baseValue, factoryLevel, randomReduction, newBaseValue, this.performance));
        }

        log.info(String.format("UPGRADE COMPLETE: %d -> %d (change: %+d)",
                oldPerformance, this.performance, this.performance - oldPerformance));
    }
}