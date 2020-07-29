package org.github.boziroland;

import org.github.boziroland.entities.User;
import org.github.boziroland.services.ICommentService;
import org.github.boziroland.services.ILeagueService;
import org.github.boziroland.services.IOverwatchService;
import org.github.boziroland.services.IUserService;
import org.github.boziroland.services.impl.CommentService;
import org.github.boziroland.services.impl.LeagueService;
import org.github.boziroland.services.impl.OverwatchService;
import org.github.boziroland.services.impl.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
		userService.register("bonifác", "KAcsa11&", "bonifac.solyom@gmail.com", "meshons#EUNE", "Spricsma#21972");
		users.add(userService.login("bonifac.solyom@gmail.com", "KAcsa11&"));
		Thread.sleep(2000);
		userService.register("amy99", "KAcsa11&", "amy.glassires@gmail.com", "meshons#EUNE", "Spricsma#21972");
		users.add(userService.login("amy.glassires@gmail.com", "KAcsa11&"));
//		userService.requestInformation(users.get(0).get(), overwatchService);
//		userService.requestInformation(users.get(0).get(), leagueService);
//		LOGGER.info(users.get(0).get().getOverwatchData().getPlayer().getUsername());
//		LOGGER.info(users.get(0).get().getLeagueData().getPlayer().getName());
		User bonifac = users.get(0).get();
		User amy = users.get(1).get();
		commentService.sendComment(bonifac, amy, "Miss you Amy!");

	}
}
