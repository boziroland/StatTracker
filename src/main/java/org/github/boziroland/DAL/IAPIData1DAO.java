package org.github.boziroland.DAL;

import org.github.boziroland.entities.SpecificAPIData1;

import java.util.List;
import java.util.Optional;

/**
 * The interface IAPIData1DAO defines the performable CRUD operations on the SpecificAPIData1 class.
 */
public interface IAPIData1DAO {

    /**
     * Creates or updates an existing record of a SpecificAPIData1.
     * @param player The SpecificAPIData1 we want to add/update
     */
    void createOrUpdate(SpecificAPIData1 player);

    /**
     * Finds SpecificAPIData1s by their name.
     * @param name The name of the SpecificAPIData1 we want to find
     * @return A List of the found SpecificAPIData1s
     */
    List<SpecificAPIData1> findByName(String name);

    /**
     * Finds a SpecificAPIData1 by their ID.
     * @param id The ID of the SpecificAPIData1 we want to fund
     * @return The SpecificAPIData1 we found, wrapped into an Optional Container
     */
    Optional<SpecificAPIData1> findByID(String id);

    /**
     * Lists every SpecificAPIData1.
     * @return A List of every SpecificAPIData1
     */
    List<SpecificAPIData1> list();

    /**
     * Removes every SpecificAPIData1 with a certain name.
     * @param name The SpecificAPIData1 name we want to remove
     */
    void deleteByName(String name);

    /**
     * Removes every SpecificAPIData1 by their ID,
     * @param id The ID of the player we want to remove
     */
    void deleteByID(String id);
}
