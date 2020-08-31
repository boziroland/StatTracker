package org.github.boziroland.services.impl;

import org.apache.commons.lang3.tuple.Pair;
import org.github.boziroland.Constants;
import org.github.boziroland.entities.Comment;
import org.github.boziroland.entities.User;
import org.github.boziroland.exceptions.LoginException;
import org.github.boziroland.exceptions.RegistrationException;
import org.github.boziroland.repositories.IUserRepository;
import org.github.boziroland.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalTime;
import java.util.*;

public class UserService implements IUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IMilestoneService milestoneService;

	@Autowired
	private ISecurityService securityService;

	@Autowired
	List<IAPIService> apiServices;

	@Autowired
	private IScheduledInformationRetrieverService sirs;

	@Override
	public User create(User user) {

		for(var apiService : apiServices)
			requestInformation(user, apiService);

		User savedUser = userRepository.save(user);

		milestoneService.addMilestones(savedUser);

		addUserToScheduler(savedUser);
		update(user);

		return savedUser;
	}

	@Override
	public User create(String name, String password, String email, List<Comment> commentsOnProfile, List<Comment> comments, String leagueName, String overwatchName) {
		return create(new User(name, password, email, commentsOnProfile, comments, leagueName, overwatchName));
	}

	@Override
	public User update(User user) {
		return userRepository.save(user);
	}

	@Override
	public User updatePassword(User user, String password) {
		user.setPassword(securityService.hashPassword(password));
		return update(user);
	}

	@Override
	public User updateEmail(User user, String email) {
		user.setEmail(email);
		return update(user);
	}

	@Override
	public User updateProfileVisibility(User user, boolean isPublic) {
		user.setProfilePublic(isPublic);
		return update(user);
	}

	@Override
	public User updateEmailReceivability(User user, boolean canReceive) {
		user.setSendEmails(canReceive);
		return update(user);
	}

	public User updateOWName(User user, String name) {
		user.setOverwatchID(name);
		return update(user);
	}

	public User updateLeagueName(User user, String name) {
		user.setLeagueID(name);
		return update(user);
	}

	@Override
	public User update(int id, String name, String password, String email, List<Comment> commentsOnProfile, List<Comment> comments, String leagueName, String overwatchName) {
		return update(new User(name, password, email, commentsOnProfile, comments, leagueName, overwatchName));
	}

	@Override
	public Optional<User> findById(int ID) {
		return userRepository.findById(ID);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		if (securityService.isValidEmail(email))
			return Optional.ofNullable(userRepository.findByEmail(email));

		return Optional.empty();
	}

	@Override
	public Optional<User> findByName(String name) {
		return Optional.ofNullable(userRepository.findByName(name));
	}

	@Override
	public List<User> findByNameContaining(String name) {
		return userRepository.findByNameContaining(name);
	}

	@Override
	public List<User> list() {
		return userRepository.findAll();
	}

	@Override
	public void delete(int id, String name, String password, String email, List<Comment> commentsOnProfile, List<Comment> comments, String leagueName, String overwatchName) {
		userRepository.delete(new User(name, password, email, commentsOnProfile, comments, leagueName, overwatchName));
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
	public void requestInformation(int id, IAPIService service) {
		var user = findById(id);

		if (user.isPresent()) {
			service.requestInformation(user.get());
		} else {
			throw new RuntimeException("Nincs ilyen id-vel rendelkező felhasználó!");
		}
	}

	@Override
	public void requestInformation(User user, IAPIService service) {
		service.requestInformation(user);
	}

	@Override
	public Optional<User> register(String name, String password, String email, List<Comment> commentsOnProfile, List<Comment> comments, String leagueID, String overwatchName) {
		if (findByEmail(email).isPresent()) {
			throw new RegistrationException("Email cím foglalt!");
		} else {
			boolean isValidUsername = securityService.isValidUsername(name);
			boolean isValidEmail = securityService.isValidEmail(email);
			Pair<Boolean, String> isValidPassword = securityService.isValidPassword(password);

			if (!isValidUsername) {
				throw new RegistrationException("A felhasználónévnek legalább 5 karakter hosszúnak kell lennie!");
			} else if(findByName(name).isPresent()){
				throw new RegistrationException("Létezik már ilyen nevű felhasználó!");
			}else if (!isValidEmail) {
				throw new RegistrationException("Formailag helytelen email!");
			} else if(findByEmail(email).isPresent()){
				throw new RegistrationException("Ez az email cím már használatban van!");
			} else if (!isValidPassword.getLeft()) {
				throw new RegistrationException(isValidPassword.getRight());
			} else {
				LOGGER.info("New registered user: {}", name);
				Optional<User> user = Optional.of(new User(name, securityService.hashPassword(password), email, commentsOnProfile, comments, leagueID, overwatchName));
				return Optional.of(create(user.get()));
			}
		}
	}

	@Override
	public Optional<User> register(String name, String password, String email, String leagueName, String overwatchName) {
		return register(name, password, email, List.of(), List.of(), leagueName, overwatchName);
	}

	@Override
	public Optional<User> register(User user) {
		return register(user.getName(), user.getPassword(), user.getEmail(), user.getLeagueID(), user.getOverwatchID());
	}

	@Override
	public Optional<User> login(String email, String password) {
		Optional<User> user = findByEmail(email);

		if (user.isPresent()) {
			if (securityService.checkPassword(password, user.get().getPassword())) {
				LOGGER.info("{} just logged in!", user.get().getName());
				return user;
			} else {
				throw new LoginException("Rossz jelszó!");
			}
		} else {
			throw new LoginException("Rossz email cím!");
		}
	}

	private void addUserToScheduler(User user) {
		int leagueDelay = (int) Constants.DATA_RETRIEVE_DELAY_IN_SECONDS;
		int owDelay = (int) Constants.DATA_RETRIEVE_DELAY_IN_SECONDS;
		for(var apiService : apiServices)
			sirs.retrieve(user, apiService, leagueDelay);
		LOGGER.info("Added user " + user.getName() + " to for scheduling, League in " + LocalTime.ofSecondOfDay(leagueDelay) + ", OW in " + LocalTime.ofSecondOfDay(owDelay));
	}

	public IScheduledInformationRetrieverService getSirs() {
		return sirs;
	}
}
