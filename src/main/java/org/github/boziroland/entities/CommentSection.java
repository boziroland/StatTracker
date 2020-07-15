package org.github.boziroland.entities;

import java.util.List;
import java.util.Objects;

public class CommentSection {

    private String id;
    private User user;
    private List<Comment> messages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Comment> getMessages() {
        return messages;
    }

    public void setMessages(List<Comment> messages) {
        this.messages = messages;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentSection that = (CommentSection) o;
        return messages.equals(that.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messages);
    }
}
