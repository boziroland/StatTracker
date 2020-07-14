package org.github.boziroland.services;

import org.github.boziroland.entities.SpecificAPIData1;

import java.util.List;
import java.util.Optional;

public interface IAPIData1Service {

    void createOrUpdate(SpecificAPIData1 param);
    List<SpecificAPIData1> findByName(String name);
    Optional<SpecificAPIData1> findByID(String id);
    List<SpecificAPIData1> list();
    void deleteByName(String name);
    void deleteByID(String id);

}
