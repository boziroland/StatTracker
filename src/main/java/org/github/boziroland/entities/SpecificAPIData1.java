package org.github.boziroland.entities;

import java.util.Objects;

public class SpecificAPIData1 extends GeneralAPIData {

    private String token;
    private String username;
    private String userID;

    public SpecificAPIData1() {
    }

    public SpecificAPIData1(String token, String username, String userID) {
        this.token = token;
        this.username = username;
        this.userID = userID;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getUserID() {
        return userID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecificAPIData1 that = (SpecificAPIData1) o;
        return token.equals(that.token) &&
                username.equals(that.username) &&
                userID.equals(that.userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, username, userID);
    }
}
