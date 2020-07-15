package org.github.boziroland.entities;

import java.util.Objects;

public class Comment {

    private User sender;
    private String message;
    private String ID;

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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return ID == comment.ID &&
                sender.equals(comment.sender) &&
                message.equals(comment.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, message, ID);
    }
}
