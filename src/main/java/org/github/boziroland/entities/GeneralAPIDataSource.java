package org.github.boziroland.entities;

import java.util.Objects;

public abstract class GeneralAPIDataSource {

    protected String URL;
    protected String userName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneralAPIDataSource that = (GeneralAPIDataSource) o;
        return URL.equals(that.URL) &&
                userName.equals(that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(URL, userName);
    }
}
