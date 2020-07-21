package org.github.boziroland.services;

import org.apache.commons.validator.routines.EmailValidator;
import org.github.boziroland.entities.*;
import org.github.boziroland.DAL.IUserDAO;
import org.github.boziroland.exceptions.RegistrationException;

import java.util.List;
import java.util.Optional;

/**
 * The interface IUserService defines the performable CRUD and other operations on the User class.
 */
public interface IUserService{

    /**
     * Passes the User instance to
     * @see IUserDAO#createOrUpdate(User)
     * @param user The user to pass
     */
    void create(User user);

    /**
     * Passes the User instance to
     * @see IUserDAO#createOrUpdate(User)
     * @param user The user to pass
     */
    void update(User user);

    /**
     * Creates a User instance, and passes it to
     * @see IUserDAO#createOrUpdate(User)
     *
     * @param id The user's ID
     * @param name The user's name
     * @param password The user's password
     * @param email The user's email
     * @param milestones The user's milestones
     * @param comments The user's comments
     * @param leagueName The user's League account name
     * @param gameName2 The user's <i>Specific</i> account name
     */
    void create(int id, String name, String password, String email, MilestoneHolder milestones, List<Comment> commentsOnProfile, List<Comment> comments, String leagueName, String gameName2);

    /**
     * Creates a User instance, and passes it to
     * @see IUserDAO#createOrUpdate(User)
     *
     * @param id The user's ID
     * @param name The user's name
     * @param password The user's password
     * @param email The user's email
     * @param milestones The user's milestones
     * @param comments The user's comments
     * @param leagueName The user's League account name
     * @param gameName2 The user's <i>Specific</i> account name
     */
    void update(int id, String name, String password, String email, MilestoneHolder milestones, List<Comment> commentsOnProfile, List<Comment> comments, String leagueName, String gameName2);

    /**
     * Finds a user by their ID
     * @param ID The user's ID
     * @return The user, wrapped in an Optional container
     */
    Optional<User> findById(int ID);

    /**
     * Finds a user by their email
     * @param email The user's email address
     * @return The user, wrapped in an Optional container
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds users by a name
     * @param name The user's name
     * @return A List of the found users
     */
    List<User> findByName(String name);

    /**
     * Lists every user
     * @return A List of every user
     */
    List<User> list();

    /**
     * Creates a User instance, and passes it to
     * @see IUserDAO#createOrUpdate(User)
     *
     * @param id The user's ID
     * @param name The user's name
     * @param password The user's password
     * @param email The user's email
     * @param milestones The user's milestones
     * @param comments The user's comments
     * @param leagueName The user's League account name
     * @param gameName2 The user's <i>Specific</i> account name
     */
    void delete(int id, String name, String password, String email, MilestoneHolder milestones, List<Comment> commentsOnProfile, List<Comment> comments, String leagueName, String gameName2);

    /**
     * Requests information about the user from the GeneralAPIDataSource.
     * @param id The id of the user whose information we want to request
     * @param gap The data source service
     * @param location Where to save the data
     */
    void requestInformation(int id, IAPIService gap, GeneralAPIData location);

    /**
     * Send an email to the user.
     * @param id The user's id whom we want to send the email to
     * @param message The message to send
     */
    void sendEmail(int id, String message);

    /**
     * Registers a user.
     *
     * @param id The user's ID
     * @param name The user's name
     * @param password The user's password
     * @param email The user's email
     * @param milestones The user's milestones
     * @param comments The user's comments
     * @param leagueName The user's League account name
     * @param gameName2 The user's <i>Specific</i> account name
     * @return The registered user, wrapped in an Optional container
     */
    Optional<User> register(int id, String name, String password, String email, MilestoneHolder milestones, List<Comment> commentsOnProfile, List<Comment> comments, String leagueName, String gameName2) throws RegistrationException;

    /**
     * Logs the user in
     *
     * @param email The email addresss of the user
     * @param password The password of the user
     * @return The logged in user, wrapped in an Optional container
     */
    Optional<User> login(String email, String password);

    /**
     * Checks the validity of an email
     * @param email The email to check
     * @return True, if it's a valid email, false otherwise
     */
    default boolean isValidEmail(String email){
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

    /**
     * Checks a user's milestones, if one's requirements are fulfilled, it sends an email and deletes the milestone
     * @param id The user's id whose milestone we want to check
     */
    void checkMilestones(int id);

}
