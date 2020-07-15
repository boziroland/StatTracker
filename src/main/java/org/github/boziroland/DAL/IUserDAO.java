package org.github.boziroland.DAL;

import org.github.boziroland.entities.User;

import java.util.List;
import java.util.Optional;

/**
 * The interface IUserDAO defines the performable CRUD operations on the User class.
 */
public interface IUserDAO {

    /**
     * Creates or updates an existing record of a user.
     * @param user The user to add/update
     */
    void createorUpdate(User user);

    /**
     * Finds a user by their ID.
     * @param ID The ID of the user we want to find
     * @return The user we found, wrapped into an Optional container
     */
    Optional<User> findById(int ID);

    /**
     * Finds a User by their email address.
     * @param email The email address of the user we want to find
     * @return The user we found, wrapped into an Optional container
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds users by their name.
     * @param name The name of the users we want to find
     * @return A List of the found users
     */
    List<User> findByName(String name);

    /**
     * Lists every user.
     * @return A List of every user
     */
    List<User> list();

    /**
     * Removes a user.
     * @param user The user we want to remove
     */
    void delete(User user);

}
