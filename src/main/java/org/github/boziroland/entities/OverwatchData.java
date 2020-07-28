package org.github.boziroland.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.github.boziroland.entities.apiEntities.OWPlayer;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OverwatchData extends GeneralAPIData {

	@OneToOne
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	OWPlayer player;

}
