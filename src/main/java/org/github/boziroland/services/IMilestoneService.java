package org.github.boziroland.services;

import org.github.boziroland.entities.Milestone;
import org.github.boziroland.DAL.IMilestoneDAO;

import java.util.List;
import java.util.Optional;

/**
 * The interface IMilestoneService defines the performable CRUD operations on the Milestone class.
 */
public interface IMilestoneService {

    /**
     * @see IMilestoneDAO#createOrUpdate(Milestone)
     */
    void createOrUpdate(Milestone milestone);

    /**
     * @see IMilestoneDAO#findByName(String)
     */
    Optional<Milestone> findByName(String name);

    /**
     * @see IMilestoneDAO#list()
     */
    List<Milestone> list();

    /**
     * @see IMilestoneDAO#deleteByName(String)
     */
    void deleteByName(String name);

    /**
     * @see IMilestoneDAO#delete(Milestone)
     */
    void delete(Milestone milestone);
}
