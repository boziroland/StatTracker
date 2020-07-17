package org.github.boziroland.services.impl;

import org.github.boziroland.entities.MilestoneHolder;
import org.github.boziroland.entities.User;
import org.github.boziroland.exceptions.RegistrationException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestUtils {

    public static List<User> createNDifferentUsers(UserService service, int n) throws RegistrationException {
        List<User> ret = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            var user = service.register(i, UUID.randomUUID().toString(), "KAcsa11&", "bonifac.solyom@gmail.com", new MilestoneHolder(), List.of(), null, null);
            ret.add(user.get());
        }
        return ret;
    }
}
