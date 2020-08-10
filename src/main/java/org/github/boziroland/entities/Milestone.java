package org.github.boziroland.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Milestone {

	public enum Game {
		LEAGUE, OVERWATCH
	}

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ID_SEQUENCE")
	@SequenceGenerator(name="ID_SEQUENCE", sequenceName="ID_SEQUENCE", allocationSize=1)
	private Integer id;

	private String name;

	private String description;

	private Integer requirement;

	@Enumerated
	private Game game;

	public Milestone(String name, String description, int requirement, Game game) {
		this.name = name;
		this.description = description;
		this.requirement = requirement;
		this.game = game;
	}
}

