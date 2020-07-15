package org.github.boziroland.services.impl;

import org.github.boziroland.DAL.IMilestoneDAO;
import org.github.boziroland.entities.Milestone;
import org.github.boziroland.services.IMilestoneService;

import java.util.List;
import java.util.Optional;

public class MilestoneService implements IMilestoneService {

    IMilestoneDAO dao;

    public MilestoneService(IMilestoneDAO dao) {
        this.dao = dao;
    }

    @Override
    public void createOrUpdate(String name, String description, int requirement) {
        dao.createOrUpdate(new Milestone(name, description, requirement));
    }

    @Override
    public Optional<Milestone> findByName(String name) {
        return dao.findByName(name);
    }

    @Override
    public List<Milestone> list() {
        return dao.list();
    }

    @Override
    public void deleteByName(String name) {
        dao.deleteByName(name);
    }

    @Override
    public void delete(String name, String description, int requirement) {
        dao.delete(new Milestone(name, description, requirement));
    }
}
