package org.github.boziroland.entities;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class SpecificAPIData1 extends GeneralAPIData {

    private String token;

    public SpecificAPIData1() {
    }

    public SpecificAPIData1(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
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
                id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, id);
    }
}
