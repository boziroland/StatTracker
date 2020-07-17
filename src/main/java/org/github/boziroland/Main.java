package org.github.boziroland;

import org.github.boziroland.DAL.impl.LeagueDataInMemory;
import org.github.boziroland.DAL.impl.UserInMemory;
import org.github.boziroland.entities.MilestoneHolder;
import org.github.boziroland.entities.User;
import org.github.boziroland.exceptions.RegistrationException;
import org.github.boziroland.services.impl.LeagueService;
import org.github.boziroland.services.impl.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) throws RegistrationException, IOException {

        List<Optional<User>> users = new ArrayList<>();

        UserService service = new UserService(new UserInMemory());
        users.add(service.register(1, "bonifác", "AAAaa123&", "bonifac.solyom@gmail.com", new MilestoneHolder(), List.of(), null, null));
        service.login("bonifác", "AAAaaa123&");

        LeagueService leagueService = new LeagueService(new LeagueDataInMemory());

        service.requestInformation(users.get(0).get().getID(), leagueService);
        users.get(0).get().getLeagueData();

        service.checkMilestones(users.get(0).get().getID());

    }
}
