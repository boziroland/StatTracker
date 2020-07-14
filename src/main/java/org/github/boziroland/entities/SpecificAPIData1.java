package org.github.boziroland.entities;

public class SpecificAPIData1 extends GeneralAPIData{

    private String token;
    private String username;
    private String userID;

    @Override
    public String retrieveData() {
        return null;
    }

    private void requestAccessToken(){
        //TODO
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getUserID() {
        return userID;
    }
}
