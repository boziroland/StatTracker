package org.github.boziroland.services.impl;

import org.github.boziroland.entities.GeneralAPIData;
import org.github.boziroland.entities.OverwatchData;
import org.github.boziroland.entities.apiEntities.OWPlayer;
import org.github.boziroland.repositories.IAPIData1Repository;
import org.github.boziroland.services.IOverwatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class OverwatchService implements IOverwatchService {

	@Autowired
	IAPIData1Repository data1Repository;

	private final RestTemplate restTemplate = new RestTemplateBuilder().build();

	public OverwatchService() {
	}

	@Override
	public void createOrUpdate(String token, String username, int userID) {
		data1Repository.save(new OverwatchData());
	}

	@Override
	public Optional<OverwatchData> findById(int id) {
		return data1Repository.findById(id);
	}

	@Override
	public List<OverwatchData> list() {
		return data1Repository.findAll();
	}

	@Override
	public void deleteById(int id) {
		data1Repository.deleteById(id);
	}

	@Override
	//TODO
	public void requestInformation(String accountId, GeneralAPIData location) {
		ResponseEntity<OWPlayer> response = restTemplate.getForEntity("http://owapi.io/profile/pc/us/Spricsma-21972", OWPlayer.class);
		((OverwatchData)location).setPlayer(response.getBody());
	}
}
