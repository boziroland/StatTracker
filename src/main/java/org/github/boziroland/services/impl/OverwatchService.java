package org.github.boziroland.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.github.boziroland.entities.OverwatchData;
import org.github.boziroland.entities.User;
import org.github.boziroland.entities.apiEntities.OWPlayer;
import org.github.boziroland.repositories.IOverwatchRepository;
import org.github.boziroland.repositories.apiEntityRepositories.IOWPlayerRepository;
import org.github.boziroland.services.IOverwatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OverwatchService implements IOverwatchService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OverwatchService.class);
	private final RestTemplate restTemplate = new RestTemplateBuilder().build();
	@Autowired
	private IOverwatchRepository overwatchRepository;
	@Autowired
	private IOWPlayerRepository owPlayerReposity;

	public OverwatchService() {
	}

	@Override
	public OverwatchData createOrUpdate(OWPlayer player, String username) {
		var myOwPlayer = owPlayerReposity.save(player);
		return overwatchRepository.save(new OverwatchData(myOwPlayer, username));
	}

	@Override
	public Optional<OverwatchData> findById(int id) {
		return overwatchRepository.findById(id);
	}

	@Override
	public List<OverwatchData> list() {
		return overwatchRepository.findAll();
	}

	@Override
	public void deleteById(int id) {
		overwatchRepository.deleteById(id);
	}

	@Override
	public void requestInformation(User user) {
		String owAccountId = user.getOverwatchID();
		LOGGER.info("Trying to get Overwatch information for: " + owAccountId + " (" + user.getName() + ")");
		if (owAccountId != null) {
			String name = owAccountId.substring(0, owAccountId.lastIndexOf("-"));
			String region = owAccountId.substring(owAccountId.lastIndexOf("-") + 1);

			LOGGER.info("Getting Overwatch information for: " + owAccountId + " (" + user.getName() + ")");

			ResponseEntity<OWPlayer> response = restTemplate.getForEntity("http://owapi.io/profile/pc/" + region + "/" + name, OWPlayer.class);
			user.setOverwatchData(new OverwatchData(response.getBody(), owAccountId));
			LOGGER.info("Done getting Overwatch information for: " + owAccountId + " (" + user.getName() + ")");
		}
	}

	public boolean checkUser(String accountId) {
		String name = accountId.substring(0, accountId.lastIndexOf("-")).replace("#", "-");
		String region = accountId.substring(accountId.lastIndexOf("-") + 1);
		String url = "http://owapi.io";
		String parameters = "/profile/pc/" + region + "/" + name;
		ObjectMapper mapper = new ObjectMapper();
		try {
			LOGGER.info("Checking Overwatch account for name {}", accountId);
			Map<String, Object> map = mapper.readValue(new URL(new URL(url), parameters), Map.class);
			LOGGER.info("Got back: " + map.get("message"));
			return !map.containsKey("message");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
