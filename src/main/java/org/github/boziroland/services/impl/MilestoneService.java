package org.github.boziroland.services.impl;

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

	public MilestoneService() {}

	@PostConstruct
	void init(){
		/*
			... achievement létrehozás/beolvasás
		 */

		List<Milestone> milestones = new ArrayList<>();

		milestones.add(createOrUpdate("100-as szint", "Érd el a 100-as szintet", 100, Milestone.Game.LEAGUE));
		milestones.add(createOrUpdate("100-as szint", "Érd el a 100-as szintet", 100, Milestone.Game.OVERWATCH));
		// ...

		Constants.setMilestones(Collections.unmodifiableList(milestones));
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

		for(var entry : Constants.getMilestonesAsSet()){
			if(userMilestones.containsKey(entry.getName())){
				if(userMilestones.get(entry.getName()).getValue() >= entry.getRequirement()){
					ret.add(entry.getName());
				}
			}
		}
		return ret;
	}
}
