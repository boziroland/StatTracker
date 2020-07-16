package org.github.boziroland.DAL.impl;

import org.github.boziroland.DAL.IAPIData1DAO;
import org.github.boziroland.entities.SpecificAPIDataSource1;

import java.util.*;

public class APIData1InMemory implements IAPIData1DAO {

    Map<String, SpecificAPIDataSource1> idSpecificdataMap = new HashMap<>();

    @Override
    public void createOrUpdate(SpecificAPIDataSource1 player) {
        idSpecificdataMap.put(player.getUserID(), player);
    }

    @Override
    public List<SpecificAPIDataSource1> findByName(String name) {
        var ret = new ArrayList<SpecificAPIDataSource1>();

        for (var elem : idSpecificdataMap.entrySet()){
            if(elem.getValue().getUsername().equals(name)){
                ret.add(elem.getValue());
            }
        }

        return ret;
    }

    @Override
    public Optional<SpecificAPIDataSource1> findByID(String id) {
        return Optional.ofNullable(idSpecificdataMap.get(id));
    }

    @Override
    public List<SpecificAPIDataSource1> list() {
        return new ArrayList<>(idSpecificdataMap.values());
    }

    @Override
    public void deleteByName(String name) {
        var usersToRemove = findByName(name);

        for(var user : usersToRemove){
            idSpecificdataMap.remove(user.getUserID());
        }
    }

    @Override
    public void deleteByID(String id) {
        idSpecificdataMap.remove(id);
    }
}
