package org.github.boziroland.DAL;

import org.github.boziroland.entities.Milestone;

import java.util.List;
import java.util.Optional;

/**
 * The interface IMilestoneDAO defines the performable CRUD operations on the Milestone class.
 */
public interface IMilestoneDAO {

    /**
     * Creates or updates a milestone.
     * @param milestone The milestone the create/update
     */
    void createOrUpdate(Milestone milestone);

    /**
     * Finds a milestone by its name
     * @param name The name of the milestone we want to find
     * @return The Milestone we found, wrappedinto an Optional containe
     */
    Optional<Milestone> findByName(String name);

    /**
     * Lists every milestone.
     * @return The milestone list
     */
    List<Milestone> list();

    /**
     * Removes a milestone by its name.
     * @param name The name of the milestone
     */
    void deleteByName(String name);

    /**
     * Removes a milestone.
     * @param milestone The milestone to remove
     */
    void delete(Milestone milestone);
}
