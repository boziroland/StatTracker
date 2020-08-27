package org.github.boziroland.services;

import org.github.boziroland.DAL.IMilestoneDAO;
import org.github.boziroland.entities.Milestone;
import org.github.boziroland.entities.User;
import org.github.boziroland.repositories.IMilestoneRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The interface IMilestoneService defines the performable CRUD operations on the
 *
 * @see Milestone class, as well as a way to check a users completed achievements.
 */
public interface IMilestoneService {

	/**
	 * Creates a milestone instance and passes it to
	 *
	 * @param name        The name of the milestone
	 * @param description The description of the milestone
	 * @param requirement The point requiremnt of the milestone
	 * @return The saved milestone
	 * @see IMilestoneRepository#save(Object)
	 */
	Milestone createOrUpdate(String name, String description, Integer requirement, Milestone.Game game);

	/**
	 * Passes the milestone in the parameter to
	 *
	 * @param m The milestone to store
	 * @return The saved milestone
	 * @see IMilestoneRepository#save(Object)
	 */
	Milestone createOrUpdate(Milestone m);

	/**
	 * Finds a Milestone by its name
	 *
	 * @param name The name of the wanted Milestone
	 * @return The Milestone, wrapped in an optional container
	 */
	Optional<Milestone> findByName(String name);

	/**
	 * Lists every Milestone
	 *
	 * @return A List of every Milestone
	 */
	List<Milestone> list();

	/**
	 * Deletes a milestone, given its name
	 *
	 * @param name The name of the milestone we want to remove
	 */
	void deleteByName(String name);

	/**
	 * Creates a milestone instance and passes it to
	 *
	 * @param name        The name of the Milestone
	 * @param description The description of the Milestone
	 * @param requirement The point requirement for the Milestone
	 * @param game        The game which the Milestone is associated with
	 * @see IMilestoneDAO#delete(Milestone)
	 */
	void delete(String name, String description, Integer requirement, Milestone.Game game);

	/**
	 * Returns a list of the completed achievements
	 *
	 * @param user The user whose achievements are to be checked
	 * @return The completed achievements
	 */
	List<String> checkAchievements(User user);

	void addMilestones(User user);

	void setMilestones(List<Milestone> m);

	List<Milestone> getMilestonesAsList();

	Set<Milestone> getMilestonesAsSet();

}
