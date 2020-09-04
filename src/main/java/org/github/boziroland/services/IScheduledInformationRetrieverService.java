package org.github.boziroland.services;

import org.github.boziroland.entities.User;

/**
 * The IScheduledInformationRetrieverService provides an interface for scheduled information retrieving from an API service.
 */
public interface IScheduledInformationRetrieverService {

	/**
	 * The method retrieves a user's information from an API service (specified in the parameters) after a given delay
	 *
	 * @param user    The user whose information is to be retrieved
	 * @param service The API's service which we want to retrieve data from
	 * @param delay   The delay after which we want to retrieve the information
	 */
	void retrieve(User user, IAPIService service, long delay);

	/**
	 * Sends an email to the user in the parameter with the message in the parameter
	 *
	 * @param user    The user whom the email is to be sent
	 * @param message The message of the email
	 */
	void sendEmail(User user, String message);

	/**
	 * Checks the users milestones
	 *
	 * @param user The user whose milestones are to be checked
	 */
	void checkMilestones(User user);
}
