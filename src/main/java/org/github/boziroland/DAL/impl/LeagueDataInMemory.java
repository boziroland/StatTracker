package org.github.boziroland.DAL.impl;

import org.github.boziroland.DAL.ILeagueDAO;
import org.github.boziroland.entities.LeagueData;

import java.util.*;

public class LeagueDataInMemory implements ILeagueDAO {

	Map<String, LeagueData> idPlayerMap = new HashMap<>();

	@Override
	public void createOrUpdate(LeagueData player) {
		idPlayerMap.put(player.getPlayer().getAccountId(), player);
	}

	@Override
	public List<LeagueData> findByUsername(String name) {
		var ret = new ArrayList<LeagueData>();

		for (var player : idPlayerMap.entrySet()) {
			var currentPlayer = player.getValue();
			if (currentPlayer.getPlayer().getName().equals(name))
				ret.add(currentPlayer);
		}

		return ret;
	}

	@Override
	public Optional<LeagueData> findByUserId(String id) {
		return Optional.ofNullable(idPlayerMap.get(id));
	}

	@Override
	public List<LeagueData> list() {
		return new ArrayList<>(idPlayerMap.values());
	}

	@Override
	public void deleteByName(String name) {
		var usersToRemove = findByUsername(name);

		for (var user : usersToRemove) {
			idPlayerMap.remove(user.getPlayer().getAccountId());
		}
	}

	@Override
	public void deleteById(String id) {
		idPlayerMap.remove(id);
	}
}
