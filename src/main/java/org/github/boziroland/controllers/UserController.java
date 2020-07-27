package org.github.boziroland.controllers;

import lombok.RequiredArgsConstructor;
import org.github.boziroland.Application;
import org.github.boziroland.entities.LeagueData;
import org.github.boziroland.entities.User;
import org.github.boziroland.exceptions.RegistrationException;
import org.github.boziroland.services.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    IUserService userService;

    @GetMapping
    public List<User> users() {
        LOGGER.info("GET Request: /users");
        return userService.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> users(@PathVariable("id") Integer id) {
        LOGGER.info("GET Request: /users/" + id);
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            LOGGER.info("User found");
            return ResponseEntity.ok(userService.findById(id).get());
        } else {
            LOGGER.info("No user found");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        LOGGER.info("POST Request: /register");
        Optional<User> registeredUser = userService.register(user);
        if (registeredUser.isPresent()) {
            LOGGER.info("User " + registeredUser.get().getName() + " registered with id: " + registeredUser.get().getId());
            return ResponseEntity.ok(registeredUser.get());
        }
        LOGGER.info("Registration failure!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody String email, @RequestBody String password) {
        LOGGER.info("POST Request: /login");
        Optional<User> loggedInUser = userService.login(email, password);
        if (loggedInUser.isPresent()) {
            LOGGER.info("User " + loggedInUser.get().getName() + " logged in");
            return ResponseEntity.ok(loggedInUser.get());
        }
        LOGGER.info("Login failure for User with email " + email);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> removeUserById(@PathVariable("id") Integer id) {
        LOGGER.info("DELETE Request: /delete/" + id);
        try {
            userService.deleteById(id);
        } catch (Exception e) {
            LOGGER.info("Deletion failure!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        LOGGER.info("Deletion successful");
        return ResponseEntity.ok("Sikeres törlés!");
    }

    @GetMapping("/league/{id}")
    public ResponseEntity<LeagueData> getLeagueData(@PathVariable("id") Integer id) {
        LOGGER.info("GET Request: /league/" + id);
        var foundUser = userService.findById(id);
        if (foundUser.isPresent()) {
            LOGGER.info("User league name found!");
            return ResponseEntity.ok(foundUser.get().getLeagueData());
        }
        LOGGER.info("User league name not found!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

}
