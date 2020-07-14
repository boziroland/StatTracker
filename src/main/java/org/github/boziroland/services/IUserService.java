package org.github.boziroland.services;

import org.github.boziroland.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService{

    void createorUpdate(User user);
    Optional<User> findById(int ID);
    Optional<User> findByEmail(String email);
    List<User> findByName(String name);
    List<User> list();
    void delete(User user);


}
