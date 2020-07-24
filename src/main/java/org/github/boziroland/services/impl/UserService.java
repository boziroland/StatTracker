package org.github.boziroland.services.impl;

import jdk.jshell.spi.ExecutionControl;
import lombok.SneakyThrows;
import org.github.boziroland.entities.Comment;
import org.github.boziroland.entities.GeneralAPIData;
import org.github.boziroland.entities.User;
import org.github.boziroland.exceptions.RegistrationException;
import org.github.boziroland.repositories.IUserRepository;
import org.github.boziroland.services.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UserService implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IMilestoneService milestoneService;

    ISecurityService securityService = new SecurityService();

    @Autowired
    ILeagueService leagueService;

    ScheduledInformationRetrieverService sirs = new ScheduledInformationRetrieverService();

    Map<User, LocalTime> userQueryTimeMap = new HashMap<>();

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public UserService() {//TODO inject other api service too
        scheduleHourlyQuery();
    }

    @Override
    public User create(User user) {
        //TODO ezt valahogy jobban kéne
        for (var m : user.getLeagueMilestones().entrySet()) {
            var milestone = milestoneService.createOrUpdate(m.getKey());
            int value = user.getLeagueMilestones().remove(m.getKey());
            user.getLeagueMilestones().put(milestone, value);
        }

        for (var m : user.getGameMilestones2().entrySet())
            milestoneService.createOrUpdate(m.getKey());

        User savedUser = userRepository.save(user);
        int secondsInADay = 24 * 60 * 60;
        userQueryTimeMap.put(user, LocalTime.ofSecondOfDay(new Random().nextInt(secondsInADay)));
        return savedUser;
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public User create(String name, String password, String email, List<Comment> commentsOnProfile, List<Comment> comments, String leagueName, String gameName2) {
        return create(new User(name, password, email, commentsOnProfile, comments, leagueName, gameName2));
    }

    @Override
    public void update(int id, String name, String password, String email, List<Comment> commentsOnProfile, List<Comment> comments, String leagueName, String gameName2) {
        update(new User(name, password, email, commentsOnProfile, comments, leagueName, gameName2));
    }

    @Override
    public Optional<User> findById(int ID) {
        return userRepository.findById(ID);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if (isValidEmail(email))
            return Optional.ofNullable(userRepository.findByEmail(email));

        return Optional.empty();
    }

    @Override
    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public List<User> list() {
        return userRepository.findAll();
    }

    @Override
    public void delete(int id, String name, String password, String email, List<Comment> commentsOnProfile, List<Comment> comments, String leagueName, String gameName2) {
        userRepository.delete(new User(name, password, email, commentsOnProfile, comments, leagueName, gameName2));
    }

    @Override
    public void delete(User user) {
        userRepository.deleteById(user.getId());
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public void requestInformation(int id, IAPIService IAPIService, GeneralAPIData location) {
        var user = findById(id);

        if (user.isPresent())
            IAPIService.requestInformation(user.get().getLeagueID(), location);
        else
            throw new RuntimeException("Nincs ilyen id-vel rendelkező felhasználó!");
    }

    @SneakyThrows
    @Override
    public void sendEmail(int id, String message) {
        var user = findById(id);

        if (user.isPresent()) {
            var userEmail = user.get().getEmail();
            throw new ExecutionControl.NotImplementedException("should have sent an email");
        } else {
            throw new RuntimeException("Nincs ilyen id-vel rendelkező felhasználó!");
        }
    }

    @Override
    public Optional<User> register(String name, String password, String email, List<Comment> commentsOnProfile, List<Comment> comments, String leagueID, String gameName2) throws RegistrationException {
        if (findByEmail(email).isPresent()) {
            throw new RegistrationException("Email cím foglalt!");
        } else {
            if (!isValidUsername(name)) {
                throw new RegistrationException("A felhasználónévnek legalább 5 hosszúnak kell lennie!");
            } else if (!isValidPassword(password)) {
                throw new RegistrationException("Túl gyenge jelszó! A jelszónak tartalmaznia kell legalább 1 nagybetűt, 1 kisbetűt, 1 számot, nem tartalmazhat szóközt és legalább 8 karakter hosszúnak kell lennie!");
            } else if (!isValidEmail(email)) {
                throw new RegistrationException("Rossz email!");
            } else {
                Optional<User> user = Optional.of(new User(name, securityService.hashPassword(password), email, commentsOnProfile, comments, leagueID, gameName2));
                return Optional.of(create(user.get()));
            }
        }
    }

    @Override
    public Optional<User> register(String name, String password, String email, String leagueName, String gameName2) throws RegistrationException {
        return register(name, password, email, List.of(), List.of(), leagueName, gameName2);
    }

    @Override
    public Optional<User> login(String email, String password) {
        String hashedPassword = securityService.hashPassword(password);
        Optional<User> user = findByEmail(email);

        if (user.isPresent())
            if (user.get().getPassword().equals(hashedPassword))
                return user;

        return Optional.empty();
    }

    @Override
    public void checkMilestones(int id) {
        var user = findById(id);

        var milestones = user.get();

        for (var m : milestones.getLeagueMilestones().entrySet())
            if (milestoneService.checkAchievement(m.getValue(), m.getKey()))
                sendEmail(id, "Gratulálok! A(z) " + m.getKey().getName() + " nevű mérföldkő követelményét teljesítetted!");
    }

    @Override
    public void scheduleHourlyQuery() {
        Runnable command = () -> {
            updateUsersToQuery();
            scheduleHourlyQuery();
        };
        long delay = ChronoUnit.SECONDS.between(LocalTime.now(), LocalTime.now().plusHours(1));
        scheduler.schedule(command, delay, TimeUnit.SECONDS);
    }

    @Override
    public void updateUsersToQuery() {
        for (var entry : userQueryTimeMap.entrySet()) {
            LocalTime queryTime = entry.getValue();
            sirs.setRetrieveTime(queryTime);
            sirs.retrieve(entry.getKey(), leagueService);
        }
    }
}
