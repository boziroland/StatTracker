package org.github.boziroland.services.impl;

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

import java.util.List;
import java.util.Optional;

public class OverwatchService implements IOverwatchService {

	Logger LOGGER = LoggerFactory.getLogger(OverwatchService.class);

	@Autowired
	IOverwatchRepository overwatchRepository;

	@Autowired
	IOWPlayerRepository owPlayerReposity;

	private final RestTemplate restTemplate = new RestTemplateBuilder().build();

	public OverwatchService() {
	}

	@Override
	public void createOrUpdate(OWPlayer player) {
		var myOwPlayer = owPlayerReposity.save(player);
		overwatchRepository.save(new OverwatchData(myOwPlayer));
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
		OverwatchData location = new OverwatchData();
		String accountId = user.getOverwatchID();
		String name = accountId.substring(0, accountId.lastIndexOf("-"));
		String region = accountId.substring(name.length());

		LOGGER.info("Getting Overwatch information for: " + accountId);

		ResponseEntity<OWPlayer> response = restTemplate.getForEntity("http://owapi.io/profile/pc/" + region + "/" + accountId, OWPlayer.class);
		location.setPlayer(response.getBody());
		user.setOverwatchData(location);
	}
}
