package com.example.diplomaanahit.security;

import com.example.diplomaanahit.enums.UserType;

import java.time.LocalDateTime;

/**
 * Created by garik on 5/26/17
 */
public class TokenUser {

    private String username;
    private UserType userType;
    private LocalDateTime dateTime;

    public TokenUser(String username, UserType userType, LocalDateTime dateTime) {
        this.username = username;
        this.userType = userType;
        this.dateTime = dateTime;
    }

    public boolean expired(){
        return LocalDateTime.now().isAfter(dateTime);
    }

    public boolean validUserType(UserType userType){
        return this.userType != null && this.userType == userType;
    }

    public boolean validToken(UserType userType){
        return !expired() && username != null && validUserType(userType);
    }

    public String getUsername() {
        return username;
    }

    public UserType getUserType() {
        return userType;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}

