package org.github.boziroland.entities;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
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

	public Milestone() {
	}

	public Milestone(String name, String description, int requirement, Game game) {
		this.name = name;
		this.description = description;
		this.requirement = requirement;
		this.game = game;
		this.doneAlready = false;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getRequirement() {
		return requirement;
	}

	public Game getGame() {
		return game;
	}

	public boolean isDoneAlready() {
		return doneAlready;
	}

	public void setDoneAlready(boolean doneAlready) {
		this.doneAlready = doneAlready;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Milestone milestone = (Milestone) o;
		return requirement == milestone.requirement &&
				name.equals(milestone.name) &&
				description.equals(milestone.description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, description, requirement);
	}

}

