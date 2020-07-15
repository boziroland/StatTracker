package org.github.boziroland.entities;

import java.util.Objects;

public class Milestone {

    private String name;
    private String description;
    private int requirement;

    public Milestone(String name, String description, int requirement) {
        this.name = name;
        this.description = description;
        this.requirement = requirement;
    }

    public boolean checkAchievement(int userScore){
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRequirement() {
        return requirement;
    }

    public void setRequirement(int requirement) {
        this.requirement = requirement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Milestone milestone = (Milestone) o;
        return requirement == milestone.requirement &&
                name.equals(milestone.name) &&
                description.equals(milestone.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, requirement);
    }

}

