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

	public OverwatchService() {}

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
		String name = owAccountId.substring(0, owAccountId.lastIndexOf("-"));
		String region = owAccountId.substring(name.length());

		LOGGER.info("Getting Overwatch information for: " + owAccountId + " (" + user.getName() + ")");

		ResponseEntity<OWPlayer> response = restTemplate.getForEntity("http://owapi.io/profile/pc/" + region + "/" + owAccountId, OWPlayer.class);
		var savedData = createOrUpdate(response.getBody(), owAccountId);
		user.setOverwatchData(savedData);
	}
}
