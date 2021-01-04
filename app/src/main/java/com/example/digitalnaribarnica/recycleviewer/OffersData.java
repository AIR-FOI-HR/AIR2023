package com.example.digitalnaribarnica.recycleviewer;

import androidx.annotation.NonNull;

public class OffersData {
    private String name;
    private String location;
    private String imageurl;
    private String price;
    private String idKorisnika;
    private String offerID;
    private String smallFish;
    private String mediumFish;
    private String largeFish;
    private String status;

    public String getIdKorisnika() {
        return idKorisnika;
    }

    public void setIdKorisnika(String idKorisnika) {
        this.idKorisnika = idKorisnika;
    }

    public OffersData (String name, String location, String imageurl, String price, String idKorisnika, String smallFish, String mediumFish, String largeFish, int i) {
        this.name = name;
        this.location = location;
        this.imageurl = imageurl;
        this.price = price;
        this.idKorisnika = idKorisnika;
        this.smallFish = smallFish;
        this.mediumFish = mediumFish;
        this.largeFish = largeFish;
        this.status = "Aktivna";
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSmallFish() {
        return smallFish;
    }

    public void setSmallFish(String smallFish) {
        this.smallFish = smallFish;
    }

    public String getMediumFish() {
        return mediumFish;
    }

    public void setMediumFish(String mediumFish) {
        this.mediumFish = mediumFish;
    }

    public String getLargeFish() {
        return largeFish;
    }

    public void setLargeFish(String largeFish) {
        this.largeFish = largeFish;
    }

    public String getOfferID() {
        return offerID;
    }

    public void setOfferID(String offerID) {
        this.offerID = offerID;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OffersData(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public OffersData(String name, String location, String imageurl) {
        this.name = name;
        this.location = location;
        this.imageurl = imageurl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}