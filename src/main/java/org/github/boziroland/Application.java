package org.github.boziroland;

import org.github.boziroland.entities.User;
import org.github.boziroland.services.*;
import org.github.boziroland.services.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.*;

@SpringBootApplication
public class Application {

	static Logger LOGGER = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);

		IUserService userService = applicationContext.getBean(UserService.class);

		LOGGER.info("main end");
	}
}
