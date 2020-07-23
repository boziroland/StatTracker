package org.github.boziroland.services.impl;

import TestUtils.TestUtils;
import org.github.boziroland.entities.LeagueData;
import org.github.boziroland.entities.MilestoneHolder;
import org.github.boziroland.entities.User;
import org.github.boziroland.exceptions.RegistrationException;
import org.github.boziroland.services.ILeagueService;
import org.github.boziroland.services.IUserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ComponentScan("org.github.boziroland")
class UserServiceTest {

    @Autowired
    IUserService userService;

    @Autowired
    ILeagueService leagueService;

    @Test
    void testUserServiceCreate() {
        userService.create(new User("bonifác", "KAcsa11&", "bonifac.solyom@gmail.com", List.of(), List.of(), null, null));
        assertEquals("bonifác", userService.list().get(0).getName());
    }

    @Test
    void testSendEmailButCantBecauseNoSuchUserExists() {
        assertThrows(RuntimeException.class, () -> userService.sendEmail(-1, "teszt"));
    }

    @Test
    void testRequestInformationButCantBecauseNoSuchUserExists() throws IOException, RegistrationException {
        assertThrows(RuntimeException.class, () -> userService.requestInformation(-1, leagueService, new LeagueData()));
    }

    @Test
    void testRegisterNewUserButEmailAlreadyExists() throws RegistrationException {
        userService.register("bonifác", "KAcsa11&", "bonifac.solyom@gmail.com", List.of(), List.of(), null, null);
        assertThrows(RegistrationException.class, () -> userService.register("albert", "Kutya11&", "bonifac.solyom@gmail.com", List.of(), List.of(), null, null));
    }

    @Test
    void testRegistrationSuccess() throws RegistrationException {
        var registeredUser = userService.register("bonifác", "KAcsa11&", "bonifac.solyom@gmail.com", List.of(), List.of(), null, null);
        assertTrue(registeredUser.isPresent());
    }

    @Test
    void testLoginSuccess() throws RegistrationException {
        var registeredUser = userService.register("bonifác", "KAcsa11&", "bonifac.solyom@gmail.com", List.of(), List.of(), null, null);
        assertEquals(userService.login("bonifac.solyom@gmail.com", "KAcsa11&").get().getId(), registeredUser.get().getId());
    }

    @Test
    void testLoginFailureBecauseNoSuchUserExists(){
        assertTrue(userService.login("IDoNot", "Exist").isEmpty());
    }

    @Test
    void testLoginFailureBecauseThePasswordWasIncorrect() throws RegistrationException {
        User user = TestUtils.createNDifferentUsers(userService, 1).get(0);
        assertTrue(userService.login(user.getEmail(), "incorrectPassword").isEmpty());
    }

    @Test
    void testScheduling() {
        await().atMost(5, TimeUnit.SECONDS);
        userService.scheduleHourlyQuery();
        await().atLeast(4, TimeUnit.SECONDS).atMost(10, TimeUnit.SECONDS);
        userService.scheduleHourlyQuery();
        await().atLeast(4, TimeUnit.SECONDS).atMost(10, TimeUnit.SECONDS);
    }

}
