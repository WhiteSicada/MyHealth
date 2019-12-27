package com.example.login;

public class MedModel {
    private String mednum , medname;

    public MedModel(String mednum, String medname) {
        this.mednum = mednum;
        this.medname = medname;
    }
    public MedModel() {
    }

    public String getMednum() {
        return mednum;
    }

    public void setMednum(String mednum) {
        this.mednum = mednum;
    }

    public String getMedname() {
        return medname;
    }

    public void setMedname(String medname) {
        this.medname = medname;
    }
}
