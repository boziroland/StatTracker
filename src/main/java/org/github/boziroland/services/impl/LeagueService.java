package org.github.boziroland.services.impl;

import lombok.SneakyThrows;
import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;
import org.github.boziroland.entities.LeagueData;
import org.github.boziroland.entities.User;
import org.github.boziroland.entities.apiEntities.MyMatchReference;
import org.github.boziroland.entities.apiEntities.MySummoner;
import org.github.boziroland.repositories.ILeagueRepository;
import org.github.boziroland.repositories.apiEntityRepositories.IMyMatchReferenceRepository;
import org.github.boziroland.repositories.apiEntityRepositories.IMySummonerRepository;
import org.github.boziroland.services.ILeagueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class LeagueService implements ILeagueService {

	static Logger LOGGER = LoggerFactory.getLogger(LeagueService.class);

	@Autowired
	private ILeagueRepository leagueRepository;

	@Autowired
	private IMySummonerRepository summonerRepository;

	@Autowired
	private IMyMatchReferenceRepository matchReferenceRepository;

	private RiotApi api;

	public LeagueService() throws IOException {
		init();
	}

	@SneakyThrows
	void init() {
		FileReader reader = new FileReader("src/main/resources/properties/riotAPI.properties");
		Properties p = new Properties();
		p.load(reader);

		if (p.containsKey("key")) {
			ApiConfig config = new ApiConfig().setKey(p.getProperty("key"));
			api = new RiotApi(config);
		}
	}

	@Override
	public LeagueData createOrUpdate(Summoner player, List<MatchReference> lastTenMatches, String username) {

		MySummoner myPlayer = summonerRepository.save(new MySummoner(player));

		List<MyMatchReference> myLastTenMatches = new ArrayList<>();

		for (var match : lastTenMatches)
			myLastTenMatches.add(matchReferenceRepository.save(new MyMatchReference(match)));

		return leagueRepository.save(new LeagueData(myPlayer, myLastTenMatches, username));
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
	public void requestInformation(User user) {
		String leagueAccountId = user.getLeagueID();
		if (leagueAccountId != null) {
			Platform platform = getRegion(leagueAccountId);
			String riotName = leagueAccountId.substring(0, leagueAccountId.lastIndexOf("-"));

			LOGGER.info("Getting League information for: " + leagueAccountId + " (" + user.getName() + ")");

			try {

				Summoner summoner = api.getSummonerByName(platform, riotName);
				List<MatchReference> matchList = api.getMatchListByAccountId(platform, summoner.getAccountId()).getMatches();

				//var savedData = createOrUpdate(summoner, matchList.subList(0, 10), leagueAccountId);

				user.setLeagueData(new LeagueData(summoner, matchList.subList(0, 10), leagueAccountId));
			} catch (RiotApiException e) {
				e.printStackTrace();
			}
		} else {
			user.setLeagueData(new LeagueData());
		}
	}

	private Platform getRegion(String accountId) {
		String region = accountId.substring(accountId.lastIndexOf("-") + 1);

		return switch (region.toUpperCase()) {
			case "EUNE" -> Platform.EUNE;
			case "EUW" -> Platform.EUW;
			case "BR" -> Platform.BR;
			case "JP" -> Platform.JP;
			case "KR" -> Platform.KR;
			case "LAN" -> Platform.LAN;
			case "LAS" -> Platform.LAS;
			case "OCE" -> Platform.OCE;
			case "NA" -> Platform.NA;
			case "TR" -> Platform.TR;
			default -> Platform.RU;
		};
	}
}
