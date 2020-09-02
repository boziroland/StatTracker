package org.github.boziroland.entities.apiEntities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.mutable.MutableInt;

import javax.persistence.*;
import java.time.Duration;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OWPlayer {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ID_SEQUENCE")
	@SequenceGenerator(name="ID_SEQUENCE", sequenceName="ID_SEQUENCE", allocationSize=1)
	private Integer id;

	private String username;

	@Column(name = "playerLevel")
	private MutableInt level;
	private String portrait;

	@JsonProperty(value = "private")
	private Boolean isPrivate;

	private MutableInt gamesQuickplayWon;

	private MutableInt gamesCompetitiveWon;
	private MutableInt gamesCompetitiveLost;
	private MutableInt gamesCompetitiveDraw;
	private MutableInt gamesCompetitivePlayed;
	private MutableInt gamesCompetitiveWinRate;

	private Duration playtimeQuickplay;
	private Duration playtimeCompetitive;

	private MutableInt competitiveTankRank;
	private MutableInt competitiveDamageRank;
	private MutableInt competitiveSupportRank;

	@JsonProperty(value = "level")
	public void setLevel(Integer level){
		this.level = makeMutableInt(level);
	}

	@JsonProperty(value = "competitive")
	public void setCompetitive(Map<String, Object> competitive) {
		this.competitiveTankRank = makeMutableInt(((Map<String, Integer>) competitive.get("tank")).get("rank"));
		this.competitiveDamageRank = makeMutableInt(((Map<String, Integer>) competitive.get("damage")).get("rank"));
		this.competitiveSupportRank = makeMutableInt(((Map<String, Integer>) competitive.get("support")).get("rank"));
	}

	@JsonProperty(value = "games")
	public void setGames(Map<String, Object> games) {
		this.gamesQuickplayWon = makeMutableInt(((Map<String, Integer>) games.get("quickplay")).get("won"));
		this.gamesCompetitiveWon = makeMutableInt(((Map<String, Integer>) games.get("competitive")).get("won"));
		this.gamesCompetitiveLost = makeMutableInt(((Map<String, Integer>) games.get("competitive")).get("lost"));
		this.gamesCompetitiveDraw = makeMutableInt(((Map<String, Integer>) games.get("competitive")).get("draw"));
		this.gamesCompetitivePlayed = makeMutableInt(((Map<String, Integer>) games.get("competitive")).get("played"));
		this.gamesCompetitiveWinRate = makeMutableInt(((Map<String, Double>) games.get("competitive")).get("win_rate"));
	}

	@JsonProperty(value = "playtime")
	public void setPlaytime(Map<String, String> playtime) {
		String[] quickplayTime = playtime.get("quickplay").split(":");
		String[] competitiveTime = playtime.get("competitive").split(":");

		playtimeQuickplay = timePlayed(quickplayTime);
		playtimeCompetitive = timePlayed(competitiveTime);
	}

	private Duration timePlayed(String[] timeAsStringArr) {
		Duration ret = Duration.ZERO;
		switch (timeAsStringArr.length) {
			case 1 -> ret = ret.plusSeconds(Integer.parseInt(timeAsStringArr[0]));
			case 2 -> {
				ret = ret.plusMinutes(Integer.parseInt(timeAsStringArr[0]));
				ret = ret.plusSeconds(Integer.parseInt(timeAsStringArr[1]));
			}
			case 3 -> {
				ret = ret.plusHours(Integer.parseInt(timeAsStringArr[0]));
				ret = ret.plusMinutes(Integer.parseInt(timeAsStringArr[1]));
				ret = ret.plusSeconds(Integer.parseInt(timeAsStringArr[2]));
			}
		}

		return ret;
	}

	private MutableInt makeMutableInt(Integer number){
		if(number == null)
			return new MutableInt(-1);

		return new MutableInt(number);
	}

	private MutableInt makeMutableInt(Double number){
		if(number == null)
			return new MutableInt(-1);

		return new MutableInt(number.intValue());
	}

}
