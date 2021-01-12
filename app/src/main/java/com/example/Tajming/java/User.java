package com.example.Tajming.java;

import com.example.Tajming.dao.DAOuser;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String userID;
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Map<String, Object> user;

    public User(){
        user = new HashMap<String, Object>();
    }

    public User(String username, String fullName, String email, String phoneNumber){
        user = new HashMap<String, Object>();
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(){
        userID = DAOuser.getInstance().getUserID();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public Map<String, Object> getHashMap(){
        user.put("fullName", fullName);
        user.put("email", email);
        user.put("username", username);
        user.put("phoneNumber", phoneNumber);
        return user;
    }
}
