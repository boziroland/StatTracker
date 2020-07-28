package org.github.boziroland.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
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
	@CollectionTable(name = "Game2MilestonePointJoinTable")
	@MapKeyColumn(name = "Milestone")
	@JsonIgnore
	private final Map<Milestone, Integer> overwatchMilestones = new HashMap<>();

	@OneToMany(cascade = CascadeType.ALL)
	private List<Comment> commentsOnProfile;

	@OneToMany(cascade = CascadeType.ALL)
	private List<Comment> commentsSent;

	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private LeagueData leagueData = new LeagueData();

	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private OverwatchData overwatchData = new OverwatchData();

	public User() {
	}

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
		leagueMilestones.put(new Milestone("teszt", "teszt teszt", 100, Milestone.Game.LEAGUE), 0);
	}

	private void initGame2Milestones() {

	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Comment> getCommentsSent() {
		return commentsSent;
	}

	public void setCommentsSent(List<Comment> commentsSent) {
		this.commentsSent = commentsSent;
	}

	public LeagueData getLeagueData() {
		return leagueData;
	}

	public void setLeagueData(LeagueData leagueData) {
		this.leagueData = leagueData;
	}

	public OverwatchData getOverwatchData() {
		return overwatchData;
	}

	public void setOverwatchData(OverwatchData overwatchData) {
		this.overwatchData = overwatchData;
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

	public List<Comment> getCommentsOnProfile() {
		return commentsOnProfile;
	}

	public void setCommentsOnProfile(List<Comment> commentsOnProfile) {
		this.commentsOnProfile = commentsOnProfile;
	}

	public Map<Milestone, Integer> getLeagueMilestones() {
		return leagueMilestones;
	}

	public Map<Milestone, Integer> getOverwatchMilestones() {
		return overwatchMilestones;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return id.equals(user.id) &&
				Objects.equals(name, user.name) &&
				Objects.equals(password, user.password) &&
				Objects.equals(email, user.email) &&
				Objects.equals(commentsSent, user.commentsSent) &&
				Objects.equals(leagueData, user.leagueData) &&
				Objects.equals(overwatchData, user.overwatchData);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, password, email, commentsSent, leagueData, overwatchData);
	}
}
