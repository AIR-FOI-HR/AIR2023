package com.example.database;

import androidx.annotation.NonNull;

public class Offer {
    private String name;
    private String description;
    private String imageurl;
    private String price;
    private String fishClass;
    private String imageurltrophey;
    private String idKorisnika;
    private String offerID;
    private String location;
    private String smallFish;
    private String mediumFish;
    private String largeFish;

    public String getIdKorisnika() {
        return idKorisnika;
    }

    public void setIdKorisnika(String idKorisnika) {
        this.idKorisnika = idKorisnika;
    }

    public Offer(String name, String description, String imageurl, String price, String fishClass, String imageurltrophey) {
        this.name = name;
        this.description = description;
        this.imageurl = imageurl;
        this.price = price;
        this.fishClass = fishClass;
        this.imageurltrophey = imageurltrophey;
    }

    public Offer(String name, String price, String location, String smallFish, String mediumFish, String largeFish, String idKorisnika, int i) {
        this.name = name;
        this.price = price;
        this.description = location;
        this.smallFish = smallFish;
        this.mediumFish = mediumFish;
        this.largeFish = largeFish;
        this.idKorisnika = idKorisnika;
    }

    public Offer(String name, String description, String imageurl, String price, String fishClass, String imageurltrophey, String idKorisnika) {
        this.name = name;
        this.description = description;
        this.imageurl = imageurl;
        this.price = price;
        this.fishClass = fishClass;
        this.imageurltrophey = imageurltrophey;
        this.idKorisnika = idKorisnika;
    }
    public Offer(String name, String description, String imageurl, String price, String fishClass, String imageurltrophey, String idKorisnika, String offerID) {
        this.name = name;
        this.description = description;
        this.imageurl = imageurl;
        this.price = price;
        this.fishClass = fishClass;
        this.imageurltrophey = imageurltrophey;
        this.idKorisnika = idKorisnika;
        this.offerID = offerID;
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

    public String getFishClass() {
        return fishClass;
    }

    public void setFishClass(String fishClass) {
        this.fishClass = fishClass;
    }

    public String getImageurltrophey() {
        return imageurltrophey;
    }

    public void setImageurltrophey(String imageurltrophey) {
        this.imageurltrophey = imageurltrophey;
    }

    public String getImageurl() {
        return imageurl;
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

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Offer(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Offer(String name, String description, String imageurl) {
        this.name = name;
        this.description = description;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
