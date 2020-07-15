package org.github.boziroland.DAL.impl;

import org.github.boziroland.DAL.IUserDAO;
import org.github.boziroland.entities.User;

import java.util.*;

public class UserInMemory implements IUserDAO {

    List<User> users = new ArrayList<>();

    @Override
    public void createOrUpdate(User userToAdd) {
        var user = findById(userToAdd.getID());
        if(user.isEmpty()) {
            users.add(userToAdd);
        }else{
            var actualUser = user.get();
            for(var elem : users){
                if(elem == actualUser){
                    elem.setName(actualUser.getName());
                    elem.setEmail(actualUser.getEmail());
                    elem.setMilestones(actualUser.getMilestones());
                    elem.setPassword(actualUser.getPassword());
                }
            }
        }
    }

    @Override
    public Optional<User> findById(int ID) {

        for(var user : users)
            if(user.getID() == ID)
                return Optional.of(user);

        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {

        for(var user : users)
            if(user.getEmail().equals(email))
                return Optional.of(user);

        return Optional.empty();
    }

    @Override
    public List<User> findByName(String name) {

        List<User> ret = new ArrayList<>();

        for(var user : users)
            if(user.getName().equals(name))
                ret.add(user);

        return ret;
    }

    @Override
    public List<User> list() {
        return users;
    }

    @Override
    public void delete(User userToDelete) {
        var user = findById(userToDelete.getID());
        user.ifPresent(value -> users.remove(value));
    }
}
