package com.github.matyassladek.ac_wgp.services.game;

import com.github.matyassladek.ac_wgp.model.Team;

import java.util.Random;
import java.util.logging.Logger;

/**
 * Service responsible for upgrading team components.
 * Handles engine and chassis upgrades based on factory level and season.
 */
public class TeamUpgradeService {

    private static final Logger log = Logger.getLogger(TeamUpgradeService.class.getName());

    /**
     * Upgrades all components of a team (engine and chassis).
     *
     * @param team The team to upgrade
     * @param season The current season
     * @param random Random instance for upgrade calculations
     */
    public void upgradeTeamComponents(Team team, int season, Random random) {
        if (team == null) {
            throw new IllegalArgumentException("Team cannot be null");
        }

        upgradeEngine(team, season, random);
        upgradeChassis(team, season, random);

        log.info(String.format("Upgraded components for team %s (Factory Level: %d, Season: %d)",
                team.getManufacture().getNameLong(), team.getFactoryLevel(), season));
    }

    /**
     * Upgrades the team's engine.
     *
     * @param team The team to upgrade
     * @param season The current season
     * @param random Random instance for upgrade calculations
     */
    private void upgradeEngine(Team team, int season, Random random) {
        if (team.getEngine() != null) {
            team.getEngine().upgrade(team.getFactoryLevel(), season, random);
        }
    }

    /**
     * Upgrades the team's chassis.
     *
     * @param team The team to upgrade
     * @param season The current season
     * @param random Random instance for upgrade calculations
     */
    private void upgradeChassis(Team team, int season, Random random) {
        if (team.getChassis() != null) {
            team.getChassis().upgrade(team.getFactoryLevel(), season, random);
        }
    }
}