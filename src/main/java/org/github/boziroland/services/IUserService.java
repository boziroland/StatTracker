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
 * The interface IUserService defines the performable CRUD and other operations on the User class.
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
	 * Creates a User instance, and passes it to
	 *
	 * @param name       The user's name
	 * @param password   The user's password
	 * @param email      The user's email
	 * @param comments   The user's comments
	 * @param leagueName The user's League account name
	 * @param gameName2  The user's <i>Specific</i> account name
	 * @see IUserDAO#createOrUpdate(User)
	 */
	User create(String name, String password, String email, List<Comment> commentsOnProfile, List<Comment> comments, String leagueName, String gameName2);

	/**
	 * Creates a User instance, and passes it to
	 *
	 * @param id         The user's ID
	 * @param name       The user's name
	 * @param password   The user's password
	 * @param email      The user's email
	 * @param comments   The user's comments
	 * @param leagueName The user's League account name
	 * @param gameName2  The user's <i>Specific</i> account name
	 * @see IUserDAO#createOrUpdate(User)
	 */
	void update(int id, String name, String password, String email, List<Comment> commentsOnProfile, List<Comment> comments, String leagueName, String gameName2);

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
	 * @param id         The user's ID
	 * @param name       The user's name
	 * @param password   The user's password
	 * @param email      The user's email
	 * @param comments   The user's comments
	 * @param leagueName The user's League account name
	 * @param gameName2  The user's <i>Specific</i> account name
	 * @see IUserDAO#createOrUpdate(User)
	 */
	void delete(int id, String name, String password, String email, List<Comment> commentsOnProfile, List<Comment> comments, String leagueName, String gameName2);

	/**
	 * @param user The user to delete
	 */
	void delete(User user);

	/**
	 * @param id The id of the user to delete
	 */
	void deleteById(int id);

	/**
	 * Requests information about the user from the GeneralAPIDataSource.
	 *  @param id       The id of the user whose information we want to request
	 * @param gap      The data source service
	 */
	void requestInformation(int id, IAPIService gap);

	/**
	 * TODO
	 * @param user
	 * @param gap
	 */
	void requestInformation(User user, IAPIService gap);

	/**
	 * Send an email to the user.
	 *
	 * @param user The user whom we want to email
	 * @param message The message to send
	 */
	void sendEmail(User user, String message);

	/**
	 * Registers a user.
	 *
	 * @param name              The user's name
	 * @param password          The user's password
	 * @param email             The user's email
	 * @param commentsOnProfile The comments on the user's comments
	 * @param comments          The user's comments
	 * @param leagueName        The user's League account name
	 * @param gameName2         The user's <i>Specific</i> account name
	 * @return The registered user, wrapped in an Optional container
	 */
	Optional<User> register(String name, String password, String email, List<Comment> commentsOnProfile, List<Comment> comments, String leagueName, String gameName2) throws RegistrationException;

	/**
	 * Registers a user.
	 *
	 * @param name       The user's name
	 * @param password   The user's password
	 * @param email      The user's email
	 * @param leagueName The user's League account name
	 * @param gameName2  The user's <i>Specific</i> account name
	 * @return The registered user, wrapped in an Optional container
	 */
	Optional<User> register(String name, String password, String email, String leagueName, String gameName2) throws RegistrationException;

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
	 * @param email    The email addresss of the user
	 * @param password The password of the user
	 * @return The logged in user, wrapped in an Optional container
	 */
	Optional<User> login(String email, String password);

	/**
	 * Checks the validity of an email
	 *
	 * @param email The email to check
	 * @return True, if it's a valid email, false otherwise
	 */
	default boolean isValidEmail(String email) {
		EmailValidator validator = EmailValidator.getInstance();
		return validator.isValid(email);
	}

	/**
	 * Checks the validity of a username
	 * By default, a username has to be at least 5 characters long
	 *
	 * @param username The username to check
	 * @return True, if the username is valid, false otherwise
	 */
	default boolean isValidUsername(String username) {
		return username.length() >= 5;
	}

	/**
	 * @throws RegistrationException
	 *
	 * Checks the validity of a password
	 * By default, a password is valid if:
	 *  - It's at least 8 characters long
	 *  - Contains at least 1 uppercase letter
	 *  - Contains at least 1 lowercase letter
	 *  - Contains at least 1 digit
	 *  - Contains no whitespace characters
	 *
	 * @param password The password to check
	 * @return True if the password is valid, throws an exception with the problems with the password otherwise
	 */
	default boolean isValidPassword(String password) {
		PasswordValidator validator = new PasswordValidator(
				new LengthRule(8),
				new CharacterRule(EnglishCharacterData.UpperCase, 1),
				new CharacterRule(EnglishCharacterData.LowerCase, 1),
				new CharacterRule(EnglishCharacterData.Digit, 1),
				new WhitespaceRule()
		);
		var result = validator.validate(new PasswordData(password));

		if (result.isValid())
			return true;

		throw new RegistrationException("Invalid jelszó:\n" + StringUtils.join(validator.getMessages(result), "\n"));
	}

	/**
	 * Checks a user's milestones, if one's requirements are fulfilled, it sends an email
	 *
	 * @param user The users whose milestones we want to check
	 */
	void checkMilestones(User user);

}
