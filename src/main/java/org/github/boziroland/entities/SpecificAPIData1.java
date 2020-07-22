package org.github.boziroland.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class SpecificAPIData1 extends GeneralAPIData {

    @Id
    private int id;
    private String token;
    private String username;

    public SpecificAPIData1() {
    }

    public SpecificAPIData1(String token, String username, int id) {
        this.token = token;
        this.username = username;
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecificAPIData1 that = (SpecificAPIData1) o;
        return token.equals(that.token) &&
                username.equals(that.username) &&
                id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, username, id);
    }
}
