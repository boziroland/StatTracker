package org.github.boziroland.controllers;

import org.github.boziroland.entities.LeagueData;
import org.github.boziroland.entities.OverwatchData;
import org.github.boziroland.entities.User;
import org.github.boziroland.exceptions.LoginException;
import org.github.boziroland.exceptions.RegistrationException;
import org.github.boziroland.services.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IUserService userService;

	@ExceptionHandler
	private ModelAndView exceptionHandler(Exception e) {
		LOGGER.info(e.getMessage());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error");
		modelAndView.addObject("message", e.getMessage());
		return modelAndView;
	}

	@GetMapping
	public ResponseEntity<List<User>> users() {
		LOGGER.info("GET Request: /users");
		return ResponseEntity.ok(userService.list());
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> users(@PathVariable("id") Integer id) {
		LOGGER.info("GET Request: /users/{}", id);
		Optional<User> user = userService.findById(id);
		if (user.isPresent()) {
			LOGGER.info("User found");
			return ResponseEntity.ok(userService.findById(id).get());
		}
		throw new IllegalArgumentException("No user found with id " + id);
	}

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		LOGGER.info("POST Request: /register");
		Optional<User> registeredUser = userService.register(user);
		if (registeredUser.isPresent()) {
			LOGGER.info("User {} registered with id: {}", registeredUser.get().getName(), registeredUser.get().getId());
			return ResponseEntity.ok(registeredUser.get());
		}
		throw new RegistrationException("Registration failure!");
	}

	@PostMapping("/login")
	public ResponseEntity<User> loginUser(@RequestBody Map<String, String> userData) {
		LOGGER.info("POST Request: /login");
		Optional<User> loggedInUser = userService.login(userData.get("email"), userData.get("password"));
		if (loggedInUser.isPresent()) {
			LOGGER.info("User {} logged in", loggedInUser.get().getName());
			return ResponseEntity.ok(loggedInUser.get());
		}
		throw new LoginException("Login failure for User with email " + userData.get("email"));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> removeUserById(@PathVariable("id") Integer id) {
		LOGGER.info("DELETE Request: /delete/{}", id);
		try {
			userService.deleteById(id);
		} catch (Exception e) {
			throw new IllegalArgumentException("Deletion failure!");
		}
		LOGGER.info("Deletion successful");
		return ResponseEntity.ok("Sikeres törlés!");
	}

	@GetMapping("/league/{id}")
	public ResponseEntity<LeagueData> getLeagueData(@PathVariable("id") Integer id) {
		LOGGER.info("GET Request: /league/{}", id);
		var foundUser = userService.findById(id);
		if (foundUser.isPresent()) {
			LOGGER.info("User league name found!");
			return ResponseEntity.ok(foundUser.get().getLeagueData());
		}
		throw new IllegalArgumentException("User league name not found!");
	}

	@GetMapping("/ow/{id}")
	public ResponseEntity<OverwatchData> getOWData(@PathVariable("id") Integer id) {
		LOGGER.info("GET Request: /ow/{}", id);
		var foundUser = userService.findById(id);
		if (foundUser.isPresent()) {
			LOGGER.info("User overwatch name found!");
			return ResponseEntity.ok(foundUser.get().getOverwatchData());
		}
		throw new IllegalArgumentException("User overwatch name not found!");
	}

	@GetMapping("/milestones/{id}")
	public ResponseEntity<Map<String, Integer>> getMilestones(@PathVariable("id") Integer id) {
		LOGGER.info("GET Request: /milestones/{}", id);
		var foundUser = userService.findById(id);
		if (foundUser.isPresent()) {
			LOGGER.info("User milestones found!");

			var milestoneMap = foundUser.get().getMilestoneNameUserPointMap();
			Map<String, Integer> milestoneMapWithIntValues = new HashMap<>();

			for (var m : milestoneMap.entrySet())
				milestoneMapWithIntValues.put(m.getKey(), m.getValue().getValue());

			return ResponseEntity.ok(milestoneMapWithIntValues);
		}
		throw new IllegalArgumentException("User overwatch name not found!");
	}

	@PostMapping("/{game}/{id}")
	public ResponseEntity<String> setGameName(@PathVariable String game, @PathVariable int id, @RequestBody Map<String, String> userData) {
		String name = userData.get("name");
		LOGGER.info("POST Request: /{}/{} with name {}", game, id, name);

		switch (game.toLowerCase()) {
			case "league":
			case "lol":
			case "leagueoflegends":
				if (userService.updateLeagueName(id, name))
					return ResponseEntity.ok(name);

				break;
			case "ow":
			case "overwatch":
				if (userService.updateOWName(id, name))
					return ResponseEntity.ok(name);

				break;
			default:
				throw new IllegalArgumentException("No such game in the database!");
		}

		throw new IllegalArgumentException("User not found!");
	}
}
