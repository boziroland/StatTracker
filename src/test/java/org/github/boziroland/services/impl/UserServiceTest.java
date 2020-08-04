package org.github.boziroland.services.impl;

import TestUtils.TestUtils;
import jdk.jshell.spi.ExecutionControl;
import org.awaitility.proxy.AwaitilityClassProxy;
import org.github.boziroland.Constants;
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

	@Autowired
	IMilestoneService milestoneService;

	@Test
	void testUserServiceCreate() {
		userService.create(new User("bonifác", "KAcsa11&", "bonifac.solyom@gmail.com", List.of(), List.of(), null, null));
		assertEquals("bonifác", userService.list().get(0).getName());
	}

	@Test
	void testSendEmailButCantBecauseNoSuchUserExists() {
		assertThrows(ExecutionControl.NotImplementedException.class, () -> userService.sendEmail(TestUtils.registerAndLoginNDifferentusers(userService, 1).get(0),"teszt"));
	}

	@Test
	void testRequestInformationButCantBecauseNoSuchUserExists() throws IOException, RegistrationException {
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
		assertEquals(userService.login("bonifac.solyom@gmail.com", "KAcsa11&").get().getId(), registeredUser.get().getId());
	}

	@Test
	void testLoginFailureBecauseNoSuchUserExists() {
		assertTrue(userService.login("IDoNot", "Exist").isEmpty());
	}

	@Test
	void testLoginFailureBecauseThePasswordWasIncorrect() throws RegistrationException {
		//TODO: Nagyon compact, de nehezen olvasható,
		// User u = new User(x,y);
		// service.registerUser(u)
		// User expected = service.login(a,b);
		// expeted not found, Itt egyértelműen látszik hogy mi volt a szándék. Teszteknél nem szükséges a fancy-zés, mindig a legegyszerübb a legtisztább.
		User user = TestUtils.registerNDifferentUsers(userService, 1).get(0);
		assertTrue(userService.login(user.getEmail(), "incorrectPassword").isEmpty());
	}

	//@Test
	void testScheduling() {
		Constants.INITIAL_DATA_RETRIEVE_DELAY_IN_SECONDS = 2;
		Constants.DATA_RETRIEVE_DELAY_IN_SECONDS = 5;
		User user = TestUtils.registerAndLoginNDifferentusers(userService, 1).get(0);
		//await().atMost(10, TimeUnit.SECONDS).untilCall(AwaitilityClassProxy.to(userService));
	}

	@Test
	void testRemoveUser() {
		User user = TestUtils.registerAndLoginNDifferentusers(userService, 1).get(0);
		userService.delete(user);
		assertEquals(userService.list().size(), 0);
	}
}
