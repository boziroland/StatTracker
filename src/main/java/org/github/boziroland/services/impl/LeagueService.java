package org.github.boziroland.services.impl;

import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;
import org.github.boziroland.DAL.ILeagueDAO;
import org.github.boziroland.entities.GeneralAPIData;
import org.github.boziroland.entities.LeagueData;
import org.github.boziroland.services.ILeagueService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class LeagueService implements ILeagueService {

    ILeagueDAO dao;
    RiotApi api;

    public LeagueService(ILeagueDAO dao) throws IOException {
        this.dao = dao;

        Optional<String> key = readKeyFromFile("src/main/resources/riotAPIkey.txt");

        if(key.isPresent()){
            ApiConfig config = new ApiConfig().setKey(key.get());
            api = new RiotApi(config);
        }
    }

    @Override
    public void createOrUpdate(Summoner player, List<MatchReference> lastTenMatches) {
        dao.createOrUpdate(new LeagueData(player, lastTenMatches));
    }

    @Override
    public List<LeagueData> findByUsername(String name) {
        return dao.findByUsername(name);
    }

    @Override
    public Optional<LeagueData> findByuserID(String id) {
        return dao.findByUserId(id);
    }

    @Override
    public List<LeagueData> list() {
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
    public void requestInformation(String accountId, GeneralAPIData location) {

        try {

            Summoner summoner = api.getSummonerByName(Platform.EUNE, accountId);
            List<MatchReference> matchList = api.getMatchListByAccountId(Platform.EUNE, summoner.getAccountId()).getMatches();

            ((LeagueData)location).setPlayer(summoner);
            ((LeagueData)location).setLastTenMatches(matchList.subList(0, 10));

        } catch (RiotApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<String> readKeyFromFile(String file) throws IOException {
        List<String> lines;
        lines = Files.readAllLines(Paths.get(file));
        return Optional.of(lines.get(0));
    }
}
