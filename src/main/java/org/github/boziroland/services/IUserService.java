package org.github.boziroland.services;

import org.github.boziroland.entities.User;
import org.github.boziroland.DAL.IUserDAO;

import java.util.List;
import java.util.Optional;

/**
 * The interface IUserService defines the performable CRUD operations on the User class.
 */
public interface IUserService{

    /**
     * @see IUserDAO#createOrUpdate(User)
     */
    void createorUpdate(User user);

    /**
     * @see IUserDAO#findById(int)
     */
    Optional<User> findById(int ID);

    /**
     * @see IUserDAO#findByEmail(String)
     */
    Optional<User> findByEmail(String email);

    /**
     * @see IUserDAO#findByName(String)
     */
    List<User> findByName(String name);

    /**
     * @see IUserDAO#list()
     */
    List<User> list();

    /**
     * @see IUserDAO#delete(User)
     */
    void delete(User user);


}
