package org.github.boziroland.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Comment {

	@Id
	@GeneratedValue
	private Integer id;

	@NonNull
	@OneToOne
	private User sender;

	@NonNull
	@OneToOne
	private User receiver;

	@NonNull
	private String message;

	@NonNull
	private LocalDateTime time;

}
