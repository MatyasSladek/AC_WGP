package com.github.matyassladek.ac_wgp.services.calendar;

import com.github.matyassladek.ac_wgp.model.Track;
import javafx.collections.ObservableList;

/**
 * Service for managing track lists in the calendar UI.
 * Handles adding, removing, and reordering tracks.
 */
public class TrackListManager {

    /**
     * Adds a track from available list to selected list.
     *
     * @param track The track to add
     * @param selectedTracks The selected tracks list
     * @param availableTracks The available tracks list
     */
    public void addTrack(Track track, ObservableList<Track> selectedTracks,
                         ObservableList<Track> availableTracks) {
        if (track != null && !selectedTracks.contains(track)) {
            selectedTracks.add(track);
            availableTracks.remove(track);
        }
    }

    /**
     * Removes a track from selected list and returns it to available list.
     *
     * @param track The track to remove
     * @param selectedTracks The selected tracks list
     * @param availableTracks The available tracks list
     */
    public void removeTrack(Track track, ObservableList<Track> selectedTracks,
                            ObservableList<Track> availableTracks) {
        if (track != null) {
            selectedTracks.remove(track);
            availableTracks.add(track);
            availableTracks.sort((t1, t2) ->
                    t1.getDisplayName().compareToIgnoreCase(t2.getDisplayName()));
        }
    }

    /**
     * Moves a track up one position in the selected list.
     *
     * @param index The current index of the track
     * @param selectedTracks The selected tracks list
     * @return true if the track was moved, false otherwise
     */
    public boolean moveTrackUp(int index, ObservableList<Track> selectedTracks) {
        if (index <= 0 || index >= selectedTracks.size()) {
            return false;
        }

        Track track = selectedTracks.remove(index);
        selectedTracks.add(index - 1, track);
        return true;
    }

    /**
     * Moves a track down one position in the selected list.
     *
     * @param index The current index of the track
     * @param selectedTracks The selected tracks list
     * @return true if the track was moved, false otherwise
     */
    public boolean moveTrackDown(int index, ObservableList<Track> selectedTracks) {
        if (index < 0 || index >= selectedTracks.size() - 1) {
            return false;
        }

        Track track = selectedTracks.remove(index);
        selectedTracks.add(index + 1, track);
        return true;
    }
}