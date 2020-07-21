package org.github.boziroland.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Comment {

    private LocalDateTime time;
    private User sender;
    private User receiver;
    private String message;
    private int id;

    public Comment(int id, User sender, User receiver, String message, LocalDateTime time) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.time = time;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id &&
                sender.equals(comment.sender) &&
                message.equals(comment.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, message, id);
    }
}
