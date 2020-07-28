package org.github.boziroland.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Milestone {

	public enum Game {
		LEAGUE, OVERWATCH
	}

	@Id
	@GeneratedValue
	private Integer id;

	private String name;

	private String description;

	private int requirement;

	@Enumerated
	private Game game;

	private boolean doneAlready;

	public Milestone(String name, String description, int requirement, Game game) {
		this.name = name;
		this.description = description;
		this.requirement = requirement;
		this.game = game;
		this.doneAlready = false;
	}
}

