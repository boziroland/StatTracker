package org.github.boziroland.services.impl;

import org.apache.commons.collections.list.UnmodifiableList;
import org.github.boziroland.DAL.impl.LeagueDataInMemory;
import org.github.boziroland.DAL.impl.UserInMemory;
import org.github.boziroland.entities.Comment;
import org.github.boziroland.entities.MilestoneHolder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    void testRequestInformationButCantBecauseNoSuchUserExists() {
        UserService service = new UserService(new UserInMemory());
        LeagueService leagueService = new LeagueService(new LeagueDataInMemory());
        assertThrows(RuntimeException.class, () -> service.requestInformation(0, leagueService));
    }

    @Test
    void testRegisterNewUserButEmailAlreadyExists() {
        UserService service = new UserService(new UserInMemory());
        service.register(0, "bonifác", "KAcsa11&", "bonifac.solyom@gmail.com", new MilestoneHolder(), List.of(), null, null);
        assertThrows(RuntimeException.class, () -> service.register(1, "albert", "Kutya11&", "bonifac.solyom@gmail.com", new MilestoneHolder(), List.of(), null, null));
    }

    @Test
    void testRegistrationSuccess(){
        UserService service = new UserService(new UserInMemory());
        var registeredUser = service.register(0, "bonifác", "KAcsa11&", "bonifac.solyom@gmail.com", new MilestoneHolder(), List.of(), null, null);
        assertTrue(registeredUser.isPresent());
    }

    @Test
    void testLoginSuccess() {
        UserService service = new UserService(new UserInMemory());
        var registeredUser = service.register(0, "bonifác", "KAcsa11&", "bonifac.solyom@gmail.com", new MilestoneHolder(), List.of(), null, null);
        assertEquals(service.login("bonifác", "KAcsa11&").get().getID(), registeredUser.get().getID());
    }



}