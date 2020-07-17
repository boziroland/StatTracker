package org.github.boziroland.entities;

import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;

import java.util.List;
import java.util.Objects;

//Ezt felhasználva: https://github.com/taycaldwell/riot-api-java
public class LeagueData extends GeneralAPIData {

    private Summoner player;
    private List<MatchReference> lastTenMatches;

    public LeagueData() {}

    public LeagueData(Summoner player, List<MatchReference> lastTenMatches) {
        this.player = player;
        this.lastTenMatches = lastTenMatches;
    }

    public Summoner getPlayer() {
        return player;
    }

    public List<MatchReference> getLastTenMatches() {
        return lastTenMatches;
    }

    public void setPlayer(Summoner player) {
        this.player = player;
    }

    public void setLastTenMatches(List<MatchReference> lastTenMatches) {
        this.lastTenMatches = lastTenMatches;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeagueData that = (LeagueData) o;
        return player.equals(that.player) &&
                lastTenMatches.equals(that.lastTenMatches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, lastTenMatches);
    }
}