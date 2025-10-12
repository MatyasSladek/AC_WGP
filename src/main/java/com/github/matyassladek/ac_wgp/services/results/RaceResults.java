package com.github.matyassladek.ac_wgp.services.results;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Data Transfer Object for race results.
 * Contains player names in finishing order.
 */
public class RaceResults {

    private final List<String> finishingOrder;

    public RaceResults(List<String> finishingOrder) {
        this.finishingOrder = new ArrayList<>(finishingOrder);
    }

    /**
     * Gets the finishing order (unmodifiable).
     *
     * @return List of player names in finishing order
     */
    public List<String> getFinishingOrder() {
        return Collections.unmodifiableList(finishingOrder);
    }

    /**
     * Gets the number of finishers.
     *
     * @return Number of players who finished
     */
    public int getFinisherCount() {
        return finishingOrder.size();
    }

    /**
     * Gets the player name at a specific position.
     *
     * @param position The finishing position (0-based)
     * @return Player name or null if position is invalid
     */
    public String getPlayerAtPosition(int position) {
        if (position >= 0 && position < finishingOrder.size()) {
            return finishingOrder.get(position);
        }
        return null;
    }
}