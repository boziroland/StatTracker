package org.github.boziroland.services.impl;

import org.github.boziroland.entities.User;
import org.github.boziroland.exceptions.RegistrationException;
import org.github.boziroland.services.ILeagueService;
import org.github.boziroland.services.IMilestoneService;
import org.github.boziroland.services.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ComponentScan("org.github.boziroland")
class UserServiceTest {

	@Autowired
	IUserService userService;

	@Autowired
	ILeagueService leagueService;

	@Autowired
	IMilestoneService milestoneService;

	@Test
	void testUserServiceCreate() {
		userService.create(new User("bonifác", "KAcsa11&", "bonifac.solyom@gmail.com", List.of(), List.of(), null, null));
		assertEquals("bonifác", userService.list().get(0).getName());
	}

	@Test
	void testRequestInformationButCantBecauseNoSuchUserExists() throws RegistrationException {
		assertThrows(RuntimeException.class, () -> userService.requestInformation(-1, leagueService));
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
		int registeredUserId = registeredUser.get().getId();
		Optional<User> loggedInUser = userService.login("bonifac.solyom@gmail.com", "KAcsa11&");
		int loggedInuserId = loggedInUser.get().getId();
		assertEquals(loggedInuserId, registeredUserId);
	}

	@Test
	void testLoginFailureBecauseNoSuchUserExists() {
		assertTrue(userService.login("IDoNot", "Exist").isEmpty());
	}

	@Test
	void testLoginFailureBecauseThePasswordWasIncorrect() throws RegistrationException {
		User user = TestUtils.registerOneUser(userService);
		Optional<User> expected = userService.login(user.getEmail(), "incorrectPassword");
		assertTrue(expected.isEmpty());
	}

	@Test
	void testRemoveUser() {
		User user = TestUtils.registerAndLoginOneUser(userService);
		userService.delete(user);
		assertEquals(userService.list().size(), 0);
	}
}
