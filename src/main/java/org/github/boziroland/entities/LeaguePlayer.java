package org.github.boziroland.entities;

import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;

import java.util.List;

//Ezt felhasználva: https://github.com/taycaldwell/riot-api-java
public class LeaguePlayer extends GeneralAPIData{

    private Summoner player;
    private List<MatchReference> lastTenMatches;

    @Override
    public String retrieveData() { return null; }

    public Summoner getPlayer() {
        return player;
    }

    public List<MatchReference> getLastTenMatches() {
        return lastTenMatches;
    }
}
