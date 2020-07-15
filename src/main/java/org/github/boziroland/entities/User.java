package org.github.boziroland.entities;

import java.util.List;
import java.util.Objects;

public class User {

    private final int ID;
    private String name;
    private String password;
    private String email;
    private List<Milestone> milestones;
    private List<Comment> comments;

    private LeaguePlayer leaguePlayer;
    private SpecificAPIData1 specificPlayer;

    public void requestInformationGeneralAPIData(GeneralAPIData gap){
        //TODO
    }

    public void sendEmail(){
        //TODO
    }

    public User(int id) {
        ID = id;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public List<Milestone> getMilestones() {
        return milestones;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMilestones(List<Milestone> milestones) {
        this.milestones = milestones;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public LeaguePlayer getLeaguePlayer() {
        return leaguePlayer;
    }

    public void setLeaguePlayer(LeaguePlayer leaguePlayer) {
        this.leaguePlayer = leaguePlayer;
    }

    public SpecificAPIData1 getSpecificPlayer() {
        return specificPlayer;
    }

    public void setSpecificPlayer(SpecificAPIData1 specificPlayer) {
        this.specificPlayer = specificPlayer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return ID == user.ID &&
                Objects.equals(name, user.name) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(milestones, user.milestones) &&
                Objects.equals(comments, user.comments) &&
                Objects.equals(leaguePlayer, user.leaguePlayer) &&
                Objects.equals(specificPlayer, user.specificPlayer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name, password, email, milestones, comments, leaguePlayer, specificPlayer);
    }
}