package org.github.boziroland.services.impl;

import TestUtils.TestUtils;
import org.github.boziroland.entities.LeagueData;
import org.github.boziroland.entities.MilestoneHolder;
import org.github.boziroland.entities.User;
import org.github.boziroland.exceptions.RegistrationException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

class UserServiceTest {

    @Test
    void testUserServiceCreate() {
        UserService service = new UserService();
        service.create(0, "bonifác", "KAcsa11&", "bonifac.solyom@gmail.com", new MilestoneHolder(-1), List.of(), List.of(), null, null);
        assertEquals("bonifác", service.list().get(0).getName());
    }

    @Test
    void testSendEmailButCantBecauseNoSuchUserExists() {
        UserService service = new UserService();
        assertThrows(RuntimeException.class, () -> service.sendEmail(-1, "teszt"));
    }

    @Test
    void testRequestInformationButCantBecauseNoSuchUserExists() throws IOException, RegistrationException {
        UserService service = new UserService();
        LeagueService leagueService = new LeagueService();
        assertThrows(RuntimeException.class, () -> service.requestInformation(-1, leagueService, new LeagueData()));
    }

    @Test
    void testRegisterNewUserButEmailAlreadyExists() throws RegistrationException {
        UserService service = new UserService();
        service.register(0, "bonifác", "KAcsa11&", "bonifac.solyom@gmail.com", new MilestoneHolder(-1), List.of(), List.of(), null, null);
        assertThrows(RegistrationException.class, () -> service.register(1, "albert", "Kutya11&", "bonifac.solyom@gmail.com", new MilestoneHolder(-1), List.of(), List.of(), null, null));
    }

    @Test
    void testRegistrationSuccess() throws RegistrationException {
        UserService service = new UserService();
        var registeredUser = service.register(0, "bonifác", "KAcsa11&", "bonifac.solyom@gmail.com", new MilestoneHolder(-1), List.of(), List.of(), null, null);
        assertTrue(registeredUser.isPresent());
    }

    @Test
    void testLoginSuccess() throws RegistrationException {
        UserService service = new UserService();
        var registeredUser = service.register(0, "bonifác", "KAcsa11&", "bonifac.solyom@gmail.com", new MilestoneHolder(-1), List.of(), List.of(), null, null);
        assertEquals(service.login("bonifac.solyom@gmail.com", "KAcsa11&").get().getId(), registeredUser.get().getId());
    }

    @Test
    void testLoginFailureBecauseNoSuchUserExists(){
        UserService service = new UserService();
        assertTrue(service.login("IDoNot", "Exist").isEmpty());
    }

    @Test
    void testLoginFailureBecauseThePasswordWasIncorrect() throws RegistrationException {
        UserService service = new UserService();
        User user = TestUtils.createNDifferentUsers(service, 1).get(0);
        assertTrue(service.login(user.getEmail(), "incorrectPassword").isEmpty());
    }

    @Test
    void testScheduling() {
        UserService userService = new UserService();
        await().atMost(5, TimeUnit.SECONDS);
        userService.scheduleHourlyQuery();
        await().atLeast(4, TimeUnit.SECONDS).atMost(10, TimeUnit.SECONDS);
        userService.scheduleHourlyQuery();
        await().atLeast(4, TimeUnit.SECONDS).atMost(10, TimeUnit.SECONDS);
    }

}
