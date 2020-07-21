package org.github.boziroland.services.impl;

import TestUtils.TestUtils;
import org.github.boziroland.DAL.impl.LeagueDataInMemory;
import org.github.boziroland.DAL.impl.UserInMemory;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LeagueServiceTest {

    @Test
    void testRetrieveLeagueData() throws IOException {
        UserService service = new UserService(new UserInMemory());
        var user = TestUtils.registerAndLoginUserWhoHasLeagueName(service);

        LeagueService leagueService = new LeagueService(new LeagueDataInMemory());

        user.ifPresent(value -> service.requestInformation(value.getId(), leagueService, value.getLeagueData()));

        assertEquals(user.get().getLeagueData().getPlayer().getName(), user.get().getLeagueID());
    }
}