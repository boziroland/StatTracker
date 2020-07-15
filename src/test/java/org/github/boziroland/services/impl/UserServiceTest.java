package org.github.boziroland.services.impl;

import org.github.boziroland.DAL.impl.UserInMemory;
import org.github.boziroland.entities.LeagueData;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Test
    void testUserServiceCreate() {
        UserService service = new UserService(new UserInMemory());
        service.createOrUpdate(0, "bonifác", "kacsa123", "bonifac.solyom@gmail.com", List.of(), List.of(), null, null);
        assertEquals("bonifác", service.dao.list().get(0).getName());
    }

    @Test
    void testSendEmailButCantBecauseUserHasNoEmail() {
        UserService service = new UserService(new UserInMemory());
        service.createOrUpdate(0, "bonifác", "kacsa123", null, List.of(), List.of(), null, null);
        assertThrows(NullPointerException.class, () -> service.sendEmail(0));
    }

    @Test
    void testSendEmailButCantBecauseNoSuchUserExists() {
        UserService service = new UserService(new UserInMemory());
        assertThrows(RuntimeException.class, () -> service.sendEmail(0));
    }

    @Test
    void testRequestInformationButCantBecauseNoSuchUserExists() {
        UserService service = new UserService(new UserInMemory());
        LeagueData player = new LeagueData();
        assertThrows(RuntimeException.class, () -> service.requestInformation(0, player));
    }
}