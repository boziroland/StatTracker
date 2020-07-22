package org.github.boziroland.services.impl;

import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;
import org.github.boziroland.entities.GeneralAPIData;
import org.github.boziroland.entities.LeagueData;
import org.github.boziroland.repositories.ILeagueRepository;
import org.github.boziroland.services.ILeagueService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class LeagueService implements ILeagueService {

    //ILeagueDAO dao;
    ILeagueRepository leagueRepository;
    RiotApi api;

    public LeagueService() throws IOException{
        init();
    }

    void init() throws IOException {
        Optional<String> key = readKeyFromFile("src/main/resources/riotAPIkey.txt");

        if(key.isPresent()){
            ApiConfig config = new ApiConfig().setKey(key.get());
            api = new RiotApi(config);
        }
    }

    @Override
    public void createOrUpdate(Summoner player, List<MatchReference> lastTenMatches) {
        leagueRepository.save(new LeagueData(-1, player, lastTenMatches));
    }

    @Override
    public Optional<LeagueData> findById(int id) {
        return leagueRepository.findById(id);
    }

    @Override
    public List<LeagueData> list() {
        return leagueRepository.findAll();
    }

    @Override
    public void deleteById(int id) {
        leagueRepository.deleteById(id);
    }

    @Override
    public void requestInformation(String accountId, GeneralAPIData location) {

        try {

            Summoner summoner = api.getSummonerByName(Platform.EUNE, accountId);
            List<MatchReference> matchList = api.getMatchListByAccountId(Platform.EUNE, summoner.getAccountId()).getMatches();

            ((LeagueData)location).setPlayer(summoner);
            ((LeagueData)location).setLastTenMatches(matchList.subList(0, 10));
            createOrUpdate(summoner, matchList.subList(0, 10));
        } catch (RiotApiException e) {
            e.printStackTrace();
        }
    }
}
