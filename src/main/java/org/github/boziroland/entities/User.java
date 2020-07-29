package org.github.boziroland.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String password;
	private String email;

	@ElementCollection
	@CollectionTable(name = "LeagueMilestonePointJoinTable")
	@MapKeyColumn(name = "Milestone")
	@JsonIgnore
	private final Map<Milestone, Integer> leagueMilestones = new HashMap<>();

	@ElementCollection
	@CollectionTable(name = "OverwatchMilestonePointJoinTable")
	@MapKeyColumn(name = "Milestone")
	@JsonIgnore
	private final Map<Milestone, Integer> overwatchMilestones = new HashMap<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Comment> commentsOnProfile = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Comment> commentsSent = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private LeagueData leagueData = new LeagueData();

	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private OverwatchData overwatchData = new OverwatchData();

	public User(String name, String password, String email, String leagueID, String gameName2) {
		this.name = name;
		this.password = password;
		this.email = email;

		leagueData.setUsername(leagueID);

		overwatchData.setUsername(gameName2);
	}

	public User(String name, String password, String email, List<Comment> commentsOnProfile, List<Comment> commentsSent, String leagueID, String overwatchID) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.commentsOnProfile = commentsOnProfile;
		this.commentsSent = commentsSent;

		leagueData = new LeagueData();
		leagueData.setUsername(leagueID);

		overwatchData = new OverwatchData();
		overwatchData.setUsername(overwatchID);

		initLeagueMilestones();
		initGame2Milestones();
	}

	private void initLeagueMilestones() {
		leagueMilestones.put(new Milestone("League_Milestone_name", "League_Milestone_description", 100, Milestone.Game.LEAGUE), 0);
	}

	private void initGame2Milestones() {
		overwatchMilestones.put(new Milestone("OW_Milestone_name", "OW_Milestone_description", 120, Milestone.Game.OVERWATCH), 0);
	}

	public String getLeagueID() {
		return leagueData.getUsername();
	}

	public void setLeagueID(String leagueID) {
		leagueData.setUsername(leagueID);
	}

	public String getOverwatchID() {
		return overwatchData.getUsername();
	}

	public void setOverwatchID(String overwatchID) {
		overwatchData.setUsername(overwatchID);
	}
}
