package com.github.matyassladek.ac_wgp.service;

// TODO: state machine
public interface ScreenService {

    void showSplashscreen() throws Exception;

    void showMainMenu();

    void showSettings();

    void showNewGame();

    void showSavedGames();

    void showSelectTeam();

    void showSignContract();

    void showCreateCalendar();

    void showSeasonSettings();

    void showSeasonPreview();

    void showSeasonOverview();

    void showStats();

    void showTrophies();

    void showPerformance();

    void showDevelopmentStrategy();

    void showCareerEnd();
}
