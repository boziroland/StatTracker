package org.github.boziroland.services.impl;

import org.apache.commons.lang3.mutable.MutableInt;
import org.github.boziroland.entities.User;
import org.github.boziroland.services.IMilestoneService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ComponentScan("org.github.boziroland")
class MilestoneServiceTest {

	@Autowired
	IMilestoneService milestoneService;

	@Test
	void testMilestoneNotDone() {
		User user = Mockito.mock(User.class);
		Map<String, MutableInt> userMilestones = new HashMap<>();
		userMilestones.put("100-as szint", new MutableInt(10));

		when(user.getMilestoneNameUserPointMap()).thenReturn(userMilestones);

		assertEquals(0, milestoneService.checkAchievements(user).size());
	}

	@Test
	void testMilestoneDone() {
		User user = Mockito.mock(User.class);
		Map<String, MutableInt> userMilestones = new HashMap<>();
		userMilestones.put("100-as szint", new MutableInt(100));
		when(user.getMilestoneNameUserPointMap()).thenReturn(userMilestones);

		assertEquals(2, milestoneService.checkAchievements(user).size());
	}

}