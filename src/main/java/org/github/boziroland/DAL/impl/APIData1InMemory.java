package org.github.boziroland.DAL.impl;

import org.github.boziroland.DAL.IAPIData1DAO;
import org.github.boziroland.entities.SpecificAPIData1;

import java.util.*;

public class APIData1InMemory implements IAPIData1DAO {

    Map<Integer, SpecificAPIData1> idSpecificdataMap = new HashMap<>();

    @Override
    public void createOrUpdate(SpecificAPIData1 player) {
        idSpecificdataMap.put(player.getId(), player);
    }

    @Override
    public List<SpecificAPIData1> findByName(String name) {
        var ret = new ArrayList<SpecificAPIData1>();

        for (var elem : idSpecificdataMap.entrySet())
            if(elem.getValue().getUsername().equals(name))
                ret.add(elem.getValue());

        return ret;
    }

    @Override
    public Optional<SpecificAPIData1> findByID(int id) {
        return Optional.ofNullable(idSpecificdataMap.get(id));
    }

    @Override
    public List<SpecificAPIData1> list() {
        return new ArrayList<>(idSpecificdataMap.values());
    }

    @Override
    public void deleteByName(String name) {
        var usersToRemove = findByName(name);

        for(var user : usersToRemove){
            idSpecificdataMap.remove(user.getId());
        }
    }

    @Override
    public void deleteByID(int id) {
        idSpecificdataMap.remove(id);
    }
}
