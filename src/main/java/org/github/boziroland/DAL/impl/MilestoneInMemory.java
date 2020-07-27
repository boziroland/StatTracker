package org.github.boziroland.DAL.impl;

import org.github.boziroland.DAL.IMilestoneDAO;
import org.github.boziroland.entities.Milestone;

import java.util.*;

public class MilestoneInMemory implements IMilestoneDAO {

	Map<String, Milestone> nameMilestoneMap = new HashMap<>();

	@Override
	public void createOrUpdate(Milestone milestone) {
		nameMilestoneMap.put(milestone.getName(), milestone);
	}

	@Override
	public Optional<Milestone> findByName(String name) {
		return Optional.ofNullable(nameMilestoneMap.get(name));
	}

	@Override
	public List<Milestone> list() {
		return new ArrayList<>(nameMilestoneMap.values());
	}

	@Override
	public void deleteByName(String name) {
		nameMilestoneMap.remove(name);
	}

	@Override
	public void delete(Milestone milestone) {
		nameMilestoneMap.remove(milestone.getName());
	}
}
