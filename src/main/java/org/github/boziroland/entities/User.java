package org.github.boziroland.entities;

import java.util.List;

public class User {

    private final int ID;
    private String name;
    private String password;
    private String email;
    private List<Milestone> milestones;
    private List<Comment> comments;

    private LeaguePlayer leaguePlayer;
    private SpecificAPIData1 specificPlayer;

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

    public void requestInformationGeneralAPIData(GeneralAPIData gap){
        //TODO
    }

    public void sendEmail(){
        //TODO
    }

}
