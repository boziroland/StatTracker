package org.github.boziroland.services;

import org.github.boziroland.entities.OverwatchData;
import org.github.boziroland.entities.apiEntities.OWPlayer;
import org.github.boziroland.repositories.IOverwatchRepository;

import java.util.List;
import java.util.Optional;

/**
 * The interface IOverwatchService defines the performable CRUD operations on the
 *
 * @see OverwatchData class.
 */
public interface IOverwatchService extends IAPIService {

	/**
	 * Creates an
	 *
	 * @param player   The player to store
	 * @param username The name of the player
	 * @return The saved data
	 * @see OverwatchData from the player in the parameter and passes it to the
	 * @see IOverwatchRepository#save(Object) function
	 */
	OverwatchData createOrUpdate(OWPlayer player, String username);

	/**
	 * Finds an
	 *
	 * @param id The id of the data to find
	 * @return The data we found, wrapped in an Optional container
	 * @see OverwatchData by its id
	 */
	Optional<OverwatchData> findById(int id);

	/**
	 * Lists every stored
	 *
	 * @see OverwatchData
	 */
	List<OverwatchData> list();

	/**
	 * Removes the data with the id in the parameter
	 *
	 * @param id The id of the data to remove
	 */
	void deleteById(int id);
}
