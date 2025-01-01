package com.github.matyassladek.ac_wgp.factory;

import com.github.matyassladek.ac_wgp.enums.Manufacture;
import com.github.matyassladek.ac_wgp.model.Driver;
import com.github.matyassladek.ac_wgp.model.Team;

import java.util.List;

public abstract class TeamFactory {

    public List<Team> createTeamList(Driver player, Manufacture playerTeam) {
        List<Team> teams = teamInit();
        for (Team team : teams) {
            if (team.getManufacture().equals(playerTeam)) {
                team.setDriver2(player);
                return teams;
            }
        }
        return teams;
    }

    protected abstract List<Team> teamInit();
}
