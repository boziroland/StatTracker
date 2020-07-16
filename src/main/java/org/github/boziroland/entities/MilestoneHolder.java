package org.github.boziroland.entities;

import java.util.HashMap;
import java.util.Map;

public class MilestoneHolder {

    private final Map<Milestone, Integer> leagueMilestones = new HashMap<>();
    private final Map<Milestone, Integer> gameMilestones2 = new HashMap<>();

    public MilestoneHolder() {
        initLeagueMilestones();
        initGame2Milestones();
    }

    private void initLeagueMilestones() {
        leagueMilestones.put(new Milestone("teszt", "teszt teszt", 120, Milestone.Game.LOL), 0);
    }

    private void initGame2Milestones() {

    }

    public Map<Milestone, Integer> getLeagueMilestones() {
        return leagueMilestones;
    }

    public Map<Milestone, Integer> getGameMilestones2() {
        return gameMilestones2;
    }
}
