package org.github.boziroland.entities;

import java.util.Objects;

public abstract class GeneralAPIData {

    protected String URL;
    protected String userName;

    public abstract String retrieveData();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneralAPIData that = (GeneralAPIData) o;
        return URL.equals(that.URL) &&
                userName.equals(that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(URL, userName);
    }
}
