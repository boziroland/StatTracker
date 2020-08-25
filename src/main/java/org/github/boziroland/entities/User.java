package org.github.boziroland.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.mutable.MutableInt;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "RUser")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ID_SEQUENCE")
	@SequenceGenerator(name="ID_SEQUENCE", sequenceName="ID_SEQUENCE", allocationSize=1)
	private Integer id;
	private String name;
	private String password;
	private String email;

	private Boolean profilePublic;
	private Boolean sendEmails;

	@ElementCollection
	@CollectionTable(name = "MilestonePointJoinTable")
	@MapKeyColumn(name = "Milestone")
	@JsonIgnore
	Map<String, MutableInt> milestoneNameUserPointMap = new HashMap<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	private List<Comment> commentsOnProfile = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	private List<Comment> commentsSent = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private LeagueData leagueData;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private OverwatchData overwatchData;

	public User(String name, String password, String email, String leagueID, String overwatchID) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.profilePublic = true;
		this.sendEmails = true;

		leagueData.setUsername(leagueID);

		overwatchData.setUsername(overwatchID);
	}

	public User(String name, String password, String email, List<Comment> commentsOnProfile, List<Comment> commentsSent, String leagueID, String overwatchID) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.commentsOnProfile = commentsOnProfile;
		this.commentsSent = commentsSent;
		this.profilePublic = true;
		this.sendEmails = true;

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
		if(leagueData == null)
			leagueData = new LeagueData();
		leagueData.setUsername(leagueID);
	}

	public String getOverwatchID() {
		return overwatchData == null ? null : overwatchData.getUsername();
	}

	public void setOverwatchID(String overwatchID) {
		if(overwatchData == null)
			overwatchData = new OverwatchData();
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

	public boolean hasOverwatchData(){
		return overwatchData != null;
	}

	public boolean hasLeagueData(){
		return leagueData != null;
	}
}
