package org.github.boziroland;

import org.github.boziroland.entities.MilestoneHolder;
import org.github.boziroland.entities.User;
import org.github.boziroland.services.impl.LeagueService;
import org.github.boziroland.services.impl.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class, args);

        List<Optional<User>> users = new ArrayList<>();
        LeagueService leagueService = new LeagueService();
        UserService service = new UserService(leagueService);
        service.register(1, "bonifác", "KAcsa11&", "bonifac.solyom@gmail.com", new MilestoneHolder(), List.of(), List.of(), "meshons", null);
        users.add(service.login("bonifac.solyom@gmail.com", "KAcsa11&"));
        System.out.println(users.get(0).get().getName());

    }
}
