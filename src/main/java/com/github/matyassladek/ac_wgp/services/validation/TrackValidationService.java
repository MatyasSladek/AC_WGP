package com.github.matyassladek.ac_wgp.services.validation;

import com.github.matyassladek.ac_wgp.model.Track;
import com.github.matyassladek.ac_wgp.services.ac.AssettoCorsaIntegrationService;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service for validating that championship tracks are available in the AC installation.
 */
public class TrackValidationService {

    private static final Logger log = Logger.getLogger(TrackValidationService.class.getName());

    /**
     * Validates that all tracks in the calendar are available in the AC installation.
     *
     * @param calendar List of tracks in the championship
     * @param acGamePath Path to AC installation
     * @return List of missing track names (empty if all tracks are available)
     */
    public List<String> validateTracks(List<Track> calendar, String acGamePath) {
        List<String> missingTracks = new ArrayList<>();

        try {
            AssettoCorsaIntegrationService acService =
                    new AssettoCorsaIntegrationService(acGamePath);

            for (Track track : calendar) {
                if (!isTrackAvailable(track, acService)) {
                    missingTracks.add(track.getDisplayName());
                    log.warning("Track not available: " + track.getDisplayName());
                }
            }
        } catch (Exception e) {
            log.warning("Failed to validate tracks: " + e.getMessage());
        }

        return missingTracks;
    }

    /**
     * Checks if a specific track is available in the AC installation.
     *
     * @param track The track to check
     * @param acService The AC integration service
     * @return true if track is available
     */
    private boolean isTrackAvailable(Track track, AssettoCorsaIntegrationService acService) {
        String trackFolderId = track.getTrackFolderName();
        String layoutId = track.getLayoutFolderName();

        return acService.isTrackAvailable(trackFolderId, layoutId);
    }
}