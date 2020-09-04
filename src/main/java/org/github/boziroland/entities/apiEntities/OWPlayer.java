package org.github.boziroland.entities.apiEntities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OWPlayer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQUENCE")
	@SequenceGenerator(name = "ID_SEQUENCE", sequenceName = "ID_SEQUENCE", allocationSize = 1)
	private Integer id;

	private String username;

	@Column(name = "playerLevel")
	private Integer level;
	private String portrait;

	@JsonProperty(value = "private")
	private Boolean isPrivate;

	private Integer gamesQuickplayWon;

	private Integer gamesCompetitiveWon;
	private Integer gamesCompetitiveLost;
	private Integer gamesCompetitiveDraw;
	private Integer gamesCompetitivePlayed;
	private Double gamesCompetitiveWinRate;

	private Duration playtimeQuickplay;
	private Duration playtimeCompetitive;

	private Integer competitiveTankRank;
	private Integer competitiveDamageRank;
	private Integer competitiveSupportRank;

	@JsonProperty(value = "level")
	public void setLevel(Integer level) {
		this.level = level;
	}

	@JsonProperty(value = "competitive")
	public void setCompetitive(Map<String, Object> competitive) {
		this.competitiveTankRank = ((Map<String, Integer>) competitive.get("tank")).get("rank");
		this.competitiveDamageRank = ((Map<String, Integer>) competitive.get("damage")).get("rank");
		this.competitiveSupportRank = ((Map<String, Integer>) competitive.get("support")).get("rank");
	}

	@JsonProperty(value = "games")
	public void setGames(Map<String, Object> games) {
		this.gamesQuickplayWon = ((Map<String, Integer>) games.get("quickplay")).get("won");
		this.gamesCompetitiveWon = ((Map<String, Integer>) games.get("competitive")).get("won");
		this.gamesCompetitiveLost = ((Map<String, Integer>) games.get("competitive")).get("lost");
		this.gamesCompetitiveDraw = ((Map<String, Integer>) games.get("competitive")).get("draw");
		this.gamesCompetitivePlayed = ((Map<String, Integer>) games.get("competitive")).get("played");
		this.gamesCompetitiveWinRate = ((Map<String, Double>) games.get("competitive")).get("win_rate");
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
}
