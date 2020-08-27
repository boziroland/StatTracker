package org.github.boziroland.controllers;

import org.github.boziroland.Constants;
import org.github.boziroland.entities.Milestone;
import org.github.boziroland.services.IMilestoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/milestones")
public class MilestoneController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MilestoneController.class);

	@Autowired
	private IMilestoneService milestoneService;

	@GetMapping
	public ResponseEntity<List<Milestone>> milestones() {
		LOGGER.info("GET Request: /milestones");
		return ResponseEntity.ok(milestoneService.getMilestonesAsList());
	}

	@GetMapping("/league")
	public ResponseEntity<List<Milestone>> leagueMilestones() {
		LOGGER.info("GET Request: /league");
		List<Milestone> milestones = milestoneService.getMilestonesAsList();
		List<Milestone> ret = new ArrayList<>();
		for (var m : milestones)
			if (m.getGame() == Milestone.Game.LEAGUE)
				ret.add(m);
		return ResponseEntity.ok(ret);
	}

	@GetMapping("/overwatch")
	public ResponseEntity<List<Milestone>> overwatchMilestones() {
		LOGGER.info("GET Request: /overwatch");
		List<Milestone> milestones = milestoneService.getMilestonesAsList();
		List<Milestone> ret = new ArrayList<>();
		for (var m : milestones)
			if (m.getGame() == Milestone.Game.OVERWATCH)
				ret.add(m);
		return ResponseEntity.ok(ret);
	}
}
