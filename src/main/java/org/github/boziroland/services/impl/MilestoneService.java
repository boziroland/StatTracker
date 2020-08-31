package org.github.boziroland.services.impl;

import org.apache.commons.lang3.mutable.MutableInt;
import org.github.boziroland.Constants;
import org.github.boziroland.entities.Milestone;
import org.github.boziroland.entities.User;
import org.github.boziroland.repositories.IMilestoneRepository;
import org.github.boziroland.services.IMilestoneService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.*;

public class MilestoneService implements IMilestoneService {

	@Autowired
	private IMilestoneRepository milestoneRepository;

	private Set<Milestone> milestones;

	public void setMilestones(List<Milestone> m) {
		if (milestones == null)
			milestones = new HashSet<>(m);
	}

	public List<Milestone> getMilestonesAsList() {
		return new ArrayList<>(milestones);
	}

	public Set<Milestone> getMilestonesAsSet() {
		return milestones;
	}

	@PostConstruct
	void init() {
		List<Milestone> milestones = list();
		setMilestones(Collections.unmodifiableList(milestones));
	}

	@Override
	public Milestone createOrUpdate(String name, String description, Integer requirement, Milestone.Game game) {
		return milestoneRepository.save(new Milestone(name, description, requirement, game));
	}

	@Override
	public Milestone createOrUpdate(Milestone m) {
		return createOrUpdate(m.getName(), m.getDescription(), m.getRequirement(), m.getGame());
	}

	@Override
	public Optional<Milestone> findByName(String name) {
		return milestoneRepository.findById(name);
	}

	@Override
	public List<Milestone> list() {
		return milestoneRepository.findAll();
	}

	@Override
	public void deleteByName(String name) {
		milestoneRepository.deleteById(name);
	}

	@Override
	public void delete(String name, String description, Integer requirement, Milestone.Game game) {
		milestoneRepository.delete(new Milestone(name, description, requirement, game));
	}

	@Override
	public List<String> checkAchievements(User user) {
		List<String> ret = new ArrayList<>();

		var userMilestones = user.getMilestoneNameUserPointMap();

		for (var entry : milestones) {
			if (userMilestones.containsKey(entry.getName())) {
				if (userMilestones.get(entry.getName()).getValue() >= entry.getRequirement()) {
					ret.add(entry.getName());
				}
			}
		}
		return ret;
	}

	public void addMilestones(User user) {
		List<Milestone> milestones = getMilestonesAsList();
		Map<String, MutableInt> usersPreviousPoints = user.getMilestoneNameUserPointMap();

		Map<String, MutableInt> idPointMap = new HashMap<>();

		if (user.getLeagueData() != null && user.getLeagueData().getUsername() != null && user.getLeagueData().getPlayer() != null) {
			if (milestones.get(0).getRequirement() > user.getLeagueData().getPlayer().getSummonerLevel().getValue()) {
				idPointMap.put(milestones.get(0).getName(), user.getLeagueData().getPlayer().getSummonerLevel());
			} else if (usersPreviousPoints.size() > 0) {
				if (milestones.get(0).getRequirement() > usersPreviousPoints.get(milestones.get(0).getName()).getValue()
						&& milestones.get(0).getRequirement() <= user.getLeagueData().getPlayer().getSummonerLevel().getValue())

					idPointMap.put(milestones.get(0).getName(), user.getLeagueData().getPlayer().getSummonerLevel());
			}
		}

		if (user.getOverwatchData() != null && user.getOverwatchData().getUsername() != null && user.getOverwatchData().getPlayer() != null) {
			if (milestones.get(1).getRequirement() > user.getOverwatchData().getPlayer().getLevel().getValue()) {
				idPointMap.put(milestones.get(1).getName(), user.getOverwatchData().getPlayer().getLevel());
			} else if (usersPreviousPoints.size() > 0) {
				if (milestones.get(1).getRequirement() > usersPreviousPoints.get(milestones.get(1).getName()).getValue()
						&& milestones.get(1).getRequirement() <= user.getOverwatchData().getPlayer().getLevel().getValue()) {

					idPointMap.put(milestones.get(1).getName(), user.getOverwatchData().getPlayer().getLevel());
				}
			}

			// ...
		}

		user.setMilestoneNameUserPointMap(new HashMap<>(idPointMap));
		//update(user);
	}
}
