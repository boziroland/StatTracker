package org.github.boziroland.services.impl;

import org.github.boziroland.entities.Comment;
import org.github.boziroland.entities.GeneralAPIData;
import org.github.boziroland.entities.MilestoneHolder;
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

    //IUserDAO userDao;
    @Autowired
    IUserRepository userRepository;

    IMilestoneService milestoneService = new MilestoneService();
    ISecurityService securityService = new SecurityService();

    ILeagueService leagueService;

    ScheduledInformationRetrieverService sirs = new ScheduledInformationRetrieverService();

    Map<User, LocalTime> userQueryTimeMap = new HashMap<>();

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public UserService() {
        scheduleHourlyQuery();
    }

    public UserService(ILeagueService leagueService) {//TODO inject other api service too
        scheduleHourlyQuery();
        this.leagueService = leagueService;
    }

    @Override
    public void create(User user) {
        userRepository.save(user);
        int secondsInADay = 24 * 60 * 60;
        userQueryTimeMap.put(user, LocalTime.ofSecondOfDay(new Random().nextInt(secondsInADay)));
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public void create(int id, String name, String password, String email, MilestoneHolder milestones, List<Comment> commentsOnProfile, List<Comment> comments, String leagueName, String gameName2) {
        create(new User(id, name, password, email, milestones, commentsOnProfile, comments, leagueName, gameName2));
    }

    @Override
    public void update(int id, String name, String password, String email, MilestoneHolder milestones, List<Comment> commentsOnProfile, List<Comment> comments, String leagueName, String gameName2) {
        update(new User(id, name, password, email, milestones, commentsOnProfile, comments, leagueName, gameName2));
    }

    @Override
    public Optional<User> findById(int ID) {
        return userRepository.findById(ID);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if(isValidEmail(email))
            return Optional.of(userRepository.findByEmail(email));
            //return userDao.findByEmail(email);

        return Optional.empty();
    }

    @Override
    public List<User> findByName(String name) {
        return userRepository.findByName(name);
        //return userDao.findByName(name);
    }

    @Override
    public List<User> list() {
        return userRepository.findAll();
    }

    @Override
    public void delete(int id, String name, String password, String email, MilestoneHolder milestones, List<Comment> commentsOnProfile, List<Comment> comments, String leagueName, String gameName2) {
        userRepository.delete(new User(id, name, password, email, milestones, commentsOnProfile, comments, leagueName, gameName2));
    }

    @Override
    public void requestInformation(int id, IAPIService IAPIService, GeneralAPIData location) {
        var user = findById(id);

        if(user.isPresent()){
            IAPIService.requestInformation(user.get().getLeagueID(), location);

            //user.get().setLeagueData(); TODO

        }else{
            throw new RuntimeException("Nincs ilyen id-vel rendelkező felhasználó!");
        }
    }

    @Override
    public void sendEmail(int id, String message) {
        var user = findById(id);

        if(user.isPresent()){
            var userEmail = user.get().getEmail();
                //TODO send email
        }else{
            throw new RuntimeException("Nincs ilyen id-vel rendelkező felhasználó!");
        }
    }

    @Override
    public Optional<User> register(int id, String name, String password, String email, MilestoneHolder milestones, List<Comment> commentsOnProfile, List<Comment> comments, String leagueID, String gameName2) throws RegistrationException {
        if(findByEmail(email).isPresent()){
            throw new RegistrationException("Email cím foglalt!");
        }else{
            if(!password.matches("^(?=.*[A-Z].*[A-Z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8,}$")){
                throw new RegistrationException("Túl gyenge jelszó! A jelszónak tartalmaznia kell legalább 2 nagybetűt, 3 kisbetűt, 2 számot, 1 speciális karaktert és legalább 8 hosszúnak kell lennie!");
            }else if(!isValidEmail(email)){
                throw new RegistrationException("Rossz email!");
            }else{
                Optional<User> user = Optional.of(new User(id, name, securityService.hashPassword(password), email, milestones, commentsOnProfile, comments, leagueID, gameName2));
                create(user.get());
                return user;
            }
        }
    }

    @Override
    public Optional<User> login(String email, String password) {
        String hashedPassword = securityService.hashPassword(password);
        Optional<User> user = findByEmail(email);

        if(user.isPresent())
            if (user.get().getPassword().equals(hashedPassword))
                return user;

        return Optional.empty();
    }

    @Override
    public void checkMilestones(int id) {
        var user = findById(id);

        var milestones = user.get().getMilestones();

        for(var m : milestones.getLeagueMilestones().entrySet())
            if(milestoneService.checkAchievement(m.getValue(), m.getKey()))
                sendEmail(id, "Gratulálok! A(z) " + m.getKey().getName() + " nevű mérföldkő követelményét teljesítetted!");
    }

    void scheduleHourlyQuery(){
        Runnable command = () -> {
            updateUsersToQuery();
            scheduleHourlyQuery();
        };
        long delay = ChronoUnit.SECONDS.between(LocalTime.now(), LocalTime.now().plusHours(1));
        scheduler.schedule(command, delay, TimeUnit.SECONDS);
    }

    void updateUsersToQuery(){
        for(var entry : userQueryTimeMap.entrySet()){
            LocalTime queryTime = entry.getValue();
            sirs.setRetrieveTime(queryTime);
            sirs.retrieve(entry.getKey(), leagueService);
        }
    }
}
