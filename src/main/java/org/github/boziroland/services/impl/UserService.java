package org.github.boziroland.services.impl;

import org.github.boziroland.DAL.IUserDAO;
import org.github.boziroland.DAL.impl.MilestoneInMemory;
import org.github.boziroland.entities.*;
import org.github.boziroland.exceptions.RegistrationException;
import org.github.boziroland.services.*;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UserService implements IUserService {

    IUserDAO userDao;

    IMilestoneService milestoneService = new MilestoneService(new MilestoneInMemory());
    ISecurityService securityService = new SecurityService();

    ILeagueService leagueService;

    ScheduledInformationRetrieverService sirs = new ScheduledInformationRetrieverService();

    Map<User, LocalTime> userQueryTimeMap = new HashMap<>();

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public UserService(IUserDAO userDao) {
        scheduleHourlyQuery();
        this.userDao = userDao;
    }

    public UserService(IUserDAO userDao, ILeagueService leagueService) {//TODO inject other api service too
        scheduleHourlyQuery();
        this.userDao = userDao;
        this.leagueService = leagueService;
    }

    @Override
    public void create(User user) {
        userDao.createOrUpdate(user);
        int secondsInADay = 24 * 60 * 60;
        userQueryTimeMap.put(user, LocalTime.ofSecondOfDay(new Random().nextInt(secondsInADay)));
    }

    @Override
    public void update(User user) {
        userDao.createOrUpdate(user);
    }

    @Override
    public void create(int id, String name, String password, String email, MilestoneHolder milestones, List<Comment> comments, String leagueName, String gameName2) {
        create(new User(id, name, password, email, milestones, comments, leagueName, gameName2));
    }

    @Override
    public void update(int id, String name, String password, String email, MilestoneHolder milestones, List<Comment> comments, String leagueName, String gameName2) {
        update(new User(id, name, password, email, milestones, comments, leagueName, gameName2));
    }

    @Override
    public Optional<User> findById(int ID) {
        return userDao.findById(ID);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if(isValidEmail(email))
            return userDao.findByEmail(email);

        return Optional.empty();
    }

    @Override
    public List<User> findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public List<User> list() {
        return userDao.list();
    }

    @Override
    public void delete(int id, String name, String password, String email, MilestoneHolder milestones, List<Comment> comments, String leagueName, String gameName2) {
        userDao.delete(new User(id, name, password, email, milestones, comments, leagueName, gameName2));
    }

    @Override
    public void requestInformation(int id, IAPIService IAPIService, GeneralAPIData location) {
        var user = userDao.findById(id);

        if(user.isPresent()){
            IAPIService.requestInformation(user.get().getLeagueID(), location);

            //user.get().setLeagueData(); TODO

        }else{
            throw new RuntimeException("Nincs ilyen id-vel rendelkező felhasználó!");
        }
    }

    @Override
    public void sendEmail(int id, String message) {
        var user = userDao.findById(id);

        if(user.isPresent()){
            var userEmail = user.get().getEmail();
                //TODO send email
        }else{
            throw new RuntimeException("Nincs ilyen id-vel rendelkező felhasználó!");
        }
    }

    @Override
    public Optional<User> register(int id, String name, String password, String email, MilestoneHolder milestones, List<Comment> comments, String leagueID, String gameName2) throws RegistrationException {
        if(userDao.findByEmail(email).isPresent()){
            throw new RegistrationException("Email cím foglalt!");
        }else{
            if(!password.matches("^(?=.*[A-Z].*[A-Z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8,}$")){
                throw new RegistrationException("Túl gyenge jelszó! A jelszónak tartalmaznia kell legalább 2 nagybetűt, 3 kisbetűt, 2 számot, 1 speciális karaktert és legalább 8 hosszúnak kell lennie!");
            }else if(!isValidEmail(email)){
                throw new RegistrationException("Rossz email!");
            }else{
                Optional<User> user = Optional.of(new User(id, name, securityService.hashPassword(password), email, milestones, comments, leagueID, gameName2));
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
        var user = userDao.findById(id);

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

    public void setLeagueService(ILeagueService leagueService) {
        this.leagueService = leagueService;
    }
}
