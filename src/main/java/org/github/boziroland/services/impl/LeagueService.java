package org.github.boziroland.services.impl;

import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;
import org.github.boziroland.DAL.ILeagueDAO;
import org.github.boziroland.entities.LeagueDataSource;
import org.github.boziroland.services.ILeagueService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LeagueService implements ILeagueService {

    ILeagueDAO dao;
    RiotApi api;

    public LeagueService(ILeagueDAO dao) {
        this.dao = dao;

        ApiConfig config = new ApiConfig().setKey("YOUR-API-KEY-HERE");
        api = new RiotApi(config);
    }

    @Override
    public void createOrUpdate(Summoner player, List<MatchReference> lastTenMatches) {
        dao.createOrUpdate(new LeagueDataSource(player, lastTenMatches));
    }

    @Override
    public List<LeagueDataSource> findByUsername(String name) {
        return dao.findByUsername(name);
    }

    @Override
    public Optional<LeagueDataSource> findByuserID(String id) {
        return dao.findByUserId(id);
    }

    @Override
    public List<LeagueDataSource> list() {
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

    @Override
    public Map<String, String> requestInformation(String accountId) {

        Map<String, String> ret = new HashMap<>();

        try {

            Summoner summoner = api.getSummonerByName(Platform.EUNE, accountId);

            ret.put("Name", summoner.getName());
            ret.put("ID", summoner.getAccountId());
            /* ... */

        } catch (RiotApiException e) {
            e.printStackTrace();
        }

        return ret;
    }
}
