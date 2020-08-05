package org.github.boziroland.services.impl;

import org.github.boziroland.services.ILeagueService;
import org.github.boziroland.services.IMilestoneService;
import org.github.boziroland.services.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ComponentScan("org.github.boziroland")
class LeagueServiceTest {

	@Autowired
	IUserService userService;

	@Autowired
	ILeagueService leagueService;

	@Autowired
	IMilestoneService milestoneService;

	@Test
	void testRetrieveLeagueData(){
		//TODO TESZTEK
		var user = TestUtils.registerAndLoginUserWhoHasLeagueName(userService);

		user.ifPresent(value -> userService.requestInformation(value, leagueService));

		assertEquals(user.get().getLeagueData().getPlayer().getName(), "meshons");
	}
}