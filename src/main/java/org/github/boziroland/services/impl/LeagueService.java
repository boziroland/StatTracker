package org.github.boziroland.services.impl;

import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import org.github.boziroland.DAL.ILeagueDAO;
import org.github.boziroland.entities.LeaguePlayer;
import org.github.boziroland.services.ILeagueService;

import java.util.List;
import java.util.Optional;

public class LeagueService implements ILeagueService {

    ILeagueDAO dao;

    public LeagueService(ILeagueDAO dao) {
        this.dao = dao;
    }

    @Override
    public void createOrUpdate(Summoner player, List<MatchReference> lastTenMatches) {
        dao.createOrUpdate(new LeaguePlayer(player, lastTenMatches));
    }

    @Override
    public List<LeaguePlayer> findByusername(String name) {
        return dao.findByuserName(name);
    }

    @Override
    public Optional<LeaguePlayer> findByuserID(String id) {
        return dao.findByUserId(id);
    }

    @Override
    public List<LeaguePlayer> list() {
        return dao.list();
    }

    @Override
    public void deleteByName(String name) {
        dao.deleteByName(name);
    }

    @Override
    public void deleteById(String id) {
        dao.deleteById(id);
    }
}
