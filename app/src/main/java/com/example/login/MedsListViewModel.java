package com.example.login;

public class MedsListViewModel {
    private String Medname , Meddescription , MedDureeTrait , Aconsommer;
    private boolean AvantPtitDej , AvantDej , AvantDiner , ApresPtitDej , ApresDej , ApresDinner;

    public MedsListViewModel()
    {}

    public MedsListViewModel(String medname, String meddescription, String MedDureeTrait , String aconsommer, boolean avantPtitDej, boolean avantDej, boolean avantDiner, boolean apresPtitDej, boolean apresDej, boolean apresDinner) {
        Medname = medname;
        Meddescription = meddescription;
        MedDureeTrait = MedDureeTrait;
        Aconsommer = aconsommer;
        AvantPtitDej = avantPtitDej;
        AvantDej = avantDej;
        AvantDiner = avantDiner;
        ApresPtitDej = apresPtitDej;
        ApresDej = apresDej;
        ApresDinner = apresDinner;
    }

    public String getMedname() {
        return Medname;
    }

    public void setMedname(String medname) {
        Medname = medname;
    }

    public String getMeddescription() {
        return Meddescription;
    }

    public void setMeddescription(String meddescription) {
        Meddescription = meddescription;
    }

    public String getAconsommer() {
        return Aconsommer;
    }

    public void setAconsommer(String aconsommer) {
        Aconsommer = aconsommer;
    }

    public boolean isAvantPtitDej() {
        return AvantPtitDej;
    }

    public void setAvantPtitDej(boolean avantPtitDej) {
        AvantPtitDej = avantPtitDej;
    }

    public boolean isAvantDej() {
        return AvantDej;
    }

    public void setAvantDej(boolean avantDej) {
        AvantDej = avantDej;
    }

    public boolean isAvantDiner() {
        return AvantDiner;
    }

    public void setAvantDiner(boolean avantDiner) {
        AvantDiner = avantDiner;
    }

    public boolean isApresPtitDej() {
        return ApresPtitDej;
    }

    public void setApresPtitDej(boolean apresPtitDej) {
        ApresPtitDej = apresPtitDej;
    }

    public boolean isApresDej() {
        return ApresDej;
    }

    public void setApresDej(boolean apresDej) {
        ApresDej = apresDej;
    }

    public boolean isApresDinner() {
        return ApresDinner;
    }

    public void setApresDinner(boolean apresDinner) {
        ApresDinner = apresDinner;
    }

    public String getMedDureeTrait() {
        return MedDureeTrait;
    }

    public void setMedDureeTrait(String medDureeTrait) {
        MedDureeTrait = medDureeTrait;
    }
}
