package TestUtils;

import org.github.boziroland.entities.MilestoneHolder;
import org.github.boziroland.entities.User;
import org.github.boziroland.exceptions.RegistrationException;
import org.github.boziroland.services.IUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TestUtils {

    public static List<User> createNDifferentUsers(IUserService service, int n) throws RegistrationException {
        List<User> ret = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            var user = service.register(UUID.randomUUID().toString(), "KAcsa11&", "bonifac.solyom@gmail.com", List.of(), List.of(), null, null);
            ret.add(user.get());
        }
        return ret;
    }

    public static Optional<User> registerAndLoginUserWhoHasLeagueName(IUserService service) throws RegistrationException {
        service.register("happyuser", "KAcsa11!", "unique@email.com", List.of(), List.of(), "meshons", null);
        return service.login("unique@email.com", "KAcsa11!");
    }

}
