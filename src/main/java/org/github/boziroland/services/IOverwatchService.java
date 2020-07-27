package org.github.boziroland.services;

import org.github.boziroland.DAL.IAPIData1DAO;
import org.github.boziroland.entities.OverwatchData;

import java.util.List;
import java.util.Optional;

/**
 * The interface IAPIData1Service defines the performable CRUD operations on the SpecificAPIData1 class.
 */
public interface IOverwatchService extends IAPIService {

	//TODO comments

	/**
	 * Creates a SpecificAPIData1 instance and passes it to
	 *
	 * @param token    The OAuth token of the player
	 * @param username The username of the player
	 * @param userID   The ID of the player
	 * @see IAPIData1DAO#createOrUpdate(OverwatchData)
	 */
	void createOrUpdate(String token, String username, int userID);

	/**
	 * @param id
	 * @see IAPIData1DAO#findByID(int)
	 */
	Optional<OverwatchData> findById(int id);

	/**
	 * @see IAPIData1DAO#list()
	 */
	List<OverwatchData> list();

	/**
	 * @param id
	 * @see IAPIData1DAO#deleteByID(int)
	 */
	void deleteById(int id);
}
