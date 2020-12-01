package com.example.database;

import com.google.firebase.Timestamp;

public class Rezervation {
    private String IDponude;
    private Timestamp date;
    private int kolicina;
    private String kupacID;
    private String rezervationID;
    private String statusID;

    public Rezervation(String IDponude, Timestamp date, int kolicina, String kupacID, String rezervationID, String statusID) {
        this.IDponude = IDponude;
        this.date = date;
        this.kolicina = kolicina;
        this.kupacID = kupacID;
        this.rezervationID = rezervationID;
        this.statusID = statusID;
    }

    public String getIDponude() {
        return IDponude;
    }

    public void setIDponude(String IDponude) {
        this.IDponude = IDponude;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public String getKupacID() {
        return kupacID;
    }

    public void setKupacID(String kupacID) {
        this.kupacID = kupacID;
    }

    public String getRezervationID() {
        return rezervationID;
    }

    public void setRezervationID(String rezervationID) {
        this.rezervationID = rezervationID;
    }

    public String getStatusID() {
        return statusID;
    }

    public void setStatusID(String statusID) {
        this.statusID = statusID;
    }


}
