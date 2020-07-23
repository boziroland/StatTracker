package org.github.boziroland;

import org.github.boziroland.entities.User;
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
        UserService service = applicationContext.getBean(UserService.class);
        service.register("bonifác", "KAcsa11&", "bonifac.solyom@gmail.com", List.of(), List.of(), "meshons", null);
        users.add(service.login("bonifac.solyom@gmail.com", "KAcsa11&"));
        System.out.println(users.get(0).get().getName());

    }
}
