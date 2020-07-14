package org.github.boziroland.DAL;

import org.github.boziroland.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserDAO {

    void createorUpdate(User user);
    Optional<User> findById(int ID);
    Optional<User> findByEmail(String email);
    List<User> findByName(String name);
    List<User> list();
    void delete(User user);

}
