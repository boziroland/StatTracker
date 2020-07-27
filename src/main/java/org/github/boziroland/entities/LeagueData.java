package org.github.boziroland.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import org.github.boziroland.entities.apiEntities.MyMatchReference;
import org.github.boziroland.entities.apiEntities.MySummoner;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//Ezt felhasználva: https://github.com/taycaldwell/riot-api-java

@Entity
public class LeagueData extends GeneralAPIData {

	@OneToOne
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private MySummoner player;

	@OneToMany(cascade = CascadeType.ALL)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private List<MyMatchReference> lastTenMatches;

	public LeagueData() {
	}

	public LeagueData(Summoner player, List<MatchReference> lastTenMatches) {
		setPlayer(player);
		setLastTenMatches(lastTenMatches);
	}

	public MySummoner getPlayer() {
		return player;
	}

	public List<MyMatchReference> getLastTenMatches() {
		return lastTenMatches;
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		LeagueData that = (LeagueData) o;
		return player.equals(that.player) &&
				lastTenMatches.equals(that.lastTenMatches);
	}

	@Override
	public int hashCode() {
		return Objects.hash(player, lastTenMatches);
	}

	public void setId(int id) {
		this.id = id;
	}
}
