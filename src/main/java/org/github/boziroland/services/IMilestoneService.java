package org.github.boziroland.services;

import org.github.boziroland.DAL.IMilestoneDAO;
import org.github.boziroland.entities.Milestone;
import org.github.boziroland.repositories.IMilestoneRepository;

import java.util.List;
import java.util.Optional;

/**
 * The interface IMilestoneService defines the performable CRUD operations on the Milestone class.
 */
public interface IMilestoneService {

	/**
	 * Creates a milestone instance and passes it to
	 * @see IMilestoneRepository#save(Object)
	 * @param name The name of the milestone
	 * @param description The description of the milestone
	 * @param requirement The point requiremnt of the milestone
	 * @return The saved milestone
	 */
	Milestone createOrUpdate(String name, String description, int requirement, Milestone.Game game);

	/**
	 * Passes the milestone in the paramter to
	 * @see IMilestoneRepository#save(Object)
	 * @param m The milestone to store
	 * @return The saved milestone
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
	void delete(String name, String description, int requirement, Milestone.Game game);

	/**
	 * Checks whether the given score is above or equal to the achievement's requirement
	 *
	 * @param userScore The user's score
	 * @param m         The achievement we are checking
	 * @return True if it's above or equal, false otherwise
	 */
	boolean checkAchievement(int userScore, Milestone m);
}
