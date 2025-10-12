package com.github.matyassladek.ac_wgp.services.calendar;

import com.github.matyassladek.ac_wgp.model.Track;

import java.util.List;

/**
 * Service for validating calendar configurations.
 */
public class CalendarValidationService {

    public static final int MIN_RACES = 1;
    public static final int MAX_RACES = 50;

    /**
     * Validates if a calendar meets the requirements.
     *
     * @param calendar The list of tracks
     * @return true if valid, false otherwise
     */
    public boolean isValidCalendar(List<Track> calendar) {
        if (calendar == null) {
            return false;
        }

        int size = calendar.size();
        return size >= MIN_RACES && size <= MAX_RACES;
    }

    /**
     * Checks if adding another track would exceed the maximum.
     *
     * @param currentCount Current number of tracks
     * @return true if can add more tracks
     */
    public boolean canAddMoreTracks(int currentCount) {
        return currentCount < MAX_RACES;
    }

    /**
     * Gets the number of tracks needed to reach the minimum.
     *
     * @param currentCount Current number of tracks
     * @return number of tracks needed (0 if minimum is met)
     */
    public int getTracksNeeded(int currentCount) {
        return Math.max(0, MIN_RACES - currentCount);
    }
}