package org.github.boziroland;

import org.github.boziroland.entities.LeagueData;
import org.github.boziroland.entities.MilestoneHolder;
import org.github.boziroland.entities.User;
import org.github.boziroland.exceptions.RegistrationException;
import org.github.boziroland.services.ICommentService;
import org.github.boziroland.services.impl.CommentService;
import org.github.boziroland.services.impl.LeagueService;
import org.github.boziroland.services.impl.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) throws RegistrationException, IOException {

        List<Optional<User>> users = new ArrayList<>();

        LeagueService leagueService = new LeagueService();
        UserService service = new UserService(leagueService);
        service.register(1, "bonifác", "KAcsa11&", "bonifac.solyom@gmail.com", new MilestoneHolder(), List.of(), List.of(), "meshons", null);
        users.add(service.login("bonifac.solyom@gmail.com", "KAcsa11&"));
        service.register(2, "bonifác", "KAcsa11&", "bon1fac.solyom@gmail.com", new MilestoneHolder(), List.of(), List.of(), "meshons", null);
        users.add(service.login("bon1fac.solyom@gmail.com", "KAcsa11&"));


        service.requestInformation(users.get(0).get().getId(), leagueService, users.get(0).get().getLeagueData());
        LeagueData meshonsData = users.get(0).get().getLeagueData();

        System.out.println(meshonsData.getPlayer().getName());
        System.out.println(meshonsData.getPlayer().getAccountId());

        service.checkMilestones(users.get(0).get().getId());

        ICommentService commentService = new CommentService();

        commentService.sendComment(users.get(0).get(), users.get(1).get(), "valami");

    }
}
