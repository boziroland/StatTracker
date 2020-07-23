package org.github.boziroland.entities;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String password;
    private String email;

    @OneToOne
    @Autowired
    private MilestoneHolder milestones;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> commentsOnProfile;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> commentsSent;

    @OneToOne(cascade = CascadeType.ALL)
    private LeagueData leagueData;

    @OneToOne(cascade = CascadeType.ALL)
    private SpecificAPIData1 specificPlayer;

    public User(){}

    public User(String name, String password, String email, MilestoneHolder milestones, List<Comment> commentsOnProfile, List<Comment> commentsSent, String leagueID, String gameName2) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.milestones = milestones;
        this.commentsOnProfile = commentsOnProfile;
        this.commentsSent = commentsSent;

        leagueData = new LeagueData();
        leagueData.setUsername(leagueID);

        specificPlayer = new SpecificAPIData1();
        specificPlayer.setUsername(gameName2);
    }

    public User(String name, String password, String email, List<Comment> commentsOnProfile, List<Comment> commentsSent, String leagueID, String gameName2) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.commentsOnProfile = commentsOnProfile;
        this.commentsSent = commentsSent;

        leagueData = new LeagueData();
        leagueData.setUsername(leagueID);

        specificPlayer = new SpecificAPIData1();
        specificPlayer.setUsername(gameName2);
    }

    public Integer getId() {
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
        return leagueData.getUsername();
    }

    public void setLeagueID(String leagueID) {
        leagueData.setUsername(leagueID);
    }

    public String getGameName2() {
        return specificPlayer.getUsername();
    }

    public void setGameName2(String gameName2) {
        specificPlayer.setUsername(gameName2);
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
