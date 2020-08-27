package org.github.boziroland.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import org.github.boziroland.entities.apiEntities.MyMatchReference;
import org.github.boziroland.entities.apiEntities.MySummoner;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//Ezt felhasználva: https://github.com/taycaldwell/riot-api-java

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LeagueData extends GeneralAPIData {

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private MySummoner player;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) //MERGE?? ALL??
	@Fetch(value = FetchMode.SELECT)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private List<MyMatchReference> lastTenMatches;

	public LeagueData(String accountName) {
		username = accountName;
	}

	public LeagueData(Summoner player, List<MatchReference> lastTenMatches, String accountName) {
		username = accountName;
		setPlayer(player);
		setLastTenMatches(lastTenMatches);
	}

	public LeagueData(MySummoner player, List<MyMatchReference> lastTenMatches, String accountName) {
		username = accountName;
		setPlayer(player);
		this.lastTenMatches = lastTenMatches;
	}

	@JsonIgnore
	public void setPlayer(MySummoner player) {
		this.player = player;
	}

	public void setPlayer(Summoner player) {
		this.player = new MySummoner(player);
	}

	public void setLastTenMatches(List<MatchReference> lastTenMatches) {

		List<MyMatchReference> myLastThenMatches = new ArrayList<>();
		for (var match : lastTenMatches)
			myLastThenMatches.add(new MyMatchReference(match));

		this.lastTenMatches = myLastThenMatches;
	}

	@Override
	public String toString() {
		return "LeagueData{" +
				"id=" + id +
				", username='" + username + '\'' +
				'}';
	}
}
