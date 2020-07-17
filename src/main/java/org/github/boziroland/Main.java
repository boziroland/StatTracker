package org.github.boziroland;

import org.github.boziroland.DAL.impl.LeagueDataInMemory;
import org.github.boziroland.DAL.impl.UserInMemory;
import org.github.boziroland.entities.LeagueData;
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
        service.register(1, "bonifác", "KAcsa11&", "bonifac.solyom@gmail.com", new MilestoneHolder(), List.of(), "meshons", null);
        users.add(service.login("bonifác", "KAcsa11&"));

        LeagueService leagueService = new LeagueService(new LeagueDataInMemory());

        service.requestInformation(users.get(0).get().getId(), leagueService, users.get(0).get().getLeagueData());
        LeagueData meshonsData = users.get(0).get().getLeagueData();

        System.out.println(meshonsData.getPlayer().getName());
        System.out.println(meshonsData.getPlayer().getAccountId());

        service.checkMilestones(users.get(0).get().getId());
    }
}
