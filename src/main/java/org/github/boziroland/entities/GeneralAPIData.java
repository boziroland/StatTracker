package org.github.boziroland.entities;

import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Data
public abstract class GeneralAPIData {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQUENCE")
	@SequenceGenerator(name = "ID_SEQUENCE", sequenceName = "ID_SEQUENCE", allocationSize = 1)
	protected Integer id;

	protected String username;

	public void setUsername(String username) {
		if (username != null) {
			username = username.replace("#", "-");
			this.username = username;
		}
	}
}
