package org.github.boziroland.services;

import org.github.boziroland.entities.User;

/**
 * The interface IAPIService provides a common base interface for the specific API data classes.
 */
public interface IAPIService {

	/**
	 * Requests information form the API service for the user in the parameters
	 *
	 * @param user The user whose information we want to request
	 */
	void requestInformation(User user);

	/**
	 * Checks if the user whose name is in the parameter can be queried from the game's API
	 *
	 * @param accountId The user's account name
	 * @return true, if they can be, false otherwise
	 */
	boolean checkUser(String accountId);
}
