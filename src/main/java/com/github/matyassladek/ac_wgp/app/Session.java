package com.github.matyassladek.ac_wgp.app;

import com.github.matyassladek.ac_wgp.service.DefaultScreenService;
import com.github.matyassladek.ac_wgp.service.ScreenService;
import javafx.stage.Stage;
import java.util.logging.Logger;

/**
 * This class stores information about the actual application session.
 */
public class Session {

    private static final Logger logger = Logger.getLogger(Session.class.getName());

    private final ScreenService screenService;

    public Session(Stage stage) {
        screenService = new DefaultScreenService(stage);
    }

    /**
     * Called on application startup.
     * Following procedures would happen:
     * - display loading game
     * - while loading game is displayed, it searches for saved settings
     * - if settings are saved, main menu is displayed
     * - else settings screen is displayed
     */
    public void start() throws Exception {
        screenService.showSplashscreen();
    }
}
