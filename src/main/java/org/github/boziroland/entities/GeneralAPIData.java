package org.github.boziroland.entities;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public abstract class GeneralAPIData {

	@Id
	@GeneratedValue
	protected Integer id;

	protected String username;

	public void setUsername(String username) {
		if (username != null) {
			username = username.replace("#", "-");
			this.username = username;
		}
	}
}
