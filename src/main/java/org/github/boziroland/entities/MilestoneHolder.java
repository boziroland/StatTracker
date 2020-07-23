package org.github.boziroland.entities;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class MilestoneHolder {

    @Id
    @GeneratedValue
    private Integer id;

    @ElementCollection
    @CollectionTable(name="LeagueMilestonePointJoinTable")
    @MapKeyColumn(name="Milestone")
    private Map<Milestone, Integer> leagueMilestones = new HashMap<>();

    @ElementCollection
    @CollectionTable(name="Game2MilestonePointJoinTable")
    @MapKeyColumn(name="Milestone")
    private Map<Milestone, Integer> gameMilestones2 = new HashMap<>();

    public MilestoneHolder() {
        initLeagueMilestones();
        initGame2Milestones();
    }

    private void initLeagueMilestones() {
        leagueMilestones.put(new Milestone("teszt", "teszt teszt", 100, Milestone.Game.LEAGUE), 0);
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
