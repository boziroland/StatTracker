package org.github.boziroland.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String password;
    private String email;

    @ElementCollection
    @CollectionTable(name = "LeagueMilestonePointJoinTable")
    @MapKeyColumn(name = "Milestone")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Map<Milestone, Integer> leagueMilestones = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "Game2MilestonePointJoinTable")
    @MapKeyColumn(name = "Milestone")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Map<Milestone, Integer> gameMilestones2 = new HashMap<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Comment> commentsOnProfile;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Comment> commentsSent;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LeagueData leagueData;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private SpecificAPIData1 specificPlayer;

    public User() {
    }

    public User(String name, String password, String email, String leagueID, String gameName2){
        this.name = name;
        this.password = password;
        this.email = email;

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

        initLeagueMilestones();
        initGame2Milestones();
    }

    private void initLeagueMilestones() {
        leagueMilestones.put(new Milestone("teszt", "teszt teszt", 100, Milestone.Game.LEAGUE), 0);
    }

    private void initGame2Milestones() {

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

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Map<Milestone, Integer> getLeagueMilestones() {
        return leagueMilestones;
    }

    public Map<Milestone, Integer> getGameMilestones2() {
        return gameMilestones2;
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
                Objects.equals(commentsSent, user.commentsSent) &&
                Objects.equals(leagueData, user.leagueData) &&
                Objects.equals(specificPlayer, user.specificPlayer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, email, commentsSent, leagueData, specificPlayer);
    }
}
