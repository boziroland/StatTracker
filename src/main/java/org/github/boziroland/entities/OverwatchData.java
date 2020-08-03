package org.github.boziroland.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.github.boziroland.entities.apiEntities.OWPlayer;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OverwatchData extends GeneralAPIData {

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	OWPlayer player;

	public OverwatchData(String username){
		this.username = username;
	}

	public OverwatchData(OWPlayer owPlayer, String username) {
		this.username = username;
		this.player = owPlayer;
	}

	@Override
	public String toString() {
		return "OverwatchData{" +
				"id=" + id +
				", username='" + username + '\'' +
				'}';
	}
}
