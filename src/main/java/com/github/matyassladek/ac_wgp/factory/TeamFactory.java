package com.github.matyassladek.ac_wgp.factory;

import com.github.matyassladek.ac_wgp.enums.DriverEnum;
import com.github.matyassladek.ac_wgp.enums.Manufacture;
import com.github.matyassladek.ac_wgp.model.Driver;
import com.github.matyassladek.ac_wgp.model.Team;
import com.github.matyassladek.ac_wgp.services.game.TeamUpgradeService;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@NoArgsConstructor
public class TeamFactory {

    private static final TeamUpgradeService teamUpgradeService = new TeamUpgradeService();

    private static final Random random = new Random();

    private int garage = 0;

    public List<Team> createTeamList(Driver player, Manufacture playerTeam) {
        List<Team> teams = teamInit();
        for (Team team : teams) {
            teamUpgradeService.upgradeTeamComponents(team, 1, random);
            if (team.getManufacture().equals(playerTeam)) {
                team.setDriver2(player);
            }
        }
        return teams;
    }

    private List<Team> teamInit() {

        List<Team> defaultTeams = new ArrayList<>();

        defaultTeams.add(new Team(Manufacture.JPS, ++garage,
                DriverEnum.ANTOINE_LAVOIE.getDriver(),
                DriverEnum.RICK_SWART.getDriver(),
                3));

        defaultTeams.add(new Team(Manufacture.NEWMAN, ++garage,
                DriverEnum.EMILIO_CEVALLOS.getDriver(),
                DriverEnum.HAKARU_KOJIMA.getDriver(),
                2));

        defaultTeams.add(new Team(Manufacture.MARTINI, ++garage,
                DriverEnum.JOERI_KAANDORP.getDriver(),
                DriverEnum.MIKKO_VANHALA.getDriver(),
                4));

        defaultTeams.add(new Team(Manufacture.RBR, ++garage,
                DriverEnum.SONNY_KELLY.getDriver(),
                DriverEnum.FRANZ_HERMANN.getDriver(),
                6));

        defaultTeams.add(new Team(Manufacture.GULF, ++garage,
                DriverEnum.DAVID_STARK.getDriver(),
                DriverEnum.LUCAS_PEREYRA.getDriver(),
                5));

        defaultTeams.add(new Team(Manufacture.CASTROL, ++garage,
                DriverEnum.FREDERIC_GASCOIGNE.getDriver(),
                DriverEnum.BRUNO_CAMPIONI.getDriver(),
                3));

        defaultTeams.add(new Team(Manufacture.EM, ++garage,
                DriverEnum.FELIPE_PINTO.getDriver(),
                DriverEnum.JAN_VALENTA.getDriver(),
                1));

        defaultTeams.add(new Team(Manufacture.BWT, ++garage,
                DriverEnum.SAMUEL_SCHMID.getDriver(),
                DriverEnum.MARCOS_PERALTA.getDriver(),
                2));

        defaultTeams.add(new Team(Manufacture.VODAFONE, ++garage,
                DriverEnum.NOAH_TANNER.getDriver(),
                DriverEnum.JAIME_OLIVEIRA.getDriver(),
                3));

        defaultTeams.add(new Team(Manufacture.TARGET, ++garage,
                DriverEnum.DIEGO_CABRERA.getDriver(),
                DriverEnum.JACK_CAMPBELL.getDriver(),
                3));

        return defaultTeams;
    }
}
