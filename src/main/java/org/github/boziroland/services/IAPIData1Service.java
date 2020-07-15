package org.github.boziroland.services;

import org.github.boziroland.DAL.IAPIData1DAO;
import org.github.boziroland.entities.SpecificAPIData1;

import java.util.List;
import java.util.Optional;

/**
 * The interface IAPIData1Service defines the performable CRUD operations on the SpecificAPIData1 class.
 */
public interface IAPIData1Service {

    /**
     * @see IAPIData1DAO#createOrUpdate(SpecificAPIData1)
     */
    void createOrUpdate(SpecificAPIData1 player);

    /**
     * @see IAPIData1DAO#findByName(String)
     */
    List<SpecificAPIData1> findByName(String name);

    /**
     * @see IAPIData1DAO#findByID(String)
     */
    Optional<SpecificAPIData1> findByID(String id);

    /**
     * @see IAPIData1DAO#list()
     */
    List<SpecificAPIData1> list();

    /**
     * @see IAPIData1DAO#deleteByName(String)
     */
    void deleteByName(String name);

    /**
     * @see IAPIData1DAO#deleteByID(String)
     */
    void deleteByID(String id);

}
