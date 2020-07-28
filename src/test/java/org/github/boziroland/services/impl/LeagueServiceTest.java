package org.github.boziroland.services.impl;

import TestUtils.TestUtils;
import org.github.boziroland.services.ILeagueService;
import org.github.boziroland.services.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ComponentScan("org.github.boziroland")
class LeagueServiceTest {

	@Autowired
	IUserService userService;

	@Autowired
	ILeagueService leagueService;

	@Test
	void testRetrieveLeagueData(){
		var user = TestUtils.registerAndLoginUserWhoHasLeagueName(userService);

		if (user.isPresent()) {
			userService.requestInformation(user.get().getId(), leagueService, user.get().getLeagueData());
		}

		assertEquals(user.get().getLeagueData().getPlayer().getName(), "meshons");
	}
}