package org.github.boziroland.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "profileComments")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SEQUENCE")
	@SequenceGenerator(name = "ID_SEQUENCE", sequenceName = "ID_SEQUENCE", allocationSize = 1)
	private Integer id;

	@NonNull
	@Column(name = "senderId")
	private Integer senderId;

	@NonNull
	@Column(name = "receiverId")
	private Integer receiverId;

	@NonNull
	private String message;

	@NonNull
	@Column(name = "postTime")
	private LocalDateTime time;

}
