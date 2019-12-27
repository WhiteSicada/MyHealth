package com.example.login;

public class ConsultationListViewModel  {


    private String nameconsultation ,dateconsultation;

    public ConsultationListViewModel(String nameconsultation, String dateconsultation) {
        this.nameconsultation = nameconsultation;
        this.dateconsultation = dateconsultation;
    }

    public ConsultationListViewModel()
    {}

    public String getNameconsultation() {
        return nameconsultation;
    }

    public void setNameconsultation(String nameconsultation) {
        this.nameconsultation = nameconsultation;
    }

    public String getDate() {
        return dateconsultation;
    }

    public void setDate(String date) {
        this.dateconsultation = date;
    }

    public String toString()
    {
        return "nameconsultation" + nameconsultation + "date " + dateconsultation ;
    }
}
