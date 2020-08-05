package org.github.boziroland.DAL.impl;

import org.github.boziroland.DAL.IAPIData1DAO;
import org.github.boziroland.entities.OverwatchData;

import java.util.*;

public class APIData1InMemory implements IAPIData1DAO {

	Map<Integer, OverwatchData> idSpecificdataMap = new HashMap<>();

	@Override
	public void createOrUpdate(OverwatchData player) {
		idSpecificdataMap.put(player.getId(), player);
	}

	@Override
	public List<OverwatchData> findByName(String name) {
		var ret = new ArrayList<OverwatchData>();

		for (var elem : idSpecificdataMap.entrySet())
			if (elem.getValue().getUsername().equals(name))
				ret.add(elem.getValue());

		return ret;
	}

	@Override
	public Optional<OverwatchData> findByID(int id) {
		return Optional.ofNullable(idSpecificdataMap.get(id));
	}

	@Override
	public List<OverwatchData> list() {
		return new ArrayList<>(idSpecificdataMap.values());
	}

	@Override
	public void deleteByName(String name) {
		var usersToRemove = findByName(name);

		for (var user : usersToRemove) {
			idSpecificdataMap.remove(user.getId());
		}
	}

	@Override
	public void deleteByID(int id) {
		idSpecificdataMap.remove(id);
	}
}
