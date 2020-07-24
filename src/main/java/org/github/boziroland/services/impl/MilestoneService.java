package org.github.boziroland.services.impl;

import org.github.boziroland.entities.Milestone;
import org.github.boziroland.repositories.IMilestoneRepository;
import org.github.boziroland.services.IMilestoneService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class MilestoneService implements IMilestoneService {

    @Autowired
    IMilestoneRepository milestoneRepository;

    public MilestoneService() {}

    @Override
    public Milestone createOrUpdate(String name, String description, int requirement, Milestone.Game game) {
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
    public void delete(String name, String description, int requirement, Milestone.Game game) {
        milestoneRepository.delete(new Milestone(name, description, requirement, game));
    }

    @Override
    public boolean checkAchievement(int userScore, Milestone m){
        if(userScore >= m.getRequirement() && !m.isDoneAlready()) {
            m.setDoneAlready(true);
            return true;
        }
        return false;
    }
}
