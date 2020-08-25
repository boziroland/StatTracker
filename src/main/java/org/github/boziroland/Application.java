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

	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);

		List<Optional<User>> users = new ArrayList<>();
		IUserService userService = applicationContext.getBean(UserService.class);
		ILeagueService leagueService = applicationContext.getBean(LeagueService.class);
		IOverwatchService overwatchService = applicationContext.getBean(OverwatchService.class);
		ICommentService commentService = applicationContext.getBean(CommentService.class);

		userService.register("bonifuc", "KAcsa11&", "bonifuc.solyom@gmail.com", "meshons#EUNE", null);
		users.add(userService.login("bonifuc.solyom@gmail.com", "KAcsa11&"));
		Thread.sleep(2000);
		//userService.register("amy99", "KAcsa11&", "amy.glassires@gmail.com", "meshons#EUNE", "Spricsma#21972-eu");
		//users.add(userService.login("amy.glassires@gmail.com", "KAcsa11&"));

//		User bonifac = users.get(0).get();
//		User amy = users.get(1).get();
//		commentService.sendComment(bonifac, amy, "Miss you Amy!");
//		userService.update(bonifac);
//		userService.update(amy);

		LOGGER.info("main end");
	}
}
