package org.github.boziroland.controllers;

import lombok.RequiredArgsConstructor;
import org.github.boziroland.Application;
import org.github.boziroland.entities.User;
import org.github.boziroland.services.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    Logger LOGGER = LoggerFactory.getLogger(Application.class);

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

    @PostMapping("/add")
    public User addUser(@RequestBody User user){
        LOGGER.info("User " + user.getName() + " created with id: " + user.getId());
        return userService.create(user);
    }

}
