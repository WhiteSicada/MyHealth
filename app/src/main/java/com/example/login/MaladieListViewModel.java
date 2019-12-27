package com.example.login;

public class MaladieListViewModel {

    private String maladiename ,maladienum;

    public MaladieListViewModel(String maladiename,String maladienum) {
        this.maladiename = maladiename;
        this.maladienum = maladienum;
    }
    public MaladieListViewModel()
    {}

    public String getMaladiename() {
        return maladiename;
    }

    public void setMaladiename(String maladiename) {
        this.maladiename = maladiename;
    }


    public String toString()
    {
        return "Maladie :" + maladiename;
    }

    public String getMaladienum() {
        return maladienum;
    }

    public void setMaladienum(String maladienum) {
        this.maladienum = maladienum;
    }
}
