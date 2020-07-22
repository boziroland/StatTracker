package org.github.boziroland.entities;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@RequiredArgsConstructor
public class Comment {

    @Id
    @org.springframework.lang.NonNull
    private int id;

    @NonNull
    private User sender;

    @NonNull
    private User receiver;

    @NonNull
    private String message;

    @NonNull
    private LocalDateTime time;

}
