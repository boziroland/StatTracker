package org.github.boziroland.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.github.boziroland.Constants;
import org.github.boziroland.entities.apiEntities.MyMatchReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "RUser")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQUENCE")
	@SequenceGenerator(name = "ID_SEQUENCE", sequenceName = "ID_SEQUENCE", allocationSize = 1)
	private Integer id;
	private String name;
	private String password;
	private String email;

	private Boolean profilePublic;
	private Boolean sendEmails;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "MilestonePointJoinTable")
	@MapKeyColumn(name = "Milestone")
	@Fetch(value = FetchMode.SELECT)
	@JsonIgnore
	Map<String, Integer> milestoneNameUserPointMap = new HashMap<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	private List<Comment> commentsOnProfile = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	private List<Comment> commentsSent = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@Fetch(value = FetchMode.SELECT)
	@JsonIgnore
	private List<LeagueData> leagueDataList = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@Fetch(value = FetchMode.SELECT)
	@JsonIgnore
	private List<OverwatchData> overwatchDataList = new ArrayList<>();

	public User(String name, String password, String email, String leagueID, String overwatchID) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.profilePublic = Constants.PROFILE_PUBLIC_DEFAULT_VALUE;
		this.sendEmails = Constants.SEND_EMAIL_DEFAULT_VALUE;

		if (leagueID != null) {
			leagueDataList.add(new LeagueData());
			leagueDataList.get(0).setUsername(leagueID);
		}

		if (overwatchID != null) {
			overwatchDataList.add(new OverwatchData());
			overwatchDataList.get(0).setUsername(overwatchID);
		}
	}

	public User(String name, String password, String email, List<Comment> commentsOnProfile, List<Comment> commentsSent, String leagueID, String overwatchID) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.commentsOnProfile = commentsOnProfile;
		this.commentsSent = commentsSent;
		this.profilePublic = Constants.PROFILE_PUBLIC_DEFAULT_VALUE;
		this.sendEmails = Constants.SEND_EMAIL_DEFAULT_VALUE;

		if (leagueID != null) {
			leagueDataList.add(new LeagueData());
			leagueDataList.get(0).setUsername(leagueID);
		}

		if (overwatchID != null) {
			overwatchDataList.add(new OverwatchData());
			overwatchDataList.get(0).setUsername(overwatchID);
		}
	}

	public String getLeagueID() {
		return leagueDataList.size() == 0 ? null : leagueDataList.get(0).getUsername();
	}

	public void setLeagueID(String leagueID) {
		if (leagueDataList.size() == 0)
			leagueDataList.add(new LeagueData());
		leagueDataList.get(0).setUsername(leagueID);
	}

	public String getOverwatchID() {
		return overwatchDataList.size() == 0 ? null : overwatchDataList.get(0).getUsername();
	}

	public void setOverwatchID(String overwatchID) {
		if (overwatchDataList.size() == 0)
			overwatchDataList.add(new OverwatchData());
		overwatchDataList.get(0).setUsername(overwatchID);
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

	public boolean hasOverwatchData() {
		return overwatchDataList.size() > 0;
	}

	public boolean hasLeagueData() {
		return leagueDataList.size() > 0;
	}

	public void setLeagueData(LeagueData leagueData) {
		leagueDataList.add(leagueData);
	}

	public LeagueData getLeagueData() {
		if (leagueDataList.size() == 0)
			return null;
		return leagueDataList.get(leagueDataList.size() - 1);
	}

	public List<LeagueData> getLeagueDataList(){
		return leagueDataList;
	}

	public List<Long> getLeagueLevelList(){
		List<Long> ret = new ArrayList<>();
		for(var data : leagueDataList){
			if(data.getPlayer() == null)
				ret.add(null);
			else
				ret.add(data.getPlayer().getSummonerLevel().longValue());
		}

		return ret;
	}

	public List<Long> getLeaguePlayedMatchesList(){
		List<Long> ret = new ArrayList<>();
		ret.add(null);
		for(int i = 1; i < leagueDataList.size(); i++){
			if(leagueDataList.get(i) != null && leagueDataList.get(i - 1) != null){
				var matchesToday = leagueDataList.get(i).getLastTenMatches();
				var matchesYesterday = leagueDataList.get(i - 1).getLastTenMatches();
				if(matchesYesterday.size() > 0) {
					long playedMatchesSinceYesterday = 0;
					for (MyMatchReference myMatchReference : matchesToday) {
						if (!myMatchReference.getId().equals(matchesYesterday.get(0).getId()))
							playedMatchesSinceYesterday++;
					}
					ret.add(playedMatchesSinceYesterday);
				}else{
					ret.add((long) matchesToday.size());
				}
			}else{
				ret.add(null);
			}
		}

		return ret;
	}

	public void setOverwatchData(OverwatchData overwatchData) {
		overwatchDataList.add(overwatchData);
	}

	public OverwatchData getOverwatchData() {
		if (overwatchDataList.size() == 0)
			return null;
		return overwatchDataList.get(overwatchDataList.size() - 1);
	}

	public List<OverwatchData> getOverwatchDataList(){
		return overwatchDataList;
	}

	public List<Long> getOverwatchLevelList(){
		List<Long> ret = new ArrayList<>();
		for(var data : overwatchDataList){
			if(data.getPlayer() == null)
				ret.add(null);
			else
				ret.add(data.getPlayer().getLevel().longValue());
		}

		return ret;
	}

	public List<Long> getOverwatchCompetitiveMatchesList(){
		List<Long> ret = new ArrayList<>();
		for(var data : overwatchDataList){
			if(data.getPlayer() == null)
				ret.add(null);
			else
				ret.add(data.getPlayer().getGamesCompetitivePlayed().longValue());
		}

		return ret;
	}

	public List<Long> getOverwatchCompetitiveMatchesWonList(){
		List<Long> ret = new ArrayList<>();
		for(var data : overwatchDataList){
			if(data.getPlayer() == null)
				ret.add(null);
			else
				ret.add(data.getPlayer().getGamesCompetitiveWon().longValue());
		}

		return ret;
	}

	public List<Long> getOverwatchCompetitiveMatchesLostList(){
		List<Long> ret = new ArrayList<>();
		for(var data : overwatchDataList){
			if(data.getPlayer() == null)
				ret.add(null);
			else
				ret.add(data.getPlayer().getGamesCompetitiveLost().longValue());
		}

		return ret;
	}

	public List<Long> getOverwatchQuickplayMatchesWonList(){
		List<Long> ret = new ArrayList<>();
		for(var data : overwatchDataList){
			if(data.getPlayer() == null)
				ret.add(null);
			else
				ret.add(data.getPlayer().getGamesQuickplayWon().longValue());
		}

		return ret;
	}

	public List<Long> getOverwatchQuickplayPlaytimeList(){
		List<Long> ret = new ArrayList<>();
		for(var data : overwatchDataList){
			if(data.getPlayer() == null)
				ret.add(null);
			else
				ret.add(data.getPlayer().getPlaytimeQuickplay().toSeconds());
		}

		return ret;
	}

	public List<Long> getOverwatchCompetitivePlaytimeList(){
		List<Long> ret = new ArrayList<>();
		for(var data : overwatchDataList){
			if(data.getPlayer() == null)
				ret.add(null);
			else
				ret.add(data.getPlayer().getPlaytimeCompetitive().toSeconds());
		}

		return ret;
	}
}
