package com.example.login;

public class UserProfile {

    public String userAge , userEmail , userName  , userType , userGender;

    public UserProfile(String userAge, String userEmail, String userName, String userType, String userGender) {
        this.userAge = userAge;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userType = userType;
        this.userGender = userGender;
    }

    public UserProfile() {

    }

    public String getUserAge() {
        return userAge;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserName() {
        return userName;
    }


    public String getUserType() {
        return userType;
    }

    public String getUserGender() {
        return userGender;
    }
}
