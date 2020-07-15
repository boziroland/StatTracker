package org.github.boziroland.entities;

import java.util.List;
import java.util.Objects;

public class CommentSection {

    private User user;
    private List<Comment> messages;

    public CommentSection(User user, List<Comment> messages) {
        this.user = user;
        this.messages = messages;
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
