package org.github.boziroland.services;

import org.github.boziroland.entities.GeneralAPIData;
import org.github.boziroland.entities.OverwatchData;
import org.github.boziroland.entities.User;
import org.github.boziroland.entities.apiEntities.OWPlayer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

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
}
