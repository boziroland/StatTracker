package org.github.boziroland.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntegerEntity {

	@Id
	@GeneratedValue
	Integer id;

	@Basic
	Integer value;

	public IntegerEntity(Integer i) {
		value = i;
	}
}
