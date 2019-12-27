package com.example.login;

public class UserMeds {

    private String title , description ;
    private Boolean avantPetitDejeuner , apresPetitDejeuner , avantDejeuner , apresDejeuner , avantDiner , apresDiner ;

    public UserMeds(String title, String description, Boolean avantPetitDejeuner, Boolean apresPetitDejeuner, Boolean avantDejeuner, Boolean apresDejeuner, Boolean avantDiner, Boolean apresDiner) {
        this.title = title;
        this.description = description;
        this.avantPetitDejeuner = avantPetitDejeuner;
        this.apresPetitDejeuner = apresPetitDejeuner;
        this.avantDejeuner = avantDejeuner;
        this.apresDejeuner = apresDejeuner;
        this.avantDiner = avantDiner;
        this.apresDiner = apresDiner;
    }

    public UserMeds() {
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

    public Boolean getAvantPetitDejeuner() {
        return avantPetitDejeuner;
    }

    public void setAvantPetitDejeuner(Boolean avantPetitDejeuner) {
        this.avantPetitDejeuner = avantPetitDejeuner;
    }

    public Boolean getApresPetitDejeuner() {
        return apresPetitDejeuner;
    }

    public void setApresPetitDejeuner(Boolean apresPetitDejeuner) {
        this.apresPetitDejeuner = apresPetitDejeuner;
    }

    public Boolean getAvantDejeuner() {
        return avantDejeuner;
    }

    public void setAvantDejeuner(Boolean avantDejeuner) {
        this.avantDejeuner = avantDejeuner;
    }

    public Boolean getApresDejeuner() {
        return apresDejeuner;
    }

    public void setApresDejeuner(Boolean apresDejeuner) {
        this.apresDejeuner = apresDejeuner;
    }

    public Boolean getAvantDiner() {
        return avantDiner;
    }

    public void setAvantDiner(Boolean avantDiner) {
        this.avantDiner = avantDiner;
    }

    public Boolean getApresDiner() {
        return apresDiner;
    }

    public void setApresDiner(Boolean apresDiner) {
        this.apresDiner = apresDiner;
    }
}
