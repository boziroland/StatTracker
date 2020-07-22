package org.github.boziroland.services;

import org.github.boziroland.DAL.IAPIData1DAO;
import org.github.boziroland.entities.SpecificAPIData1;

import java.util.List;
import java.util.Optional;

/**
 * The interface IAPIData1Service defines the performable CRUD operations on the SpecificAPIData1 class.
 */
public interface IAPIData1Service extends IAPIService {

    //TODO comments

    /**
     * Creates a SpecificAPIData1 instance and passes it to
     * @see IAPIData1DAO#createOrUpdate(SpecificAPIData1)
     *@param token The OAuth token of the player
     * @param username The username of the player
     * @param userID The ID of the player
     */
    void createOrUpdate(String token, String username, int userID);

    /**
     * @see IAPIData1DAO#findByID(int)
     * @param id
     */
    Optional<SpecificAPIData1> findById(int id);

    /**
     * @see IAPIData1DAO#list()
     */
    List<SpecificAPIData1> list();

    /**
     * @see IAPIData1DAO#deleteByID(int)
     * @param id
     */
    void deleteById(int id);
}
