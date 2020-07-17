package org.github.boziroland.services.impl;

import org.github.boziroland.DAL.impl.LeagueDataInMemory;
import org.github.boziroland.DAL.impl.MilestoneInMemory;
import org.github.boziroland.DAL.impl.UserInMemory;
import org.github.boziroland.entities.Milestone;
import org.github.boziroland.entities.MilestoneHolder;
import org.github.boziroland.exceptions.RegistrationException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Test
    void testUserServiceCreate() {
        UserService service = new UserService(new UserInMemory());
        service.createOrUpdate(0, "bonifác", "KAcsa11&", "bonifac.solyom@gmail.com", new MilestoneHolder(), List.of(), null, null);
        assertEquals("bonifác", service.dao.list().get(0).getName());
    }

    @Test
    void testSendEmailButCantBecauseNoSuchUserExists() {
        UserService service = new UserService(new UserInMemory());
        assertThrows(RuntimeException.class, () -> service.sendEmail(0, "teszt"));
    }

    @Test
    void testRequestInformationButCantBecauseNoSuchUserExists() throws IOException {
        UserService service = new UserService(new UserInMemory());
        LeagueService leagueService = new LeagueService(new LeagueDataInMemory());
        assertThrows(RuntimeException.class, () -> service.requestInformation(0, leagueService));
    }

    @Test
    void testRegisterNewUserButEmailAlreadyExists() throws RegistrationException {
        UserService service = new UserService(new UserInMemory());
        service.register(0, "bonifác", "KAcsa11&", "bonifac.solyom@gmail.com", new MilestoneHolder(), List.of(), null, null);
        assertThrows(RegistrationException.class, () -> service.register(1, "albert", "Kutya11&", "bonifac.solyom@gmail.com", new MilestoneHolder(), List.of(), null, null));
    }

    @Test
    void testRegistrationSuccess() throws RegistrationException {
        UserService service = new UserService(new UserInMemory());
        var registeredUser = service.register(0, "bonifác", "KAcsa11&", "bonifac.solyom@gmail.com", new MilestoneHolder(), List.of(), null, null);
        assertTrue(registeredUser.isPresent());
    }

    @Test
    void testLoginSuccess() throws RegistrationException {
        UserService service = new UserService(new UserInMemory());
        var registeredUser = service.register(0, "bonifác", "KAcsa11&", "bonifac.solyom@gmail.com", new MilestoneHolder(), List.of(), null, null);
        assertEquals(service.login("bonifác", "KAcsa11&").get().getID(), registeredUser.get().getID());
    }

    @Test
    void testLoginFailureBecauseNoSuchUserExists(){
        UserService service = new UserService(new UserInMemory());
        assertTrue(service.login("IDoNot", "Exist").isEmpty());
    }

}
