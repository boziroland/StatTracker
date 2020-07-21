package TestUtils;

import org.github.boziroland.config.ApplicationConfig;
import org.github.boziroland.entities.MilestoneHolder;
import org.github.boziroland.entities.User;
import org.github.boziroland.exceptions.RegistrationException;
import org.github.boziroland.services.impl.APIData1Service;
import org.github.boziroland.services.impl.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TestUtils {

    public static List<User> createNDifferentUsers(UserService service, int n) throws RegistrationException {
        List<User> ret = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            var user = service.register(i, UUID.randomUUID().toString(), "KAcsa11&", "bonifac.solyom@gmail.com", new MilestoneHolder(), List.of(), List.of(), null, null);
            ret.add(user.get());
        }
        return ret;
    }

    public static Optional<User> registerAndLoginUserWhoHasLeagueName(UserService service) throws RegistrationException {
        service.register(-1, "happyuser", "KAcsa11!", "unique@email.com", new MilestoneHolder(), List.of(), List.of(), "meshons", null);
        return service.login("unique@email.com", "KAcsa11!");
    }

}
