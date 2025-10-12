package com.github.matyassladek.ac_wgp.factory;

import com.github.matyassladek.ac_wgp.enums.Country;
import com.github.matyassladek.ac_wgp.enums.Manufacture;
import com.github.matyassladek.ac_wgp.model.Game;

/**
 * Factory for creating Game instances.
 * Encapsulates game creation logic.
 */
public class GameFactory {

    private final TeamFactory teamFactory;

    public GameFactory() {
        this.teamFactory = new TeamFactory();
    }

    public GameFactory(TeamFactory teamFactory) {
        this.teamFactory = teamFactory;
    }

    /**
     * Creates a new game with player information and paths.
     *
     * @param playerFirstName Player's first name
     * @param playerLastName Player's last name
     * @param playerCountry Player's country
     * @param playerTeam Player's team
     * @param jsonResultsPath Path to JSON results file
     * @param acGamePath Path to Assetto Corsa installation
     * @return New Game instance
     */
    public Game createGame(String playerFirstName, String playerLastName,
                           Country playerCountry, Manufacture playerTeam,
                           String jsonResultsPath, String acGamePath) {
        return Game.create(
                playerFirstName,
                playerLastName,
                playerCountry,
                playerTeam,
                jsonResultsPath,
                acGamePath,
                teamFactory
        );
    }

    /**
     * Creates a new game with default paths.
     *
     * @param playerFirstName Player's first name
     * @param playerLastName Player's last name
     * @param playerCountry Player's country
     * @param playerTeam Player's team
     * @return New Game instance
     */
    public Game createGame(String playerFirstName, String playerLastName,
                           Country playerCountry, Manufacture playerTeam) {
        return createGame(playerFirstName, playerLastName, playerCountry, playerTeam, "", "");
    }
}