package org.github.boziroland.services.impl;

import com.sun.xml.bind.v2.TODO;
import jdk.jshell.spi.ExecutionControl;
import lombok.SneakyThrows;
import org.github.boziroland.entities.Comment;
import org.github.boziroland.entities.User;
import org.github.boziroland.exceptions.RegistrationException;
import org.github.boziroland.repositories.IUserRepository;
import org.github.boziroland.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UserService implements IUserService {

	Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Autowired
	IUserRepository userRepository;

	@Autowired
	IMilestoneService milestoneService;

	@Autowired
	ISecurityService securityService;

	@Autowired
	ILeagueService leagueService;

	@Autowired
	IOverwatchService overwatchService;

	ScheduledInformationRetrieverService sirs = new ScheduledInformationRetrieverService();

	Map<User, LocalTime> userQueryTimeMap = new HashMap<>();

	ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

	public UserService() {
		LOGGER.info("Constructor");
		scheduleHourlyQuery();
	}

	@Override
	public User create(User user) {
		generateMilestoneIDs(user);

		User savedUser = userRepository.save(user);

		addUserToScheduler(savedUser);

		return savedUser;
	}

	@Override
	public User create(String name, String password, String email, List<Comment> commentsOnProfile, List<Comment> comments, String leagueName, String gameName2) {
		return create(new User(name, password, email, commentsOnProfile, comments, leagueName, gameName2));
	}

	@Override
	public void update(User user) {
		userRepository.save(user);
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
	public void requestInformation(int id, IAPIService apiService) {
		var user = findById(id);

		if (user.isPresent())
			apiService.requestInformation(user.get());
		else
			throw new RuntimeException("Nincs ilyen id-vel rendelkező felhasználó!");
	}

	@Override
	public void requestInformation(User user, IAPIService apiService) {
		apiService.requestInformation(user);
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
	public Optional<User> register(String name, String password, String email, List<Comment> commentsOnProfile, List<Comment> comments, String leagueID, String overwatchName) {
		if (findByEmail(email).isPresent()) {
			throw new RegistrationException("Email cím foglalt!");
		} else {
			if (!isValidUsername(name)) {
				throw new RegistrationException("A felhasználónévnek legalább 5 hosszúnak kell lennie!");
			} else if (!isValidEmail(email)) {
				throw new RegistrationException("Rossz email!");
			} else if (isValidPassword(password)) {
				Optional<User> user = Optional.of(new User(name, securityService.hashPassword(password), email, commentsOnProfile, comments, leagueID, overwatchName));
				return Optional.of(create(user.get()));
			}
		}
		return Optional.empty();
	}

	@Override
	public Optional<User> register(String name, String password, String email, String leagueName, String overwatchName) throws RegistrationException {
		return register(name, password, email, List.of(), List.of(), leagueName, overwatchName);
	}

	@Override
	public Optional<User> register(User user) {
		user.setPassword(securityService.hashPassword(user.getPassword()));
		return register(user.getName(), user.getPassword(), user.getEmail(), user.getLeagueID(), user.getOverwatchID());
	}

	@Override
	public Optional<User> login(String email, String password) {
		Optional<User> user = findByEmail(email);

		if (user.isPresent())
			if (securityService.checkPassword(password, user.get().getPassword()))
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
		long delay = ChronoUnit.SECONDS.between(LocalTime.now(), LocalTime.now().plusMinutes(1));
		LOGGER.info("Next hourly query at: " + LocalTime.now().plusMinutes(1));
		scheduler.schedule(command, delay, TimeUnit.SECONDS);
	}

	@Override
	public void updateUsersToQuery() {
		for (var entry : userQueryTimeMap.entrySet()) {
			LocalTime queryTime = entry.getValue();
			if(queryTime.minusMinutes(1).isBefore(LocalTime.now())) {
				LOGGER.info("Next query at: " + queryTime);
				sirs.setRetrieveTime(queryTime);
				sirs.retrieve(entry.getKey(), leagueService);
				sirs.retrieve(entry.getKey(), overwatchService);
				userQueryTimeMap.put(entry.getKey(), LocalTime.now().plusSeconds(60));
			}
		}
		LOGGER.info("========================");
		LOGGER.info("SCHEDULES");
		for(var entry : userQueryTimeMap.entrySet()){
			LOGGER.info("User " + entry.getKey() + " is scheduled to query at: " + entry.getValue());
		}
		LOGGER.info("========================");
		//TODO
	}

	private void addUserToScheduler(User user){
		int secondsInADay = 60;//24 * 60 * 60;
		LOGGER.info("Adding user " + user.getName() + " to scheduler");
		userQueryTimeMap.put(user, LocalTime.now().plusSeconds(90));//LocalTime.ofSecondOfDay(new Random().nextInt(secondsInADay)));
	}

	private void generateMilestoneIDs(User user){

		//TODO ezt valahogy jobban kéne
		for (var m : user.getLeagueMilestones().entrySet()) {
			var milestone = milestoneService.createOrUpdate(m.getKey());
			int value = user.getLeagueMilestones().remove(m.getKey());
			user.getLeagueMilestones().put(milestone, value);
		}

		for (var m : user.getOverwatchMilestones().entrySet())
			milestoneService.createOrUpdate(m.getKey());
	}
}
