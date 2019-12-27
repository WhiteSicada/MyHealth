package com.example.login;


import java.text.DateFormat;
import java.util.Calendar;

public class UserMaladie {


    private String title , description;
    private String currentDate;


    public UserMaladie(String title, String description) {
        this.title = title;
        this.description = description;
        Calendar calendar = Calendar.getInstance();
        this.currentDate = DateFormat.getDateInstance().format(calendar.getTime());
    }

    public UserMaladie() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
}
