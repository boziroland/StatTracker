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
     * Creates a milestone instance and passes it to
     * @see IMilestoneDAO#createOrUpdate(Milestone)
     *
     * @param name
     * @param description
     * @param requirement
     */
    void createOrUpdate(String name, String description, int requirement, Milestone.Game game);

    /**
     * Finds a Milestone by its name
     * @param name The name of the wanted Milestone
     * @return The Milestone, wrapped in an optional container
     */
    Optional<Milestone> findByName(String name);

    /**
     * Lists every Milestone
     * @return A List of every Milestone
     */
    List<Milestone> list();

    /**
     * Deletes a milestone, given its name
     * @param name The name of the milestone we want to remove
     */
    void deleteByName(String name);

    /**
     * Creates a milestone instance and passes it to
     * @see IMilestoneDAO#delete(Milestone)
     *
     * @param name
     * @param description
     * @param requirement
     */
    void delete(String name, String description, int requirement, Milestone.Game game);
}
