package org.github.boziroland.entities;

import java.util.List;
import java.util.Objects;

public class User {

    private final int ID;
    private String name;
    private String password;
    private String email;
    private MilestoneHolder milestones;
    private List<Comment> comments;
    private String leagueName;
    private String gameName2;

    private LeagueDataSource leagueData;
    private SpecificAPIDataSource1 specificPlayer;

    public User(int ID, String name, String password, String email, MilestoneHolder milestones, List<Comment> comments, String leagueName, String gameName2) {
        this.ID = ID;
        this.name = name;
        this.password = password;
        this.email = email;
        this.milestones = milestones;
        this.comments = comments;
        this.leagueName = leagueName;
        this.gameName2 = gameName2;
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

    public MilestoneHolder getMilestones() {
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

    public void setMilestones(MilestoneHolder milestones) {
        this.milestones = milestones;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public LeagueDataSource getLeagueData() {
        return leagueData;
    }

    public void setLeagueData(LeagueDataSource leagueData) {
        this.leagueData = leagueData;
    }

    public SpecificAPIDataSource1 getSpecificPlayer() {
        return specificPlayer;
    }

    public void setSpecificPlayer(SpecificAPIDataSource1 specificPlayer) {
        this.specificPlayer = specificPlayer;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getGameName2() {
        return gameName2;
    }

    public void setGameName2(String gameName2) {
        this.gameName2 = gameName2;
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
                Objects.equals(leagueData, user.leagueData) &&
                Objects.equals(specificPlayer, user.specificPlayer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name, password, email, milestones, comments, leagueData, specificPlayer);
    }
}
