package org.github.boziroland.entities;

import java.util.List;
import java.util.Objects;

public class User {

    private final int id;
    private String name;
    private String password;
    private String email;
    private MilestoneHolder milestones;
    private List<Comment> commentsOnProfile;
    private List<Comment> commentsSent;
    private String leagueID;
    private String gameName2;

    private LeagueData leagueData;
    private SpecificAPIData1 specificPlayer;

    public User(int id, String name, String password, String email, MilestoneHolder milestones, List<Comment> commentsOnProfile, List<Comment> commentsSent, String leagueID, String gameName2) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.milestones = milestones;
        this.commentsOnProfile = commentsOnProfile;
        this.commentsSent = commentsSent;
        this.leagueID = leagueID;
        this.gameName2 = gameName2;

        leagueData = new LeagueData();
        specificPlayer = new SpecificAPIData1();
    }

    public int getId() {
        return id;
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

    public List<Comment> getCommentsSent() {
        return commentsSent;
    }

    public void setCommentsSent(List<Comment> commentsSent) {
        this.commentsSent = commentsSent;
    }

    public LeagueData getLeagueData() {
        return leagueData;
    }

    public void setLeagueData(LeagueData leagueData) {
        this.leagueData = leagueData;
    }

    public SpecificAPIData1 getSpecificPlayer() {
        return specificPlayer;
    }

    public void setSpecificPlayer(SpecificAPIData1 specificPlayer) {
        this.specificPlayer = specificPlayer;
    }

    public String getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(String leagueID) {
        this.leagueID = leagueID;
    }

    public String getGameName2() {
        return gameName2;
    }

    public void setGameName2(String gameName2) {
        this.gameName2 = gameName2;
    }

    public List<Comment> getCommentsOnProfile() {
        return commentsOnProfile;
    }

    public void setCommentsOnProfile(List<Comment> commentsOnProfile) {
        this.commentsOnProfile = commentsOnProfile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(name, user.name) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(milestones, user.milestones) &&
                Objects.equals(commentsSent, user.commentsSent) &&
                Objects.equals(leagueData, user.leagueData) &&
                Objects.equals(specificPlayer, user.specificPlayer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, email, milestones, commentsSent, leagueData, specificPlayer);
    }
}
