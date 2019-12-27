package com.example.login;

public class MaladieModelInfo {

    private String maladiename , maladiedegree , maladiedescription ;

    public MaladieModelInfo(String maladiename, String maladiedegree, String maladiedescription) {
        this.maladiename = maladiename;
        this.maladiedegree = maladiedegree;
        this.maladiedescription = maladiedescription;
    }
    public MaladieModelInfo() {
    }

    public String getMaladiename() {
        return maladiename;
    }

    public void setMaladiename(String maladiename) {
        this.maladiename = maladiename;
    }

    public String getMaladiedegree() {
        return maladiedegree;
    }

    public void setMaladiedegree(String maladiedegree) {
        this.maladiedegree = maladiedegree;
    }

    public String getMaladiedescription() {
        return maladiedescription;
    }

    public void setMaladiedescription(String maladiedescription) {
        this.maladiedescription = maladiedescription;
    }
}
