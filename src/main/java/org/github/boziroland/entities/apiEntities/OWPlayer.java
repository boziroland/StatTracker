package org.github.boziroland.entities.apiEntities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Duration;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OWPlayer {

	@Id
	@GeneratedValue
	private Integer id;

	private String username;
	private Integer level;
	private String portrait;

	@JsonProperty(value = "private")
	private Boolean isPrivate;

	private Integer gamesQuickplayWon;

	private Integer gamesCompetitiveWon;
	private Integer gamesCompetitiveLost;
	private Integer gamesCompetitiveDraw;
	private Integer gamesCompetitivePlayed;
	private Integer gamesCompetitiveWinRate;

	private Duration playtimeQuickplay;
	private Duration playtimeCompetitive;

	private Integer competitiveTankRank;
	private Integer competitiveDamageRank;
	private Integer competitiveSupportRank;

	@JsonProperty(value = "competitive")
	public void setCompetitive(Map<String, Object> competitive){
		this.competitiveTankRank = ((Map<String, Integer>)competitive.get("tank")).get("rank");
		this.competitiveDamageRank = ((Map<String, Integer>)competitive.get("damage")).get("rank");
		this.competitiveSupportRank = ((Map<String, Integer>)competitive.get("support")).get("rank");
	}

	@JsonProperty(value = "games")
	public void setGames(Map<String, Object> games){
		this.gamesQuickplayWon = ((Map<String, Integer>)games.get("quickplay")).get("won");
		this.gamesCompetitiveWon = ((Map<String, Integer>)games.get("competitive")).get("won");
		this.gamesCompetitiveLost = ((Map<String, Integer>)games.get("competitive")).get("lost");
		this.gamesCompetitiveDraw = ((Map<String, Integer>)games.get("competitive")).get("draw");
		this.gamesCompetitivePlayed = ((Map<String, Integer>)games.get("competitive")).get("played");
		this.gamesCompetitiveWinRate = ((Map<String, Integer>)games.get("competitive")).get("win_rate");
	}

	@JsonProperty(value = "playtime")
	public void setPlaytime(Map<String, String> playtime) {
		String[] quickplayTime = playtime.get("quickplay").split(":");
		String[] competitiveTime = playtime.get("competitive").split(":");

		playtimeQuickplay = Duration.ZERO;
		playtimeCompetitive = Duration.ZERO;

		this.playtimeQuickplay = playtimeQuickplay.plusHours(Integer.parseInt(quickplayTime[0]));
		this.playtimeQuickplay = playtimeQuickplay.plusMinutes(Integer.parseInt(quickplayTime[1]));
		this.playtimeQuickplay = playtimeQuickplay.plusSeconds(Integer.parseInt(quickplayTime[2]));

		this.playtimeCompetitive = playtimeCompetitive.plusHours(Integer.parseInt(competitiveTime[0]));
		this.playtimeCompetitive = playtimeCompetitive.plusMinutes(Integer.parseInt(competitiveTime[1]));
		//this.playtimeCompetitive = playtimeCompetitive.plusSeconds(Integer.parseInt(competitiveTime[2]));
		//TODO
	}
}
