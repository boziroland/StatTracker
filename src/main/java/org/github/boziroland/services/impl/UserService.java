package org.github.boziroland.services.impl;

import lombok.SneakyThrows;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.tuple.Pair;
import org.github.boziroland.Constants;
import org.github.boziroland.entities.Comment;
import org.github.boziroland.entities.Milestone;
import org.github.boziroland.entities.User;
import org.github.boziroland.exceptions.LoginException;
import org.github.boziroland.exceptions.RegistrationException;
import org.github.boziroland.repositories.IUserRepository;
import org.github.boziroland.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

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
	private ILeagueService leagueService;

	@Autowired
	private IOverwatchService overwatchService;

	@Autowired
	private JavaMailSender emailSender;

	private ScheduledInformationRetrieverService sirs = new ScheduledInformationRetrieverService();

	public UserService() {
	}

	@Override
	public User create(User user) {

		requestInformation(user, leagueService);
		requestInformation(user, overwatchService);

		User savedUser = userRepository.save(user);

		addMilestones(savedUser);

		addUserToScheduler(savedUser);

		return savedUser;
	}

	@Override
	public User create(String name, String password, String email, List<Comment> commentsOnProfile, List<Comment> comments, String leagueName, String overwatchName) {
		return create(new User(name, password, email, commentsOnProfile, comments, leagueName, overwatchName));
	}

	@Override
	public void update(User user) {
		var foundUser = userRepository.findById(user.getId());

		userRepository.save(user);
	}

	@Override
	public void update(int id, String name, String password, String email, List<Comment> commentsOnProfile, List<Comment> comments, String leagueName, String overwatchName) {
		update(new User(name, password, email, commentsOnProfile, comments, leagueName, overwatchName));
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
	public List<User> findByName(String name) {
		return userRepository.findByName(name);
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
			checkMilestones(user.get());
		} else {
			throw new RuntimeException("Nincs ilyen id-vel rendelkező felhasználó!");
		}
	}

	@Override
	public void requestInformation(User user, IAPIService service) {
		service.requestInformation(user);
	}

	@SneakyThrows
	@Override
	public void sendEmail(User user, String message) {

		var userEmail = user.getEmail();

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom("noreply@stattracker.com");
		simpleMailMessage.setTo(userEmail);
		simpleMailMessage.setSubject("Teljesítmény elérve!");
		simpleMailMessage.setText(message);
		emailSender.send(simpleMailMessage);

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
			} else if (!isValidEmail) {
				throw new RegistrationException("Rossz email!");
			} else if (!isValidPassword.getLeft()) {
				throw new RegistrationException(isValidPassword.getRight());
			}else{
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

	@Override
	public void checkMilestones(User user) {
		var completedMilestones = milestoneService.checkAchievements(user);

		for (var m : completedMilestones) {
			sendEmail(user, "Gratulálok!\n\nTeljesítetted a(z) " + m + " nevű teljesítmény követelményeit!");
			user.getMilestoneNameUserPointMap().remove(m);
		}
	}

	private void addUserToScheduler(User user) {
		Random random = new Random();
		int leagueDelay = random.nextInt(Math.toIntExact(Constants.DATA_RETRIEVE_DELAY_IN_SECONDS));
		int owDelay = random.nextInt(Math.toIntExact(Constants.DATA_RETRIEVE_DELAY_IN_SECONDS));
		sirs.retrieve(user, leagueService, leagueDelay);
		sirs.retrieve(user, overwatchService, owDelay);
		//TODO possibly átírni h napi 1x updateljen csak
		LOGGER.info("Added user " + user.getName() + " to for scheduling, League in " + LocalTime.ofSecondOfDay(leagueDelay) + ", OW in " + LocalTime.ofSecondOfDay(owDelay));
	}

	public void addMilestones(User user) {
		List<Milestone> milestones = Constants.getMilestonesAsList();

		Map<String, MutableInt> idPointMap = new HashMap<>();

		if (user.getLeagueData() != null) {
			if (milestones.get(0).getRequirement() > user.getLeagueData().getPlayer().getSummonerLevel().getValue())
				idPointMap.put(milestones.get(0).getName(), user.getLeagueData().getPlayer().getSummonerLevel());
			// ...
		}

		if (user.getOverwatchData() != null) {
			if (milestones.get(1).getRequirement() > user.getOverwatchData().getPlayer().getCompetitiveDamageRank().getValue())
				idPointMap.put(milestones.get(1).getName(), user.getOverwatchData().getPlayer().getCompetitiveDamageRank());
			// ...
		}

		user.setMilestoneNameUserPointMap(idPointMap);

	}

	public ScheduledInformationRetrieverService getSirs() {
		return sirs;
	}
}
