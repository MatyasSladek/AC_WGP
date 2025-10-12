package com.github.matyassladek.ac_wgp.services.game;

import com.github.matyassladek.ac_wgp.factory.ChampionshipFactory;
import com.github.matyassladek.ac_wgp.model.Championship;
import com.github.matyassladek.ac_wgp.model.Game;
import com.github.matyassladek.ac_wgp.model.Team;

import java.util.Random;
import java.util.logging.Logger;

/**
 * Service responsible for managing season progression and team upgrades.
 * Handles the complex logic of moving between seasons.
 */
public class SeasonProgressionService {

    private static final Logger log = Logger.getLogger(SeasonProgressionService.class.getName());
    private static final int FACTORY_UPGRADE_CHANCE = 5; // 1 in 5 chance

    private final Random random;
    private final ChampionshipFactory championshipFactory;
    private final TeamUpgradeService teamUpgradeService;

    public SeasonProgressionService() {
        this(new Random(), new ChampionshipFactory(), new TeamUpgradeService());
    }

    public SeasonProgressionService(Random random, ChampionshipFactory championshipFactory,
                                    TeamUpgradeService teamUpgradeService) {
        this.random = random;
        this.championshipFactory = championshipFactory;
        this.teamUpgradeService = teamUpgradeService;
    }

    /**
     * Advances the game to the next season.
     *
     * @param game The game instance
     * @return true if successfully advanced to next season, false if no more seasons
     */
    public boolean advanceToNextSeason(Game game) {
        if (!game.hasMoreSeasons()) {
            log.info("Cannot advance: No more seasons available");
            return false;
        }

        int newSeasonIndex = game.getCurrentSeason() + 1;
        log.info("Advancing from season " + game.getCurrentSeason() + " to season " + newSeasonIndex);

        // Update season
        game.setCurrentSeason(newSeasonIndex);

        // Process end-of-season standings and upgrades
        processEndOfSeason(game);

        // Initialize new championship
        if (game.hasCurrentCalendar()) {
            Championship newChampionship = championshipFactory.createChampionship(
                    game.getTeams(),
                    game.getCurrentCalendar()
            );
            game.setCurrentChampionship(newChampionship);
        }

        log.info("Successfully advanced to season " + newSeasonIndex);
        return true;
    }

    /**
     * Processes end-of-season activities: standings, garage assignments, and upgrades.
     *
     * @param game The game instance
     */
    private void processEndOfSeason(Game game) {
        Championship championship = game.getCurrentChampionship();
        if (championship == null) {
            log.warning("No championship to process");
            return;
        }

        // Sort constructor standings by points
        championship.getConstructorsStandings().sort(
                (teamSlot1, teamSlot2) -> Integer.compare(
                        teamSlot2.getPoints(),
                        teamSlot1.getPoints()
                )
        );

        log.info("Constructor standings sorted for season " + (game.getCurrentSeason() - 1));

        // Update garages and upgrade teams
        assignGaragesAndUpgradeTeams(championship, game.getCurrentSeason());
    }

    /**
     * Assigns garage positions based on standings and upgrades team components.
     *
     * @param championship The championship with final standings
     * @param newSeasonIndex The new season index for upgrade calculations
     */
    private void assignGaragesAndUpgradeTeams(Championship championship, int newSeasonIndex) {
        int garagePosition = 0;

        for (Championship.TeamSlot teamSlot : championship.getConstructorsStandings()) {
            Team team = teamSlot.getTeam();

            // Assign garage based on championship position
            team.setGarage(garagePosition);
            garagePosition++;

            // Upgrade team components
            teamUpgradeService.upgradeTeamComponents(team, newSeasonIndex + 1, random);

            // Random factory level upgrade
            if (shouldUpgradeFactory()) {
                int newFactoryLevel = team.getFactoryLevel() + 1;
                team.setFactoryLevel(newFactoryLevel);
                log.info(team.getManufacture().getNameLong() + " factory upgraded to level " + newFactoryLevel);
            }
        }
    }

    /**
     * Determines if a team should receive a factory upgrade.
     *
     * @return true if factory should be upgraded
     */
    private boolean shouldUpgradeFactory() {
        return random.nextInt(FACTORY_UPGRADE_CHANCE) == 0;
    }
}