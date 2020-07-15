package org.github.boziroland.services;

import org.github.boziroland.entities.*;
import org.github.boziroland.DAL.IUserDAO;

import java.util.List;
import java.util.Optional;

/**
 * The interface IUserService defines the performable CRUD operations on the User class.
 */
public interface IUserService{

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
     * @param leaguePlayer The user's League account
     * @param specificPlayer The user's <i>Specific</i> account
     */
    void createOrUpdate(int id, String name, String password, String email, List<Milestone> milestones, List<Comment> comments, LeaguePlayer leaguePlayer, SpecificAPIData1 specificPlayer);

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
     * @param leaguePlayer The user's League account
     * @param specificPlayer The user's <i>Specific</i> account
     */
    void delete(int id, String name, String password, String email, List<Milestone> milestones, List<Comment> comments, LeaguePlayer leaguePlayer, SpecificAPIData1 specificPlayer);

    void requestInformation(GeneralAPIData gap);

    void sendEmail();
}
