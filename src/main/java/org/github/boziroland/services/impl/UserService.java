package org.github.boziroland.services.impl;

import org.github.boziroland.DAL.IUserDAO;
import org.github.boziroland.DAL.impl.LeagueDataInMemory;
import org.github.boziroland.entities.*;
import org.github.boziroland.services.IUserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserService implements IUserService {

    IUserDAO dao;

    LeagueService ls = new LeagueService(new LeagueDataInMemory());

    public UserService(IUserDAO dao) {
        this.dao = dao;
    }

    @Override
    public void createOrUpdate(User user) {
        dao.createOrUpdate(user);
    }

    @Override
    public void createOrUpdate(int id, String name, String password, String email, MilestoneHolder milestones, List<Comment> comments, String leagueName, String gameName2) {

        createOrUpdate(new User(id, name, password, email, milestones, comments, leagueName, gameName2));

        //TODO itt le lehetne kérdezni napi 1x a felhasználó adatait
        //TODO viszont akkor talán egy időt is kéne felhasználónként tárolni, hogy neki mikor kérdezzük

    }

    @Override
    public Optional<User> findById(int ID) {
        return dao.findById(ID);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if(isValidEmail(email))
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
    public void delete(int id, String name, String password, String email, MilestoneHolder milestones, List<Comment> comments, String leagueName, String gameName2) {
        dao.delete(new User(id, name, password, email, milestones, comments, leagueName, gameName2));
    }

    @Override
    public void requestInformation(int id, APIService gap) {
        var user = dao.findById(id);

        if(user.isPresent()){
            Map<String, String> data = gap.requestInformation(user.get().getLeagueName());

            //user.get().setLeagueData();

        }else{
            throw new RuntimeException("Nincs ilyen id-vel rendelkező felhasználó!");
        }
    }

    @Override
    public void sendEmail(int id, String message) {
        var user = dao.findById(id);

        if(user.isPresent()){
            var userEmail = user.get().getEmail();
                //TODO send email
        }else{
            throw new RuntimeException("Nincs ilyen id-vel rendelkező felhasználó!");
        }
    }

    @Override
    public Optional<User> register(int id, String name, String password, String email, MilestoneHolder milestones, List<Comment> comments, String leagueName, String gameName2) {
        if(dao.findByEmail(email).isPresent()){
            throw new RuntimeException("Email cím foglalt!");
        }else{
            if(!password.matches("^(?=.*[A-Z].*[A-Z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8,}$")){
                throw new RuntimeException("Túl gyenge jelszó! A jelszónak tartalmaznia kell legalább 2 nagybetűt, 3 kisbetűt, 2 számot, 1 speciális karaktert és legalább 8 hosszúnak kell lennie!");
            }else if(!isValidEmail(email)){
                throw new RuntimeException("Rossz email!");
            }else{
                Optional<User> user = Optional.of(new User(id, name,password, email, milestones, comments, leagueName, gameName2));
                createOrUpdate(user.get());
                return user;
            }
        }
    }

    @Override
    public Optional<User> login(String username, String password) {
        for(var user : findByName(username))
            if(user.getPassword().equals(password))
                return Optional.of(user);

        return Optional.empty();
    }

    @Override
    public void checkMilestones(int id) {
        var user = dao.findById(id);

        var milestones = user.get().getMilestones();

        for(var m : milestones.getLeagueMilestones().entrySet()){
            if(m.getValue() >= m.getKey().getRequirement()){
                //TODO
            }
        }
    }
}
