package org.github.boziroland.services;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.github.boziroland.DAL.IUserDAO;
import org.github.boziroland.entities.Comment;
import org.github.boziroland.entities.User;
import org.github.boziroland.exceptions.RegistrationException;
import org.passay.*;

import java.util.List;
import java.util.Optional;

/**
 * The interface IUserService defines the performable CRUD and other operations on the
 * @see User class.
 */
public interface IUserService {

	/**
	 * Passes the User instance to
	 *
	 * @param user The user to pass
	 * @see IUserDAO#createOrUpdate(User)
	 */
	User create(User user);

	/**
	 * Passes the User instance to
	 *
	 * @param user The user to pass
	 * @see IUserDAO#createOrUpdate(User)
	 */
	void update(User user);

	/**
	 * Updates the name which will be used to retrieve League information about the user
	 * @param id The id of the user whose League name is to be updated
	 * @param name The name which the name shall be updated to
	 * @return True if the user was found, and therefore their name got updated, false otherwise
	 */
	default boolean updateLeagueName(Integer id, String name){
		Optional<User> user = findById(id);
		if(user.isPresent()){
			user.get().setLeagueID(name);
			update(user.get());
			return true;
		}
		return false;
	}

	/**
	 * Updates the name which will be used to retrieve Overwatch information about the user
	 * @param id The id of the user whose Overwatch name is to be updated
	 * @param name The name which the name shall be updated to
	 * @return True if the user was found, and therefore their name got updated, false otherwise
	 */
	default boolean updateOWName(Integer id, String name){
		Optional<User> user = findById(id);
		if(user.isPresent()){
			user.get().setOverwatchID(name);
			update(user.get());
			return true;
		}
		return false;
	}

	/**
	 * Creates a User instance, and passes it to
	 *
	 * @param name       		The user's name
	 * @param password   		The user's password
	 * @param email      		The user's email
	 * @param comments   		The user's comments
	 * @param commentsOnProfile The comments on the user's profile
	 * @param leagueName 		The user's League account name
	 * @param overwatchName 	The user's Overwatch account name
	 * @see IUserDAO#createOrUpdate(User)
	 */
	User create(String name, String password, String email, List<Comment> commentsOnProfile, List<Comment> comments, String leagueName, String overwatchName);

	/**
	 * Creates a User instance, and passes it to
	 *
	 * @param id         		The user's ID
	 * @param name       		The user's name
	 * @param password   		The user's password
	 * @param email      		The user's email
	 * @param comments   		The user's comments
	 * @param commentsOnProfile The comments on the user's profile
	 * @param leagueName 		The user's League account name
	 * @param overwatchName 	The user's Overwatch account name
	 * @see IUserDAO#createOrUpdate(User)
	 */
	void update(int id, String name, String password, String email, List<Comment> commentsOnProfile, List<Comment> comments, String leagueName, String overwatchName);

	/**
	 * Finds a user by their ID
	 *
	 * @param ID The user's ID
	 * @return The user, wrapped in an Optional container
	 */
	Optional<User> findById(int ID);

	/**
	 * Finds a user by their email
	 *
	 * @param email The user's email address
	 * @return The user, wrapped in an Optional container
	 */
	Optional<User> findByEmail(String email);

	/**
	 * Finds users by a name
	 *
	 * @param name The user's name
	 * @return A List of the found users
	 */
	List<User> findByName(String name);

	/**
	 * Lists every user
	 *
	 * @return A List of every user
	 */
	List<User> list();

	/**
	 * Creates a User instance, and passes it to
	 *
	 * @param id         		The user's ID
	 * @param name       		The user's name
	 * @param password   		The user's password
	 * @param email      		The user's email
	 * @param commentsOnProfile The comments on the user's profile
	 * @param comments   		The user's comments
	 * @param leagueName 		The user's League account name
	 * @param overwatchName  	The user's Overwatch account name
	 * @see IUserDAO#createOrUpdate(User)
	 */
	void delete(int id, String name, String password, String email, List<Comment> commentsOnProfile, List<Comment> comments, String leagueName, String overwatchName);

	/**
	 * @param user The user to delete
	 */
	void delete(User user);

	/**
	 * @param id The id of the user to delete
	 */
	void deleteById(int id);

	/**
	 * Requests information about the user from the API service given in the parameter
	 *
	 * @param id  The id of the user whose information we want to request
	 * @param service The api service
	 */
	void requestInformation(int id, IAPIService service);

	/**
	 * Requests information about the user from the API service given in the parameter
	 *
	 * @param user The user whose information we want to request
	 * @param service The api service
	 */
	void requestInformation(User user, IAPIService service);

	/**
	 * Send an email to the user.
	 *
	 * @param user    The user whom we want to email
	 * @param message The message to send
	 */
	void sendEmail(User user, String message);

	/**
	 * Registers a user.
	 *
	 * @param name              The user's name
	 * @param password          The user's password
	 * @param email             The user's email
	 * @param commentsOnProfile The comments on the user's profile
	 * @param comments          The user's comments
	 * @param leagueName        The user's League account name
	 * @param overwatchName     The user's Overwatch account name
	 * @return The registered user, wrapped in an Optional container
	 */
	Optional<User> register(String name, String password, String email, List<Comment> commentsOnProfile, List<Comment> comments, String leagueName, String overwatchName);

	/**
	 * Registers a user.
	 *
	 * @param name       The user's name
	 * @param password   The user's password
	 * @param email      The user's email
	 * @param leagueName The user's League account name
	 * @param overwatchName  The user's Overwatch account name
	 * @return The registered user, wrapped in an Optional container
	 */
	Optional<User> register(String name, String password, String email, String leagueName, String overwatchName);

	/**
	 * Registers the user in the parameter
	 *
	 * @param user The user to register
	 * @return The registered user, wrapped in an Optional container
	 */
	Optional<User> register(User user);

	/**
	 * Logs the user in
	 *
	 * @param email    The email address of the user
	 * @param password The password of the user
	 * @return The logged in user, wrapped in an Optional container
	 */
	Optional<User> login(String email, String password);

	/**
	 * Checks a user's milestones, if one's requirements are fulfilled, sends an email by calling the
	 * @see IUserService#sendEmail(User, String) function
	 *
	 * @param user The users whose milestones we want to check
	 */
	void checkMilestones(User user);

}
