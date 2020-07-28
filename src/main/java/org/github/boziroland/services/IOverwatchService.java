package org.github.boziroland.services;

import org.github.boziroland.DAL.IAPIData1DAO;
import org.github.boziroland.entities.OverwatchData;
import org.github.boziroland.entities.apiEntities.OWPlayer;

import java.util.List;
import java.util.Optional;

/**
 * The interface IAPIData1Service defines the performable CRUD operations on the SpecificAPIData1 class.
 */
public interface IOverwatchService extends IAPIService {

	//TODO comments

	/**
	 * TODO
	 * @param player
	 */
	void createOrUpdate(OWPlayer player);

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
