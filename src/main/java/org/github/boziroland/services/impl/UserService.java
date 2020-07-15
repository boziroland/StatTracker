package org.github.boziroland.services.impl;

import org.github.boziroland.DAL.IUserDAO;
import org.github.boziroland.entities.*;
import org.github.boziroland.services.IUserService;

import java.util.List;
import java.util.Optional;

public class UserService implements IUserService {

    IUserDAO dao;

    public UserService(IUserDAO dao) {
        this.dao = dao;
    }

    @Override
    public void createOrUpdate(int id, String name, String password, String email, List<Milestone> milestones, List<Comment> comments, LeaguePlayer leaguePlayer, SpecificAPIData1 specificPlayer) {

        User user = new User(id, name, password, email, milestones, comments, leaguePlayer, specificPlayer);

        //TODO itt le lehetne kérdezni napi 1x a felhasználó adatait
        //TODO viszont akkor talán egy időt is kéne felhasználónként tárolni, hogy neki mikor kérdezzük

        dao.createOrUpdate(user);
    }

    @Override
    public Optional<User> findById(int ID) {
        return dao.findById(ID);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if(email.matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$"))
            return dao.findByEmail(email);

        return Optional.empty();
    }

    @Override
    public List<User> findByName(String name) {
        return dao.findByName(name);
    }

    @Override
    public List<User> list() {
        return dao.list();
    }

    @Override
    public void delete(int id, String name, String password, String email, List<Milestone> milestones, List<Comment> comments, LeaguePlayer leaguePlayer, SpecificAPIData1 specificPlayer) {
        dao.delete(new User(id, name, password, email, milestones, comments, leaguePlayer, specificPlayer));
    }

    @Override
    public void requestInformation(GeneralAPIData gap) {

    }

    @Override
    public void sendEmail() {

    }
}
