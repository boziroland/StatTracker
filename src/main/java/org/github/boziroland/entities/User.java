package org.github.boziroland.entities;

import java.util.List;

public class User {

    private final int ID;
    private String name;
    private String password;
    private String email;
    private List<Milestone> milestones;
    private List<Comment> comments;
    private CommentSection commentSection;

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

    public CommentSection getCommentSection() {
        return commentSection;
    }

    public void setCommentSection(CommentSection commentSection) {
        this.commentSection = commentSection;
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
}
