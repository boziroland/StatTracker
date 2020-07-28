package TestUtils;

import org.apache.commons.lang3.RandomStringUtils;
import org.github.boziroland.entities.User;
import org.github.boziroland.exceptions.LoginException;
import org.github.boziroland.exceptions.RegistrationException;
import org.github.boziroland.services.IUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TestUtils {

	public static List<User> registerNDifferentUsers(IUserService service, int n) throws RegistrationException {
		List<User> ret = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			String randomEmail = RandomStringUtils.randomAlphabetic(8) + "@" + RandomStringUtils.randomAlphabetic(5) + ".com";
			var user = service.register(RandomStringUtils.random(5), "KAcsa11!", randomEmail, List.of(), List.of(), null, null);
			ret.add(user.get());
		}
		return ret;
	}

	public static List<User> registerAndLoginNDifferentusers(IUserService service, int n) throws RegistrationException, LoginException {
		List<User> registeredUsers = registerNDifferentUsers(service, n);
		for (User user : registeredUsers)
			service.login(user.getEmail(), "KAcsa11!");

		return registeredUsers;
	}

	public static Optional<User> registerAndLoginUserWhoHasLeagueName(IUserService service) throws RegistrationException {
		service.register("userWithLeagueName", "KAcsa11!", "unique@email.com", List.of(), List.of(), "meshons#EUNE", null);
		return service.login("unique@email.com", "KAcsa11!");
	}

}
