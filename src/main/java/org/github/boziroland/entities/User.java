package org.github.boziroland.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.mutable.MutableInt;

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
	@CollectionTable(name = "MilestoneNamePointJoinTable")
	@MapKeyColumn(name = "Milestone")
	@JsonIgnore
	Map<String, MutableInt> milestoneNameUserPointMap = new HashMap<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Comment> commentsOnProfile = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Comment> commentsSent = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private LeagueData leagueData;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private OverwatchData overwatchData;

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

		if(leagueID != null) {
			leagueData = new LeagueData();
			leagueData.setUsername(leagueID);
		}

		if (overwatchID != null) {
			overwatchData = new OverwatchData();
			overwatchData.setUsername(overwatchID);
		}
	}

	public String getLeagueID() {
		return leagueData == null ? null : leagueData.getUsername();
	}

	public void setLeagueID(String leagueID) {
		leagueData.setUsername(leagueID);
	}

	public String getOverwatchID() {
		return overwatchData == null ? null : overwatchData.getUsername();
	}

	public void setOverwatchID(String overwatchID) {
		overwatchData.setUsername(overwatchID);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(id, user.id) &&
				Objects.equals(name, user.name) &&
				Objects.equals(password, user.password) &&
				Objects.equals(email, user.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, password, email);
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", password='" + password + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
